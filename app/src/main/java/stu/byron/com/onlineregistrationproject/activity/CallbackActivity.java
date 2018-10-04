package stu.byron.com.onlineregistrationproject.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

public class CallbackActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title,tv_save;
    private EditText ed_callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        initView();
        setListener();
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
                CallbackActivity.this.finish();
                break;
            case R.id.tv_save:
                String content=ed_callback.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(CallbackActivity.this,"反馈内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Consumer consumer=new Consumer();
                    consumer= LitePal.findAll(Consumer.class).get(0);
                    String cm_id=String.valueOf(consumer.getCm_id());
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.CALLBACK, "content",content,"cm_id",cm_id, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    Toast.makeText(CallbackActivity.this,"您的建议是我们成功的基石",Toast.LENGTH_SHORT).show();
                    CallbackActivity.this.finish();
                }
                break;
                default:
                    break;
        }
    }
}
