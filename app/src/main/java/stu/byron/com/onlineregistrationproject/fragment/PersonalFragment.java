package stu.byron.com.onlineregistrationproject.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.litepal.LitePal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.activity.CallbackActivity;
import stu.byron.com.onlineregistrationproject.activity.LoginActivity;
import stu.byron.com.onlineregistrationproject.activity.MyCountActivity;
import stu.byron.com.onlineregistrationproject.activity.SettingActivity;
import stu.byron.com.onlineregistrationproject.activity.UserInfoActivity;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.view.CircleImageView;

/**
 * Created by Byron on 2018/9/17.
 */

public class PersonalFragment extends Fragment implements View.OnClickListener{
    View view;
    private CircleImageView iv_head_icon;
    private TextView tv_text_username;

    private RelativeLayout ll_title_bar;
    private TextView tv_back,tv_main_text;

    private RelativeLayout rl_mycount,rl_coupons,rl_callback,rl_setting;

    private SharedPreferencesUtil sharedPreferencesUtil=null;

    private Boolean isLogin;
    private String spUserName;
    //private ImageView iv_person_icon;
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
        iv_head_icon=view.findViewById(R.id.iv_person_icon);
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
        //iv_person_icon=view.findViewById(R.id.iv_person_icon);
    }

    private void initData()
    {
        if (isLogin){
            Consumer consumer=new Consumer();
            consumer= LitePal.findAll(Consumer.class).get(0);
            if (consumer.getCm_image()!=null&&consumer.getCm_image().length>0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(consumer.getCm_image(), 0, consumer.getCm_image().length);
                iv_head_icon.setImageBitmap(bitmap);
            }
            tv_text_username.setText(spUserName);
        }else {
            iv_head_icon.setImageResource(R.drawable.default_icon);
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
            case R.id.iv_person_icon:
                if (isLogin){
                    Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,1);
                }
                break;
            case R.id.tv_user_name:
                if (isLogin){
                    Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                    startActivityForResult(intent,1);
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
        //Toast.makeText(getActivity(),"执行到这里1",Toast.LENGTH_SHORT).show();
        if (data!=null) {
            //Toast.makeText(getActivity(),"执行到这里2",Toast.LENGTH_SHORT).show();
            isLogin = sharedPreferencesUtil.getResult("isLogin");
            spUserName = sharedPreferencesUtil.getInfo("username");
            initData();
//            Consumer consumer=new Consumer();
//            String cm_id=String.valueOf(consumer.getCm_id());
//            HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.GET_APPOINTMENTINFO, "cm_id", cm_id, new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responseText=response.body().string();
//                    ParseData.handleAppointmentInfoResponse(responseText);
//                }
//            });
        }
    }
}
