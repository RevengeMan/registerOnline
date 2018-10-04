package stu.byron.com.onlineregistrationproject.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Byron on 2018/9/9.
 */

public class Constant {
    public static final String BASE_PATH="http://10.255.47.220:8080/BackStageManager/";
    public static final String LOGIN="data/login";
    public static final String HOSPITAL_DATA="data/hospital";
    public static final String REGISTER="data/register";
    public static final String UPDATE="data/update";
    public static final String GET_SECTION="data/section";
    public static final String GET_DOCTOR="data/doctor";
    public static final String INSERT_PATIENT="data/insertPatient";
    public static final String GET_PATIENT="data/getPatient";
    public static final String UPDATA_PATIENT="data/updatePatient";
    public static final String DELETE_PATIENT="data/deletePatient";
    public static final String INSERT_APPOINTMENT="data/insertAppointment";
    public static final String GET_APPOINTMENTINFO="data/appointmentInfo";
    public static final String PAY_FOR="data/payfor";
    public static final String CANCEL_APPOINTMENT="data/deleteAppointment";
    public static final String RECHARGE="data/recharge";
    public static final String CALLBACK="data/callback";
    public static final String UPDATE_PASSWORD="data/updatePassword";
    public static final String FIND_PASSWORD="data/findPassword";
    public static final String INSERT_CASTHISTORY="data/insertHistory";
    public static final String GET_HISTORY="data/getHistory";

    public static void showDialog(Context context,String text){
        new AlertDialog.Builder(context)
                .setTitle(text)
                .setPositiveButton("确定",null)
                .show();
    }
}
