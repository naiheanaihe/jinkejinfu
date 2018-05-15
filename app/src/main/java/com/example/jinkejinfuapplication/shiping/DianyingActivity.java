package com.example.jinkejinfuapplication.shiping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.shouye.dianjing.Saishi_xiangqActivity;
import com.example.jinkejinfuapplication.taojinke.huiyuan.MyVIPActivity;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MediaUtils;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.view.FullDialog;
import com.example.jinkejinfuapplication.xiangq.PinglunAdapter;
import com.example.jinkejinfuapplication.xiangq.ShipingActivity;
import com.example.jinkejinfuapplication.xiangq.ShouyouActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DianyingActivity extends BaseActivity implements View.OnClickListener {
    private String mId;
    private ImageView back;
    private LinearLayout rel_zhankai;
    private TextView title,guojia,yuyan,leixing,daoyan,zhuyan,jieshao;
    private boolean flag=true;
    private LinearLayout xiazai,guangkan;
    private Map<String,String> map_xiangq=new HashMap<>();
    private WebView webView;
    private String yinshi_type;
    private RecyclerView rec_pinglun,rec_tuijian;
    private PinglunAdapter pinglunAdapter;
    private Shiping_yingshiAdapter shiping_yingshiAdapter;

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list=new ArrayList<>();
    private PowerManager.WakeLock wakeLock;
    View rootView;
    private RelativeLayout rel_top;
    private ScrollView scrollView;
    private RelativeLayout includeview;
    private List<Map<String, String>> mTuijianlist=new ArrayList<>();

    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private ImageView pinglun;
    private LinearLayout lin_pinglun_kong;
    private EditText ed_pinglun;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    private TextView num_dianzan;
    private ImageView xin_dianzan;
    private ImageView more;
    private LinearLayout lin_pinglun;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_dianying, null);
        setContentView(rootView);
        rel_top=findView(R.id.rel_top);
        includeview =findView(R.id.includeview);
        initvideo();

        guangkan=findView(R.id.guangkan);
        guangkan.setOnClickListener(this);
        back=findView(R.id.back);
        back.setOnClickListener(this);
        title=findView(R.id.mytitle);
        guojia=findView(R.id.guojia);
        yuyan=findView(R.id.yuyan);
        leixing=findView(R.id.leixing);
        daoyan=findView(R.id.daoyan);
        zhuyan=findView(R.id.zhuyan);
        jieshao =findView(R.id.jianjie);
        webView=findView(R.id.webview);
        rel_zhankai=findView(R.id.zhankai);
        rel_zhankai.setOnClickListener(this);
        xiazai=findView(R.id.xiazai);
        xiazai.setOnClickListener(this);
        pinglun=findView(R.id.pinglun);
        pinglun.setOnClickListener(this);
        lin_pinglun_kong=findView(R.id.lin_pinglun_kong);
        ed_pinglun=findView(R.id.ed_pinglun);
        num_dianzan=findView(R.id.num_dianzan);
        xin_dianzan=findView(R.id.xin_dianzan);
        xin_dianzan.setOnClickListener(this);
        more=findView(R.id.more);
        more.setOnClickListener(this);
        lin_pinglun=findView(R.id.lin_pinglun);

        rec_tuijian=findView(R.id.rec_tuijian);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_tuijian.setLayoutManager(layoutManager);
        rec_tuijian.setHasFixedSize(true);
        rec_tuijian.addItemDecoration(new SpaceItemDecoration(10,false));
        shiping_yingshiAdapter=new Shiping_yingshiAdapter(this,mTuijianlist);
        rec_tuijian.setAdapter(shiping_yingshiAdapter);

        rec_pinglun=findView(R.id.rec_pinglun);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_pinglun.setLayoutManager(layoutManager5);
        rec_pinglun.setHasFixedSize(true);
        pinglunAdapter=new PinglunAdapter(this,mPinglunlist);
        rec_pinglun.setAdapter(pinglunAdapter);
        rec_pinglun.setNestedScrollingEnabled(false);

        ptrFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        moredate();
                        ptrFrameLayout.refreshComplete();
                    }
                },100);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page=0;
                        redate();
                        ptrFrameLayout.refreshComplete();
                    }
                },1000);
            }
        });

    }
    private void initvideo() {
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK , "liveTAG");
        wakeLock.setReferenceCounted(false);

        player = new PlayerView(this, rootView) {
            @Override
            public PlayerView toggleProcessDurationOrientation() {
                hideSteam(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return setProcessDurationOrientation(getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ? PlayStateParams.PROCESS_PORTRAIT : PlayStateParams.PROCESS_LANDSCAPE);
            }

            @Override
            public PlayerView setPlaySource(List<VideoijkBean> list) {
                return super.setPlaySource(list);
            }
        }
                .setProcessDurationOrientation(PlayStateParams.PROCESS_PORTRAIT)
                .setScaleType(PlayStateParams.fillparent)
                .forbidTouch(false)
                .hideSteam(true)
                .hideCenterPlayer(true)
                .hideHideTopBar(true)
                .hideRotation(true)
                .hideCenterPlayer(false)
                .setChargeTie(true,15)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load(R.drawable.img_loading_shiping)
                                .placeholder(R.color.cl_default)
                                .error(R.color.white)
                                .into(ivThumbnail);
                    }
                });

    }
    @Override
    protected void initData() {
        mId=getIntent().getStringExtra("id");
        String url= AppBaseUrl.YINGSHI_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("影视",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("movie");
                    map_xiangq.put("actress",temp.getString("actress"));
                    map_xiangq.put("code",temp.getString("code"));
                    map_xiangq.put("like",temp.getString("like"));
                    map_xiangq.put("director",temp.getString("director"));
                    map_xiangq.put("language",temp.getString("language"));
                    map_xiangq.put("movie_name",temp.getString("movie_name"));
                    map_xiangq.put("sort",temp.getString("sort"));
                    map_xiangq.put("type",temp.getString("type"));
                    map_xiangq.put("years",temp.getString("years"));
                    map_xiangq.put("url",temp.getString("url"));
                    map_xiangq.put("cover",temp.getString("cover"));
                    map_xiangq.put("update_num",temp.getString("update_num"));
                    map_xiangq.put("total",temp.getString("total"));
                    map_xiangq.put("id",temp.getString("id"));
                    map_xiangq.put("region",temp.getString("region"));
                    map_xiangq.put("introduction",AppBaseUrl.WENZHANG+temp.getString("introduction"));
                    map_xiangq.put("yun_address",temp.getString("yun_address"));
                    map_xiangq.put("yun_code",temp.getString("yun_code"));
                    map_xiangq.put("state",temp.getString("state"));
                    yinshi_type=temp.getString("type");
                    if (yinshi_type.equals("电影"))
                    {
                        guangkan.setVisibility(View.VISIBLE);
                    }
                    init();
                    inittuijian();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情"," "+ex.toString());
                ToastUtil.show(DianyingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
        initpinglun();
    }
    private void init() {
        if (map_xiangq.get("url").isEmpty()){
            includeview.setVisibility(View.GONE);
        }else {
            includeview.setVisibility(View.VISIBLE);
            String url1=AppBaseUrl.isHttp(map_xiangq.get("url"));
            VideoijkBean m1 = new VideoijkBean();
            m1.setStream("高清");
            m1.setUrl(url1);
            list.add(m1);
            player .setPlaySource(list).startPlay();
            if (map_xiangq.get("state").equals("1")){
                player.setChargeTie(false,15);
            }
        }

        title.setText(map_xiangq.get("movie_name"));
        if (map_xiangq.get("introduction").isEmpty()){
            rel_zhankai.setVisibility(View.GONE);
        }
        guojia.setText(map_xiangq.get("region"));
        daoyan.setText(map_xiangq.get("director"));
        yuyan.setText(map_xiangq.get("language"));
        leixing.setText(map_xiangq.get("sort"));
        zhuyan.setText(map_xiangq.get("actress"));

        //设置要加载的网页
        webView.loadUrl(map_xiangq.get("introduction"));
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        num_dianzan.setText(map_xiangq.get("like"));

    }
    private void redate(){
        initpinglun();
        inittuijian();
    }

    private void inittuijian() {
        page=0;
        mTuijianlist.clear();
        String url2= AppBaseUrl.YINGSHI_TUIJIAN;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("sort",map_xiangq.get("sort"));
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("推荐",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("more");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("cover",temp.getString("cover"));
                        map.put("actress",temp.getString("actress"));
                        map.put("update_num",temp.getString("update_num"));
                        map.put("id",temp.getString("id"));
                        map.put("movie_name",temp.getString("movie_name"));
                        map.put("yingshi",temp.getString("type"));
                        mTuijianlist.add(map);
                    }
                    shiping_yingshiAdapter.setmList(mTuijianlist);
                    shiping_yingshiAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(DianyingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void initpinglun(){
        page=0;
        mPinglunlist.clear();
        String url2= AppBaseUrl.PINGLUN_LIST;
        RequestParams params2 = new RequestParams(url2);
        params2.addBodyParameter("id",mId);
        params2.addBodyParameter("sort","movie");
        params2.addBodyParameter("str","0");
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("评论",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("comment_list");
                    JSONArray jrry=jObj.getJSONArray("comment");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        map.put("pic",AppBaseUrl.BASEURL+temp.getString("pic"));
                        map.put("name",temp.getString("name"));
                        map.put("replynum",temp.getString("replynum"));
                        map.put("commentId",temp.getString("commentId"));
                        map.put("sort",temp.getString("sort"));
                        map.put("content",temp.getString("content"));
                        mPinglunlist.add(map);
                    }
                    pinglunAdapter.setmList(mPinglunlist);
                    pinglunAdapter.notifyDataSetChanged();
                    if (mPinglunlist.size()>0){
                        lin_pinglun_kong.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(DianyingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void moredate(){
        String url= AppBaseUrl.PINGLUN_LIST;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("sort","movie");
        params.addBodyParameter("str",page+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多评论",result);
                try {
                    ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    jObj=jObj.getJSONObject("comment_list");
                    JSONArray jrry=jObj.getJSONArray("comment");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("datetime",temp.getString("datetime"));
                        map.put("like",temp.getString("like"));
                        map.put("pic",AppBaseUrl.BASEURL+temp.getString("pic"));
                        map.put("name",temp.getString("name"));
                        map.put("replynum",temp.getString("replynum"));
                        map.put("commentId",temp.getString("commentId"));
                        map.put("sort",temp.getString("sort"));
                        map.put("content",temp.getString("content"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        ptrFrameLayout.refreshComplete();
                        ToastUtil.show(DianyingActivity.this,"没有更多数据了");
                    }else
                    {
                        mPinglunlist.addAll(mNewslist);
                        ptrFrameLayout.refreshComplete();
                        pinglunAdapter.notifyItemRangeInserted(mPinglunlist.size()-mNewslist.size(),mNewslist.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(DianyingActivity.this,"网络发生错误!");
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
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.zhankai:
                if(flag){
                    flag = false;
                    jieshao.setEllipsize(null); // 展开
                    jieshao.setSingleLine(flag);
                }else{
                    flag = true;
                    jieshao.setMaxLines(3);
                    jieshao.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                }
                break;
            case R.id.xiazai:
                String url1 = map_xiangq.get("yun_address");
                if (url1.isEmpty()){
                    showToastMsg("暂时不提供下载！");
                }else {
                    Uri uri = Uri.parse(url1);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                break;
            case R.id.pinglun:
                if (ed_pinglun.getText().toString().isEmpty()){
                    showToastMsg("评论内容不能为空！");
                    return;
                }
                String url2= AppBaseUrl.PINGLUN;
                RequestParams params1 = new RequestParams(url2);
                params1.addBodyParameter("id",mId);
                params1.addBodyParameter("sort","movie");
                params1.addBodyParameter("content",ed_pinglun.getText().toString());
                params1.addBodyParameter("userId", MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_LOGIN? MyApplication.getInstance().getYonghuBean().getUserId():"");
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("评论",result);
                        ed_pinglun.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        showToastMsg("评论成功！");
                        if (mPinglunlist.size()<10){
                            try {
                                JSONObject jObj = new JSONObject(result);
                                JSONObject temp = jObj.getJSONObject("comment");
                                Map<String,String> map=new HashMap<String, String>();
                                map.put("datetime",temp.getString("datetime"));
                                map.put("like",temp.getString("like"));
                                map.put("pic",AppBaseUrl.BASEURL+temp.getString("photo"));
                                map.put("name",temp.getString("name"));
                                map.put("replynum",temp.getString("replynum"));
                                map.put("commentId",temp.getString("commentId"));
                                map.put("sort",temp.getString("sort"));
                                map.put("content",temp.getString("content"));
                                mPinglunlist.add(0,map);
                                pinglunAdapter.notifyItemRangeInserted(0,1);
                                lin_pinglun_kong.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误详情",ex.toString());
                        ToastUtil.show(DianyingActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case R.id.xin_dianzan:
                String url= AppBaseUrl.DIANZAN;
                RequestParams params = new RequestParams(url);
                params.addBodyParameter("id",map_xiangq.get("id"));
                params.addBodyParameter("sort","movie");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("点赞",result);
                        xin_dianzan.setImageResource(R.drawable.ic_hongxin);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            jObj=jObj.getJSONObject("likenum");
                            num_dianzan.setText(jObj.getString("likenum"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.d("错误详情",ex.toString());
                        ToastUtil.show(mContext,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case R.id.more:
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    Intent intent=new Intent(mContext,LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                UMWeb web = null;

                web = new UMWeb("http://vip.goldcs.net/video/movie/details/"+ map_xiangq.get("id"));
                web.setTitle(map_xiangq.get("movie_name"));
                web.setDescription(map_xiangq.get("sort"));

                UMImage thumb =  new UMImage(mContext, map_xiangq.get("cover"));
                web.setThumb(thumb);  //缩略图
                /*web.setDescription("my description");//描述*/
                new ShareAction((Activity) mContext)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }
                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(mContext,share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Toast.makeText(mContext,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                            }
                        }).open();
                break;
            case R.id.guangkan:
                if (map_xiangq.get("state").equals("1")){
                    showToastMsg("您已购买过此电影!");
                    return;
                }
                RequestParams params2 = new RequestParams(AppBaseUrl.BASEURL+"app/video/movie/guanying_num");
                params2.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
                x.http().get(params2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("赛事",result);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            int num= Integer.parseInt(jObj.getString("num"));
                            if (num>0){
                                final FullDialog dialog = new FullDialog(DianyingActivity.this);
                                final View views=View.inflate(DianyingActivity.this, R.layout.dianyin_guangkan_dialog,null);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(views);
                                ImageView cha= (ImageView) views.findViewById(R.id.cha);
                                cha.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                TextView text_num= (TextView) views.findViewById(R.id.num);
                                text_num.setText(num+"");
                                Button queren=(Button) views.findViewById(R.id.queren);
                                queren.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        goumaidianyin();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }else {
                                final FullDialog dialog = new FullDialog(DianyingActivity.this);
                                final View views=View.inflate(DianyingActivity.this, R.layout.dianyin_guangkan_dialog1,null);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(views);
                                ImageView cha= (ImageView) views.findViewById(R.id.cha);
                                cha.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                Button queren=(Button) views.findViewById(R.id.queren);
                                queren.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Intent intent=new Intent(DianyingActivity.this, MyVIPActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LalaLog.i("错误信息",ex.toString());
                        ToastUtil.show(DianyingActivity.this,"网络发生错误!");
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }
                    @Override
                    public void onFinished() {
                    }
                });

                break;
        }
    }

    private void goumaidianyin() {
        RequestParams params2 = new RequestParams(AppBaseUrl.BASEURL+"app/video/movie/guanying_ajax");
        params2.addBodyParameter("id", mId);
        params2.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().get(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("购买",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    int num= Integer.parseInt(jObj.getString("msg"));
                    if (num==4){
                        showToastMsg("购买成功，点击继续播放");
                        player.setChargeTie(false,15);
                    }else if (num==1){
                        showToastMsg("暂不提供播放");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.i("错误信息",ex.toString());
                ToastUtil.show(DianyingActivity.this,"网络发生错误!");
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
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, true);
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 当新设置中，屏幕布局模式为横排时
        if(newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE)
        {
            rel_top.setVisibility(View.GONE);
            lin_pinglun.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setTransparent();
            }

        }else if (newConfig.orientation ==Configuration.ORIENTATION_PORTRAIT){
            rel_top.setVisibility(View.VISIBLE);
            lin_pinglun.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setBlack();
            }

        }
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        /**demo的内容，恢复设备亮度状态*/
        if (wakeLock != null) {
            wakeLock.release();
        }
    }
    public static String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,pathandname.length());
        }else{
            return null;
        }

    }
}
