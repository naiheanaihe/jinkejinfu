package com.example.jinkejinfuapplication.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.utils.DisplayUtil;

/**
 * @author (●—●)
 *
 * @data 2015-12-15下午3:36:00
 *
 * @describe
 */
public class TextSwitchView extends TextSwitcher implements ViewFactory{
    private int index= -1;
    private Context context;
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        };
    };
    private List<String> resources=new ArrayList<>();
    private Timer timer; //
    public TextSwitchView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        if(timer==null)
            timer = new Timer();
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }
    public void setResources( List<String> res){
        this.resources = res;
    }
    public void setTextStillTime(long time){
        if(timer==null){
            timer = new Timer();
        }else{
            timer.scheduleAtFixedRate(new MyTask(), 1, time);//每3秒更新
        }
    }
    private class MyTask extends TimerTask{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }
    private int next(){
        int flag = index+1;
        if(flag>resources.size()-1){
            flag=flag-resources.size();
        }
        return flag;
    }
    private void updateText(){
        this.setText(resources.get(index));
    }
    @Override
    public View makeView() {
        TextView tv =new TextView(context);
        tv.setTextSize(13);
        tv.setTextColor(Color.BLACK);
        FrameLayout.LayoutParams layoutParams= new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(layoutParams);
        return tv;
    }
}
