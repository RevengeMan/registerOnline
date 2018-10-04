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

public class ModifyPswActivity extends AppCompatActivity {

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;
    private EditText et_original_psw,et_new_psw,et_new_psw_again;
    private Button btn_save;
    private String original_psw,new_psw,new_psw_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        initView();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改密码");
        btn_save=findViewById(R.id.btn_save);

        et_original_psw=findViewById(R.id.et_original_psw);
        et_new_psw=findViewById(R.id.et_new_psw);
        et_new_psw_again=findViewById(R.id.et_new_psw_again);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPswActivity.this.finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                Consumer consumer=new Consumer();
                consumer= LitePal.findAll(Consumer.class).get(0);
                if (TextUtils.isEmpty(original_psw)){
                    Toast.makeText(ModifyPswActivity.this,"请输入原始密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(new_psw)){
                    Toast.makeText(ModifyPswActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(new_psw_again)){
                    Toast.makeText(ModifyPswActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!new_psw.equals(new_psw_again)){
                    Toast.makeText(ModifyPswActivity.this,"输入两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if (!original_psw.equals(consumer.getCm_password())){
                    Toast.makeText(ModifyPswActivity.this,"原始密码有误",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String cm_id=String.valueOf(consumer.getCm_id());
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.UPDATE_PASSWORD, "cm_id", cm_id, "cm_password", new_psw, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    ContentValues values = new ContentValues();
                    //String recharge_money=String.valueOf(count);
                    values.put("cm_password", new_psw);
                    LitePal.updateAll(Consumer.class, values, "cm_id = ?", cm_id);
                    Toast.makeText(ModifyPswActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                    ModifyPswActivity.this.finish();
                }
            }
        });
    }

    private void getEditString(){
        original_psw=et_original_psw.getText().toString();
        new_psw=et_new_psw.getText().toString();
        new_psw_again=et_new_psw_again.getText().toString();
    }
}
