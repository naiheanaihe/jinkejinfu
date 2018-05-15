package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class AddWendaActivity extends BaseActivity {
    private EditText ed_title,ed_neirong;
    private TextView num_text;
    private CheckBox check_huati;
    private SpinKitView spinKitView;
    private ImageButton fabiao;

    private RecyclerView recyclerView1;
    private PhotoAdapter photoAdapter1;
    private ArrayList<String> selectedPhotos1 = new ArrayList<>();
    private ArrayList<String> ossImgUrl = new ArrayList<>();
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_wenda);
        setTitle("我要提问");

        ed_title=findView(R.id.ed_title);
        ed_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                num_text.setText(editable.length()+"");
            }
        });
        ed_neirong=findView(R.id.ed_neirong);
        num_text=findView(R.id.num_text);
        check_huati=findView(R.id.check_huati);
        spinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        Style style = Style.values()[9];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);

        recyclerView1 = (RecyclerView) findViewById(R.id.rec);
        photoAdapter1 = new PhotoAdapter(this, selectedPhotos1);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView1.setAdapter(photoAdapter1);

        findViewById(R.id.img_sel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setGridColumnCount(4)
                        .start(AddWendaActivity.this);
            }
        });
        fabiao=findView(R.id.fabiao);
        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=ed_title.getText().toString();
                String neirong=ed_neirong.getText().toString();
                if (title.isEmpty()){
                    showToastMsg("问题不能为空");
                    return;
                }
                fabiao.setClickable(false);
                spinKitView.setVisibility(View.VISIBLE);
                if (selectedPhotos1.size()==0){
                    fabiao();
                }else {
                    uploadData(selectedPhotos1.get(0),1);

                }

            }
        });
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE))
        {

            List<String> photos = null;
            if (data != null)
            {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos1.clear();

            if (photos != null)
            {

                selectedPhotos1.addAll(photos);
            }
            photoAdapter1.notifyDataSetChanged();
        }
    }
    private void uploadData(String uploadFilePath, final int xuhao) {
        final String usercode= MyApplication.getInstance().getYonghuBean().getUserId();

        PutObjectRequest put = new PutObjectRequest(MyApplication.OSS_BUCKET, "image/albums/"+getImageObjectKey(usercode), uploadFilePath);


        MyApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                Log.d("url",request.getObjectKey());

                ossImgUrl.add(AppBaseUrl.OSSURL+request.getObjectKey());
                if (xuhao>=selectedPhotos1.size()){
                    fabiao();
                }else {
                    uploadData(selectedPhotos1.get(xuhao),xuhao+1);
                }

            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                ToastUtil.show(AddWendaActivity.this,"网络错误!");
                spinKitView.setVisibility(View.GONE);
                fabiao.setClickable(true);
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }

            }
        });

    }

    private void fabiao() {

        String url1= AppBaseUrl.HUATI_ADD;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId",MyApplication.getInstance().getYonghuBean().getUserId());
        params1.addBodyParameter("title",ed_title.getText().toString());
        params1.addBodyParameter("content",ed_neirong.getText().toString());
        params1.addBodyParameter("sort","topic1"); //分类topic为话题、topic1问答
        params1.addBodyParameter("state",check_huati.isChecked()?"1":"0"); ////匿名状态：1为匿名 0不匿名
        for (int i=0;i<ossImgUrl.size();i++){
            params1.addBodyParameter("img"+(i+1),ossImgUrl.get(i));
        }
        x.http().get(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ToastUtil.show(AddWendaActivity.this,"发表成功");
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

    private String getImageObjectKey(String strUserCode) {
        Date date = new Date();
        return new SimpleDateFormat("yyyy/M/d").format(date)+"/"+strUserCode+new SimpleDateFormat("yyyyMMddssSSS").format(date)+".jpg";
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
