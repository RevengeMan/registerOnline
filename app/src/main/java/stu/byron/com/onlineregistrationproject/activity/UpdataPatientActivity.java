package stu.byron.com.onlineregistrationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Patient;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

/**
 * Created by Byron on 2018/9/23.
 */

public class UpdataPatientActivity extends AppCompatActivity {
    private Patient patient;
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title,tv_delete;

    //private RelativeLayout rl_bottom_bar;
    private Button bt_save;

    private EditText et_patient_name,et_id_card;

    private RadioGroup rg;
    private String name,id_card,sex;
    private RadioButton male;
    private RadioButton female;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        patient= (Patient) this.getIntent().getSerializableExtra("patient");
        //Log.e("patientId", "onCreate: "+patient.getPt_id());
        initView();
        initData();
        setListener();
    }
    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改患者信息");
        rg=findViewById(R.id.rg);
        //rl_bottom_bar=findViewById(R.id.rl_bottom_bar);
        bt_save=findViewById(R.id.bt_next);
        bt_save.setText("保存");
        et_patient_name=findViewById(R.id.et_patient_name);
        et_id_card=findViewById(R.id.patient_id_card);
        tv_delete=findViewById(R.id.tv_save);
        tv_delete.setVisibility(View.VISIBLE);
        tv_delete.setText("删除");
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
    }

    private void initData(){
        et_id_card.setText(patient.getPt_idnumber());
        et_patient_name.setText(patient.getPt_name());
        if (patient.getPt_sex().equals("男")){
            male.setChecked(true);
            sex="男";
        }else if (patient.getPt_sex().equals("女")){
            female.setChecked(true);
            sex="女";
        }
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataPatientActivity.this.finish();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex=checkedId==R.id.male ? "男" : "女";
                //Log.e("sex", "onCheckedChanged: "+sex);
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(UpdataPatientActivity.this,"请输入姓名",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(id_card)){
                    Toast.makeText(UpdataPatientActivity.this,"请输入身份证号",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Patient patientPersonal=new Patient();
                    patientPersonal.setPt_id(patient.getPt_id());
                    patientPersonal.setAdd_time(TimeUtil.getNowTime());
                    patientPersonal.setPt_sex(sex);
                    patientPersonal.setPt_idnumber(id_card);
                    patientPersonal.setPt_name(name);
                    HashMap<String,String> map=new HashMap<>();
                    map= PostMap.PatientPacking(patientPersonal);
                    Log.e("Info", "onClick: "+patientPersonal.getPt_id()+","+patientPersonal.getPt_sex());
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.UPDATA_PATIENT, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    Intent  data=new Intent();
                    setResult(RESULT_OK,data);
                    UpdataPatientActivity.this.finish();
                }
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.DELETE_PATIENT, "pt_id",String.valueOf(patient.getPt_id()) , new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                Intent  data=new Intent();
                setResult(RESULT_OK,data);
                UpdataPatientActivity.this.finish();
            }
        });
    }

    private void getEditString(){
        name=et_patient_name.getText().toString().trim();
        id_card=et_id_card.getText().toString().trim();
    }
}
