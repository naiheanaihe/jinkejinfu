package com.example.jinkejinfuapplication.xiangq;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppStatusConstant;
import com.example.jinkejinfuapplication.base.AppStatusManager;
import com.example.jinkejinfuapplication.utils.LalaLog;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {
    private ImageView main_bk;
    public static StartActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL);//进入应用初始化设置成未登录状态
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        LalaLog.d("重启！","重启");
        instance=this;
        main_bk= (ImageView) findViewById(R.id.main_bk);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        main_bk.setVisibility(View.GONE);
                        Intent intent=new Intent(StartActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        },1000);
    }


}
