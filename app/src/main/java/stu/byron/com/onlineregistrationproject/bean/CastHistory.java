package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/10/3.
 */

public class CastHistory extends LitePalSupport implements Serializable {
    private int ch_id;
    private int ch_status;
    private int cm_id;
    private double money;
    private String add_time;

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getCh_id() {
        return ch_id;
    }

    public void setCh_id(int ch_id) {
        this.ch_id = ch_id;
    }

    public int getCh_status() {
        return ch_status;
    }

    public void setCh_status(int ch_status) {
        this.ch_status = ch_status;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
