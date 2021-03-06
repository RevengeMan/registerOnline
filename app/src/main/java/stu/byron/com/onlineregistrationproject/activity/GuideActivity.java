package stu.byron.com.onlineregistrationproject.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.GuideAdapter;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager vp;
    private int []imageIdArray;  //图片资源的数组
    private List<View> viewList; //图片资源的集合
    private ViewGroup vg;       //放值圆点

    //实例化圆点View
    private ImageView iv_point;
    private ImageView []ivPointArray;

    //最后一页的按钮
    private Button ib_start;

    //标记是否第一次进入
    private Boolean isFirst=false;
    private SharedPreferencesUtil sharedPreferencesUtil=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将bar隐藏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //判断是否第一次进入
        sharedPreferencesUtil=SharedPreferencesUtil.getInstance(GuideActivity.this);
        isFirst=sharedPreferencesUtil.getResult("isFirst");
        if (!isFirst){
            sharedPreferencesUtil.insertData("isFirst",true);
            setContentView(R.layout.activity_guide);
            //加载ViewPager
            initViewPager();
            //加载底部圆点
            initPoint();
            //加载最后一页按钮监听
            setListener();
        }else {
            Intent intent=new Intent(GuideActivity.this,SplashActivity.class);
            startActivity(intent);
        }
    }

    private void initViewPager(){
        //实例化引导界面
        vp=findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray=new int[]{R.drawable.guide1,R.drawable.guide2,R.drawable.guide3};
        viewList=new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len=imageIdArray.length;
        for(int i=0;i<len;i++){
            //new ImageView并设置全屏和图片资源
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
        }
        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuideAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }

    private void initPoint(){
        //实例化圆点Linearlayout
        vg=findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray=new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size=viewList.size();
        for (int i=0;i<size;i++){
            iv_point=new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(15,15));
            iv_point.setPadding(30,0,30,0);
            ivPointArray[i]=iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i==0){
                iv_point.setBackgroundResource(R.drawable.full_hololy);
            }else {
                iv_point.setBackgroundResource(R.drawable.empty_hololy);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }
    }

    private void setListener(){
        ib_start=findViewById(R.id.guide_ib_start);
        ib_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //滑动后的监听
    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length=imageIdArray.length;
        for (int i=0;i<length;i++){
            ivPointArray[position].setBackgroundResource(R.drawable.full_hololy);
            if (position!=i){
                ivPointArray[i].setBackgroundResource(R.drawable.empty_hololy);
            }

            //判断是否是最后一页，若是则显示按钮
            if (position==imageIdArray.length-1){
                ib_start.setVisibility(View.VISIBLE);
            }else {
                ib_start.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
