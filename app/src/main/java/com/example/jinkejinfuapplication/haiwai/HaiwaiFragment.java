package com.example.jinkejinfuapplication.haiwai;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.Haiwai_gameBean;
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

public class HaiwaiFragment extends BaseFragment implements OnPullListener {
    private String gameType[] =new String[]{"角色扮演","射击枪战","策略战棋","模拟经营","休闲益智","体育运动","动作闯关","卡牌游戏"
            ,"赛车竞速","冒险解谜","音乐舞蹈"};
    private RadioGroup group_guojia,group_zhuji,group_type;
    private RecyclerView rec_haiwai;
    private List<Haiwai_gameBean> mList =new ArrayList<>();
    private int page=0;
    private NestRefreshLayout nestRefreshLayout;
    private HaiwaiAdapter haiwaiAdapter;
    private String guojia="全部",zhuji="全部",type="全部";
    private LinearLayout lin_shaix;
    private ImageView img_moreshaix;
    private boolean moreshaix=false;
    private RollPagerView mRollViewPager;
    private TestNormalAdapter testNormalAdapter;
    private List<Map<String,String>> LEADING_IMAGE_RESOURCES=new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
    }
    private void redate(){
        page=0;
        String url= AppBaseUrl.HAIWAI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("overseas",guojia);
        params.addBodyParameter("type",zhuji);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("海外",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Haiwai_gameBean bean=new Haiwai_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        bean.setZhujitype(temp.getString("type"));

                        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
                        JSONArray more=temp.getJSONArray("more");
                        for (int n=0;n<more.length();n++) {
                            JSONObject ob =more.getJSONObject(n);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("gameId",ob.getString("gameId"));
                            map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                            map.put("id",ob.getString("id"));
                            map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                            map.put("game_name",ob.getString("game_name"));
                            map.put("type",ob.getString("type"));
                            list.add(map);
                        }
                        bean.setGameList(list);
                        mList.add(bean);
                    }
                    haiwaiAdapter.setmList(mList);
                    haiwaiAdapter.notifyDataSetChanged();
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
        params3.addBodyParameter("sort","2");
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
    private void moredate(){
        String url= AppBaseUrl.HAIWAI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("overseas",guojia);
        params.addBodyParameter("type",zhuji);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多海外",result);
                try {
                    List<Haiwai_gameBean> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Haiwai_gameBean bean=new Haiwai_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));
                        bean.setZhujitype(temp.getString("type"));

                        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
                        JSONArray more=temp.getJSONArray("more");
                        for (int n=0;n<more.length();n++) {
                            JSONObject ob =more.getJSONObject(n);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("gameId",ob.getString("gameId"));
                            map.put("background",AppBaseUrl.BASEURL+ob.getString("background"));
                            map.put("id",ob.getString("id"));
                            map.put("game_pic",AppBaseUrl.BASEURL+ob.getString("game_pic"));
                            map.put("game_name",ob.getString("game_name"));
                            map.put("type",ob.getString("type"));
                            list.add(map);
                        }
                        bean.setGameList(list);
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
                        haiwaiAdapter.notifyItemRangeInserted(page*9,mNewslist.size());
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
        rec_haiwai= (RecyclerView) view.findViewById(R.id.rec_haiwai);
        LinearLayoutManager linManager1=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_haiwai.setLayoutManager(linManager1);
        rec_haiwai.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));
        rec_haiwai.setHasFixedSize(true);
        haiwaiAdapter=new HaiwaiAdapter(getContext(),mList);
        rec_haiwai.setAdapter(haiwaiAdapter);
        nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);

        mRollViewPager= (RollPagerView) view.findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormalAdapter(LEADING_IMAGE_RESOURCES,getContext());
        mRollViewPager.setAdapter(testNormalAdapter);
        int height= DeviceUtil.deviceWidth(getContext())*420/1080;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);

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
        group_guojia= (RadioGroup) view.findViewById(R.id.group_guojia);
        group_guojia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_guojia1:
                        guojia="全部";
                        redate();
                        break;
                    case R.id.btn_guojia2:
                        guojia="US";
                        redate();
                        break;
                    case R.id.btn_guojia3:
                        guojia="Japan";
                        redate();
                        break;
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
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_haiwai;
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
