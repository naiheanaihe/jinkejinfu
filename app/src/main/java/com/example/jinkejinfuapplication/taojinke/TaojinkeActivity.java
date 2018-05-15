package com.example.jinkejinfuapplication.taojinke;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;

public class TaojinkeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    protected final int TAOJINKE_FLAG = 0x101;
    protected final int HUIYUAN_FLAG = 0x102;
    protected final int HEHUO_FLAG = 0x103;
    protected final int TUIJIAN_FLAG = 0x104;
    private TaojinkeFragment taojinkeFragment;
    private HuiyuanFragment huiyuanFragment;
    private HehuorenFragment hehuorenFragment;
    private TuijianFragment tuijianFragment;
    private RadioGroup mGroup;
    private RadioButton rb_taojinke,rb_huiyuan,rb_hehuo,rb_tuijian;
    private FragmentManager mFarg;
    private FragmentTransaction ft;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_taojinke);
        setTitle("淘金客");

        mGroup=findView(R.id.rg_tabs);
        mGroup.setOnCheckedChangeListener(this);
        rb_taojinke=findView(R.id.rb_taojinke);
        rb_huiyuan=findView(R.id.rb_huiyuan);
        rb_hehuo=findView(R.id.rb_hehuo);
        rb_tuijian=findView(R.id.rb_tuijian);
        mFarg=getSupportFragmentManager();
        showContentFragment(TAOJINKE_FLAG, null);
        if (getIntent().getStringExtra("type")!=null){
            String type=getIntent().getStringExtra("type");
            switch (type){
                case "1":
                    rb_taojinke.setChecked(true);
                    break;
                case "2":
                    rb_huiyuan.setChecked(true);
                    break;
                case "3":
                    rb_hehuo.setChecked(true);
                    break;
                case "4":
                    rb_tuijian.setChecked(true);
                    break;
            }
        }

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
            case R.id.rb_taojinke:
                setTitle("淘金客");
                showContentFragment(TAOJINKE_FLAG,null);
                break;
            case R.id.rb_huiyuan:
                setTitle("会员");
                showContentFragment(HUIYUAN_FLAG,null);
                break;
            case R.id.rb_hehuo:
                setTitle("合伙人");
                showContentFragment(HEHUO_FLAG,null);
                break;
            case R.id.rb_tuijian:
                setTitle("推荐分享");
                showContentFragment(TUIJIAN_FLAG,null);
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
            case TAOJINKE_FLAG:
                if (taojinkeFragment == null) {
                    taojinkeFragment = new TaojinkeFragment();
                    taojinkeFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, taojinkeFragment);
                } else {
                    ft.show(taojinkeFragment);
                }
                break;
            case HUIYUAN_FLAG:
                if (huiyuanFragment == null) {
                    huiyuanFragment = new HuiyuanFragment();
                    huiyuanFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, huiyuanFragment);
                } else {
                    ft.show(huiyuanFragment);
                }
                break;
            case HEHUO_FLAG:
                if (hehuorenFragment == null) {
                    hehuorenFragment = new HehuorenFragment();
                    hehuorenFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, hehuorenFragment);
                } else {
                    ft.show(hehuorenFragment);
                }
                break;
            case TUIJIAN_FLAG:
                if (tuijianFragment == null) {
                    tuijianFragment = new TuijianFragment();
                    tuijianFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, tuijianFragment);
                } else {
                    ft.show(tuijianFragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (taojinkeFragment != null) {
            ft.hide(taojinkeFragment);
        }
        if (huiyuanFragment != null) {
            ft.hide(huiyuanFragment);
        }
        if (hehuorenFragment != null) {
            ft.hide(hehuorenFragment);
        }
        if (tuijianFragment != null) {
            ft.hide(tuijianFragment);
        }
    }
}
