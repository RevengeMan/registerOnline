package stu.byron.com.onlineregistrationproject.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.util.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import stu.byron.com.onlineregistrationproject.R;
import stu.byron.com.onlineregistrationproject.bean.Consumer;
import stu.byron.com.onlineregistrationproject.bean.Message;
import stu.byron.com.onlineregistrationproject.db.SharedPreferencesUtil;
import stu.byron.com.onlineregistrationproject.util.Constant;
import stu.byron.com.onlineregistrationproject.util.HttpUtil;
import stu.byron.com.onlineregistrationproject.util.ParseData;
import stu.byron.com.onlineregistrationproject.view.SideslipListView;

/**
 * Created by Byron on 2018/9/17.
 */

public class MessageFragment extends Fragment {
    View view;
    private static final String TAG = "MainActivity";
    private SideslipListView mSideslipListView;
    private List<Message> messageList;
    private List<Message> mDataList=new ArrayList<>();

    private RelativeLayout rl_title_bar;
    private TextView tv_back,tv_main_title;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private Boolean isLogin=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_message_layout,container,false);
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getActivity());
        isLogin=sharedPreferencesUtil.getResult("isLogin");
        if (isLogin) {
            initView();
            initData();
            mSideslipListView = (SideslipListView) view.findViewById(R.id.sideslipListView);
            mSideslipListView.setAdapter(new CustomAdapter());//设置适配器
            setListener();
        }else {
            Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void initView(){
        rl_title_bar=view.findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back=view.findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_main_title=view.findViewById(R.id.tv_main_title);
        tv_main_title.setText("消息");
    }

    private void initData(){
        queryFormServe();
        mDataList=LitePal.findAll(Message.class);
    }

    private void queryFormServe(){
        Consumer consumer=new Consumer();
        consumer= LitePal.findAll(Consumer.class).get(0);
        HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.GET_MESSAGE, "cm_id", String.valueOf(consumer.getCm_id()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                Boolean result=false;
                result=ParseData.handleMessageResponse(responseText);
                if (result){
                    queryMessage();
                }
            }
        });
    }

    private void queryMessage(){
        messageList=LitePal.findAll(Message.class);
        if (messageList.size()>0){
            mDataList.clear();
            for (Message message:messageList){
                mDataList.add(message);
            }
            //mSideslipListView.deferNotifyDataSetChanged();
            //mSideslipListView.setSelection(0);
        }else {
            queryFormServe();
        }
    }

    private void setListener(){
        //设置item点击事件
        mSideslipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (mSideslipListView.isAllowItemClick()) {
                    Log.i(TAG, mDataList.get(position) + "被点击了");
                    Toast.makeText(getActivity(), mDataList.get(position) + "被点击了",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //设置item长按事件
        mSideslipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                if (mSideslipListView.isAllowItemClick()) {
                    Log.i(TAG, mDataList.get(position) + "被长按了");
                    Toast.makeText(getActivity(), mDataList.get(position) + "被长按了",
                            Toast.LENGTH_SHORT).show();
                    return true;//返回true表示本次事件被消耗了，若返回
                }
                return false;
            }
        });
    }

    /**
     * 自定义ListView适配器
     */
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                convertView = View.inflate(getActivity(), R.layout.message_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                viewHolder.tv_date=convertView.findViewById(R.id.tv_date);
                viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_content.setText(mDataList.get(position).getMsg_content());
            viewHolder.tv_date.setText(mDataList.get(position).getAdd_time());
            final int pos = position;
            final int msg_id=mDataList.get(position).getMsg_id();
            viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "消息已删除",
                            Toast.LENGTH_SHORT).show();
                    mDataList.remove(pos);
                    LitePal.deleteAll(Message.class,"msg_id=?",String.valueOf(msg_id));
                    HttpUtil.sendOkHttpRequest(Constant.BASE_PATH + Constant.DELETE_MESSAGE, "msg_id",String.valueOf(msg_id) , new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                    notifyDataSetChanged();
                    mSideslipListView.turnNormal();
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        public TextView tv_content,tv_date;
        public TextView txtv_delete;
    }
}
