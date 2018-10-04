package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;

public class MyCountActivity extends AppCompatActivity {
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private TextView tv_last_money;
    private RelativeLayout rl_recharge,rl_expanse_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_count);
        initView();
        initData();
        setListener();
    }
    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        tv_back=findViewById(R.id.tv_back);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("我的账户");

        tv_last_money=findViewById(R.id.last_money);
        rl_recharge=findViewById(R.id.rl_recharge);
        rl_expanse_history=findViewById(R.id.rl_expense_history);
    }

    private void initData(){
        Consumer consumer=new Consumer();
        consumer= LitePal.findAll(Consumer.class).get(0);
        tv_last_money.setText(String.valueOf(consumer.getCm_count()));
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCountActivity.this.finish();
            }
        });

        rl_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //充值界面
                Intent intent=new Intent(MyCountActivity.this,RechargeActivity.class);
                startActivityForResult(intent,1);
            }
        });

        rl_expanse_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //消费记录
                Intent intent=new Intent(MyCountActivity.this, CastHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
