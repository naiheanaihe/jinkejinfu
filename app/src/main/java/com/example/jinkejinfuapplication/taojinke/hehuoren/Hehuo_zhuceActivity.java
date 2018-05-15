package com.example.jinkejinfuapplication.taojinke.hehuoren;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;

public class Hehuo_zhuceActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    protected final int HEHUO1_FLAG = 0x101;
    protected final int HEHUO2_FLAG = 0x102;
    private Hehuo_zhuceFragment hehuo_zhuceFragment;
    private Hehuofaqi_zhuceFragment hehuofaqi_zhuceFragment;
    private RadioGroup mGroup;
    private FragmentManager mFarg;
    private FragmentTransaction ft;
    public static int hehuotype=1;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hehuo_zhuce);
        setTitle("合伙人注册");

        mGroup=findView(R.id.group_hehuo);
        mGroup.setOnCheckedChangeListener(this);
        mFarg=getSupportFragmentManager();
        showContentFragment(HEHUO1_FLAG, null);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i)
        {
            case R.id.btn_hehuo1:
                setTitle("合伙人注册");
                hehuotype=1;
                //showContentFragment(HEHUO1_FLAG,null);
                break;
            case R.id.btn_hehuo2:
                setTitle("合伙发起人注册");
                hehuotype=2;
                //showContentFragment(HEHUO2_FLAG,null);
                break;
        }
    }
    private void showContentFragment(int index, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ft = mFarg.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case HEHUO1_FLAG:
                if (hehuo_zhuceFragment == null) {
                    hehuo_zhuceFragment = new Hehuo_zhuceFragment();
                    hehuo_zhuceFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, hehuo_zhuceFragment);
                } else {
                    ft.show(hehuo_zhuceFragment);
                }
                break;
            case HEHUO2_FLAG:
                if (hehuofaqi_zhuceFragment == null) {
                    hehuofaqi_zhuceFragment = new Hehuofaqi_zhuceFragment();
                    hehuofaqi_zhuceFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, hehuofaqi_zhuceFragment);
                } else {
                    ft.show(hehuofaqi_zhuceFragment);
                }
                break;

        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (hehuo_zhuceFragment != null) {
            ft.hide(hehuo_zhuceFragment);
        }
        if (hehuofaqi_zhuceFragment != null) {
            ft.hide(hehuofaqi_zhuceFragment);
        }
    }
}
