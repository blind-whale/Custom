package com.custom.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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

    public static class Item{
        public int layoutId;
        public Class fragmentType;
        public Item(int id,Class type){
            this.layoutId=id;
            this.fragmentType=type;
        }
    }
}
