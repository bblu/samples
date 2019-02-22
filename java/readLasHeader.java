import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
class LasHeadInfo {
    String name;
    int majorVersion;
    int minorVersion;
    int dayOfYear;
    int creationYear;
    double[] range;
    double disX,disY;
    //double maxZ,minZ;
    //if creationYear > 0 then the file head is init.
    public LasHeadInfo(String name){
        this.name = name;
        this.creationYear = -1;
        range = new double[4];
        //double minX,maxX,minY,maxY;
    }
    public boolean intersectWith(LasHeadInfo that){
        double dminX = Math.min(this.minX(), that.minX());
        double dmaxX = Math.max(this.maxX(), that.maxX());
        if(dmaxX - dminX > this.disX + that.disX)
            return false;

        double dminY = Math.min(this.minY(), that.minY());
        double dmaxY = Math.max(this.maxY(), that.maxY());
        if(dmaxY - dminY > this.disY + that.disY)
            return false;
        return true;
    }

    public void setVersion(int major,int minor){
        this.majorVersion = major;
        this.minorVersion = minor;
    }

    public void setFileDate(int year,int day){
        this.creationYear = year;
        this.dayOfYear = day;
    }
    /* Max X double 8 bytes *
       Min X double 8 bytes *
       Max Y double 8 bytes *
       Min Y double 8 bytes */
    public void setRange(double[] range){
        this.setXRange(range[1],range[0]);
        this.setYRange(range[3],range[2]);
    }
    public double[] getRange(){
        return this.range;
    }
    public double minX(){return  this.range[0];}
    public double minY(){return  this.range[2];}
    public double maxX(){return  this.range[1];}
    public double maxY(){return  this.range[3];}

    //public double disX(){return this.range[1] - this.range[0];}

    public void setXRange(double min,double max){
        this.range[0] = min;
        this.range[1] = max;
        this.disX = max - min;
    }
    public void setYRange(double min,double max){
        this.range[2] = min;
        this.range[3] = max;
        this.disY = max - min;
    }

}
public class LasReader {

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

    public static void readLasInDir(String dir){
        File dot = new File(dir);
        //the tower pointCloud fileName begin with tower and the same with line and dtm
        LinkedList<LasHeadInfo> towers = new LinkedList<LasHeadInfo>();
        LinkedList<LasHeadInfo> lines = new LinkedList<LasHeadInfo>();
        LinkedList<LasHeadInfo> dtms = new LinkedList<LasHeadInfo>();

        Map<String,LinkedList<LasHeadInfo>> info = new HashMap<String, LinkedList<LasHeadInfo>>();
        info.put("d", dtms);
        info.put("l",lines);
        info.put("t",towers);

        File[] files = dot.listFiles();
        for(File f: files){
            info.get(f.getName().substring(0,1)).add(getFileInfo(f.getAbsolutePath()));
        }

/* local Test Data in case service not work
        String[] towerNames = {
                "tower3-1#.las",
                "tower3-2#.las",
                "tower3-3#.las",
                "tower3-4#.las",
                "tower3-5#.las"
        };
        String[] lineNames = {
                "line3-1.las",
                "line3-2.las",
                "line3-3.las",
                "line3-4.las"};
        String[] dtmNames = {
                "dtm3-1.las",
                "dtm3-2.las",
                "dtm3-3.las",
                "dtm3-4.las"};

        //LasHeadInfo[] towers = new LasHeadInfo[5];
        for (String tower:towerNames) {
            towers.add(getFileInfo(dir + File.separator + tower));
        }
        for (String line:lineNames) {
            lines.add(getFileInfo(dir + File.separator + line));
        }

        for (String dtm:dtmNames) {
            dtms.add(getFileInfo(dir + File.separator + dtm));
        }*/


        for (LasHeadInfo t:towers) {
            for(LasHeadInfo l: lines){
                if (t.intersetWith(l)){
                    System.out.println(t.name + " - link to -> " + l.name);
                }
            }
            for(LasHeadInfo d: dtms){
                if (t.intersetWith(d)){
                    System.out.println(t.name + " - link to -> " + d.name);
                }
            }
        }

    }



    public static LasHeadInfo getFileInfo(String fileName) {
        System.out.println("read file:" + fileName);
        File file = new File(fileName);
        InputStream in = null;
        LasHeadInfo head = new LasHeadInfo(fileName);
        try {
            in = new FileInputStream(fileName);
            DataInputStream dis = new DataInputStream(in);

            int byteread = 0;
            byte[] signature = new byte[4];
            byteread = dis.read(signature);
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
            System.out.println("File Version " + major + "." + minor);
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
            //Legacy Number of points by return  unsigned long[5] 20 bytes
            dis.skipBytes(24);
            //scale and offset factor double 8 bytes for xyz=2x3x8
            dis.skipBytes(48);
            // Range for X and Y
            byte[] lt8 = new byte[8];
            double[] range = new double[4];
            for (int i=0; i<4; i++) {
                byteread = dis.read(lt8);
                range[i] = littleEndian2Double(lt8);
                System.out.println(range[i]);
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
