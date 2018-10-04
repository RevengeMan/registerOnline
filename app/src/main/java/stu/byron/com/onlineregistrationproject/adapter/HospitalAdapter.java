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
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

/**
 * Created by Byron on 2018/9/20.
 */

public class HospitalAdapter extends BaseAdapter {
    private List<Hospital> hospitalList;
    private LayoutInflater inflater;

    public HospitalAdapter(Context context, List<Hospital> hospitals){
        this.hospitalList=hospitals;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return hospitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitalList.get(position);
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
            convertView=inflater.inflate(R.layout.hospital_item,null);
            vh.iv_hospital=convertView.findViewById(R.id.iv_hospital);
            vh.tv_hospital_name=convertView.findViewById(R.id.tv_hospital_name);
            vh.tv_hospital_level=convertView.findViewById(R.id.tv_hospital_level);
            vh.tv_data_count=convertView.findViewById(R.id.tv_data_count);
            convertView.setTag(vh);
        }else
        vh= (ViewHolder) convertView.getTag();
        Hospital hospital=hospitalList.get(position);
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+hospital.getHp_image(), new okhttp3.Callback() {
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
        vh.tv_hospital_name.setText(hospital.getHp_name());
        vh.tv_hospital_level.setText("三甲");
        vh.tv_data_count.setText("100");
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_hospital;
        private TextView tv_hospital_name,tv_hospital_level,tv_data_count;
        private byte[] imageBytes;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ViewHolder vh= (ViewHolder) msg.obj;
                    byte[] pictures=vh.imageBytes;
                    Bitmap bitmap=BitmapFactory.decodeByteArray(pictures,0,pictures.length);
                    vh.iv_hospital.setImageBitmap(bitmap);
                    break;
            }
        }
    };

}
