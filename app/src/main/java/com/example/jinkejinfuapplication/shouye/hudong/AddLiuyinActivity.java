package com.example.jinkejinfuapplication.shouye.hudong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddLiuyinActivity extends BaseActivity {
    private TextView ed_liuyin;
    private ImageView fabiao;
    private SpinKitView spinKitView;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_liuyin);
        setTitle("添加留言");

        ed_liuyin=findView(R.id.ed_liuyin);
        fabiao=findView(R.id.fabiao);
        spinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        Style style = Style.values()[9];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);

        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String liuyin=ed_liuyin.getText().toString();
                if (liuyin.isEmpty()){
                    showToastMsg("留言不能为空");
                    return;
                }
                fabiao.setClickable(false);
                spinKitView.setVisibility(View.VISIBLE);

                String url1= AppBaseUrl.LIUYIN_ADD;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                params1.addBodyParameter("content",liuyin);
                x.http().get(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        ToastUtil.show(AddLiuyinActivity.this,"发表成功");
                        spinKitView.setVisibility(View.GONE);
                        fabiao.setClickable(true);
                        finish();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误信息",ex.toString());
                        ToastUtil.show(getApplicationContext(),"网络发生错误!");
                        spinKitView.setVisibility(View.GONE);
                        fabiao.setClickable(true);
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });

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
        return true;
    }


}
