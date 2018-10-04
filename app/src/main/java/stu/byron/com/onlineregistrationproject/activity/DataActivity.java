package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Appointment;
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Doctor;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.bean.Patient;
import stu.byron.com.onlineregistrationproject.bean.Section;
import stu.byron.com.onlineregistrationproject.bean.StartLocation;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class DataActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_introduce,tv_main_title;

    private RelativeLayout rl_section,rl_doctor,rl_addtime,rl_patient,rl_navigation;
    private TextView tv_hospital,tv_section,tv_doctor,tv_addtime,tv_patient;
    private String hospitalName,sectionName,doctorName,addTime,patientName;
    private EditText et_desciption;
    private Button bt_next_button;

    private SharedPreferencesUtil sharedPreferencesUtil=null;
    private Boolean isSection=false;
    private String section_id,doctor_id,patient_id,description;
    private Hospital hospital;
    private String data;

    private String at_time;
    private int at_status,pt_id,sc_id,cm_id,hp_id,dc_id;
    private String add_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Intent getData=new Intent(DataActivity.this,NavigationActivity.class);
        startActivityForResult(getData,1);
        initView();
        initData();
        setListener();
    }
    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);
        tv_introduce=findViewById(R.id.tv_save);

        rl_section=findViewById(R.id.rl_section);
        rl_doctor=findViewById(R.id.rl_doctor);
        rl_addtime=findViewById(R.id.rl_datatime);
        rl_patient=findViewById(R.id.rl_patient);
        tv_hospital=findViewById(R.id.tv_hospital);
        tv_section=findViewById(R.id.tv_section);
        tv_doctor=findViewById(R.id.tv_doctor);
        tv_addtime=findViewById(R.id.tv_addtime);
        tv_patient=findViewById(R.id.tv_patient);
        et_desciption=findViewById(R.id.description_content);
        bt_next_button=findViewById(R.id.bt_next);
        rl_navigation=findViewById(R.id.rl_navigation);
    }

    private void initData(){

        hospital= (Hospital) this.getIntent().getSerializableExtra("hospital");

        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        /*String patient_name=sharedPreferencesUtil.getInfo("patient_name");
        tv_patient.setText(patient_name);*/
        isSection=sharedPreferencesUtil.getResult("isSection");
        section_id=sharedPreferencesUtil.getInfo("section_id");
        patient_id=sharedPreferencesUtil.getInfo("patient_id");
        doctor_id=sharedPreferencesUtil.getInfo("doctor_id");
        Log.e("信息:", "initData: "+"section_id:"+section_id +",patient_id:"+patient_id+",doctor_id:"+doctor_id+"hospital_id"+hospital.getHp_id());

        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_introduce.setText("服务介绍");
        tv_main_title.setText("挂号陪诊");
        tv_hospital.setText(hospital.getHp_name());
        HashMap<String,String> map=new HashMap<>();
        map= PostMap.HospitalPacking(hospital);
        Log.d("DataActivity", "initData: "+map);
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.GET_SECTION, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //加载科室数据
                String responseText=response.body().string();
                ParseData.handleSectionResponse(responseText);
            }
        });
        if (null!=section_id&&!section_id.equals("")) {
            HashMap<String, String> params = new HashMap<>();
            params.put("sc_id", section_id);
            Log.d("sc_id:", section_id);
            HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.GET_DOCTOR, "sc_id", section_id, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Log.e("responseText", responseText);
                    ParseData.handleDoctorResponse(responseText);
                }
            });
        }
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        tv_introduce.setOnClickListener(this);
        rl_section.setOnClickListener(this);
        rl_doctor.setOnClickListener(this);
        rl_addtime.setOnClickListener(this);
        rl_patient.setOnClickListener(this);
        bt_next_button.setOnClickListener(this);
        rl_navigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                LitePal.deleteAll(Section.class);
                LitePal.deleteAll(StartLocation.class);
                LitePal.deleteAll(Patient.class);
                sharedPreferencesUtil.insertData("getLocation","");
                sharedPreferencesUtil.insertData("isSection",false);
                sharedPreferencesUtil.insertData("section_id","");
                sharedPreferencesUtil.insertData("doctor_id","");
                sharedPreferencesUtil.insertData("patient_id","");
                sharedPreferencesUtil.insertData("section_name","");
                sharedPreferencesUtil.insertData("doctor_name","");
                sharedPreferencesUtil.insertData("patient_name","");
                DataActivity.this.finish();
                break;
            case R.id.tv_save:
                //进入服务介绍界面
                break;
            case R.id.rl_section:
                //科室选择
                Intent section=new Intent(DataActivity.this,SectionActivity.class);
                startActivityForResult(section,1);
                break;
            case R.id.rl_doctor:
                //选了科室才能选其他
                if (isSection){
                    //医生选择
                        Intent doctor=new Intent(DataActivity.this,DoctorActivity.class);
                        startActivityForResult(doctor,1);
                }else {
                    Toast.makeText(DataActivity.this,"请先选择科室",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_datatime:
                if (isSection) {
                    //预约时间
                    datePicker();
                }else {
                    Toast.makeText(DataActivity.this,"请先选择科室",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_patient:
               /* if (LitePal.findAll(Patient.class).size()==0){
                    //患者信息
                    Intent patient=new Intent(DataActivity.this,PatientActivity.class);
                    startActivityForResult(patient,1);
                }else {*/
                    Intent patientList=new Intent(DataActivity.this,PatientListActivity.class);
                    startActivityForResult(patientList,1);

                break;
            case R.id.bt_next:
                //下一步
                getStringInfo();
                if (TextUtils.isEmpty(sectionName)){
                    Toast.makeText(DataActivity.this,"请选择科室",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(doctorName)){
                    Toast.makeText(DataActivity.this,"请选择医生",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(addTime)){
                    Toast.makeText(DataActivity.this,"请选择预约时间",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(patientName)){
                    Toast.makeText(DataActivity.this,"请填写患者信息",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(description)){
                    Toast.makeText(DataActivity.this,"请输入病情描述",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Appointment appointment=new Appointment();
                    appointment.setAdd_time(TimeUtil.getNowTime());
                    appointment.setAt_status(0);
                    appointment.setDescription(description);
                    appointment.setAt_time(data);
                    Log.e("data", String.valueOf(data));
                    appointment.setHp_id(hospital.getHp_id());
                    appointment.setSc_id(sc_id);
                    appointment.setDt_id(dc_id);
                    appointment.setPt_id(pt_id);
                    HashMap<String , String > map=new HashMap<>();
                    map=PostMap.AppointmengPacking(appointment);
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.INSERT_APPOINTMENT, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });

                    /*//跳到支付界面
                    AppointmentInfo info=new AppointmentInfo();
                    info.setAdd_time(appointment.getAdd_time());
                    info.setDescription(appointment.getDescription());
                    info.setDt_name(hospital.getHp_name());
                    info.setPt_name(patientName);
                    info.setSc_name(sectionName);
                    info.setAt_time(data);
                    info.setHp_name(hospitalName);
                    Intent intent=new Intent(DataActivity.this,PayActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("info",info);
                    intent.putExtras(bundle);
                    startActivity(intent);*/

                    LitePal.deleteAll(Section.class);
                    LitePal.deleteAll(StartLocation.class);
                    LitePal.deleteAll(Patient.class);
                    sharedPreferencesUtil.insertData("getLocation","");
                    sharedPreferencesUtil.insertData("isSection",false);
                    sharedPreferencesUtil.insertData("section_id","");
                    sharedPreferencesUtil.insertData("doctor_id","");
                    sharedPreferencesUtil.insertData("patient_id","");
                    sharedPreferencesUtil.insertData("section_name","");
                    sharedPreferencesUtil.insertData("doctor_name","");
                    sharedPreferencesUtil.insertData("patient_name","");
                    DataActivity.this.finish();
                }
                break;
            case R.id.rl_navigation:
                Intent intent=new Intent(DataActivity.this,GdNavigationActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("hospital",hospital);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    private void getStringInfo(){
        //tv_hospital,tv_section,tv_doctor,tv_addtime,tv_patient;
        //hospitalName,sectionName,doctorName,addTime,patientName;
        hospitalName=tv_hospital.getText().toString().trim();
        sectionName=tv_section.getText().toString().trim();
        doctorName=tv_doctor.getText().toString().trim();
        addTime=tv_addtime.getText().toString().trim();
        patientName=tv_patient.getText().toString().trim();
        description=et_desciption.getText().toString().trim();
        data=tv_addtime.getText().toString().trim();
        //pt_id,sc_id,cm_id,hp_id;
        //section_id,doctor_id,patient_id,description;
        sc_id=Integer.parseInt(section_id);
        pt_id=Integer.parseInt(patient_id);
        dc_id=Integer.parseInt(doctor_id);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String sectionName=sharedPreferencesUtil.getInfo("section_name");
            String doctorName=sharedPreferencesUtil.getInfo("doctor_name");
            String patientName=sharedPreferencesUtil.getInfo("patient_name");
            Log.e("Data", "onActivityResult: "+sectionName+","+doctorName+","+patientName);
            tv_section.setText(sectionName);
            tv_doctor.setText(doctorName);
            tv_patient.setText(patientName);
            initData();
            String location=sharedPreferencesUtil.getInfo("getLocation");
            if (location.equals("location")){
                initView();
                setListener();
            }
        }
    }

    private void datePicker(){
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(3);
        dialog.onBackPressed();
        Date date = new Date(System.currentTimeMillis());
        dialog.setStartDate(date);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        dialog.setCanceledOnTouchOutside(true);
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {
                Date date1=date;
                Log.d("Data", "onChanged: "+date);
            }
        });
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(final Date date) {
                Log.d("Data","sure"+date);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data_time=TimeUtil.ConverToString(date);
                        Date date = new Date(System.currentTimeMillis());
                        String now_time=TimeUtil.ConverToString(date);
                        Log.e("Time", "select: "+data_time+",now:"+now_time );
                        if (TimeUtil.isDateOneBigger(data_time,now_time)) {
                            Log.e("datatime", "run: " + data_time);
                            tv_addtime.setText(String.valueOf(data_time));
                        }else {
                            Toast.makeText(DataActivity.this,"请选择明天或之后的时间",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
}
