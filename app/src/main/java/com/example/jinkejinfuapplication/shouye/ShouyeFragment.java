package com.example.jinkejinfuapplication.shouye;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.MainActivity;
import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.Shouye_gameBean;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.shiping.ShipingFragment;
import com.example.jinkejinfuapplication.shouye.dianjing.DianjingActivity;
import com.example.jinkejinfuapplication.shouye.hudong.HudongActivity;
import com.example.jinkejinfuapplication.shouye.huodong.HuodongActivity;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.DisplayUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.example.jinkejinfuapplication.view.MyHorizontalScrollView;
import com.example.jinkejinfuapplication.view.MyScrollView;
import com.example.jinkejinfuapplication.view.Mybanner;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.ZhoubianActivity;
import com.example.jinkejinfuapplication.xiangq.ZhoubianAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

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

import cn.appsdream.nestrefresh.base.AbsRefreshLayout;
import cn.appsdream.nestrefresh.base.OnPullListener;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by naihe on 2017/11/1.
 */

public class ShouyeFragment extends BaseFragment {
    private RecyclerView rec_shouye,rec_shiping;
    private RecyclerView rec_zixun,rec_hudong,rec_shangcheng;
    private ZhoubianAdapter zhoubianAdapter;
    private Shouye_hudongAdapter hudongAdapter;
    private ShouyeAdapter shouyeAdapter;
    private List<Shouye_gameBean> mList =new ArrayList<>();
    private NestRefreshLayout nestRefreshLayout;
    private Shouye_shipingAdapter shouye_shipingAdapter;
    private List<Map<String,String>> mShipinglist =new ArrayList<>();
    private Shouye_zixunAdapter shouye_zixunAdapter;
    private List<Map<String,String>> mZixunlist =new ArrayList<>();
    private List<Map<String,String>> mHudonglist =new ArrayList<>();
    private MyScrollView myScrollView;
    private Mybanner mRollViewPager;
    private List<Map<String,String>> LEADING_IMAGE_RESOURCES=new ArrayList<>();
    private TestNormalAdapter testNormalAdapter;
    private TextView more_zixun,more_shiping;
    private PtrClassicFrameLayout ptrFrameLayout;

    private float firstx,firsty,firsty1;
    private RadioGroup group_zixun,group_shiping,group_youxi,group_hudong;
    private String zixun_type="游戏",shiping_type="游戏",youxi_type="手游",hudong_type="topic";
    private List<Map<String, String>> mZhoubianList=new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
    }
    private void redate(){

        inityouxi();
        initshiping();
        initzixun();
        initzhoubian();
        inithudong();

        String url3= AppBaseUrl.BANNER_SHOUYE;
        RequestParams params3 = new RequestParams(url3);
        params3.addBodyParameter("sort","1");
        x.http().get(params3, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("广告",result);
                LEADING_IMAGE_RESOURCES.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("banner");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",temp.getString("ID"));
                        map.put("url", AppBaseUrl.BASEURL+temp.getString("img"));
                        map.put("sort",temp.getString("sort"));
                        map.put("type",temp.getString("type"));
                        LEADING_IMAGE_RESOURCES.add(map);
                    }
                    testNormalAdapter.setPhotoPaths(LEADING_IMAGE_RESOURCES);
                    testNormalAdapter.notifyDataSetChanged();
                    mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.YELLOW,Color.WHITE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void initzhoubian() {
        String url1= AppBaseUrl.ZHOUBIAN_SHOUYE;
        RequestParams params1= new RequestParams(url1);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("周边",result);
                mZhoubianList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("commodity");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("commodity_Id",ob.getString("commodity_Id"));
                        map.put("commodity_img",ob.getString("commodity_img"));
                        map.put("commodity_price",ob.getString("commodity_price"));
                        map.put("commodity_name",ob.getString("commodity_name"));
                        mZhoubianList.add(map);
                    }
                    zhoubianAdapter.setmList(mZhoubianList);
                    zhoubianAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void inityouxi() {
        String url= AppBaseUrl.SHOUYE;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("type",youxi_type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("首页",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Shouye_gameBean bean=new Shouye_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        bean.setZhujitype(temp.getString("type"));

                        JSONArray news=temp.getJSONArray("news");
                        if (news.length()>0){
                            bean.setHasnews(true);
                            for (int n=0;n<news.length();n++) {
                                JSONObject ob =news.getJSONObject(n);
                                bean.setJiashao(ob.getString("title"));
                                bean.setImg1(AppBaseUrl.BASEURL+ob.getString("background"));
                                bean.setTime(ob.getString("datetime"));
                                bean.setZixunid(ob.getString("id"));
                            }
                        }

                        JSONArray video=temp.getJSONArray("video");
                        bean.setHasshiping(video.length());
                        for (int n=0;n<video.length();n++) {
                            JSONObject ob =video.getJSONObject(n);
                            if (n==0){
                                bean.setImg2(AppBaseUrl.BASEURL+ob.getString("background"));
                                bean.setShipingid1(ob.getString("id"));
                            }else {
                                bean.setImg3(AppBaseUrl.BASEURL+ob.getString("background"));
                                bean.setShipingid2(ob.getString("id"));
                            }
                        }

                        mList.add(bean);
                    }
                    shouyeAdapter.setmList(mList);
                    shouyeAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void initshiping() {
        String url1= AppBaseUrl.SHIPING_TYPE;
        RequestParams params1= new RequestParams(url1);
        params1.addBodyParameter("type",shiping_type);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("视频",result);
                mShipinglist.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("video");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",ob.getString("gameId"));
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        mShipinglist.add(map);
                    }
                    shouye_shipingAdapter.setmList(mShipinglist);
                    shouye_shipingAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void initzixun() {
        String url2= AppBaseUrl.ZIXUN_TYPE;
        RequestParams params2= new RequestParams(url2);
        params2.addBodyParameter("sort",zixun_type);
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("资讯",result);
                mZixunlist.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("news");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",ob.getString("gameId"));
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("title",ob.getString("title"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("name",ob.getString("name"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mZixunlist.add(map);
                    }
                    shouye_zixunAdapter.setmList(mZixunlist);
                    shouye_zixunAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void inithudong(){
        String url2= AppBaseUrl.SHOUYE_HUDONG;
        RequestParams params2= new RequestParams(url2);
        params2.addBodyParameter("sort",hudong_type);
        x.http().post(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("互动",result);
                mHudonglist.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("topic");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();

                        map.put("img",AppBaseUrl.isHttp(ob.getString("img")));
                        map.put("id",ob.getString("id"));
                        map.put("sort",ob.getString("sort"));
                        map.put("datetime",ob.getString("datetime"));
                        map.put("content",ob.getString("content"));
                        map.put("likenum",ob.getString("likenum"));
                        map.put("clicknum",ob.getString("clicknum"));
                        mHudonglist.add(map);
                    }
                    hudongAdapter.setmList(mHudonglist);
                    hudongAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LalaLog.d("错误详情",ex.toString());
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {

        mRollViewPager= (Mybanner) view.findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormalAdapter(LEADING_IMAGE_RESOURCES,getContext());
        mRollViewPager.setAdapter(testNormalAdapter);

        int height= DeviceUtil.deviceWidth(getContext())*420/1080;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);


        view.findViewById(R.id.more_zixun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),More_zixunActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.more_shiping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity= (MainActivity) getActivity();
                activity.mGroup.check(R.id.rb_shiping);

            }
        });
        view.findViewById(R.id.more_shangcheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ShangchengActivity.class);
                getActivity().startActivity(intent);

            }
        });
        view.findViewById(R.id.more_hudong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),HudongActivity.class);
                getActivity().startActivity(intent);

            }
        });
        view.findViewById(R.id.more_youxi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity= (MainActivity) getActivity();
                switch (youxi_type){
                    case "手游":
                        activity.mGroup.check(R.id.rb_dujia);
                        break;
                    case "VR":
                        activity.mGroup.check(R.id.rb_vr);
                        break;
                    case "海外":
                        activity.mGroup.check(R.id.rb_haiwai);
                        break;
                }


            }
        });
        view.findViewById(R.id.lin_huodong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    showToast("请先登录！");
                    Intent intent=new Intent(getContext(),LoginActivity.class);
                    getContext().startActivity(intent);
                }else {
                    Intent intent=new Intent(getContext(),HuodongActivity.class);
                    getContext().startActivity(intent);
                }

            }
        });
        view.findViewById(R.id.lin_hudong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.LOGINSTATE==MainActivity.LOGINSTATE_NOTLOGIN){
                    showToast("请先登录！");
                    Intent intent=new Intent(getContext(),LoginActivity.class);
                    getContext().startActivity(intent);
                }else {
                    Intent intent=new Intent(getContext(),HudongActivity.class);
                    getContext().startActivity(intent);
                }

            }
        });
        view.findViewById(R.id.lin_haiwai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity= (MainActivity) getActivity();
                activity.mGroup.check(R.id.rb_haiwai);

            }
        });
        view.findViewById(R.id.lin_dianyin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShipingFragment.FLAG=0x103;
                MainActivity activity= (MainActivity) getActivity();
                activity.mGroup.check(R.id.rb_shiping);

            }
        });
        view.findViewById(R.id.lin_zhibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),Youxi_zhiboActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.lin_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),WangzhuanActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.lin_dianjing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),DianjingActivity.class);
                getContext().startActivity(intent);
            }
        });
        view.findViewById(R.id.lin_shangcheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ShangchengActivity.class);
                getContext().startActivity(intent);
            }
        });

        rec_shouye= (RecyclerView) view.findViewById(R.id.rec_shouye);
        LinearLayoutManager linManager1=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_shouye.setLayoutManager(linManager1);
        /*rec_shouye.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));*/
        /*rec_shouye.setHasFixedSize(true);*/
        rec_shouye.setNestedScrollingEnabled(false);
        shouyeAdapter=new ShouyeAdapter(getContext(),mList);
        rec_shouye.setAdapter(shouyeAdapter);
        /*nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);*/

        rec_shiping= (RecyclerView) view.findViewById(R.id.rec_shiping);
        LinearLayoutManager linManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rec_shiping.setLayoutManager(linManager2);
        rec_shiping.addItemDecoration(new SpaceItemDecoration(15,false));
        rec_shiping.setHasFixedSize(true);
        shouye_shipingAdapter=new Shouye_shipingAdapter(getContext(),mShipinglist);
        rec_shiping.setAdapter(shouye_shipingAdapter);

        rec_zixun= (RecyclerView) view.findViewById(R.id.rec_zixun);
        LinearLayoutManager linManager3=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_zixun.setLayoutManager(linManager3);
        rec_zixun.setHasFixedSize(true);
        shouye_zixunAdapter=new Shouye_zixunAdapter(getContext(),mZixunlist);
        rec_zixun.setAdapter(shouye_zixunAdapter);
        //解决下拉刷新与recview冲突
        rec_zixun.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                ptrFrameLayout.setEnabled(topRowVerticalPosition >= -10);

            }
        });
        myScrollView= (MyScrollView) view.findViewById(R.id.myscr);
        myScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    firstx =motionEvent.getRawX();
                    firsty=motionEvent.getRawY();
                } else if (motionEvent.getAction()==MotionEvent.ACTION_MOVE){
                    if (Math.abs(motionEvent.getRawY()-firsty)>0){
                        ptrFrameLayout.setEnabled(true);
                    }
                }
                return false;
            }
        });
        ptrFrameLayout= (PtrClassicFrameLayout) view.findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redate();
                        ptrFrameLayout.refreshComplete();
                    }
                },1000);
            }
        });
        ptrFrameLayout.disableWhenHorizontalMove(true);

        rec_hudong= (RecyclerView) view.findViewById(R.id.rec_hudong);
        LinearLayoutManager linManager4=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_hudong.setLayoutManager(linManager4);
        rec_hudong.addItemDecoration(new SpaceItemDecoration(10,true));
        rec_hudong.setHasFixedSize(true);
        hudongAdapter=new Shouye_hudongAdapter(getContext(),mHudonglist);
        rec_hudong.setAdapter(hudongAdapter);

        rec_shangcheng= (RecyclerView) view.findViewById(R.id.rec_shangcheng);
        LinearLayoutManager linManager5=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rec_shangcheng.setLayoutManager(linManager5);
        rec_shangcheng.setHasFixedSize(true);
        zhoubianAdapter=new ZhoubianAdapter(getContext(),mZhoubianList);
        rec_shangcheng.setAdapter(zhoubianAdapter);

        group_zixun= (RadioGroup) view.findViewById(R.id.group_zixun);
        group_zixun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_zixun1:
                        zixun_type="游戏";
                        break;
                    case R.id.btn_zixun2:
                        zixun_type="VR";
                        break;
                    case R.id.btn_zixun3:
                        zixun_type="热点";
                        break;
                    case R.id.btn_zixun4:
                        zixun_type="攻略";
                        break;
                    case R.id.btn_zixun5:
                        zixun_type="新闻";
                        break;
                    case R.id.btn_zixun6:
                        zixun_type="娱乐";
                        break;
                    case R.id.btn_zixun7:
                        zixun_type="科技";
                        break;
                }
                initzixun();
            }
        });
        group_shiping= (RadioGroup) view.findViewById(R.id.group_shiping);
        group_shiping.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_shiping1:
                        shiping_type="游戏";
                        break;
                    case R.id.btn_shiping2:
                        shiping_type="VR";
                        break;
                    case R.id.btn_shiping3:
                        shiping_type="娱乐";
                        break;
                }
                initshiping();
            }
        });
        group_youxi= (RadioGroup) view.findViewById(R.id.group_youxi);
        group_youxi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_youxi1:
                        youxi_type="手游";
                        break;
                    case R.id.btn_youxi2:
                        youxi_type="VR";
                        break;
                    case R.id.btn_youxi3:
                        youxi_type="海外";
                        break;
                }
                inityouxi();
            }
        });
        group_hudong= (RadioGroup) view.findViewById(R.id.group_hudong);
        group_hudong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_hudong1:
                        hudong_type="topic";
                        break;
                    case R.id.btn_hudong2:
                        hudong_type="topic1";
                        break;
                    case R.id.btn_hudong3:
                        hudong_type="message";
                        break;
                }
                inithudong();
            }
        });

        mRollViewPager.setFocusable(true);
        mRollViewPager.setFocusableInTouchMode(true);
        mRollViewPager.requestFocus();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_shouye;
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}
