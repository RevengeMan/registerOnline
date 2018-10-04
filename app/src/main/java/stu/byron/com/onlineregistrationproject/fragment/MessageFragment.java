package stu.byron.com.onlineregistrationproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stu.byron.com.onlineregistrationproject.R;

/**
 * Created by Byron on 2018/9/17.
 */

public class MessageFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_message_layout,container,false);
        return view;
    }
}
