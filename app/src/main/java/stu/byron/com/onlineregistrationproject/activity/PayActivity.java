package stu.byron.com.onlineregistrationproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.PostMap;
import stu.byron.com.onlineregistrationproject.util.TimeUtil;

public class PayActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;
    private TextView tv_hp_name,tv_sc_name,tv_dc_name,tv_pt_name,tv_data_time,tv_description,tv_expense;
    private Button btn_pay;

    private AppointmentInfo info;
    //private String hp_name,sc_name,dc_name,pt_name,data_time,description,expense;

    private View inflate;
   /* private TextView choosePhoto;
    private TextView takePhoto;*/
   private TextView expense;
   private Button pay_for;
   private Button pay,cancel;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        info=(AppointmentInfo) this.getIntent().getSerializableExtra("info");
        initView();
        //setListener();
    }

    private void initView(){
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("订单信息");
        //pay=findViewById(R.id.pay_for);
        cancel=findViewById(R.id.btn_cancel);
        //pay.setOnClickListener(this);
        cancel.setOnClickListener(this);

        tv_hp_name=findViewById(R.id.tv_hp_name);
        tv_sc_name=findViewById(R.id.tv_sc_name);
        tv_dc_name=findViewById(R.id.tv_dc_name);
        tv_pt_name=findViewById(R.id.tv_pt_name);
        tv_data_time=findViewById(R.id.tv_data_time);
        tv_description=findViewById(R.id.tv_description);
        tv_expense=findViewById(R.id.tv_expense);
        pay=findViewById(R.id.btn_pay);

        tv_hp_name.setText(info.getHp_name());
        tv_sc_name.setText(info.getSc_name());
        tv_dc_name.setText(info.getDt_name());
        tv_pt_name.setText(info.getPt_name());
        tv_data_time.setText(info.getAt_time());
        tv_description.setText(info.getDescription());
        tv_expense.setText("30.00");
        tv_back.setOnClickListener(this);
        if (info.getAt_status()!=0){
            //cancel.setVisibility(View.GONE);
            pay.setVisibility(View.GONE);
        }
    }

    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.pay_dialog_layout, null);
        //初始化控件
        /*choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);*/
        expense=inflate.findViewById(R.id.tv_expense_detail);
        expense.setText("30.00");
        pay_for=inflate.findViewById(R.id.pay_for);
        //将布局设置给Dialog
        pay_for.setOnClickListener(this);
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//    将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }


    @Override
    public void onClick(View v) {
        Consumer consumer=new Consumer();
        consumer= LitePal.findAll(Consumer.class).get(0);
        switch (v.getId()){
            case R.id.tv_back:
                //dialog.dismiss();
                PayActivity.this.finish();
                break;
            case R.id.pay_for:
                //支付费用，改变状态
                final double money=Double.parseDouble(expense.getText().toString());
                double count=consumer.getCm_count()-money;
                if (count>=0){
                    String cm_id=String.valueOf(consumer.getCm_id());
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.PAY_FOR, "at_id", String.valueOf(info.getAt_id()), "count", String.valueOf(count),"cm_id",String.valueOf(consumer.getCm_id()), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    CastHistory castHistory=new CastHistory();
                    castHistory.setMoney(money);
                    castHistory.setCm_id(consumer.getCm_id());
                    castHistory.setCh_status(2);
                    castHistory.setAdd_time(TimeUtil.getNowTime());
                    HashMap<String , String> map=new HashMap<>();
                    map= PostMap.CastHistoryPacking(castHistory);
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.INSERT_CASTHISTORY, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    ContentValues values = new ContentValues();
                    String recharge_money=String.valueOf(count);
                    values.put("cm_count", count);
                    LitePal.updateAll(Consumer.class, values, "cm_id = ?", cm_id);
                    Intent intent=new Intent();
                    setResult(RESULT_OK,intent);
                    dialog.dismiss();
                    PayActivity.this.finish();
                }else {
                    Toast.makeText(PayActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                //取消预约
                AlertDialog.Builder dialog=new AlertDialog.Builder(PayActivity.this);
                dialog.setTitle("取消");
                dialog.setMessage("是否取消预约");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Test外面", "onClick: "+info.getAt_status() );
                        if (info.getAt_status()==1){
                            //退款状态为2
                            Log.e("Test", "onClick: "+info.getAt_status() );
                            Consumer consumer1=new Consumer();
                            consumer1= LitePal.findAll(Consumer.class).get(0);
                            double money=Double.parseDouble("30.0");
                            CastHistory castHistory=new CastHistory();
                            castHistory.setCh_status(3);
                            castHistory.setCm_id(consumer1.getCm_id());
                            castHistory.setMoney(money);
                            castHistory.setAdd_time(TimeUtil.getNowTime());
                            HashMap<String,String > map=new HashMap<>();
                            map=PostMap.CastHistoryPacking(castHistory);
                            HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.INSERT_CASTHISTORY, map, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                }
                            });
                        }
                        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+Constant.CANCEL_APPOINTMENT, "at_id", String.valueOf(info.getAt_id()), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                        Toast.makeText(PayActivity.this,"取消预约成功",Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        PayActivity.this.finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PayActivity.this,"您已放弃了取消",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
        }
    }
}
