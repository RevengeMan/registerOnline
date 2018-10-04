package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.PatientAdapter;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Patient;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;

public class PatientListActivity extends AppCompatActivity {
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title,tv_edit;

    private ListView lv_patient_listView;
    private List<Patient> patientList;
    private List<Patient> dataList = new ArrayList<>();
    private PatientAdapter adapter;

    private SharedPreferencesUtil sharedPreferencesUtil=null;

    private boolean isEdit=false;
    private Button btn_add_patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("选择信息");
        tv_edit=findViewById(R.id.tv_save);
        tv_edit.setText("编辑");
        tv_edit.setVisibility(View.VISIBLE);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

        btn_add_patient=findViewById(R.id.btn_add_patient);

        lv_patient_listView=findViewById(R.id.lv_patient_listView);
    }

    private void initData(){
        queryFromService();
    }

    private void queryPatient(){
        patientList=LitePal.findAll(Patient.class);
        if (patientList.size()>0){
            dataList.clear();
            for (Patient patient:patientList){
                dataList.add(patient);
                adapter.notifyDataSetChanged();
                lv_patient_listView.setSelection(0);
            }
        }else {
            queryFromService();
        }
    }

    private void queryFromService(){
        Consumer consumer=LitePal.findAll(Consumer.class).get(0);
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.GET_PATIENT, "cm_id",String.valueOf(consumer.getCm_id()) , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result=false;
                result=ParseData.handlePatientResponse(responseText);
                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryPatient();
                        }
                    });
                }
            }
        });
    }

    private void setAdapter(){
        adapter=new PatientAdapter(PatientListActivity.this,dataList);
        lv_patient_listView.setAdapter(adapter);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                LitePal.deleteAll(Patient.class);
                PatientListActivity.this.finish();
            }
        });

        lv_patient_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isEdit){
                    Intent intent=new Intent(PatientListActivity.this,UpdataPatientActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("patient",patientList.get(position));
                    //Log.e("patient", "onItemClick: "+patientList.get(position).getPt_idnumber() );
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }else {
                    Intent data = new Intent();
                    data.putExtra("patient_name", patientList.get(position).getPt_name());
                    data.putExtra("patient_id",patientList.get(position).getPt_id());
                    saveInfo(patientList.get(position).getPt_id(), patientList.get(position).getPt_name());
                    setResult(RESULT_OK, data);
                    PatientListActivity.this.finish();
                }
            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add_patient.setVisibility(View.VISIBLE);
                isEdit=true;
            }
        });
        btn_add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientListActivity.this,PatientActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    private void saveInfo(int patient_id,String patient_name){
        sharedPreferencesUtil.insertData("patient_id",String.valueOf(patient_id));
        sharedPreferencesUtil.insertData("patient_name",patient_name);
        //Log.e("PatientListActivity", "saveInfo: "+patient_id+","+patient_name );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Result", "执行 " );
        LitePal.deleteAll(Patient.class);
        initData();
    }
}
