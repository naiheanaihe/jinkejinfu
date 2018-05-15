package com.example.jinkejinfuapplication.shouye.huodong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.MyzhanghuActivity;
import com.example.jinkejinfuapplication.taojinke.TaojinkeActivity;
import com.example.jinkejinfuapplication.utils.BitmapUtil;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.ZixunActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Shouyou_libaoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_bk;
    private TextView xiangq,num_yonghu,num_jine;
    private ImageView fenxiang,lingjiang,lingjiang1,lingjiang2,lingjiang3,lingjiang4,lingjiang5;
    private float leiji,yonghushu;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shouyou_libao);
        setTitle("手游大礼包");
        img_bk=findView(R.id.img_bk);
        int height= DeviceUtil.deviceWidth(this)*6084/720;
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) img_bk.getLayoutParams();
        params.height=height;
        img_bk.setLayoutParams(params);
        Bitmap bit= BitmapUtil.readBitMap(this, R.drawable.img_huodong_libao_bk);
        img_bk.setImageBitmap(bit);

        xiangq=findView(R.id.xiangq);
        xiangq.setOnClickListener(this);
        num_yonghu=findView(R.id.num_yonghu);
        num_jine=findView(R.id.num_jine);
        fenxiang=findView(R.id.fenxiang);
        lingjiang=findView(R.id.lingjiang);
        lingjiang1=findView(R.id.lingjiang1);
        lingjiang2=findView(R.id.lingjiang2);
        lingjiang3=findView(R.id.lingjiang3);
        lingjiang4=findView(R.id.lingjiang4);
        lingjiang5=findView(R.id.lingjiang5);
        fenxiang.setOnClickListener(this);
        lingjiang.setOnClickListener(this);
        lingjiang1.setOnClickListener(this);
        lingjiang2.setOnClickListener(this);
        lingjiang3.setOnClickListener(this);
        lingjiang4.setOnClickListener(this);
        lingjiang5.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        String url= AppBaseUrl.BASEURL+"app/activity/game";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("活动",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("game");
                    leiji= Float.parseFloat(temp.getString("game_cost"));
                    yonghushu= Float.parseFloat(temp.getString("fans_num"));
                    num_jine.setText(temp.getString("game_cost")+"元");
                    num_yonghu.setText(temp.getString("fans_num"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Shouyou_libaoActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.xiangq:
                intent=new Intent(this,Huodong_chongzhiActivity.class);
                startActivity(intent);
                break;
            case R.id.fenxiang:
                intent=new Intent(this,TaojinkeActivity.class);
                intent.putExtra("type","4");
                startActivity(intent);
                break;
            case R.id.lingjiang:
                lingquyubi();
                break;
            case R.id.lingjiang1:
                lingqu("100");
                break;
            case R.id.lingjiang2:
                lingqu("1000");
                break;
            case R.id.lingjiang3:
                lingqu("5000");
                break;
            case R.id.lingjiang4:
                lingqu("10000");
                break;
            case R.id.lingjiang5:
                lingqu("50000");
                break;
        }
    }

    private void lingqu(final String s) {
        if (leiji<Float.parseFloat(s)){
            showToastMsg("未达成领取资格!");
            return;
        }
        String url= AppBaseUrl.BASEURL+"app/activity/account/receive";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("money",s);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("领取",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    String state=jObj.getString("state");
                    if (state.equals("1")){
                        leiji-=Float.parseFloat(s);
                        num_jine.setText(leiji+"元");
                        showToastMsg("领取成功!可在个人中心的个人账户内查看");

                    }else {
                        showToastMsg("服务器出错，请重新领取!");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Shouyou_libaoActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void lingquyubi() {
        if (leiji<3){
            showToastMsg("未达成领取资格!");
            return;
        }
        String url= AppBaseUrl.BASEURL+"app/activity/fans/money";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("领取",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("fans_moeny");
                    String msg=jObj.getString("msg");
                    if (msg.equals("领取成功")){
                        yonghushu-=3;
                        num_yonghu.setText(yonghushu+"");
                        showToastMsg("领取成功");

                    }else {
                        showToastMsg("服务器出错，请重新领取!");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(Shouyou_libaoActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
}
