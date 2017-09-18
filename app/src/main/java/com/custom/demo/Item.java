package com.custom.demo;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Item {
    public int layoutId;
    public Class fragmentType;
    public String des;
    public String url;
    public Item(int id,Class type,String author,String url){
        this.layoutId=id;
        this.fragmentType=type;
        this.des =author;
        this.url=url;
    }
}
