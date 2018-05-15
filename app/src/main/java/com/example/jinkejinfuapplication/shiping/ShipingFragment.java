package com.example.jinkejinfuapplication.shiping;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.utils.LalaLog;

/**
 * Created by naihe on 2017/11/1.
 */

public class ShipingFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    protected final int YOUXI_FLAG = 0x101;
    protected final int VR_FLAG = 0x102;
    protected final int YINGSHI_FLAG = 0x103;
    public static int FLAG=0x101;
    private RadioGroup mGroup;
    private YouxiFragment youxiFragment;
    private Shiping_vrFragment shiping_vrFragment;
    private YingshiFragment yingshiFragment;
    private FragmentTransaction ft;
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView(View view) {
        mGroup= (RadioGroup) view.findViewById(R.id.group_shiping);
        mGroup.setOnCheckedChangeListener(this);
        showContentFragment(YINGSHI_FLAG, null);
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_shiping;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.btn_shiping1:
                FLAG=YOUXI_FLAG;
                showContentFragment(YOUXI_FLAG,null);
                break;
            case R.id.btn_shiping2:
                FLAG=VR_FLAG;
                showContentFragment(VR_FLAG,null);
                break;
            case R.id.btn_shiping3:
                FLAG=YINGSHI_FLAG;
                showContentFragment(YINGSHI_FLAG,null);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (FLAG==YINGSHI_FLAG){
            mGroup.check(R.id.btn_shiping3);
        }
        LalaLog.d("影视",FLAG+"="+YINGSHI_FLAG);*/
    }

    private void showContentFragment(int index, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ft = getChildFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (index) {
            case YOUXI_FLAG:
                if (youxiFragment == null) {
                    youxiFragment = new YouxiFragment();
                    youxiFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, youxiFragment);
                } else {
                    ft.show(youxiFragment);
                }
                break;
            case VR_FLAG:
                if (shiping_vrFragment == null) {
                    shiping_vrFragment = new Shiping_vrFragment();
                    shiping_vrFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, shiping_vrFragment);
                } else {
                    ft.show(shiping_vrFragment);
                }
                break;
            case YINGSHI_FLAG:
                if (yingshiFragment == null) {
                    yingshiFragment = new YingshiFragment();
                    yingshiFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, yingshiFragment);
                } else {
                    ft.show(yingshiFragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (youxiFragment != null) {
            ft.hide(youxiFragment);
        }
        if (shiping_vrFragment != null) {
            ft.hide(shiping_vrFragment);
        }
        if (yingshiFragment != null) {
            ft.hide(yingshiFragment);
        }
    }
    private OnButtonClick onButtonClick;
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }
    public interface OnButtonClick{
        public void onClick(View view, int i);
    }
}
