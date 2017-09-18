package com.custom.demo.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.custom.demo.R;

/**
 * Created by Administrator on 2017/9/18.
 */

public class BaseFragment extends Fragment {
    public static final String BASE_DES="BASE_DES";
    public static final String BASE_ADDRESS="BASE_ADDRESS";

    public void init(View view){
        TextView desTv=view.findViewById(R.id.tv_des);
        desTv.setText(getArguments().getString(BASE_DES));
        TextView addressTv=view.findViewById(R.id.tv_address);
        addressTv.setText("地址："+getArguments().getString(BASE_ADDRESS));
    }
}
