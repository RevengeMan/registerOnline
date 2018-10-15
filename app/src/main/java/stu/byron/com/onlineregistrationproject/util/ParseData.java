package stu.byron.com.onlineregistrationproject.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import stu.byron.com.onlineregistrationproject.bean.Appointment;
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Doctor;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.bean.Message;
import stu.byron.com.onlineregistrationproject.bean.Patient;
import stu.byron.com.onlineregistrationproject.bean.Section;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

/**
 * Created by Byron on 2018/9/19.
 */


public class ParseData {

    public static Boolean handleConsumerResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            Consumer consumer = gson.fromJson(response, Consumer.class);
            consumer.save();
            return true;
        }
        return false;
    }

    public static Boolean handleSectionResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray array = new JSONArray(response);
                LitePal.deleteAll(Section.class);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    Gson gson = new Gson();
                    Section section = gson.fromJson(json.toString(), Section.class);
                    if (LitePal.findAll(Section.class).size() < array.length()) {
                        section.save();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    public static Boolean handleHospitalResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                //Log.e("arrayInfo",response);
                JSONArray array = new JSONArray(response);
                LitePal.deleteAll(Hospital.class);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    Gson gson = new Gson();
                    Hospital hospital = gson.fromJson(json.toString(), Hospital.class);
                    //LitePal.deleteAll(Hospital.class);
                    /*if (LitePal.findAll(Hospital.class).size() < array.length()) {
                        //Log.e("Data", "handleHospitalResponse: "+hospital.getHp_name());
                        hospital.save();
                        Log.e("size", "LitepalSize: "+LitePal.findAll(Hospital.class).size()+"ArraySize:"+array.length());
                    }*/
                    hospital.save();
                }
                /*for (int i=0;i<LitePal.findAll(Hospital.class).size();i++){
                    Log.e("LitepalInfo", LitePal.findAll(Hospital.class).get(i).getHp_name());
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Boolean handleDoctorResponse(String response){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray array = new JSONArray(response);
                LitePal.deleteAll(Doctor.class);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    Gson gson = new Gson();
                    Doctor doctor = gson.fromJson(json.toString(), Doctor.class);
                    if (LitePal.findAll(Doctor.class).size() < array.length()) {
                        doctor.save();
                        Log.e("Doctor", "handleDoctorResponse: "+doctor.getDt_name());
                        Log.e("Size", "handleDoctorResponse: "+LitePal.findAll(Doctor.class).size() );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Boolean handlePatientResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray array=new JSONArray(response);
                LitePal.deleteAll(Patient.class);
                for (int i=0;i<array.length();i++){
                    JSONObject json= array.getJSONObject(i);
                    Gson gson=new Gson();
                    Patient patient=gson.fromJson(json.toString(),Patient.class);
                    if (LitePal.findAll(Patient.class).size()<array.length()){
                        patient.save();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Boolean handleAppointmentInfoResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray array=new JSONArray(response);
                LitePal.deleteAll(AppointmentInfo.class);
                for (int i=0;i<array.length();i++){
                    JSONObject json=array.getJSONObject(i);
                    Gson gson=new Gson();
                    AppointmentInfo appointment=gson.fromJson(json.toString(), AppointmentInfo.class);
                    if (LitePal.findAll(AppointmentInfo.class).size()<array.length()){
                        appointment.save();
                        Log.e("Info", "handleAppointmentInfoResponse: "+appointment.getSc_name());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Boolean handleCastHistoryInfoResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray array=new JSONArray(response);
                LitePal.deleteAll(CastHistory.class);
                for (int i=0;i<array.length();i++) {
                    JSONObject json = array.getJSONObject(i);
                    Gson gson = new Gson();
                    CastHistory castHistory = gson.fromJson(json.toString(),CastHistory.class);
                    if (LitePal.findAll(CastHistory.class).size()<array.length()){
                        castHistory.save();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  true;
        }
        return false;
    }

    public static Boolean handleMessageResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray array=new JSONArray(response);
                LitePal.deleteAll(Message.class);
                for (int i=0;i<array.length();i++){
                    JSONObject json=array.getJSONObject(i);
                    Gson gson=new Gson();
                    Message message=gson.fromJson(json.toString(),Message.class);
                    if (LitePal.findAll(Message.class).size()<array.length()){
                        message.save();
                        Log.e("ee", "执行到这里" );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
