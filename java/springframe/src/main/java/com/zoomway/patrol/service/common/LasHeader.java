package com.zoomway.patrol.service.common;

import java.util.*;

public class LasHeader {
    String name;
    int majorVersion;
    int minorVersion;
    int dayOfYear;
    int creationYear;

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(long numPoints) {
        this.numPoints = (int)numPoints;
    }

    int numPoints;
    double[] range;
    double disX,disY,disZ;
    //double maxZ,minZ;
    //if creationYear > 0 then the file head is init.
    public LasHeader(String name){
        this.name = name;
        this.creationYear = -1;
        range = new double[6];
        //double minX,maxX,minY,maxY;
    }
    public boolean intersectWith(LasHeader that){
        double dminX = Math.min(this.minX(), that.minX());
        double dmaxX = Math.max(this.maxX(), that.maxX());
        float deltaX = (float) ((dmaxX - dminX) * 1e5);
        float doublX = (float) ((this.disX + that.disX) * 1e5) + 2;
        if( deltaX> doublX)
            return false;

        double dminY = Math.min(this.minY(), that.minY());
        double dmaxY = Math.max(this.maxY(), that.maxY());
        float deltaY = (float) ((dmaxY - dminY) * 1e5);
        float doublY = (float) ((this.disY + that.disY) * 1e5) + 2;
        if( deltaY> doublY)
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
        this.setZRange(range[5],range[4]);
    }
    public double[] getRange(){
        return this.range;
    }
    public String getPolygonStr(){
        StringBuilder sb = new StringBuilder("POLYGON((");
        sb.append(minX()).append(" ").append(minY()).append(",");
        sb.append(minX()).append(" ").append(maxY()).append(",");
        sb.append(maxX()).append(" ").append(maxY()).append(",");
        sb.append(maxX()).append(" ").append(minY()).append(",");
        sb.append(minX()).append(" ").append(minY()).append("))");
        return sb.toString();
    }
    public String getRangeStr(){
        String t = Arrays.toString(range);
        return t.substring(1,t.length()-1);
    }
    public double minX(){return  this.range[0];}
    public double minY(){return  this.range[2];}
    public double maxX(){return  this.range[1];}
    public double maxY(){return  this.range[3];}
    public double minZ(){return  this.range[4];}
    public double maxZ(){return  this.range[5];}

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
    public void setZRange(double min,double max){
        this.range[4] = min;
        this.range[5] = max;
        this.disZ = max - min;
    }

}
