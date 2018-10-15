package com.example.myceshi;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    Button mBtton;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtton= (Button) findViewById(R.id.button);
        img= (ImageView) findViewById(R.id.img);
        mBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ObjectAnimator animator=ObjectAnimator.ofFloat(mBtton,"rotation", 0, 180);
                animator.setDuration(2000).start();*/
                /*ValueAnimator animator=ValueAnimator.ofFloat(0,400,500);
                animator.setTarget(mBtton);
                animator.setDuration(2000).start();
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        *//*int value= (int) animation.getAnimatedValue();
                        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) mBtton.getLayoutParams();
                        layoutParams.setMargins(value,0,0,0);
                        mBtton.setLayoutParams(layoutParams);*//*
                        mBtton.setTranslationX((Float) animation.getAnimatedValue());
                    }
                });*/
                /*ObjectAnimator animator1=ObjectAnimator.ofFloat(mBtton,"rotation",0,360*14);
                ObjectAnimator animator2=ObjectAnimator.ofFloat(mBtton,"translationX",0,700,0);
                ObjectAnimator animator5=ObjectAnimator.ofFloat(mBtton,"translationY",0,300,0,300,0);
                ObjectAnimator animator3=ObjectAnimator.ofFloat(mBtton,"scaleX",1,2,1);
                ObjectAnimator animator4=ObjectAnimator.ofFloat(mBtton,"scaleY",1,2,1);
                AnimatorSet animatorSet=new AnimatorSet();
                animatorSet.setDuration(3000);
                animatorSet.playTogether(animator1,animator2,animator3,animator4,animator5);
                animatorSet.start();*/
                Intent intent=new Intent(MainActivity.this,Test1Activity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,img,"share").toBundle());
                }else {
                    startActivity(intent);
                }

            }
        });


    }
}
