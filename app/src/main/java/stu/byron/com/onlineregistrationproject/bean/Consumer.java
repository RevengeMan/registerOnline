package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/16.
 */

public class Consumer extends LitePalSupport implements Serializable {
    private int cm_id;
    private byte[] cm_image;
    private String cm_nickname;
    private String cm_sex;
    private String cm_realname;
    private String cm_phone;
    private String cm_password;
    private String cm_idnumber;
    private String add_time;
    private double cm_count;

    public byte[] getCm_image() {
        return cm_image;
    }

    public void setCm_image(byte[] cm_image) {
        this.cm_image = cm_image;
    }

    public double getCm_count() {
        return cm_count;
    }

    public void setCm_count(double cm_count) {
        this.cm_count = cm_count;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }

    public String getNickname() {
        return cm_nickname;
    }

    public void setNickname(String nickname) {
        this.cm_nickname = nickname;
    }

    public String getSex() {
        return cm_sex;
    }

    public void setSex(String sex) {
        this.cm_sex = sex;
    }

    public String getCm_realname() {
        return cm_realname;
    }

    public void setCm_realname(String cm_realname) {
        this.cm_realname = cm_realname;
    }

    public String getCm_phone() {
        return cm_phone;
    }

    public void setCm_phone(String cm_phone) {
        this.cm_phone = cm_phone;
    }

    public String getCm_password() {
        return cm_password;
    }

    public void setCm_password(String cm_password) {
        this.cm_password = cm_password;
    }

    public String getCm_idnumber() {
        return cm_idnumber;
    }

    public void setCm_idnumber(String cm_idnumber) {
        this.cm_idnumber = cm_idnumber;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
