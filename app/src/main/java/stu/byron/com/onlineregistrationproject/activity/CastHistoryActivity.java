package stu.byron.com.onlineregistrationproject.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.CastHistoryAdapter;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;

public class CastHistoryActivity extends AppCompatActivity {
    private List<CastHistory> dataList=new ArrayList<CastHistory>();
    private List<CastHistory> castHistoryList;
    private ListView listView;
    private CastHistoryAdapter adapter;

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_history);
        initView();
        initData();
        setAdapter();
        setListener();
    }

    private void initView(){
        listView=findViewById(R.id.cast_listView);
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("消费记录");
    }

    private void initData(){
        queryFromServer();
    }

    private void setAdapter(){
        adapter=new CastHistoryAdapter(CastHistoryActivity.this,dataList);
        listView.setAdapter(adapter);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CastHistoryActivity.this.finish();
            }
        });
    }

    private void queryHistory(){
        castHistoryList=LitePal.findAll(CastHistory.class);
        if (castHistoryList.size()>0){
            dataList.clear();;
            for (CastHistory castHistory:castHistoryList){
                dataList.add(castHistory);
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        }else {
            queryFromServer();
        }
    }

    private void queryFromServer(){
        HashMap<String,String> map=new HashMap<>();
        Consumer consumer=new Consumer();
        consumer= LitePal.findAll(Consumer.class).get(0);
        String cm_id=String.valueOf(consumer.getCm_id());
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.GET_HISTORY, "cm_id", cm_id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                boolean result=false;
                result= ParseData.handleCastHistoryInfoResponse(responseText);
                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryHistory();
                        }
                    });
                }
            }
        });
    }
}
