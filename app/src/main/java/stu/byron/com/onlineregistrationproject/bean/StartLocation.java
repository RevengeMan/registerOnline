package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/27.
 */

public class StartLocation extends LitePalSupport implements Serializable {
    private String name;
    private double longitude;
    private double latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
