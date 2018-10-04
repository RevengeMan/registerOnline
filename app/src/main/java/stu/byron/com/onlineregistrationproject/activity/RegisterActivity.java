package stu.byron.com.onlineregistrationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Consumer consumer;

    private RelativeLayout ll_title_bar;
    private EditText et_user_name;
    private EditText et_psw;
    private EditText et_psw_again;
    private Button btn_register;
    private TextView tv_back;
    private TextView tv_main_title;
    private TextView et_login_text;
    private String userName,password,password_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        consumer=new Consumer();
        initView();
        setListener();
        //sendCode(this);
    }
    private void initView(){
        ll_title_bar=findViewById(R.id.title_bar);
        ll_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back.setVisibility(View.GONE);

        et_user_name=findViewById(R.id.et_user_nickname);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);
        btn_register=findViewById(R.id.btn_register);
        et_login_text=findViewById(R.id.et_login_text);

    }

    private void setListener(){
        btn_register.setOnClickListener(this);
        et_login_text.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                String country,phone;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果

                    country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    consumer.setCm_phone(phone);
                    consumer.setAdd_time(TimeUtil.getNowTime());
                    HashMap<String ,String > map= PostMap.ConsumerPacking(consumer);
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.REGISTER, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText=response.body().string();
                            if (responseText.equals("error")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Constant.showDialog(RegisterActivity.this,"账号已存在");
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent();
                                        intent.putExtra("userName",userName);
                                        setResult(RESULT_OK,intent);
                                        RegisterActivity.this.finish();
                                    }
                                });
                            }
                        }
                    });

                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(RegisterActivity.this,RegisterActivity.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }
                    });
                }
            }
        });
        page.show(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                getEditString();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(password_again)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!password.equals(password_again)){
                    Toast.makeText(RegisterActivity.this,"输入密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExistNickName(userName)){
                    Toast.makeText(RegisterActivity.this,"此昵称已存在",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    consumer.setNickname(userName);
                    consumer.setCm_password(password);
                    consumer.setSex("男");
                    sendCode(this);
                }
                break;
            case R.id.tv_back:
                RegisterActivity.this.finish();
                break;
            case R.id.et_login_text:
                RegisterActivity.this.finish();
                break;
            default:
                break;
        }
    }

    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        password=et_psw.getText().toString().trim();
        password_again=et_psw_again.getText().toString().trim();
        Log.d("RegisterActivity", "getEditString: "+userName+password+password_again);
    }

    private Boolean isExistNickName(String userName){
        return false;
    }
}
