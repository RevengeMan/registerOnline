package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Section;

/**
 * Created by Byron on 2018/9/21.
 */

public class SectionAdapter extends BaseAdapter {
    private List<Section> sectionList;
    private LayoutInflater inflater;

    public SectionAdapter(Context context,List<Section> sectionList){
        this.sectionList=sectionList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return sectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return sectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.section_item,null);
            vh.tv_section_name=convertView.findViewById(R.id.tv_section_name);
            convertView.setTag(vh);
        }
        vh= (ViewHolder) convertView.getTag();
        vh.tv_section_name.setText(sectionList.get(position).getSc_name());
        return convertView;
    }

    class ViewHolder{
        private TextView tv_section_name;
    }
}
