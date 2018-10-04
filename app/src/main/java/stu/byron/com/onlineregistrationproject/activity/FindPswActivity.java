package stu.byron.com.onlineregistrationproject.activity;

import android.content.ContentValues;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

public class FindPswActivity extends AppCompatActivity {

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private EditText et_validate_name;
    private TextView tv_reset_psw;
    private Button btn_validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        initView();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("找回密码");
        tv_back=findViewById(R.id.tv_back);

        et_validate_name=findViewById(R.id.et_validate_name);
        tv_reset_psw=findViewById(R.id.tv_reset_psw);
        btn_validate=findViewById(R.id.btn_validate);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPswActivity.this.finish();
            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=et_validate_name.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(FindPswActivity.this,"请输入注册号码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.FIND_PASSWORD, "phone", phone, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String reponseText = response.body().string();
                            if (reponseText.equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_reset_psw.setVisibility(View.VISIBLE);
                                        tv_reset_psw.setText("初始密码：123456");
                                       /* Consumer consumer = new Consumer();
                                        consumer = LitePal.findAll(Consumer.class).get(0);
                                        String cm_id = String.valueOf(consumer.getCm_id());
                                        ContentValues values = new ContentValues();
                                        //String recharge_money=String.valueOf(count);
                                        values.put("cm_password", "123456");
                                        LitePal.updateAll(Consumer.class, values, "cm_id = ?", cm_id);*/
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(FindPswActivity.this, "此号码未注册过", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                    });
                }
            }
        });
    }
}
