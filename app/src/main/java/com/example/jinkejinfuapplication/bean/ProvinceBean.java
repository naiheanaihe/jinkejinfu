package com.example.jinkejinfuapplication.bean;

/**
 * Created by zhangpan on 2016/7/2.
 */
public class ProvinceBean {
    private String name,ids;


    public ProvinceBean(String name) {
        this.name = name;
    }

    public ProvinceBean(String name, String ids) {
        this.name = name;
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return this.name;
    }


}
