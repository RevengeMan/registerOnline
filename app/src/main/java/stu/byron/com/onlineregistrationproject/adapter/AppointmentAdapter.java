package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Appointment;
import stu.byron.com.onlineregistrationproject.bean.AppointmentInfo;

/**
 * Created by Byron on 2018/9/25.
 */

public class AppointmentAdapter extends BaseAdapter {
    private List<AppointmentInfo> appointmentList;
    private LayoutInflater inflater;
    public AppointmentAdapter(Context context,List<AppointmentInfo> appointments){
        this.appointmentList=appointments;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return appointmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return appointmentList.get(position);
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
            convertView=inflater.inflate(R.layout.appointment_info_item,null);
            vh.tv_addTime=convertView.findViewById(R.id.add_time);
            vh.tv_hospital_name=convertView.findViewById(R.id.data_hospital);
            vh.tv_section_name=convertView.findViewById(R.id.data_section);
            vh.tv_doctor_name=convertView.findViewById(R.id.data_doctor);
            vh.tv_data_time=convertView.findViewById(R.id.data_time);
            vh.tv_operator=convertView.findViewById(R.id.tv_operator);
            convertView.setTag(vh);
        }
        vh= (ViewHolder) convertView.getTag();
        AppointmentInfo info=appointmentList.get(position);
        vh.tv_addTime.setText(info.getAdd_time());
        vh.tv_hospital_name.setText(info.getHp_name());
        vh.tv_section_name.setText(info.getSc_name());
        vh.tv_doctor_name.setText(info.getDt_name());
        vh.tv_data_time.setText(info.getAt_time());
        if (info.getAt_status()==0){
            vh.tv_operator.setText("去付款");
        }else if (info.getAt_status()==1){
            vh.tv_operator.setText("等待服务");
        }else if (info.getAt_status()==2){
            vh.tv_operator.setText("去评价");
        }else if (info.getAt_status()==3){
            vh.tv_operator.setText("已评价");
        }
        return convertView;
    }

    class ViewHolder{
        private TextView tv_addTime,tv_hospital_name,tv_section_name,tv_doctor_name,tv_data_time,tv_operator;
    }
}
