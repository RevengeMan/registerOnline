package stu.byron.com.onlineregistrationproject.bean;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Byron on 2018/9/17.
 */

public class Appointment extends LitePalSupport implements Serializable{
    private int at_id;
    private String at_time;
    private int at_status;
    private String add_time;
    private String description;
    private int hp_id;
    private int pt_id;
    private int sc_id;
    private int dt_id;

    public String getAt_time() {
        return at_time;
    }

    public void setAt_time(String at_time) {
        this.at_time = at_time;
    }

    public int getHp_id() {
        return hp_id;
    }

    public void setHp_id(int hp_id) {
        this.hp_id = hp_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAt_id() {
        return at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
    }

    public int getAt_status() {
        return at_status;
    }

    public void setAt_status(int at_status) {
        this.at_status = at_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public int getSc_id() {
        return sc_id;
    }

    public void setSc_id(int sc_id) {
        this.sc_id = sc_id;
    }

    public int getDt_id() {
        return dt_id;
    }

    public void setDt_id(int dt_id) {
        this.dt_id = dt_id;
    }
}
