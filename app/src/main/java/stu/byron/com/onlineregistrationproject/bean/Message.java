package stu.byron.com.onlineregistrationproject.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Byron on 2018/10/10.
 */

public class Message  extends LitePalSupport implements Serializable {
    private int msg_id;
    private String msg_content;
    private String add_time;
    private int cm_id;

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
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
