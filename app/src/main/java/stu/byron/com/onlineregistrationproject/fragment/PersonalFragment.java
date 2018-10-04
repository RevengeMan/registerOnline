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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.activity.CallbackActivity;
import stu.byron.com.onlineregistrationproject.activity.LoginActivity;
import stu.byron.com.onlineregistrationproject.activity.MyCountActivity;
import stu.byron.com.onlineregistrationproject.activity.SettingActivity;
import stu.byron.com.onlineregistrationproject.activity.UserInfoActivity;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

/**
 * Created by Byron on 2018/9/17.
 */

public class PersonalFragment extends Fragment implements View.OnClickListener{
    View view;
    private ImageView iv_head_icon;
    private TextView tv_text_username;

    private RelativeLayout ll_title_bar;
    private TextView tv_back,tv_main_text;

    private RelativeLayout rl_mycount,rl_coupons,rl_callback,rl_setting;

    private SharedPreferencesUtil sharedPreferencesUtil=null;

    private Boolean isLogin;
    private String spUserName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_personal_layout,container,false);
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(getActivity());
        isLogin = sharedPreferencesUtil.getResult("isLogin");
        spUserName = sharedPreferencesUtil.getInfo("username");
        initView();
        initData();
        setListener();
        return view;
    }

    private void initView(){
        tv_text_username=view.findViewById(R.id.tv_user_name);
        iv_head_icon=view.findViewById(R.id.iv_head_icon);
        ll_title_bar=view.findViewById(R.id.title_bar);
        ll_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=view.findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_main_text=view.findViewById(R.id.tv_main_title);
        tv_main_text.setText("个人中心");
        rl_mycount=view.findViewById(R.id.rl_mycount);
        rl_coupons=view.findViewById(R.id.rl_coupons);
        rl_callback=view.findViewById(R.id.rl_callback);
        rl_setting=view.findViewById(R.id.rl_setting);
    }

    private void initData()
    {
        if (isLogin){
            tv_text_username.setText(spUserName);
        }else {
            tv_text_username.setText("点击登录");
        }
    }

    private void setListener(){

        iv_head_icon.setOnClickListener(this);
        tv_text_username.setOnClickListener(this);
        rl_mycount.setOnClickListener(this);
        rl_coupons.setOnClickListener(this);
        rl_callback.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_head_icon:
                if (isLogin){
                    Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,1);
                }
                break;
            case R.id.tv_user_name:
                if (isLogin){
                    Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent2,1);
                }
                break;
            case R.id.rl_mycount:
                if(isLogin){
                    //个人账户;
                    Intent intent=new Intent(getActivity(), MyCountActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_coupons:
                if(isLogin){
                    //打折
                }else {
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_callback:
                if(isLogin){
                    //回馈
                    Intent intent=new Intent(getActivity(), CallbackActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_setting:
                if(isLogin){
                    //设置
                    Intent intent=new Intent(getActivity(), SettingActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Toast.makeText(getActivity(),"您还未登录",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null) {
            isLogin = sharedPreferencesUtil.getResult("isLogin");
            spUserName = sharedPreferencesUtil.getInfo("username");
            initData();
        }
    }
}
