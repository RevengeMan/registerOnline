package stu.byron.com.onlineregistrationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import stu.byron.com.onlineregistrationproject.bean.Message;

/**
 * Created by Byron on 2018/10/10.
 */

public class MessageAdapter extends BaseAdapter {
    private List<Message> messageList;
    private LayoutInflater inflater;
    public MessageAdapter(Context context,List<Message> messageList){
        this.messageList=messageList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder{

    }
}
