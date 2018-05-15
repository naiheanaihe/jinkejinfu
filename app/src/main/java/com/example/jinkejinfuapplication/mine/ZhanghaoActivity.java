package com.example.jinkejinfuapplication.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.ToastUtil;

public class ZhanghaoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private EditText ed_name,ed_phone;
    private TextView text_qq,text_wx,text_wb;
    private TextView bangd_qq,bangd_wx,bangd_wb,xiugai_pw;
    private Button baocun;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zhanghao);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        ed_name=findView(R.id.ed_name);
        ed_phone=findView(R.id.ed_phone);
        text_qq=findView(R.id.text_qq);
        text_wx=findView(R.id.text_wx);
        text_wb=findView(R.id.text_wb);
        bangd_qq=findView(R.id.bangd_qq);
        bangd_wx=findView(R.id.bangd_wx);
        bangd_wb=findView(R.id.bangd_wb);
        xiugai_pw=findView(R.id.xiugai_pw);
        baocun=findView(R.id.baocun);
        bangd_qq.setOnClickListener(this);
        bangd_wx.setOnClickListener(this);
        bangd_wb.setOnClickListener(this);
        xiugai_pw.setOnClickListener(this);
        baocun.setOnClickListener(this);

        ed_name.setText(MyApplication.getInstance().getYonghuBean().getNickname());
        ed_phone.setText(MyApplication.getInstance().getYonghuBean().getMobilephone());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bangd_qq:
            case R.id.bangd_wx:
            case R.id.bangd_wb:
                ToastUtil.show(this,"暂未实现!");
                break;
            case R.id.xiugai_pw:
                Intent intent=new Intent(this,Xiugai_pwActivity.class);
                startActivity(intent);
                break;
        }
    }

}
