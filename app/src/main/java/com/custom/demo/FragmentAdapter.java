package com.custom.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.custom.demo.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Item> mItems;
    public FragmentAdapter(FragmentManager fm,List<Item> items) {
        super(fm);
        this.mItems=items;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        try {
            fragment= (Fragment) mItems.get(position).fragmentType.newInstance();
            Bundle bundle=new Bundle();
            bundle.putString(BaseFragment.BASE_DES,mItems.get(position).des);
            bundle.putString(BaseFragment.BASE_ADDRESS,mItems.get(position).url);
            fragment.setArguments(bundle);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if(fragment==null)
            throw new RuntimeException(mItems.get(position).getClass().getSimpleName()+" 新建实例时出错");
      }
        return fragment;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
}
