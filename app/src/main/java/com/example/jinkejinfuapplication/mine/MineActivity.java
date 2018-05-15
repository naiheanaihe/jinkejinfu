package com.example.jinkejinfuapplication.mine;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.dujia.JingxuanAdapter;
import com.example.jinkejinfuapplication.taojinke.Hehuo_zhaomuActivity;
import com.example.jinkejinfuapplication.taojinke.Huiyuan_zhaomuActivity;
import com.example.jinkejinfuapplication.taojinke.TaojinkeActivity;
import com.example.jinkejinfuapplication.taojinke.TuijianAdapter;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.PhotoUtil;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back,shezhi,myicon;
    private Button tuichu;
    private TextView name,hehuo,myvip,shouyi,tixian,fensi;
    private LinearLayout lin_vip,lin_hehuo,lin_taojinke,lin_huiyuan,lin_hehuoren,lin_tuijian,lin_zhanghu,lin_chongzhi;
    private RecyclerView rec_tuijian;
    private TuijianAdapter jingxuanAdapter;
    private List<Map<String,String>> jingxuanList =new ArrayList<>();
    private Uri uri;
    private String imgPath;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        shezhi=findView(R.id.shezhi);
        shezhi.setOnClickListener(this);
        tuichu=findView(R.id.tuichu);
        tuichu.setOnClickListener(this);
        myicon=findView(R.id.myicon);
        myicon.setOnClickListener(this);
        name=findView(R.id.name);
        hehuo=findView(R.id.hehuo);
        myvip=findView(R.id.myvip);
        shouyi=findView(R.id.shouyi);
        tixian=findView(R.id.tixian);
        fensi=findView(R.id.fensi);
        findView(R.id.lin_vip).setOnClickListener(this);
        findView(R.id.lin_hehuo).setOnClickListener(this);
        findView(R.id.lin_taojinke).setOnClickListener(this);
        findView(R.id.lin_huiyuan).setOnClickListener(this);
        findView(R.id.lin_hehuoren).setOnClickListener(this);
        findView(R.id.lin_tuijian).setOnClickListener(this);
        findView(R.id.lin_zhanghu).setOnClickListener(this);
        findView(R.id.lin_chongzhi).setOnClickListener(this);
        findView(R.id.more).setOnClickListener(this);

        rec_tuijian=findView(R.id.rec_tuijian);
        GridLayoutManager linManager1=new GridLayoutManager(this,2 ,LinearLayoutManager.VERTICAL,false);
        rec_tuijian.setLayoutManager(linManager1);
        rec_tuijian.addItemDecoration(new SpaceItemDecoration(15,false));
        rec_tuijian.setHasFixedSize(true);
        jingxuanAdapter=new TuijianAdapter(this,jingxuanList);
        rec_tuijian.setAdapter(jingxuanAdapter);
    }

    @Override
    protected void initData() {
        String url= AppBaseUrl.TUIJIAN_GEREN;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("精选",result);
                jingxuanList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                        map.put("game_name",ob.getString("game_name"));
                        map.put("game_type",ob.getString("game_type"));
                        map.put("code",ob.getString("code"));
                        jingxuanList.add(map);
                    }
                    jingxuanAdapter.setmList(jingxuanList);
                    jingxuanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(MineActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        initinfor();
    }
    private void initinfor(){
        String url1= AppBaseUrl.GEREN;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("个人",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("user");
                    myvip.setText(jObj.getString("member"));
                    hehuo.setText(jObj.getString("proxy"));
                    x.image().bind(myicon,AppBaseUrl.isHttp(jObj.getString("photo")), ImageOptions.getimageOptions());
                    name.setText(jObj.getString("name"));
                    shouyi.setText(jObj.getString("money")+"元");
                    tixian.setText(jObj.getString("tixian")+"元");
                    fensi.setText(jObj.getString("all")+"人");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(MineActivity.this,"网络发生错误!");
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
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.shezhi:
                intent=new Intent(this,ShezhiActivity.class);
                startActivity(intent);
                break;
            case R.id.tuichu:
                MainActivity.LOGINSTATE=MainActivity.LOGINSTATE_NOTLOGIN;
                SharedPreferencesUtils.setParam(this,"user","loginstate", MainActivity.LOGINSTATE+"");
                MobclickAgent.onProfileSignOff();
                finish();
                break;
            case R.id.lin_huiyuan:
                intent=new Intent(this,Huiyuan_zhaomuActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_hehuoren:
                intent=new Intent(this,Hehuo_zhaomuActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_taojinke:
                intent=new Intent(this,TaojinkeActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.lin_zhanghu:
                intent=new Intent(this,MyzhanghuActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_chongzhi:
                showToastMsg("暂未开放！");
                break;
            case R.id.lin_vip:
                intent=new Intent(this,TaojinkeActivity.class);
                intent.putExtra("type","2");
                startActivity(intent);
                break;
            case R.id.lin_hehuo:
                intent=new Intent(this,TaojinkeActivity.class);
                intent.putExtra("type","3");
                startActivity(intent);
                break;
            case R.id.lin_tuijian:
            case R.id.more:
                intent=new Intent(this,TaojinkeActivity.class);
                intent.putExtra("type","4");
                startActivity(intent);
                break;
            case R.id.myicon:
                PhotoUtil.choosePhoto(this);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //返回头像信息
        switch (requestCode)
        {
            case PhotoUtil.REQUEST_CODE_FROM_GALLERY:
                if (data==null)
                {
                    return;
                }
                uri = data.getData();
                PhotoUtil.startPhotoZoom(this,uri);
                Uri originalUri = data.getData();        //获得图片的uri
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                break;
            case PhotoUtil.REQUEST_CODE_FROM_CAMERA:
                uri = Uri.fromFile(PhotoUtil.PHOTO_FILE);
                PhotoUtil.startPhotoZoom(this, uri);
                break;
            case PhotoUtil.REQUEST_CODE_FROM_ZOOM:
                if (uri==null)
                {
                    return;
                }
                if (resultCode == RESULT_OK)
                {
                    Bundle bdl = data.getExtras();
                    Bitmap photo = bdl.getParcelable("data");
                    myicon.setImageBitmap(toRoundBitmap(photo));
                    saveBitmap(toRoundBitmap(photo));
                    uploadData(imgPath);
                }
                /*Bitmap photo = PhotoUtil.getBitmapFromUri(uri,this);*/
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 把bitmap转成圆形
     * */
    public static Bitmap toRoundBitmap(Bitmap bitmap){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int r=0;
        //取最短边做边长
        if(width<height){
            r=width;
        }else{
            r=height;
        }
        //构建一个bitmap
        Bitmap backgroundBm=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas=new Canvas(backgroundBm);
        Paint p=new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect=new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    private void saveBitmap(Bitmap photo) {
        File img = new File(getCacheDir(), MyApplication.getInstance().getYonghuBean().getUserId()+".jpg");
        LalaLog.i("保存图片",img.toString());
        imgPath=getCacheDir()+"/"+MyApplication.getInstance().getYonghuBean().getUserId()+".jpg";
        if (img.exists()) {
            img.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(img);
            photo.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();
            // Log.i(TAG, "已经保存");
            // Toast.makeText(getApplicationContext(), "已经保存", 0).show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void uploadData(String uploadFilePath) {
        final String usercode= MyApplication.getInstance().getYonghuBean().getUserId();

        PutObjectRequest put = new PutObjectRequest(MyApplication.OSS_BUCKET, "image/albums/"+getImageObjectKey(usercode), uploadFilePath);

        /*put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });*/

        MyApplication.oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(final PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                Log.d("url",request.getObjectKey());
                //将上传成功的图片地址传给自己的服务器后台，比如修改用户数据库中，用户头像的url。
                //修改后台url成功后，再利用glide 下载最新的照片，修改本地头像图片。
                //request.getObjectKey() 是图片地址，但是不包含，OSS 图片域名
//	            uploadImage(request.getObjectKey());
                //图片对外地址 shunxe.oss-cn-shenzhen.aliyuncs.com/image/albums/2016/7/28/172016072806425.jpg
//	            修改本地sp头像信息
                SharedPreferencesUtils.setParam(getApplicationContext(), "user", "photo", AppBaseUrl.OSSURL+request.getObjectKey());
                MyApplication.getInstance().getYonghuBean().setPhoto(AppBaseUrl.OSSURL+request.getObjectKey());
                String url1= AppBaseUrl.TOUXIANG_XIUGAI;
                RequestParams params1 = new RequestParams(url1);
                params1.addBodyParameter("userId",MyApplication.getInstance().getYonghuBean().getUserId());
                params1.addBodyParameter("photo",AppBaseUrl.OSSURL+request.getObjectKey());
                x.http().get(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        x.image().bind(myicon,AppBaseUrl.OSSURL+request.getObjectKey(),ImageOptions.getimageOptions());
                        MyApplication.getInstance().getYonghuBean().setPhoto(AppBaseUrl.OSSURL+request.getObjectKey());
                        SharedPreferencesUtils.setParam(MineActivity.this,"user","photo",AppBaseUrl.OSSURL+request.getObjectKey());

                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误信息",ex.toString());
                        ToastUtil.show(getApplicationContext(),"网络发生错误!");
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
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                ToastUtil.show(MineActivity.this,"网络错误!");
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
    private String getImageObjectKey(String strUserCode) {
        Date date = new Date();
        return new SimpleDateFormat("yyyy/M/d").format(date)+"/"+strUserCode+new SimpleDateFormat("yyyyMMddssSSS").format(date)+".jpg";
    }

    @Override
    protected void onResume() {
        super.onResume();
        initinfor();
    }
}
