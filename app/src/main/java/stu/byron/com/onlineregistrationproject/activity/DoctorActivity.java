package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.DoctorAdapter;
import stu.byron.com.onlineregistrationproject.bean.Doctor;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

public class DoctorActivity extends AppCompatActivity {
    private List<Doctor> doctorList=new ArrayList<>();

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private ListView lv_doctor_listview;

    private SharedPreferencesUtil sharedPreferencesUtil=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("选择医生");
        lv_doctor_listview=findViewById(R.id.lv_doctor_listView);
    }

    private void setAdapter(){
        DoctorAdapter adapter=new DoctorAdapter(DoctorActivity.this,doctorList);
        lv_doctor_listview.setAdapter(adapter);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorActivity.this.finish();
            }
        });

        lv_doctor_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("doctor_name",doctorList.get(position).getDt_name());
                Log.d("Doctor", "onItemClick: "+doctorList.get(position).getDt_name());
                setResult(RESULT_OK,intent);
                saveDoctorStatus(String.valueOf(doctorList.get(position).getDt_id()),doctorList.get(position).getDt_name());
                DoctorActivity.this.finish();
            }
        });
    }

    private void initData(){
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        doctorList=LitePal.findAll(Doctor.class);
        Log.e("doctorList",String.valueOf(doctorList));
    }

    private void saveDoctorStatus(String dc_id,String doctor_name){
        sharedPreferencesUtil.insertData("doctor_id",dc_id);
        sharedPreferencesUtil.insertData("doctor_name",doctor_name);
    }
}
