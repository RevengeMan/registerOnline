package stu.byron.com.onlineregistrationproject.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rl_title_bar,rl_sex;
    private TextView tv_back,tv_main_title,tv_save;

    private EditText  et_real_name,et_id_card;
    private TextView tv_sex,tv_phone,tv_nickName;
    private String spUserName;
    private String realName,idCard,sexString;

    private SharedPreferencesUtil sharedPreferencesUtil=SharedPreferencesUtil.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName=sharedPreferencesUtil.getInfo("username");
        initView();
        initData();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人信息");
        tv_save=findViewById(R.id.tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_back=findViewById(R.id.tv_back);

        tv_nickName=findViewById(R.id.et_nickName);
        et_real_name=findViewById(R.id.et_real_name);
        tv_sex=findViewById(R.id.tv_sex);
        tv_phone=findViewById(R.id.tv_mobile);
        rl_sex=findViewById(R.id.rl_sex);
        et_id_card=findViewById(R.id.id_card);
    }

    private void initData(){
        Consumer consumer=null;
        List<Consumer> consumerList= LitePal.where("cm_nickname=?",spUserName).find(Consumer.class);
        consumer=consumerList.get(0);
        setValue(consumer);
    }

    private void setValue(Consumer consumer){
        tv_nickName.setText(consumer.getNickname());
        et_real_name.setText(consumer.getCm_realname());
        et_id_card.setText(consumer.getCm_idnumber());
        tv_sex.setText(consumer.getSex());
        tv_phone.setText(consumer.getCm_phone());
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_sex:
                String sex=tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.tv_save:
                getEditString();
                Consumer consumer=new Consumer();
                consumer.setCm_realname(realName);
                consumer.setCm_idnumber(idCard);
                consumer.setSex(sexString);
                consumer.setAdd_time(TimeUtil.getNowTime());
                consumer.updateAll("cm_nickname=?",spUserName);
                List<Consumer> litepalConsumer= LitePal.where("cm_nickname=?",spUserName).find(Consumer.class);
                HashMap<String,String> map=PostMap.ConsumerPacking(litepalConsumer.get(0));
                HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.UPDATE, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText=response.body().string();
                        if (responseText.equals("success")) {
                            Log.d("UserInfoActivity", "onResponse: " + responseText.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Constant.showDialog(UserInfoActivity.this,"修改成功");
                                    UserInfoActivity.this.finish();
                                }
                            });
                        }
                    }

                });
                this.finish();
                break;
            default:
                break;
        }
    }

    private void getEditString(){
        realName=et_real_name.getText().toString().trim();
        idCard=et_id_card.getText().toString().trim();
        sexString=tv_sex.getText().toString().trim();
    }

    private void sexDialog(String sex){
        int sexFlag=0;
        if ("男".equals(sex)){
            sexFlag=0;
        }else if ("女".equals(sex)){
            sexFlag=1;
        }
        final  String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }
    private void setSex(String sex){
        tv_sex.setText(sex);
    }

}
