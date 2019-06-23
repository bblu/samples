package com.zoomway.patrol.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zw_tower")
public class Tower {
    @Id private int id;
    private int line;
    private float altitude;
    private String center;
    private float height;
    private float width;

    public int getId() {
        return id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public float getSerial() {
        return serial;
    }

    public void setSerial(float serial) {
        this.serial = serial;
    }


    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    private float serial;

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }
}
