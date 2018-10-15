package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

/**
 * Created by Byron on 2018/10/9.
 */

public class ComentsActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title,tv_save;
    private EditText ed_callback;
    private AppointmentInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        initView();
        setListener();
        info=(AppointmentInfo) this.getIntent().getSerializableExtra("info");
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("问题反馈");
        tv_save=findViewById(R.id.tv_save);
        tv_save.setVisibility(View.VISIBLE);
        ed_callback=findViewById(R.id.et_callback);
        tv_save.setText("提交");
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                ComentsActivity.this.finish();
                break;
            case R.id.tv_save:
                String content=ed_callback.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(ComentsActivity.this,"评价内容",Toast.LENGTH_SHORT).show();
                    return;
                }else if (content.length()>50){
                    Toast.makeText(ComentsActivity.this,"最多五十字评价",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String at_id=String.valueOf(info.getAt_id());
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.COMMENTS, "at_comments",content,"at_id",at_id,"at_statu","3", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    Toast.makeText(ComentsActivity.this,"评价成功",Toast.LENGTH_SHORT).show();
                    AppointmentInfo info2=new AppointmentInfo();
                    info2.setAt_status(3);
                    info2.updateAll("at_id=?",String.valueOf(info.getAt_id()));
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    ComentsActivity.this.finish();
                }
                break;
            default:
                break;
        }
    }
}
