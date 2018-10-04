package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Patient;

/**
 * Created by Byron on 2018/9/23.
 */

public class PatientAdapter extends BaseAdapter {
    private List<Patient> patientList;
    private LayoutInflater inflater;
    public PatientAdapter(Context context,List<Patient> patientList){
        this.patientList=patientList;
        inflater=LayoutInflater.from(context);
    }
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientList.get(position);
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
            convertView=inflater.inflate(R.layout.patient_item,null);
            vh.tv_patient_name=convertView.findViewById(R.id.tv_patient_name);
            vh.tv_patient_sex=convertView.findViewById(R.id.tv_patient_sex);
            convertView.setTag(vh);
        }
        vh= (ViewHolder) convertView.getTag();
        vh.tv_patient_sex.setText(patientList.get(position).getPt_sex());
        vh.tv_patient_name.setText(patientList.get(position).getPt_name());
        return convertView;
    }

    class ViewHolder{
        TextView tv_patient_name,tv_patient_sex;
    }
}
