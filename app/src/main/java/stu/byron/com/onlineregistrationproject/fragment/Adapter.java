package stu.byron.com.onlineregistrationproject.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.adapter.HospitalAdapter;
import stu.byron.com.onlineregistrationproject.bean.Hospital;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;

/**
 * Created by chenxiaoping on 2017/3/28.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private List<Hospital> hospitalList;
    private int[] mColors = {R.mipmap.item1, R.mipmap.item2,R.mipmap.item3,R.mipmap.item4,
            R.mipmap.item5,R.mipmap.item6};

    public onItemClick clickCb;

    public Adapter(Context c) {
        mContext = c;
    }

    public Adapter(Context c, onItemClick cb, List<Hospital> hospitalList) {
        mContext = c;
        clickCb = cb;
        this.hospitalList=hospitalList;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Hospital hospital=hospitalList.get(position%hospitalList.size());
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH+hospital.getHp_image(), new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what=1;
                holder.imageBytes = response.body().bytes();
                message.arg1=position;
                message.obj = holder;
                handler.sendMessage(message);
            }
        });
        /*Glide.with(mContext).load(mColors[position % mColors.length])
                .into(holder.img);*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
                if (clickCb != null) {
                    clickCb.clickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private byte[] imageBytes;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    interface onItemClick {
        void clickItem(int pos);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ViewHolder vh= (ViewHolder) msg.obj;
                    byte[] pictures=vh.imageBytes;
                    Bitmap bitmap= BitmapFactory.decodeByteArray(pictures,0,pictures.length);
                    /*Glide.with(mContext).load(mColors[msg.arg1 % hospitalList.size()])
                            .into(vh.img);*/
                    Glide.with(mContext).load(pictures)
                            .into(vh.img);
                    break;
            }
        }
    };
}
