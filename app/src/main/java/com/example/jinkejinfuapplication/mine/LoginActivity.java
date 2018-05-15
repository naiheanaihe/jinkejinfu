package com.example.jinkejinfuapplication.mine;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;

public class LoginActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    protected final int LOGIN_FLAG = 0x101;
    protected final int ZHUCE_FLAG = 0x102;
    private RadioGroup mGroup;
    private Login_fragment login_fragment;
    private Zhuce_fragment zhuce_fragment;
    private FragmentTransaction ft;
    private ImageView back;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        mGroup=findView(R.id.group_login);
        mGroup.setOnCheckedChangeListener(this);
        showContentFragment(LOGIN_FLAG,null);
        back=findView(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i)
        {
            case R.id.btn_denglu:
                showContentFragment(LOGIN_FLAG,null);
                break;
            case R.id.btn_zhuce:
                showContentFragment(ZHUCE_FLAG,null);
                break;
        }
    }
    private void showContentFragment(int index, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case LOGIN_FLAG:
                if (login_fragment == null) {
                    login_fragment = new Login_fragment();
                    login_fragment.setArguments(bundle);
                    ft.add(R.id.fl_content, login_fragment);
                } else {
                    ft.show(login_fragment);
                }
                break;
            case ZHUCE_FLAG:
                if (zhuce_fragment == null) {
                    zhuce_fragment = new Zhuce_fragment();
                    zhuce_fragment.setArguments(bundle);
                    ft.add(R.id.fl_content, zhuce_fragment);
                } else {
                    ft.show(zhuce_fragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (login_fragment != null) {
            ft.hide(login_fragment);
        }
        if (zhuce_fragment != null) {
            ft.hide(zhuce_fragment);
        }
    }
}
