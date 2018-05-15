package com.example.jinkejinfuapplication.shouye.hudong;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.taojinke.HehuorenFragment;
import com.example.jinkejinfuapplication.taojinke.HuiyuanFragment;
import com.example.jinkejinfuapplication.taojinke.TaojinkeFragment;
import com.example.jinkejinfuapplication.taojinke.TuijianFragment;

public class HudongActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    protected final int HUATI_FLAG = 0x101;
    protected final int WENDA_FLAG = 0x102;
    protected final int LIUYIN_FLAG = 0x103;
    private HuatiFragment huatiFragment;
    private WendaFragment wendaFragment;
    private LiuyinFragment liuyinFragment;
    private RadioGroup mGroup;
    private FragmentManager mFarg;
    private FragmentTransaction ft;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hudong);
        setTitle("互动热撩");
        mGroup=findView(R.id.group_hudong);
        mGroup.setOnCheckedChangeListener(this);
        mFarg=getSupportFragmentManager();
        showContentFragment(HUATI_FLAG, null);
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
            case R.id.btn_hudong1:
                showContentFragment(HUATI_FLAG,null);
                break;
            case R.id.btn_hudong2:
                showContentFragment(WENDA_FLAG,null);
                break;
            case R.id.btn_hudong3:
                showContentFragment(LIUYIN_FLAG,null);
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
            case HUATI_FLAG:
                if (huatiFragment == null) {
                    huatiFragment = new HuatiFragment();
                    huatiFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, huatiFragment);
                } else {
                    ft.show(huatiFragment);
                }
                break;
            case WENDA_FLAG:
                if (wendaFragment == null) {
                    wendaFragment = new WendaFragment();
                    wendaFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, wendaFragment);
                } else {
                    ft.show(wendaFragment);
                }
                break;
            case LIUYIN_FLAG:
                if (liuyinFragment == null) {
                    liuyinFragment = new LiuyinFragment();
                    liuyinFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, liuyinFragment);
                } else {
                    ft.show(liuyinFragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (huatiFragment != null) {
            ft.hide(huatiFragment);
        }
        if (wendaFragment != null) {
            ft.hide(wendaFragment);
        }
        if (liuyinFragment != null) {
            ft.hide(liuyinFragment);
        }
    }
}
