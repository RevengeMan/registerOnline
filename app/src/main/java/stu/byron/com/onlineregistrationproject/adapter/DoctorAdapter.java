package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Doctor;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

/**
 * Created by Byron on 2018/9/21.
 */

public class DoctorAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Doctor> doctorList;

    public DoctorAdapter(Context context,List<Doctor> doctors){
        inflater=LayoutInflater.from(context);
        this.doctorList=doctors;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final ViewHolder vh;
        if (convertView==null){
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.doctor_item,null);
            vh.iv_doctor=convertView.findViewById(R.id.iv_doctor_picture);
            vh.tv_name=convertView.findViewById(R.id.tv_doctor_name);
            vh.tv_section=convertView.findViewById(R.id.tv_dsection_name);
            vh.tv_level=convertView.findViewById(R.id.tv_level);
            vh.tv_description=convertView.findViewById(R.id.tv_simple_desciption);
            convertView.setTag(vh);
        }else
            vh= (ViewHolder) convertView.getTag();
        Doctor doctor=doctorList.get(position);
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+doctor.getDt_image(), new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what=1;
                vh.imageBytes = response.body().bytes();
                message.obj = vh;
                handler.sendMessage(message);
            }
        });
        vh.tv_name.setText(doctor.getDt_name());
        vh.tv_section.setText("等级");
        if (doctor.getDt_status()==0){
            vh.tv_level.setText("医师");
        }else {
            vh.tv_level.setText("主治医师");
        }
        vh.tv_description.setText("嗯 对 数据库还没写你的资料呢！");
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_doctor;
        private TextView tv_name;
        private TextView tv_section,tv_level;
        private TextView tv_description;
        private byte[] imageBytes;
    }
private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 1:
                ViewHolder vh= (ViewHolder) msg.obj;
                byte []picture=vh.imageBytes;
                Bitmap bitmap=BitmapFactory.decodeByteArray(picture,0,picture.length);
                vh.iv_doctor.setImageBitmap(bitmap);
                break;
        }
    }
    };
}
