package com.example.jinkejinfuapplication.xiangq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.MediaUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;
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

public class ShipingActivity extends BaseActivity implements View.OnClickListener {
    private String mId,gameId;
    private ImageView back,game_icon,more;
    private LinearLayout rel_zhankai;
    private TextView zhankai_text;
    private ImageView zhankai_arrow;
    private TextView title,game_name,game_type,jieshao;
    private boolean flag=true;
    private Button xiazai;
    private Map<String,String> map_xiangq=new HashMap<>();
    private RecyclerView rec_shiping,rec_pinglun;
    private Shiping_moreAdapter shipingAdapter;
    private PinglunAdapter pinglunAdapter;
    private List<Map<String,String>> shipingList=new ArrayList<>();
    private RelativeLayout rel_game;

    private PlayerView player;
    private Context mContext;
    private List<VideoijkBean> list=new ArrayList<>();
    private PowerManager.WakeLock wakeLock;
    View rootView;
    private RelativeLayout rel_top;
    private ScrollView scrollView;
    private RelativeLayout includeview;

    private List<Map<String, String>> mPinglunlist=new ArrayList<>();
    private ImageView pinglun;
    private LinearLayout lin_pinglun_kong;
    private EditText ed_pinglun;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    private TextView chakan_number,zan_number;
    private ImageView xin_dianzan;
    private LinearLayout lin_pinglun;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_shiping, null);
        setContentView(rootView);
        rel_top=findView(R.id.rel_top);
        includeview =findView(R.id.includeview);
        initvideo();

        back=findView(R.id.back);
        back.setOnClickListener(this);
        more=findView(R.id.more);
        more.setOnClickListener(this);
        title=findView(R.id.zixun_title);
        jieshao =findView(R.id.jieshao);
        rel_zhankai=findView(R.id.zhankai);
        rel_zhankai.setOnClickListener(this);
        zhankai_text=findView(R.id.zhankai_text);
        zhankai_arrow=findView(R.id.zhankai_arrow);
        game_icon=findView(R.id.img_icon);
        game_name=findView(R.id.game_name);
        game_type=findView(R.id.game_type);
        xiazai=findView(R.id.xiazai);
        xiazai.setOnClickListener(this);
        rel_game=findView(R.id.rel_game);
        pinglun=findView(R.id.pinglun);
        pinglun.setOnClickListener(this);
        lin_pinglun_kong=findView(R.id.lin_pinglun_kong);
        ed_pinglun=findView(R.id.ed_pinglun);
        chakan_number=findView(R.id.chakan_number);
        zan_number=findView(R.id.zan_number);
        xin_dianzan=findView(R.id.xin_dianzan);
        xin_dianzan.setOnClickListener(this);
        lin_pinglun=findView(R.id.lin_pinglun);

        rec_shiping=findView(R.id.rec_shiping);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_shiping.setLayoutManager(layoutManager2);
        /*rec_shiping.addItemDecoration(new SpaceItemDecoration(20,false));*/
        shipingAdapter=new Shiping_moreAdapter(this,shipingList);
        rec_shiping.setAdapter(shipingAdapter);

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
                //.setChargeTie(true,5)
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
        gameId=getIntent().getStringExtra("gameId");
        String url= AppBaseUrl.SHIPING_XIANGQ;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("id",mId);
        params.addBodyParameter("gameId",gameId);
        LalaLog.d("游戏视频id",gameId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("视频",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONObject temp = jObj.getJSONObject("video");
                    map_xiangq.put("duration",temp.getString("duration"));
                    map_xiangq.put("datetime",temp.getString("datetime"));
                    map_xiangq.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                    map_xiangq.put("video_introduce",temp.getString("video_introduce"));
                    map_xiangq.put("videos",AppBaseUrl.BASEURL+temp.getString("videos"));
                    map_xiangq.put("id",temp.getString("id"));
                    map_xiangq.put("source",temp.getString("source"));
                    map_xiangq.put("sort",temp.getString("sort"));
                    map_xiangq.put("title",temp.getString("title"));
                    map_xiangq.put("game_id",temp.getString("game_id"));
                    map_xiangq.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                    map_xiangq.put("game_type",temp.getString("game_type"));
                    map_xiangq.put("game_name",temp.getString("game_name"));
                    map_xiangq.put("game_background",AppBaseUrl.BASEURL+temp.getString("game_background"));
                    map_xiangq.put("likenum",temp.getString("likenum"));
                    map_xiangq.put("clicknum",temp.getString("clicknum"));
                    if (!temp.getString("game_id").isEmpty()){
                        map_xiangq.put("type",temp.getString("type"));
                    }
                    init();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(ShipingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
        redate();

    }

    private void redate() {
        initshiping();
        initpinglun();
    }

    private void initshiping() {
        shipingList.clear();
        String url1= AppBaseUrl.SHIPING_XIANGQ_MORE;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("id",mId);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多视频",result);
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("more");
                    for (int i=0;i<jrry.length();i++) {
                        Map<String,String> map=new HashMap<String, String>();
                        JSONObject temp = jrry.getJSONObject(i);
                        map.put("datetime",temp.getString("datetime"));
                        map.put("title",temp.getString("title"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("duration",temp.getString("duration"));
                        map.put("videos",AppBaseUrl.BASEURL+temp.getString("videos"));
                        map.put("id",temp.getString("id"));
                        map.put("gameId",temp.getString("gameId"));

                        shipingList.add(map);
                    }
                    shipingAdapter.setmList(shipingList);
                    shipingAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(ShipingActivity.this,"网络发生错误!");
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
        params2.addBodyParameter("sort","video");
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
                        map.put("pic",AppBaseUrl.isHttp(temp.getString("pic")));
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
                ToastUtil.show(ShipingActivity.this,"网络发生错误!");
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
        params.addBodyParameter("sort","video");
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
                        map.put("pic",AppBaseUrl.isHttp(temp.getString("pic")));
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
                        ToastUtil.show(ShipingActivity.this,"没有更多数据了");
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
                ToastUtil.show(ShipingActivity.this,"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private void init() {
        if (map_xiangq.get("game_id").isEmpty()){
            rel_game.setVisibility(View.GONE);
        }

        String url1=map_xiangq.get("videos");
        VideoijkBean m1 = new VideoijkBean();
        m1.setStream("高清");
        m1.setUrl(url1);
        list.add(m1);
        player .setPlaySource(list).startPlay();

        title.setText(map_xiangq.get("title"));
        jieshao.setText(map_xiangq.get("video_introduce"));
        if (map_xiangq.get("video_introduce").isEmpty()){
            rel_zhankai.setVisibility(View.GONE);
        }
        x.image().bind(game_icon,map_xiangq.get("game_pic"), ImageOptions.getimageOptions_img());
        game_name.setText(map_xiangq.get("game_name"));
        game_type.setText(map_xiangq.get("game_type"));
        chakan_number.setText(map_xiangq.get("clicknum"));
        zan_number.setText(map_xiangq.get("likenum"));
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
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.more:
                shareVideoWindow(this,more,map_xiangq);
                break;
            case R.id.zhankai:
                if(flag){
                    flag = false;
                    jieshao.setEllipsize(null); // 展开
                    jieshao.setSingleLine(flag);
                    zhankai_text.setText("收缩");
                    zhankai_arrow.setImageResource(R.drawable.arrow_up);
                }else{
                    flag = true;
                    jieshao.setMaxLines(2);
                    jieshao.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    zhankai_text.setText("展开");
                    zhankai_arrow.setImageResource(R.drawable.arrow_bttom);
                }
                break;
            case R.id.xiazai:
                if (map_xiangq.get("type").equals("手游")){
                    Intent intent=new Intent(this,ShouyouActivity.class);
                    intent.putExtra("id",gameId);
                    startActivity(intent);
                }else if (map_xiangq.get("type").equals("页游")){
                    Intent intent=new Intent(this,YeyouActivity.class);
                    intent.putExtra("id",gameId);
                    startActivity(intent);
                }else if (map_xiangq.get("type").equals("VR")){
                    Intent intent=new Intent(this,VRActivity.class);
                    intent.putExtra("id",gameId);
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
                params1.addBodyParameter("sort","video");
                params1.addBodyParameter("content",ed_pinglun.getText().toString());
                params1.addBodyParameter("userId", MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_LOGIN?MyApplication.getInstance().getYonghuBean().getUserId():"");
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
                                map.put("pic",AppBaseUrl.isHttp(temp.getString("photo")));
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
                        ToastUtil.show(ShipingActivity.this,"网络发生错误!");
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
                params.addBodyParameter("sort","video");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LalaLog.i("点赞",result);
                        xin_dianzan.setImageResource(R.drawable.ic_hongxin);
                        try {
                            JSONObject jObj = new JSONObject(result);
                            jObj=jObj.getJSONObject("likenum");
                            zan_number.setText(jObj.getString("likenum"));
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
        }
    }
    private void shareVideoWindow(final Activity mContext, ImageView imageView, final Map<String, String> map)
    {
        if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
            Intent intent=new Intent(mContext,LoginActivity.class);
            mContext.startActivity(intent);
            return;
        }
      /*  View popupView = View.inflate(mContext,R.layout.share_poupwindow,null);
        //创建PopupWindow ,指定它的显示的视图，以及它本身的宽度和高度
        final PopupWindow poupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置它获得焦点
        poupWindow.setFocusable(true);
        //设置背景
        poupWindow.setBackgroundDrawable(new BitmapDrawable());
        //动画效果
        ScaleAnimation animation = new ScaleAnimation(0f,1f,0f,1f, Animation.RELATIVE_TO_SELF,0.8f,Animation.RELATIVE_TO_SELF,0f);
        animation.setDuration(500);
        popupView.setAnimation(animation);
        //设置位置
        poupWindow.showAsDropDown(imageView);

        LinearLayout lin1= (LinearLayout) popupView.findViewById(R.id.share);
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poupWindow.dismiss();

            }
        });*/


        UMWeb web = null;
        web = new UMWeb(AppBaseUrl.SHARE_SHIPING+ map.get("id")+"-"+map.get("game_id"));
        web.setTitle(map.get("title"));
        web.setDescription(map.get("video_introduce"));

        UMImage thumb =  new UMImage(mContext, map.get("background"));
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
}
