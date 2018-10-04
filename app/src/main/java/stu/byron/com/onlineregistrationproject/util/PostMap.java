package stu.byron.com.onlineregistrationproject.util;

import java.util.HashMap;

import stu.byron.com.onlineregistrationproject.bean.Appointment;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.bean.Patient;

/**
 * Created by Byron on 2018/9/19.
 */

public class PostMap {
    public static HashMap ConsumerPacking(Consumer consumer){
        HashMap<String,String> params=new HashMap<String,String>();
        if (consumer.getCm_id()!=0) {
            params.put("cm_id", String.valueOf(consumer.getCm_id()));
        }
        if (consumer.getNickname()!=null) {
            params.put("cm_nickname", consumer.getNickname());
        }
        if (consumer.getSex()!=null) {
            params.put("cm_sex", consumer.getSex());
        }
        if (consumer.getCm_realname()!=null) {
            params.put("cm_realname", consumer.getCm_realname());
        }
        if (consumer.getCm_phone()!=null){
            params.put("cm_phone",consumer.getCm_phone());
        }
        if (consumer.getCm_password()!=null) {
            params.put("cm_password", consumer.getCm_password());
        }
        if (consumer.getCm_idnumber()!=null) {
            params.put("cm_idnumber", consumer.getCm_idnumber());
        }
        if (consumer.getAdd_time()!=null){
            params.put("add_time",consumer.getAdd_time());
        }
        return params;
    }

    public static HashMap HospitalPacking(Hospital hospital){
        HashMap<String,String> params=new HashMap<>();
        if (hospital.getHp_id()!=0){
            params.put("hp_id",String.valueOf(hospital.getHp_id()));
        }
        if (hospital.getHp_image()!=null){
            params.put("hp_image",hospital.getHp_image());
        }
        if (hospital.getHp_name()!=null){
            params.put("hp_name",hospital.getHp_name());
        }
        if (hospital.getAdd_time()!=null){
            params.put("add_time",hospital.getAdd_time());
        }
        if (hospital.getHp_latitude()!=null){
            params.put("hp_latitude",hospital.getHp_latitude());
        }
        if (hospital.getHp_longitude()!=null){
            params.put("hp_longitude",hospital.getHp_longitude());
        }
        return params;
    }

    public static HashMap PatientPacking(Patient patient){
        HashMap<String,String> params=new HashMap<>();
        params.put("pt_id",String.valueOf(patient.getPt_id()));
        params.put("pt_name",patient.getPt_name());
        params.put("pt_idnumber",patient.getPt_idnumber());
        params.put("pt_sex",patient.getPt_sex());
        params.put("add_time",patient.getAdd_time());
        params.put("cm_id",String.valueOf(patient.getCm_id()));
        return params;
    }

    public static HashMap AppointmengPacking(Appointment appointment){
        HashMap<String , String> params=new HashMap<>();
        params.put("at_id",String.valueOf(appointment.getAt_id()));
        params.put("at_status",String.valueOf(appointment.getAt_status()));
        params.put("at_time",String.valueOf(appointment.getAt_time()));
        params.put("add_time",appointment.getAdd_time());
        params.put("description",appointment.getDescription());
        params.put("hp_id",String.valueOf(appointment.getHp_id()));
        params.put("pt_id",String.valueOf(appointment.getPt_id()));
        params.put("sc_id",String.valueOf(appointment.getSc_id()));
        params.put("dt_id",String.valueOf(appointment.getDt_id()));
        return params;
    }

    public static HashMap CastHistoryPacking(CastHistory castHistory){
        HashMap<String , String> params=new HashMap<>();
        params.put("ch_status",String.valueOf(castHistory.getCh_status()));
        params.put("cm_id",String.valueOf(castHistory.getCm_id()));
        params.put("money",String.valueOf(castHistory.getMoney()));
        params.put("add_time",String.valueOf(castHistory.getAdd_time()));
        return params;
    }
}
