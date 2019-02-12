import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Main {

    public static char littleEndian2Char(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getChar();
    }

    public static long  littleEndian2UInt(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getInt()&0x0FFFFFFFF;
    }
    public static Double littleEndian2Double(byte[] input){
        ByteBuffer buffer = ByteBuffer.wrap(input,0,8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return  buffer.getDouble();
    }

    public static void main(String[] args) {
        String fileName = args[0];
        System.out.println("read file:" + fileName);
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节

            in = new FileInputStream(fileName);
            DataInputStream dis = new DataInputStream(in);

            int byteread = 0;
            byte[] signature = new byte[4];
            byteread = dis.read(signature);
            System.out.println(byteread);
            System.out.println(" The file signature must contain the four characters “LASF”");
            String sign = new String(signature);
            if(sign.equals("LASF")){
                System.out.println(" The file signature is LASF");
            }else {
                System.out.println(" The file signature is wrong" + sign);
            }
            dis.skipBytes(20);
            //File Version
            int major = dis.readUnsignedByte();
            int minor = dis.readUnsignedByte();

            dis.skipBytes(64);
            //byteread = dis.read(signature);
            //File creation Date
            byte[] lt2 = new byte[2];
            byteread = dis.read(lt2);
            char day = littleEndian2Char(lt2);//1800=>0018=>24
            byteread = dis.read(lt2);
            char year =littleEndian2Char(lt2);//E307=>07E3=>2019

            byteread = dis.read(lt2);
            int headerSize = littleEndian2Char(lt2);
            byte[] lt4 = new byte[4];
            byteread = dis.read(lt4);
            long offsetPointData = littleEndian2UInt(lt4);
            //Number of Variable Length Records
            dis.skipBytes(4);
            //Point Data Record Format 3
            byte dataFormat = dis.readByte();
            byteread = dis.read(lt2);
            char dataLength = littleEndian2Char(lt2);
            dis.skipBytes(24);
            byte[] lt8 = new byte[8];
            for (int i=0; i<12; i++){
                byteread = dis.read(lt8);
                double d = littleEndian2Double(lt8);
                System.out.println(d);
            }

            byteread = dis.read(lt8);
            double scaleX = littleEndian2Double(lt8);
            byteread = dis.read(lt8);
            double scaleY = littleEndian2Double(lt8);
            byteread = dis.read(lt8);
            double scaleZ = littleEndian2Double(lt8);

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
