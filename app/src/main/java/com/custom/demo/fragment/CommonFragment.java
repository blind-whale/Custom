package com.custom.demo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.custom.demo.R;
import com.custom.views.v1.CountDownView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CountDownView countDownView1,countDownView2,
            countDownView3,countDownView4,
            countDownView5,countDownView6;

    public CommonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommonFragment newInstance(String param1, String param2) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common, container, false);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void init(View view){
        super.init(view);
        countDownView1=view.findViewById(R.id.count_down_view1);
        countDownView2=view.findViewById(R.id.count_down_view2);
        countDownView3=view.findViewById(R.id.count_down_view3);
        countDownView4=view.findViewById(R.id.count_down_view4);
        countDownView5=view.findViewById(R.id.count_down_view5);
        countDownView6=view.findViewById(R.id.count_down_view6);
        view.findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownView1.start();
                countDownView2.start();
                countDownView3.start();
                countDownView4.start();
                countDownView5.start();
                countDownView6.start();
            }
        });

        countDownView1.setOnTimeOverListener(new CountDownView.OnTimeOverListener() {
            @Override
            public void onTimeOver() {
                Toast.makeText(getContext(),"倒计时终止",Toast.LENGTH_LONG);
            }
        });


    }
}
