package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/17.
 */

public class Doctor extends LitePalSupport implements Serializable{
    private int dt_id;
    private String dt_name;
    private String dt_image;
    private String dt_sex;
    private int dt_status;
    private String add_time;
    private int sc_id;

    public int getDt_id() {
        return dt_id;
    }

    public void setDt_id(int dt_id) {
        this.dt_id = dt_id;
    }

    public String getDt_name() {
        return dt_name;
    }

    public void setDt_name(String dt_name) {
        this.dt_name = dt_name;
    }

    public String getDt_image() {
        return dt_image;
    }

    public void setDt_image(String dt_image) {
        this.dt_image = dt_image;
    }

    public String getDt_sex() {
        return dt_sex;
    }

    public void setDt_sex(String dt_sex) {
        this.dt_sex = dt_sex;
    }

    public int getDt_status() {
        return dt_status;
    }

    public void setDt_status(int dt_status) {
        this.dt_status = dt_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getSc_id() {
        return sc_id;
    }

    public void setSc_id(int sc_id) {
        this.sc_id = sc_id;
    }
}
