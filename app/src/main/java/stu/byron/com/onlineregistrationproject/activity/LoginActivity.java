package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.util.PostMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_user_name,et_psw;
    private Button btn_login;
    private TextView tv_register,tv_find_psw;
    private String userName,password;

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private SharedPreferencesUtil sharedPreferencesUtil=SharedPreferencesUtil.getInstance(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title.setText("登录");

        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        btn_login=findViewById(R.id.btn_login);
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
    }

    private void setListener(){
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_find_psw.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String userName=data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                getEditString();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Consumer consumer=new Consumer();
                    consumer.setNickname(userName);
                    consumer.setCm_password(password);
                    HashMap<String,String> map= PostMap.ConsumerPacking(consumer);
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.LOGIN, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            String responseText = response.body().string();
                            if (responseText.equals("error")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Constant.showDialog(LoginActivity.this,"账号或者密码错误");
                                    }
                                });
                            } else {
                                Log.d("LoginActivity", "onResponse: "+responseText);

                                boolean result=false;
                                result = ParseData.handleConsumerResponse(responseText);
                                if (result) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            savaLoginStatus();
                                            LoginActivity.this.finish();
                                        }
                                    });
                                }
                            }
                        }
                    });
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.HOSPITAL_DATA, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText=response.body().string();
                            ParseData.handleHospitalResponse(responseText);
                        }
                    });
                }
                break;
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.tv_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.tv_find_psw:
                Intent intent1=new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent1);
            default:
                break;
        }
    }

    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        password=et_psw.getText().toString().trim();
    }

    private void savaLoginStatus(){
        sharedPreferencesUtil.insertData("username",userName);
        sharedPreferencesUtil.insertData("isLogin",true);
        Intent intent=new Intent();
        intent.putExtra("isLogin",true);
        setResult(RESULT_OK,intent);
    }

}
