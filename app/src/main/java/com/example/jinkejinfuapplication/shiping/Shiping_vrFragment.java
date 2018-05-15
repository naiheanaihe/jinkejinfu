package com.example.jinkejinfuapplication.shiping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.VR.VRAdapter;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.bean.VR_gameBean;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
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

public class Shiping_vrFragment extends BaseFragment implements OnPullListener {
    private String gameType[] =new String[]{"风景","美女","科幻","冒险","竞速","动作","体育","剧情","其他"};
    private RadioGroup group_zhuji,group_type;
    private RecyclerView rec_shiping;
    private List<Map<String,String>> mList =new ArrayList<>();
    private int page=0;
    private NestRefreshLayout nestRefreshLayout;
    private Shiping_zhuyeAdapter shiping_zhuyeAdapter;
    private String zhuji="全部",type="全部";
    private LinearLayout lin_shaix;
    private ImageView img_moreshaix;
    private boolean moreshaix=false;
    private RollPagerView mRollViewPager;
    private TestNormal_shipingAdapter testNormalAdapter;
    private List<Map<String,String>> LEADING_IMAGE_RESOURCES=new ArrayList<>();
    @Override
    public void initData(Bundle savedInstanceState) {
        redate();
    }
    private void redate(){
        page=0;
        String url= AppBaseUrl.SHIPING_VR;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("type",zhuji);
        params.addBodyParameter("type1",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("视频游戏",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("video");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",temp.getString("gameId"));
                        map.put("introduce",temp.getString("introduce"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("videos",AppBaseUrl.BASEURL+temp.getString("videos"));
                        map.put("title",temp.getString("title"));
                        map.put("id",temp.getString("id"));
                        map.put("datetime",AppBaseUrl.BASEURL+temp.getString("datetime"));
                        mList.add(map);
                    }
                    shiping_zhuyeAdapter.setmList(mList);
                    shiping_zhuyeAdapter.notifyDataSetChanged();
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
        String url3= AppBaseUrl.BANNER_VR;
        RequestParams params3 = new RequestParams(url3);
        x.http().get(params3, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("广告",result);
                LEADING_IMAGE_RESOURCES.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("top");
                    for (int i=0;i<jrry.length();i++)
                    {
                        JSONObject temp=jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",temp.getString("id"));
                        map.put("url", AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("gameId",temp.getString("gameId"));
                        map.put("title",temp.getString("title"));
                        map.put("video",temp.getString("video"));
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
        String url= AppBaseUrl.SHIPING_VR;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("type",zhuji);
        params.addBodyParameter("type1",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多视频游戏",result);
                try {
                    List<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("video");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("gameId",temp.getString("gameId"));
                        map.put("introduce",temp.getString("introduce"));
                        map.put("background",AppBaseUrl.BASEURL+temp.getString("background"));
                        map.put("videos",AppBaseUrl.BASEURL+temp.getString("videos"));
                        map.put("title",temp.getString("title"));
                        map.put("id",temp.getString("id"));
                        map.put("datetime",AppBaseUrl.BASEURL+temp.getString("datetime"));
                        mNewslist.add(map);
                    }
                    if (mNewslist.size()==0)
                    {
                        nestRefreshLayout.onLoadFinished();
                        ToastUtil.show(getContext(),"没有更多数据了");
                    }else
                    {
                        mList.addAll(mNewslist);
                        nestRefreshLayout.onLoadFinished();
                        shiping_zhuyeAdapter.notifyItemRangeInserted(page*12,mNewslist.size());
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
        rec_shiping= (RecyclerView) view.findViewById(R.id.rec_shiping);
        GridLayoutManager linManager1=new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        rec_shiping.setLayoutManager(linManager1);
        /*rec_dujia.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));*/
        rec_shiping.setHasFixedSize(true);
        shiping_zhuyeAdapter=new Shiping_zhuyeAdapter(getContext(),mList);
        rec_shiping.setAdapter(shiping_zhuyeAdapter);
        nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);
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

        mRollViewPager= (RollPagerView) view.findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormal_shipingAdapter(LEADING_IMAGE_RESOURCES,getContext());
        mRollViewPager.setAdapter(testNormalAdapter);
        int height= DeviceUtil.deviceWidth(getContext())*260/450;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);

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
                        zhuji ="虚拟现实（VR）";
                        redate();
                        break;
                    case R.id.btn_zhuji3:
                        zhuji ="增强现实（AR）";
                        redate();
                        break;
                    case R.id.btn_zhuji4:
                        zhuji ="混合现实（MR）";
                        redate();
                        break;
                    case R.id.btn_zhuji5:
                        zhuji ="电影现实（CR）";
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
                }
            }
        });
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_shiping_vr;
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
