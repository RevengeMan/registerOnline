package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/17.
 */

public class Patient extends LitePalSupport implements Serializable{
    private int pt_id;
    private String pt_name;
    private String pt_idnumber;
    private String pt_sex;
    private String add_time;
    private int cm_id;

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public String getPt_idnumber() {
        return pt_idnumber;
    }

    public void setPt_idnumber(String pt_idnumber) {
        this.pt_idnumber = pt_idnumber;
    }

    public String getPt_sex() {
        return pt_sex;
    }

    public void setPt_sex(String pt_sex) {
        this.pt_sex = pt_sex;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }
}
