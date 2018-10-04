package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.SectionAdapter;
import stu.byron.com.onlineregistrationproject.bean.Doctor;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.bean.Section;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

public class SectionActivity extends AppCompatActivity{
    private RelativeLayout rl_title_bar;
    private TextView tv_main_title,tv_back;

    private ListView lv_section_listview;
    private List<Section> sectionList;
    private SharedPreferencesUtil sharedPreferencesUtil=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);

        lv_section_listview=findViewById(R.id.lv_section_listView);
    }

    private void initData(){
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        sectionList= LitePal.findAll(Section.class);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title.setText("科室选择");
    }

    private void setAdapter(){
        SectionAdapter adapter=new SectionAdapter(SectionActivity.this,sectionList);
        lv_section_listview.setAdapter(adapter);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SectionActivity.this.finish();
            }
        });

        lv_section_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("section_name",sectionList.get(position).getSc_name());
                setResult(RESULT_OK,intent);
                saveSectionStatus(sectionList.get(position).getSc_id(),sectionList.get(position).getSc_name());
                LitePal.deleteAll(Doctor.class);
                sharedPreferencesUtil.insertData("doctor_name","");
                SectionActivity.this.finish();
            }
        });
    }

    private void saveSectionStatus(int section_id,String section){
        sharedPreferencesUtil.insertData("isSection",true);
        sharedPreferencesUtil.insertData("section_name",section);
        sharedPreferencesUtil.insertData("section_id",String.valueOf(section_id));
    }
}
