package com.example.jinkejinfuapplication.VR;

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
import com.example.jinkejinfuapplication.bean.VR_gameBean;
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

public class VRFragment extends BaseFragment implements OnPullListener {
    private String VRType[] =new String[]{"HTC Vive","PlayStation VR","Gear VR","Oculus Rift","移动平台 VR","Daydream"};
    private String gameType[] =new String[]{"角色扮演","射击枪战","策略战棋","模拟经营","休闲益智","体育运动","动作闯关","卡牌游戏"
            ,"赛车竞速","冒险解谜","音乐舞蹈"};
    private RadioGroup group_zhuji,group_shebei,group_type;
    private RecyclerView rec_vr;
    private List<VR_gameBean> mList =new ArrayList<>();
    private int page=0;
    private NestRefreshLayout nestRefreshLayout;
    private VRAdapter vrAdapter;
    private String shebei ="全部",zhuji="全部",type="全部";
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
        String url= AppBaseUrl.VR;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("Wearing_equipment",shebei);
        params.addBodyParameter("support_system",zhuji);
        LalaLog.d("vr参数",params.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("vr",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        VR_gameBean bean=new VR_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));

                        mList.add(bean);
                    }
                    vrAdapter.setmList(mList);
                    vrAdapter.notifyDataSetChanged();
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
        params3.addBodyParameter("sort","6");
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
        String url= AppBaseUrl.VR;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("game_type",type);
        params.addBodyParameter("Wearing_equipment",shebei);
        params.addBodyParameter("support_system",zhuji);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多vr",result);
                try {
                    List<VR_gameBean> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("game");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        VR_gameBean bean=new VR_gameBean();
                        bean.setName(temp.getString("game_name"));
                        bean.setJiashao(temp.getString("introduce_text"));
                        bean.setImg_bk(AppBaseUrl.BASEURL+temp.getString("background"));
                        bean.setId(temp.getString("id"));
                        bean.setType(temp.getString("game_type"));
                        bean.setImg_icon(AppBaseUrl.BASEURL+temp.getString("game_pic"));

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
                        vrAdapter.notifyItemRangeInserted(page*9,mNewslist.size());
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
        rec_vr = (RecyclerView) view.findViewById(R.id.rec_vr);
        LinearLayoutManager linManager1=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rec_vr.setLayoutManager(linManager1);
        rec_vr.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));
        rec_vr.setHasFixedSize(true);
        vrAdapter =new VRAdapter(getContext(),mList);
        rec_vr.setAdapter(vrAdapter);
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
        group_zhuji= (RadioGroup) view.findViewById(R.id.group_zhuji);
        group_zhuji.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_zhuji1:
                        zhuji ="全部";
                        redate();
                        break;
                    case R.id.btn_zhuji2:
                        zhuji ="安卓";
                        redate();
                        break;
                    case R.id.btn_zhuji3:
                        zhuji ="IOS";
                        redate();
                        break;
                    case R.id.btn_zhuji4:
                        zhuji ="PC";
                        redate();
                        break;
                    case R.id.btn_zhuji5:
                        zhuji ="PlayStation";
                        redate();
                        break;
                }
            }
        });
        group_shebei= (RadioGroup) view.findViewById(R.id.group_shebei);
        group_shebei.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_shebei1:
                        shebei="全部";
                        redate();
                        break;
                    case R.id.btn_shebei2:
                        shebei=VRType[0];
                        redate();
                        break;
                    case R.id.btn_shebei3:
                        shebei=VRType[1];
                        redate();
                        break;
                    case R.id.btn_shebei4:
                        shebei=VRType[2];
                        redate();
                        break;
                    case R.id.btn_shebei5:
                        shebei=VRType[3];
                        redate();
                        break;
                    case R.id.btn_shebei6:
                        shebei=VRType[4];
                        redate();
                        break;
                    case R.id.btn_shebei7:
                        shebei=VRType[5];
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
        return R.layout.fragment_vr;
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
