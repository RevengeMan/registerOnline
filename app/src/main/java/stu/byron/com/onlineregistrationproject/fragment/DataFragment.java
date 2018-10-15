package stu.byron.com.onlineregistrationproject.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
import stu.byron.com.onlineregistrationproject.activity.ComentsActivity;
import stu.byron.com.onlineregistrationproject.activity.PayActivity;
import stu.byron.com.onlineregistrationproject.adapter.AppointmentAdapter;
import stu.byron.com.onlineregistrationproject.bean.Appointment;
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.service.AutoReceiver;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Byron on 2018/9/17.
 */

public class DataFragment extends Fragment {
    private View view;
    private List<AppointmentInfo> appointmentList;
    private List<AppointmentInfo> dataList=new ArrayList<>();
    private List<AppointmentInfo> data1=new ArrayList<>();
    private List<AppointmentInfo> data2=new ArrayList<>();
    private List<AppointmentInfo> data3=new ArrayList<>();
    private AppointmentAdapter adapter;
    private AppointmentAdapter adapter1;
    private AppointmentAdapter adapter2;
    private AppointmentAdapter adapter3;
    private ListView listView;
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;
    private RelativeLayout rl,rl1,rl2,rl3;
    private SharedPreferencesUtil sharedPreferencesUtil=null;

    private SwipeRefreshLayout swipeRefresh;

    private Boolean inData=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_data_layout,container,false);
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getActivity());
        if (sharedPreferencesUtil.getResult("isLogin")) {
            initView();
            initData();
            setAdapter();
            setListener();
        }else {
            Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void initView(){
        swipeRefresh=view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        TabHost tab=view.findViewById(android.R.id.tabhost);
        listView=view.findViewById(R.id.listview);
        listView1=view.findViewById(R.id.listview1);
        listView2=view.findViewById(R.id.listview2);
        listView3=view.findViewById(R.id.listview3);

        rl=view.findViewById(R.id.rl);
        rl1=view.findViewById(R.id.rl_1);
        rl2=view.findViewById(R.id.rl_2);
        rl3=view.findViewById(R.id.rl_3);
        //初始化TabHost容器
        tab.setup();
        //在TabHost创建标签，然后设置：标题／图标／标签页布局  
        tab.addTab(tab.newTabSpec("tab1").setIndicator("全部" , null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("待付款" , null).setContent(R.id.tab2));
        tab.addTab(tab.newTabSpec("tab3").setIndicator("待服务" , null).setContent(R.id.tab3));
        tab.addTab(tab.newTabSpec("tab4").setIndicator("待评价" , null).setContent(R.id.tab4));
    }

    private void initData(){
        //inData=false;
        if (sharedPreferencesUtil.getResult("isLogin")) {
            //dataList.clear();
            queryFormServe();
            data1.clear();
            data2.clear();
            data3.clear();
            appointmentList=LitePal.findAll(AppointmentInfo.class);
            Date date=new Date(System.currentTimeMillis());
            for (int i = 0; i < appointmentList.size(); i++) {
                AppointmentInfo appointment = appointmentList.get(i);
                if (appointment.getAt_status() == 0) {
                    if (TimeUtil.isDateOneBigger(appointment.getAt_time(),TimeUtil.ConverToString(date))) {
                        data1.add(appointment);
                        Log.e("data1", "initData: " + data1);
                    }else {
                        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.CANCEL_APPOINTMENT, "at_id", String.valueOf(appointment.getAt_id()), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                    }
                } else if (appointment.getAt_status() == 1) {
                    if (TimeUtil.isDateOneBigger(appointment.getAt_time(),TimeUtil.ConverToString(date))){
                        data2.add(appointment);
                    }else {
                        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.CHANGE_APPOINTSTATUS, "at_id", String.valueOf(appointment.getAt_id()), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                        appointment.setAt_status(2);
                        appointment.updateAll("cm_id=?",String.valueOf(appointment.getAt_id()));
                        data3.add(appointment);
                    }
                } else if (appointment.getAt_status() == 2){
                    data3.add(appointment);
                }
                if (appointment.getAt_time().equals(TimeUtil.ConverToString(date))){
                    Log.e("DataFragment", "date:"+TimeUtil.ConverToString(date)+",at_time:"+appointment.getAt_time());
                    Intent intent = new Intent(getActivity(), AutoReceiver.class);
                    intent.setAction("VIDEO_TIMER");
                    // PendingIntent这个类用于处理即将发生的事情 
                    PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
                    AlarmManager am = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
                    // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
                    // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
                    am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),sender);
                }
            }

        }else {
            Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
        }
    }

    private void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("info",dataList.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("info",data1.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("info",data2.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ComentsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("info",data3.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //initView();
                initData();
                setAdapter();
            }
        });
    }

    private void setAdapter(){
        if (appointmentList.size()>0) {
            adapter = new AppointmentAdapter(getActivity(), dataList);
            listView.setAdapter(adapter);
        }else {
            listView.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
        if (data1.size()>0){
            adapter1=new AppointmentAdapter(getActivity(),data1);
            listView1.setAdapter(adapter1);
        }else {
            listView1.setVisibility(View.GONE);
            rl1.setVisibility(View.VISIBLE);
        }
        if (data2.size()>0){
            adapter2=new AppointmentAdapter(getActivity(),data2);
            listView2.setAdapter(adapter2);
        }else {
            listView2.setVisibility(View.GONE);
            rl2.setVisibility(View.VISIBLE);
        }
        if (data3.size()>0){
            adapter3=new AppointmentAdapter(getActivity(),data3);
            listView3.setAdapter(adapter3);
        }else {
            listView3.setVisibility(View.GONE);
            rl3.setVisibility(View.VISIBLE);
        }
    }

    private void queryAppointment(){
        appointmentList= LitePal.findAll(AppointmentInfo.class);
        if (appointmentList.size()>0){
            dataList.clear();
            for (AppointmentInfo appointment:appointmentList){
                dataList.add(appointment);
                Log.e("size:","大小："+dataList.size());
            }
            adapter.notifyDataSetChanged();//bug
            listView.setSelection(0);
        }else {
            queryFormServe();
        }
    }

    private void queryFormServe(){
        Consumer consumer=LitePal.findAll(Consumer.class).get(0);

        // Log.e("cm_id", "queryFormServe: "+consumer.getCm_id() );
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.GET_APPOINTMENTINFO,"cm_id",String.valueOf(consumer.getCm_id()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                //Log.e("response", "onResponse: "+responseText );
                if (responseText.equals("empty")){
                    sharedPreferencesUtil.insertData("isData",false);
                }else {
                    boolean result = false;
                    result = ParseData.handleAppointmentInfoResponse(responseText);
                    if (result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryAppointment();
                            }
                        });
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //LitePal.deleteAll(AppointmentInfo.class);
        //dataList.clear();
        //backData();
        initData();
        setAdapter();
    }
}
