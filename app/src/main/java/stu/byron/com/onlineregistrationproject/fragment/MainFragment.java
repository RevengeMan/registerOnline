package stu.byron.com.onlineregistrationproject.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.activity.DataActivity;
import stu.byron.com.onlineregistrationproject.adapter.HospitalAdapter;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;

/**
 * Created by Byron on 2018/9/17.
 */

public class MainFragment extends Fragment {
    View view;
    private ListView lv_hospital_listview;
    private List<Hospital> hospitals;

    private List<Hospital> dataList=new ArrayList<>();
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Boolean isLogin;

    private RelativeLayout rl_title_bar;
    private TextView tv_back;
    private TextView tv_main_title;

    private HospitalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main_layout,container,false);
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(getActivity());
        initView();
        initData();
        setAdapter();
        setListener();;
        return view;
    }

    private void setAdapter(){
        adapter=new HospitalAdapter(getActivity(),dataList);
        lv_hospital_listview.setAdapter(adapter);
    }

    private void setListener(){
        lv_hospital_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isLogin){
                    //进入选择
                    Intent intent=new Intent(getActivity(), DataActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("hospital",hospitals.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(){
        lv_hospital_listview=view.findViewById(R.id.lv_hospital_listview);
        rl_title_bar=view.findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=view.findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_main_title=view.findViewById(R.id.tv_main_title);
        tv_main_title.setText("首页");
    }

    private void initData(){
        isLogin=sharedPreferencesUtil.getResult("isLogin");
        queryFormServer();
    }

    private void queryHospital(){
        hospitals=LitePal.findAll(Hospital.class);
        if (hospitals.size()>0){
            dataList.clear();
            for (Hospital hospital : hospitals){
                dataList.add(hospital);
            }
            adapter.notifyDataSetChanged();
            lv_hospital_listview.setSelection(0);
        }else {
            queryFormServer();
        }
    }


    private void queryFormServer(){
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.HOSPITAL_DATA, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainFragment", "Error "+e.getCause());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                Log.d("MainFragment", "onResponse: "+responseText.toString());
                boolean result=false;
                result=ParseData.handleHospitalResponse(responseText);
                List<Hospital> hospitals= LitePal.findAll(Hospital.class);
                for (int i=0;i<hospitals.size();i++){
                    Log.d("MainFragment", "onResponse: "+hospitals.get(i).getHp_name());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryHospital();
                        }
                    });
                }
            }
        });
    }

}
