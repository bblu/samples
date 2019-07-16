package com.zoomway.patrol.service;

import com.zoomway.patrol.dao.TowerDAO;
import com.zoomway.patrol.entity.Tower;
import com.zoomway.patrol.service.common.LasHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

@Service
public class FileService {
    @Resource
    private TowerDAO towerDao;

    public List<Tower> getTower(int line){
        return towerDao.getByLine(line);
    }

    private final static Logger logger = LoggerFactory
            .getLogger(FileService.class);
    private static char littleEndian2Char(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getChar();
    }
    private static long  littleEndian2UInt(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getInt()&0x0FFFFFFFF;
    }
    private static Double littleEndian2Double(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getDouble();
    }
    public static boolean isLasFile(String fileName){
        return false;
    }

    public void updateTowerByFile(Integer id, String filePath){
        LasHeader header = FileService.getHeader(filePath);
        towerDao.updateGeometry(id,filePath);
    }

    public static LasHeader getHeader(String fileName) {
        System.out.println("read file:" + fileName);
        File file = new File(fileName);
        InputStream in = null;
        LasHeader head = new LasHeader(fileName);
        try {
            in = new FileInputStream(fileName);
            DataInputStream dis = new DataInputStream(in);

            int byteread = 0;
            byte[] signature = new byte[4];
            byteread = dis.read(signature);
            String sign = new String(signature);
            if(sign.equals("LASF")){
                //System.out.println(" The file signature is LASF");
            }else {
                logger.error(" The upload file "+fileName+" signature is wrong" + sign );
                return null;
            }
            dis.skipBytes(20);
            //File Version
            int major = dis.readUnsignedByte();
            int minor = dis.readUnsignedByte();
            //System.out.println("File Version " + major + "." + minor);
            head.setVersion(major,minor);
            dis.skipBytes(64);
            //byteread = dis.read(signature);
            //File creation Date
            byte[] lt2 = new byte[2];
            byteread = dis.read(lt2);
            char day = littleEndian2Char(lt2);//1800=>0018=>24
            byteread = dis.read(lt2);
            char year =littleEndian2Char(lt2);//E307=>07E3=>2019
            head.setFileDate(year, day);

            byteread = dis.read(lt2);
            int headerSize = littleEndian2Char(lt2);
            byte[] lt4 = new byte[4];
            byteread = dis.read(lt4);
            long offsetPointData = littleEndian2UInt(lt4);
            //Number of Variable Length Records
            dis.skipBytes(4);
            //Point Data Record Format 10[\0A] adds Wave Packets to Point Data Record Format 7.
            byte dataFormat = dis.readByte();
            byteread = dis.read(lt2);
            char dataLength = littleEndian2Char(lt2);
            //Legacy Number of point records     unsigned long     4 bytes
            byteread = dis.read(lt4);
            long numPoints = littleEndian2UInt(lt4);
            head.setNumPoints(numPoints);
            //Legacy Number of points by return  unsigned long[5] 20 bytes
            dis.skipBytes(24);
            //scale and offset factor double 8 bytes for xyz=2x3x8
            dis.skipBytes(48);
            // Range for X and Y
            byte[] lt8 = new byte[8];
            double[] range = new double[6];
            for (int i=0; i<6; i++) {
                byteread = dis.read(lt8);
                range[i] = littleEndian2Double(lt8);
                //System.out.println(range[i]);
            }
            head.setRange(range);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
            return head;
        }
    }
}
