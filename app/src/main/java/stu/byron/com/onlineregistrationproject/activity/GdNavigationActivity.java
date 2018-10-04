package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.io.File;
import java.net.URISyntaxException;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.bean.StartLocation;

public class GdNavigationActivity extends AppCompatActivity {
    private RelativeLayout rl_navigation;
    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;
    private StartLocation location;
    private Hospital hospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd_navigation);
        hospital=(Hospital) this.getIntent().getSerializableExtra("hospital");
       /* Intent getData=new Intent(GdNavigationActivity.this,NavigationActivity.class);
        startActivityForResult(getData,1);*/
        initView();
        initData();
        setListener();
    }

    private void initView(){
        rl_navigation=findViewById(R.id.rl_navigation_gaode);
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("选择导航软件");
    }

    private void initData(){
        location= LitePal.findAll(StartLocation.class).get(0);
    }

    private void setListener(){
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LitePal.deleteAll(StartLocation.class);
                GdNavigationActivity.this.finish();
            }
        });

        rl_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpGaodeAppByLoca();
            }
        });
    }

    /**
     * 确定起终点坐标BY高德
     */
    void setUpGaodeAppByLoca(){
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&slat="+location.getLatitude()+"&slon="+location.getLongitude()+"&sname="+location.getName()+"&dlat="+hospital.getHp_latitude()+"&dlon="+hospital.getHp_longitude()+"&dname="+hospital.getHp_name()+"&dev=0&m=0&t=1");
            if(isInstallByread("com.autonavi.minimap")){
                startActivity(intent);
                Log.e("Gaode", "高德地图客户端已经安装") ;
            }else {
                Log.e("Gaode", "没有安装高德地图客户端") ;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
