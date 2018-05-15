package com.example.jinkejinfuapplication.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.commbuinese.CommDataDaoImpl;


public abstract class BaseFragment extends Fragment {
//    private OnFragmentInteractionListener mListener;
    public CommDataDaoImpl commDataDao;
    private String xinxi;

    public String getXinxi() {
        return xinxi;
    }

    public void setXinxi(String xinxi) {
        this.xinxi = xinxi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View convertView = inflater.inflate(getFragmentLayoutId(), container, false);
        initView(convertView);
        initPopwind(inflater);
        return convertView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, int message) {
        showMessageDialog(getText(title), getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog(getText(title), message);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, int message) {
        showMessageDialog(title, getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /***
     * Show message dialog with custom DialogInterface.OnClickListener
     *
     * @param title
     * @param message
     * @param okListener
     */
    public void showMessageDialog(CharSequence title, CharSequence message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.dialog_ok, okListener);
        builder.show();
    }

    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    public void initPopwind(LayoutInflater inflater){
    }

    /***
     * Interface callback templates, developers can customize according to their needs
     * 接口回调模版，开发者可以根据自己需求自定义
     */
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }

    /***
     * Initialization data, the subclass must implements this method
     * 初始化数据，子类必须实现此方法
     *
     * @param savedInstanceState
     */
    public abstract void initData(Bundle savedInstanceState);

    /***
     * Initialization view, the subclass must implements this method
     * 初始化控件，子类必须实现此方法
     *
     * @param view
     */
    public abstract void initView(View view);

    /***
     * Back Layout, subclass must implements this method
     * 返回子类布局，子类需要实现此方法
     *
     * @return
     */
    public abstract int getFragmentLayoutId();

}
