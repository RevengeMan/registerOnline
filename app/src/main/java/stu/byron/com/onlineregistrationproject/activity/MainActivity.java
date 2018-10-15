package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.TabHost;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.fragment.DataFragment;
import stu.byron.com.onlineregistrationproject.fragment.MainFragment;
import stu.byron.com.onlineregistrationproject.fragment.MessageFragment;
import stu.byron.com.onlineregistrationproject.fragment.PersonalFragment;
import stu.byron.com.onlineregistrationproject.service.AutoUpdateService;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mtabHost;
    private RadioGroup radioGroup;
    private final Class[] fragments={MainFragment.class, MessageFragment.class,
            DataFragment.class, PersonalFragment.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //initData();
    }

    private void initView(){
        mtabHost=findViewById(android.R.id.tabhost);
        mtabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.realContent);
        mtabHost.setBackgroundColor(Color.parseColor("#ffffff"));
        for (int i=0;i<fragments.length;i++){
            TabHost.TabSpec tabSpec=mtabHost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            mtabHost.addTab(tabSpec,fragments[i],null);
            radioGroup=findViewById(R.id.tab_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.radio_main:
                            Intent intent=new Intent(MainActivity.this, AutoUpdateService.class);
                            startService(intent);
                            mtabHost.setCurrentTab(0);
                            break;
                        case R.id.radio_message:
                            mtabHost.setCurrentTab(1);
                            break;
                        case R.id.radio_data:
                            mtabHost.setCurrentTab(2);
                            break;
                        case R.id.radio_personal:
                            mtabHost.setCurrentTab(3);
                            break;
                    }
                }
            });
            mtabHost.setCurrentTab(0);
        }
    }

}
