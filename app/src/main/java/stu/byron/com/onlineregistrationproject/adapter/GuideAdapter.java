package stu.byron.com.onlineregistrationproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Byron on 2018/9/16.
 */

public class GuideAdapter extends PagerAdapter {
    private List<View> viewList;

    public GuideAdapter(List<View> viewList){
        this.viewList=viewList;
    }
    @Override
    public int getCount() {
        if (viewList!=null){
            return viewList.size();
        }else {
            return 0;
        }
    }

    //判断对象是否生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //初始化position的界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
