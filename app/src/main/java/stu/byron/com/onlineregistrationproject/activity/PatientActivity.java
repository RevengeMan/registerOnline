package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Patient;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class PatientActivity extends AppCompatActivity {
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    //private RelativeLayout rl_bottom_bar;
    private Button bt_save;

    private EditText et_patient_name,et_id_card;

    private RadioGroup rg;
    private String name,id_card,sex="女";

    private SharedPreferencesUtil sharedPreferencesUtil=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        initView();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("患者信息");
        rg=findViewById(R.id.rg);
        //rl_bottom_bar=findViewById(R.id.rl_bottom_bar);
        bt_save=findViewById(R.id.bt_next);
        bt_save.setText("完成");
        et_patient_name=findViewById(R.id.et_patient_name);
        et_id_card=findViewById(R.id.patient_id_card);
    }

    private void setListener(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sex=checkedId==R.id.male ? "男" : "女";
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(PatientActivity.this,"请输入姓名",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(id_card)){
                    Toast.makeText(PatientActivity.this,"请输入身份证号",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //LitePal.deleteAll(Patient.class);
                    Patient patient=new Patient();
                    patient.setPt_name(name);
                    patient.setPt_idnumber(id_card);
                    patient.setPt_sex(sex);
                    String addTime= TimeUtil.getNowTime();
                    patient.setAdd_time(addTime);
                    Consumer consumer=LitePal.findAll(Consumer.class).get(0);
                    patient.setCm_id(consumer.getCm_id());
                    //patient.save();
                    HashMap<String,String> map=new HashMap<>();
                    map= PostMap.PatientPacking(patient);
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.INSERT_PATIENT, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    Intent data=new Intent();
                    data.putExtra("1","1");
                    setResult(1,data);
                    savePatientName(name);
                    PatientActivity.this.finish();
                }
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientActivity.this.finish();
            }
        });
    }

    private void getEditString(){
        name=et_patient_name.getText().toString().trim();
        id_card=et_id_card.getText().toString().trim();
    }

    private void savePatientName(String name){
        sharedPreferencesUtil.insertData("patient_name",name);
    }
}
