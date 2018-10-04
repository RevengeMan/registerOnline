package stu.byron.com.onlineregistrationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_main_title,tv_back;
    private RelativeLayout rl_title_bar;

    private RelativeLayout rl_modify_psw,rl_security_setting,rl_exit_login;

    private SharedPreferencesUtil sharedPreferencesUtil=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);
        initView();
        setListener();
    }

    private void initView(){
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("设置");
        tv_back=findViewById(R.id.tv_back);
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        rl_modify_psw=findViewById(R.id.rl_modify_psw);
        rl_security_setting=findViewById(R.id.rl_security_setting);
        rl_exit_login=findViewById(R.id.rl_exit_login);
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        rl_modify_psw.setOnClickListener(this);
        rl_security_setting.setOnClickListener(this);
        rl_exit_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                SettingActivity.this.finish();
                break;
            case R.id.rl_modify_psw:
                //跳转到修改密码页面
                Intent intent=new Intent(SettingActivity.this,ModifyPswActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_exit_login:
                Toast.makeText(SettingActivity.this,"退出登录成功",Toast.LENGTH_SHORT).show();
                //清除登录状态和登录时的用户名
                clearLoginStatus();
                SettingActivity.this.finish();
                break;
                default:
                    break;
        }
    }
    /**
     * 清除SharedPreferences中的登录状态和登录时的用户名
     */
    private void clearLoginStatus(){
        sharedPreferencesUtil.insertData("isLogin",false);
        String userName=sharedPreferencesUtil.getInfo("username");
        LitePal.deleteAll(Consumer.class,"cm_nickname=?",userName);
        sharedPreferencesUtil.insertData("username","");
        Intent intent =new Intent();
        intent.putExtra("isLogin",false);
        //intent.putExtra("username","");
        setResult(RESULT_OK,intent);
    }
}
