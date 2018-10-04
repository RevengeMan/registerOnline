package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/9/17.
 */

public class Hospital extends LitePalSupport implements Serializable{
    private int hp_id;
    private String hp_name;
    private String hp_image;
    private String hp_longitude;
    private String hp_latitude;
    private String add_time;

    public int getHp_id() {
        return hp_id;
    }

    public void setHp_id(int hp_id) {
        this.hp_id = hp_id;
    }

    public String getHp_name() {
        return hp_name;
    }

    public void setHp_name(String hp_name) {
        this.hp_name = hp_name;
    }

    public String getHp_image() {
        return hp_image;
    }

    public void setHp_image(String hp_image) {
        this.hp_image = hp_image;
    }

    public String getHp_longitude() {
        return hp_longitude;
    }

    public void setHp_longitude(String hp_longitude) {
        this.hp_longitude = hp_longitude;
    }

    public String getHp_latitude() {
        return hp_latitude;
    }

    public void setHp_latitude(String hp_latitude) {
        this.hp_latitude = hp_latitude;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
