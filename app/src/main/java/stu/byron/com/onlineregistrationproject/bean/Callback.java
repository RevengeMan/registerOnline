package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/17.
 */

public class Callback extends LitePalSupport implements Serializable{
    private int cb_id;
    private String cb_description;
    private int cm_id;
    private String add_time;


    public void setCb_id(int cb_id) {
        this.cb_id = cb_id;
    }

    public String getCb_description() {
        return cb_description;
    }

    public void setCb_description(String cb_description) {
        this.cb_description = cb_description;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
