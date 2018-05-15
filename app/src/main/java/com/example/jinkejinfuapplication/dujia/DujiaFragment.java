package com.example.jinkejinfuapplication.dujia;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.Dujia_gameBean;
import com.example.jinkejinfuapplication.bean.Haiwai_gameBean;
import com.example.jinkejinfuapplication.bean.VR_gameBean;
import com.example.jinkejinfuapplication.haiwai.HaiwaiAdapter;
import com.example.jinkejinfuapplication.shouye.TestNormalAdapter;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.DisplayUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.jude.rollviewpager.RollPagerView;
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

/**
 * Created by naihe on 2017/11/1.
 */

public class DujiaFragment extends BaseFragment implements OnPullListener {
    private String gameType[] =new String[]{"角色扮演","射击枪战","策略战棋","模拟经营","休闲益智","体育运动","动作闯关","卡牌游戏"
            ,"赛车竞速","冒险解谜","音乐舞蹈"};
    private RadioGroup group_zhuji,group_type,group_fenlei;
    private RecyclerView rec_dujia,rec_jingxuan,rec_zhekou;
    private List<Dujia_gameBean> mList =new ArrayList<>();
    private List<Map<String,String>> jingxuanList =new ArrayList<>();
    private int page=0;
    private NestRefreshLayout nestRefreshLayout;
    private DujiaAdapter dujiaAdapter;
    private JingxuanAdapter jingxuanAdapter;
    private ZhekouAdapter zhekouAdapter;
    private String zhuji="全部",type="全部";
    private LinearLayout lin_shaix;
    private ImageView img_moreshaix;
    private boolean moreshaix=false;
    private LinearLayout lin_shouyou;
    private String dujia_url=AppBaseUrl.DUJIA_SHOUYOU;
    private String fenlei="1";
    private RadioButton zhuji_quanbu,type_quanbu;
    private RollPagerView mRollViewPager;
    private TestNormalAdapter testNormalAdapter;
    private List<Map<String,String>> LEADING_IMAGE_RESOURCES=new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
        String url= AppBaseUrl.DUJIA_JINGXUAN;
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("精选",result);
                jingxuanList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("chosen");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject ob = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                        map.put("id",ob.getString("id"));
                        map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                        map.put("game_name",ob.getString("game_name"));
                        map.put("game_type",ob.getString("game_type"));
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
                ToastUtil.show(getContext(),"网络发生错误!");
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });

        String url3= AppBaseUrl.BANNER_SHOUYE;
        RequestParams params3 = new RequestParams(url3);
        params3.addBodyParameter("sort","3");
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
    private void redate(){
        page=0;
        String url= dujia_url;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("type",zhuji);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("独家",result);
                LalaLog.i("参数","type:"+type+"zhuji:"+zhuji);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Dujia_gameBean bean=new Dujia_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));

                        if (fenlei.equals("2")){
                            bean.setShouyou_type("页游");
                        }else if (fenlei.equals("1")){
                            bean.setShouyou_type("手游");
                        }else {
                            bean.setShouyou_type(temp.getString("type"));
                            bean.setDiscount(temp.getString("discount"));
                        }
                        mList.add(bean);
                    }
                    if (fenlei.equals("3")){
                        LalaLog.d("折扣",mList.toString());
                        zhekouAdapter.setmList(mList);
                        zhekouAdapter.notifyDataSetChanged();

                    }else {
                        dujiaAdapter.setmList(mList);
                        dujiaAdapter.notifyDataSetChanged();

                    }

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
    private void moredate(){
        String url= dujia_url;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("type",zhuji);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多独家",result);
                try {
                    List<Dujia_gameBean> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Dujia_gameBean bean=new Dujia_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        if (fenlei.equals("2")){
                            bean.setShouyou_type("页游");
                        }else if (fenlei.equals("1")){
                            bean.setShouyou_type("手游");
                        }else {
                            bean.setShouyou_type(temp.getString("type"));
                            bean.setDiscount(temp.getString("discount"));
                        }
                        mNewslist.add(bean);
                    }
                    if (mNewslist.size()==0)
                    {
                        nestRefreshLayout.onLoadFinished();
                        ToastUtil.show(getContext(),"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        nestRefreshLayout.onLoadFinished();
                        if (fenlei.equals("3")){

                            zhekouAdapter.notifyItemRangeInserted(mList.size()-mNewslist.size(),mNewslist.size());
                        }else {
                            dujiaAdapter.notifyItemRangeInserted(mList.size()-mNewslist.size(),mNewslist.size());
                        }

                    }
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
    @Override
    public void initView(View view) {
        rec_dujia= (RecyclerView) view.findViewById(R.id.rec_dujia);
        GridLayoutManager linManager1=new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false);
        rec_dujia.setLayoutManager(linManager1);
        /*rec_dujia.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));*/
        rec_dujia.setHasFixedSize(true);
        dujiaAdapter=new DujiaAdapter(getContext(),mList);
        rec_dujia.setAdapter(dujiaAdapter);

        rec_zhekou= (RecyclerView) view.findViewById(R.id.rec_zhekou);
        LinearLayoutManager linManager3=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_zhekou.setLayoutManager(linManager3);
        /*rec_dujia.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));*/

        zhekouAdapter=new ZhekouAdapter(getContext(),mList);
        rec_zhekou.setAdapter(zhekouAdapter);


        mRollViewPager= (RollPagerView) view.findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormalAdapter(LEADING_IMAGE_RESOURCES,getContext());
        mRollViewPager.setAdapter(testNormalAdapter);
        int height= DeviceUtil.deviceWidth(getContext())*420/1080;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);

        rec_jingxuan= (RecyclerView) view.findViewById(R.id.rec_jingxuan);
        LinearLayoutManager linManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rec_jingxuan.setLayoutManager(linManager2);
        rec_jingxuan.addItemDecoration(new SpaceItemDecoration(15,false));
        rec_jingxuan.setHasFixedSize(true);
        jingxuanAdapter=new JingxuanAdapter(getContext(),jingxuanList);
        rec_jingxuan.setAdapter(jingxuanAdapter);

        zhuji_quanbu= (RadioButton) view.findViewById(R.id.btn_zhuji1);
        type_quanbu= (RadioButton) view.findViewById(R.id.btn_type1);

        nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);
        lin_shouyou= (LinearLayout) view.findViewById(R.id.lin_shouyou);

        lin_shaix= (LinearLayout) view.findViewById(R.id.lin_shaix);
        img_moreshaix= (ImageView) view.findViewById(R.id.img_moreshaix);
        img_moreshaix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moreshaix){
                    moreshaix=false;
                    img_moreshaix.setImageResource(R.drawable.arrow_bttom);
                    lin_shaix.setVisibility(View.GONE);
                }else {
                    moreshaix=true;
                    img_moreshaix.setImageResource(R.drawable.arrow_up);
                    lin_shaix.setVisibility(View.VISIBLE);
                }
            }
        });
        group_zhuji= (RadioGroup) view.findViewById(R.id.group_zhuji);
        group_zhuji.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_zhuji1:
                        zhuji="全部";
                        redate();
                        break;
                    case R.id.btn_zhuji2:
                        zhuji="android";
                        redate();
                        break;
                    case R.id.btn_zhuji3:
                        zhuji="IOS";
                        redate();
                        break;
                    case R.id.btn_zhuji4:
                        zhuji="PC";
                        redate();
                        break;
                }
            }
        });
        group_type= (RadioGroup) view.findViewById(R.id.group_type);
        group_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_type1:
                        type="全部";
                        redate();
                        break;
                    case R.id.btn_type2:
                        type=gameType[0];
                        redate();
                        break;
                    case R.id.btn_type3:
                        type=gameType[1];
                        redate();
                        break;
                    case R.id.btn_type4:
                        type=gameType[2];
                        redate();
                        break;
                    case R.id.btn_type5:
                        type=gameType[3];
                        redate();
                        break;
                    case R.id.btn_type6:
                        type=gameType[4];
                        redate();
                        break;
                    case R.id.btn_type7:
                        type=gameType[5];
                        redate();
                        break;
                    case R.id.btn_type8:
                        type=gameType[6];
                        redate();
                        break;
                    case R.id.btn_type9:
                        type=gameType[7];
                        redate();
                        break;
                    case R.id.btn_type10:
                        type=gameType[8];
                        redate();
                        break;
                    case R.id.btn_type11:
                        type=gameType[9];
                        redate();
                        break;
                    case R.id.btn_type12:
                        type=gameType[10];
                        redate();
                        break;
                }
            }
        });
        group_fenlei= (RadioGroup) view.findViewById(R.id.group_dujia);
        group_fenlei.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btn_dujia1:
                        fenlei="1";
                        dujia_url=AppBaseUrl.DUJIA_SHOUYOU;
                        if (type_quanbu.isChecked()){
                            zhuji="全部";
                            type="全部";
                            redate();
                        }else {
                            type_quanbu.setChecked(true);
                        }
                        if (zhuji_quanbu.isChecked()){
                            zhuji="全部";
                            type="全部";
                            redate();
                        }else {
                            zhuji_quanbu.setChecked(true);
                        }
                        rec_zhekou.setVisibility(View.GONE);
                        rec_dujia.setVisibility(View.VISIBLE);
                        lin_shouyou.setVisibility(View.VISIBLE);
                        group_zhuji.setVisibility(View.VISIBLE);
                        lin_shaix.setVisibility(View.GONE);
                        break;
                    case R.id.btn_dujia2:
                        fenlei="2";
                        dujia_url=AppBaseUrl.DUJIA_YEYOU;
                        if (zhuji_quanbu.isChecked()){
                            zhuji="全部";
                            type="全部";
                            redate();
                        }else {
                            zhuji_quanbu.setChecked(true);
                        }
                        rec_zhekou.setVisibility(View.GONE);
                        rec_dujia.setVisibility(View.VISIBLE);
                        lin_shouyou.setVisibility(View.GONE);
                        group_zhuji.setVisibility(View.GONE);
                        lin_shaix.setVisibility(View.VISIBLE);
                        break;
                    case R.id.btn_dujia3:
                        fenlei="3";
                        dujia_url=AppBaseUrl.DUJIA_ZHEKOU;
                        dujia_url=AppBaseUrl.DUJIA_ZHEKOU;
                        redate();

                        rec_zhekou.setVisibility(View.VISIBLE);
                        rec_dujia.setVisibility(View.GONE);
                        lin_shouyou.setVisibility(View.GONE);
                        group_zhuji.setVisibility(View.GONE);
                        lin_shaix.setVisibility(View.GONE);
                        break;
                    case R.id.btn_dujia4:
                        fenlei="4";
                        dujia_url=AppBaseUrl.DUJIA_MIANFEI;
                        dujia_url=AppBaseUrl.DUJIA_MIANFEI;

                        lin_shouyou.setVisibility(View.GONE);
                        group_zhuji.setVisibility(View.GONE);
                        lin_shaix.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dujia;
    }

    @Override
    public void onRefresh(AbsRefreshLayout listLoader) {
        nestRefreshLayout.onLoadFinished();
        page=0;
        redate();
    }

    @Override
    public void onLoading(AbsRefreshLayout listLoader) {
        page+=1;
        moredate();
    }
}
