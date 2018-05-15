package com.example.jinkejinfuapplication.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

public class ShezhiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private LinearLayout lin_zhanghao,lin_kefu,lin_fankui,lin_shenming,lin_guanyu;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shezhi);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        lin_zhanghao=findView(R.id.lin_zhanghao);
        lin_kefu=findView(R.id.lin_kefu);
        lin_fankui=findView(R.id.lin_fankui);
        lin_shenming=findView(R.id.lin_shenming);
        lin_guanyu=findView(R.id.lin_guanyu);
        lin_zhanghao.setOnClickListener(this);
        lin_kefu.setOnClickListener(this);
        lin_fankui.setOnClickListener(this);
        lin_shenming.setOnClickListener(this);
        lin_guanyu.setOnClickListener(this);
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
        Intent intent;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.lin_zhanghao:
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent=new Intent(this,ZhanghaoActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_kefu:
                intent=new Intent(this,KefuActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_shenming:
                intent=new Intent(this,ShenmingActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_guanyu:
                intent=new Intent(this,GuanyuActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
