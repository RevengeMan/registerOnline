package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.CastHistory;

/**
 * Created by Byron on 2018/10/3.
 */

public class CastHistoryAdapter extends BaseAdapter {
    private List<CastHistory> castHistoryList;
    private LayoutInflater inflater;
    public CastHistoryAdapter(Context context,List<CastHistory> castHistoryList){
        this.castHistoryList=castHistoryList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return castHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return castHistoryList.get(position);
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
            convertView=inflater.inflate(R.layout.cast_history_item,null);
            vh.tv_style=convertView.findViewById(R.id.tv_style);
            vh.tv_money=convertView.findViewById(R.id.tv_expanse);
            vh.tv_data=convertView.findViewById(R.id.tv_date);
            convertView.setTag(vh);
        }
        vh= (ViewHolder) convertView.getTag();
        CastHistory castHistory=new CastHistory();
        castHistory=castHistoryList.get(position);
        if (castHistory.getCh_status()==1){
            vh.tv_style.setText("充值");
            vh.tv_style.setTextColor(Color.parseColor("#1afa29"));
            vh.tv_money.setText(String.valueOf(castHistory.getMoney()));
            vh.tv_money.setTextColor(Color.parseColor("#1afa29"));
        }else if (castHistory.getCh_status()==2){
            vh.tv_style.setText("付款");
            vh.tv_style.setTextColor(Color.parseColor("#d81e06"));
            vh.tv_money.setText(String.valueOf(castHistory.getMoney()));
            vh.tv_money.setTextColor(Color.parseColor("#d81e06"));
        }else if (castHistory.getCh_status()==3){
            vh.tv_style.setText("退款");
            vh.tv_style.setTextColor(Color.parseColor("#1afa29"));
            vh.tv_money.setText(String.valueOf(castHistory.getMoney()));
            vh.tv_money.setTextColor(Color.parseColor("#1afa29"));
        }
        vh.tv_data.setText(castHistory.getAdd_time());
        return convertView;
    }

    class ViewHolder{
        private TextView tv_style,tv_money,tv_data;
    }
}
