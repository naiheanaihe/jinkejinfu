package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jinkejinfuapplication.R;

/**
 * Created by naihe on 2017/12/22.
 */

public class Spinner_huatiAdapater extends BaseAdapter{
    private String[] mList;
    private Context mContext;

    public Spinner_huatiAdapater(Context mContext, String[] mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.simple_spinner_dropdown_myitem2,null);
        if(convertView!=null) {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.spinner_text);
            _TextView1.setText(mList[position]);
        }
        return convertView;
    }
}
