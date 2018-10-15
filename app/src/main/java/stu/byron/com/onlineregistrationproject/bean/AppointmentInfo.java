package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/25.
 */

public class AppointmentInfo extends LitePalSupport implements Serializable {
    private int at_id;
    private int cm_id;
    private int at_status;
    private String at_time;
    private String add_time;
    private String description;
    private String hp_name;
    private String pt_name;
    private String sc_name;
    private String dt_name;
    private String at_comments;
    private int hp_datamoney;

    public int getHp_datamoney() {
        return hp_datamoney;
    }

    public void setHp_datamoney(int hp_datamoney) {
        this.hp_datamoney = hp_datamoney;
    }

    public String getAt_comments() {
        return at_comments;
    }

    public void setAt_comments(String at_comments) {
        this.at_comments = at_comments;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
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

    public String getAt_time() {
        return at_time;
    }

    public void setAt_time(String at_time) {
        this.at_time = at_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHp_name() {
        return hp_name;
    }

    public void setHp_name(String hp_name) {
        this.hp_name = hp_name;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getDt_name() {
        return dt_name;
    }

    public void setDt_name(String dt_name) {
        this.dt_name = dt_name;
    }

}
