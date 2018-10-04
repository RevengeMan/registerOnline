package stu.byron.com.onlineregistrationproject.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private EditText recharge_money;
    private Button btn_recharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("充值");

        recharge_money=findViewById(R.id.recharge_money);
        btn_recharge=findViewById(R.id.btn_recharge);
    }

    private void setListener(){
        tv_back.setOnClickListener(this);
        btn_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                RechargeActivity.this.finish();
                break;
            case R.id.btn_recharge:
                String chargeMoney=recharge_money.getText().toString().trim();
                double money=Double.parseDouble(chargeMoney);
                Consumer consumer=new Consumer();
                consumer= LitePal.findAll(Consumer.class).get(0);
                double totalMoney=money+consumer.getCm_count();
                CastHistory castHistory=new CastHistory();
                castHistory.setCm_id(consumer.getCm_id());
                castHistory.setCh_status(1);
                castHistory.setMoney(money);
                castHistory.setAdd_time(TimeUtil.getNowTime());
                HashMap<String,String> map=new HashMap<>();
                map= PostMap.CastHistoryPacking(castHistory);
                HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.INSERT_CASTHISTORY, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.RECHARGE, "cm_id", String.valueOf(consumer.getCm_id()),"count",String.valueOf(totalMoney),new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
                /*Consumer consumer1=new Consumer();
                consumer1.setCm_count(money);*/
                String cm_id=String.valueOf(consumer.getCm_id());
                ContentValues values = new ContentValues();
                String recharge_money=String.valueOf(money);
                values.put("cm_count", totalMoney);
                LitePal.updateAll(Consumer.class, values, "cm_id = ?", cm_id);
//                consumer1.updateAll("cm_id=？",cm_id);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                RechargeActivity.this.finish();
                break;
        }
    }
}
