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
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseFragment;
import com.example.jinkejinfuapplication.shouye.TestNormalAdapter;
import com.example.jinkejinfuapplication.utils.DeviceUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
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

public class YingshiFragment extends BaseFragment implements OnPullListener {
    private String guojiaType[] =new String[]{"内地","香港","欧美","日韩","台湾","其他"};
    private String dianyingType[] =new String[]{"喜剧","动作","爱情","犯罪","战争","惊悚","悬疑","音乐","科幻","动画","其他"};
    private String timeType[] =new String[]{"2017","2016","2015","2014","2013-2011","2010-2006","2005-2000","90年代","80年代","其他"};
    private String diquType[] =new String[]{"大陆剧","港台剧","欧美剧","日韩剧","其他"};
    private String dianshiType[] =new String[]{"古装","偶像","都市","喜剧","战争","悬疑","科幻","网络","其他"};
    private RadioGroup group_yingshi,group_guojia,group_dianyingtype,group_time,group_diqu,group_dianshitype;
    private RecyclerView rec_yingshi,rec_img;
    private List<Map<String,String>> mList =new ArrayList<>();
    private int page=0;
    private NestRefreshLayout nestRefreshLayout;
    private Shiping_yingshiAdapter shiping_yingshiAdapter;
    private String yingshi="电影",guojia="全部",dianyingtype="全部",time="全部",diqu="全部",dianshitype="全部";
    private LinearLayout lin_shaix1,lin_shaix2;
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
        String url= AppBaseUrl.SHIPING_YINGSHI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("type",yingshi);
        if (yingshi.equals("电影")){
            params.addBodyParameter("years",time);
            params.addBodyParameter("region",guojia);
            params.addBodyParameter("sort",dianyingtype);
        }else {
            params.addBodyParameter("region",diqu);
            params.addBodyParameter("sort",dianshitype);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("视频影视",result);
                mList.clear();
                try {
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("movie");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("cover",temp.getString("cover"));
                        map.put("update_num",temp.getString("update_num"));
                        map.put("actress",temp.getString("actress"));
                        map.put("total",temp.getString("total"));
                        map.put("movie_name",temp.getString("movie_name"));
                        map.put("id",temp.getString("id"));
                        map.put("url",temp.getString("url"));
                        map.put("yingshi",yingshi);
                        mList.add(map);
                    }
                    shiping_yingshiAdapter.setmList(mList);
                    shiping_yingshiAdapter.notifyDataSetChanged();
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
        if (yingshi.equals("电影")){
            params3.addBodyParameter("sort","4");
        }else {
            params3.addBodyParameter("sort","5");
        }
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
        String url= AppBaseUrl.SHIPING_YINGSHI;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("str",page+"");
        params.addBodyParameter("type",yingshi);
        if (yingshi.equals("电影")){
            params.addBodyParameter("years",time);
            params.addBodyParameter("region",guojia);
            params.addBodyParameter("sort",dianyingtype);
        }else {
            params.addBodyParameter("region",diqu);
            params.addBodyParameter("sort",dianshitype);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.i("更多视频影视",result);
                try {
                    List<Map<String,String>> mNewslist=new ArrayList<>();
                    JSONObject jObj = new JSONObject(result);
                    JSONArray jrry=jObj.getJSONArray("movie");
                    for (int i=0;i<jrry.length();i++) {
                        JSONObject temp = jrry.getJSONObject(i);
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("cover",temp.getString("cover"));
                        map.put("update_num",temp.getString("update_num"));
                        map.put("actress",temp.getString("actress"));
                        map.put("total",temp.getString("total"));
                        map.put("movie_name",temp.getString("movie_name"));
                        map.put("id",temp.getString("id"));
                        map.put("url",temp.getString("url"));
                        map.put("yingshi",yingshi);
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
                        shiping_yingshiAdapter.notifyItemRangeInserted(page*15,mNewslist.size());
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
        rec_yingshi= (RecyclerView) view.findViewById(R.id.rec_shiping);
        GridLayoutManager linManager1=new GridLayoutManager(getContext(),3, LinearLayoutManager.VERTICAL,false);
        rec_yingshi.setLayoutManager(linManager1);
        /*rec_dujia.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(getContext(),15),true));*/
        rec_yingshi.setHasFixedSize(true);
        shiping_yingshiAdapter =new Shiping_yingshiAdapter(getContext(),mList);
        rec_yingshi.setAdapter(shiping_yingshiAdapter);
        nestRefreshLayout= (NestRefreshLayout) view.findViewById(R.id.nestlayou);
        nestRefreshLayout.setOnLoadingListener(this);

        mRollViewPager= (RollPagerView) view.findViewById(R.id.inforpager);
        testNormalAdapter=new TestNormalAdapter(LEADING_IMAGE_RESOURCES,getContext());
        mRollViewPager.setAdapter(testNormalAdapter);
        int height= DeviceUtil.deviceWidth(getContext())*670/1080;
        ViewGroup.LayoutParams params=mRollViewPager.getLayoutParams();
        params.height=height;
        mRollViewPager.setLayoutParams(params);

        lin_shaix1= (LinearLayout) view.findViewById(R.id.lin_shaix1);
        lin_shaix2= (LinearLayout) view.findViewById(R.id.lin_shaix2);
        img_moreshaix= (ImageView) view.findViewById(R.id.img_moreshaix);
        img_moreshaix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moreshaix){
                    moreshaix=false;
                    img_moreshaix.setImageResource(R.drawable.arrow_bttom);
                    if (yingshi.equals("电影")){
                        lin_shaix1.setVisibility(View.GONE);
                        lin_shaix2.setVisibility(View.GONE);
                    }else {
                        lin_shaix1.setVisibility(View.GONE);
                        lin_shaix2.setVisibility(View.GONE);
                    }
                }else {
                    moreshaix=true;
                    img_moreshaix.setImageResource(R.drawable.arrow_up);
                    if (yingshi.equals("电影")){
                        lin_shaix1.setVisibility(View.VISIBLE);
                        lin_shaix2.setVisibility(View.GONE);
                    }else {
                        lin_shaix1.setVisibility(View.GONE);
                        lin_shaix2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        group_yingshi= (RadioGroup) view.findViewById(R.id.group_yingshi);
        group_yingshi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_yingshi1:
                        yingshi ="电影";
                        moreshaix=false;
                        img_moreshaix.setImageResource(R.drawable.arrow_bttom);
                        lin_shaix1.setVisibility(View.GONE);
                        lin_shaix2.setVisibility(View.GONE);
                        redate();
                        break;
                    case R.id.btn_yingshi2:
                        yingshi ="电视剧";
                        moreshaix=false;
                        img_moreshaix.setImageResource(R.drawable.arrow_bttom);
                        lin_shaix1.setVisibility(View.GONE);
                        lin_shaix2.setVisibility(View.GONE);
                        redate();
                        break;
                }
            }
        });
        group_guojia= (RadioGroup) view.findViewById(R.id.group_guojia);
        group_guojia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_guojia1:
                        guojia ="全部";
                        redate();
                        break;
                    case R.id.btn_guojia2:
                        guojia =guojiaType[0];
                        redate();
                        break;
                    case R.id.btn_guojia3:
                        guojia =guojiaType[1];
                        redate();
                        break;
                    case R.id.btn_guojia4:
                        guojia =guojiaType[2];
                        redate();
                        break;
                    case R.id.btn_guojia5:
                        guojia =guojiaType[3];
                        redate();
                        break;
                    case R.id.btn_guojia6:
                        guojia =guojiaType[4];
                        redate();
                        break;
                    case R.id.btn_guojia7:
                        guojia =guojiaType[5];
                        redate();
                        break;
                }
            }
        });
        group_dianyingtype= (RadioGroup) view.findViewById(R.id.group_dianyingtype);
        group_dianyingtype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_dianyingtype1:
                        dianyingtype ="全部";
                        redate();
                        break;
                    case R.id.btn_dianyingtype2:
                        dianyingtype =dianyingType[0];
                        redate();
                        break;
                    case R.id.btn_dianyingtype3:
                        dianyingtype =dianyingType[1];
                        redate();
                        break;
                    case R.id.btn_dianyingtype4:
                        dianyingtype =dianyingType[2];
                        redate();
                        break;
                    case R.id.btn_dianyingtype5:
                        dianyingtype =dianyingType[3];
                        redate();
                        break;
                    case R.id.btn_dianyingtype6:
                        dianyingtype =dianyingType[4];
                        redate();
                        break;
                    case R.id.btn_dianyingtype7:
                        dianyingtype =dianyingType[5];
                        redate();
                        break;
                    case R.id.btn_dianyingtype8:
                        dianyingtype =dianyingType[6];
                        redate();
                        break;
                    case R.id.btn_dianyingtype9:
                        dianyingtype =dianyingType[7];
                        redate();
                        break;
                    case R.id.btn_dianyingtype10:
                        dianyingtype =dianyingType[8];
                        redate();
                        break;
                }
            }
        });
        group_time= (RadioGroup) view.findViewById(R.id.group_time);
        group_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_time1:
                        time ="全部";
                        redate();
                        break;
                    case R.id.btn_time2:
                        time =timeType[0];
                        redate();
                        break;
                    case R.id.btn_time3:
                        time =timeType[1];
                        redate();
                        break;
                    case R.id.btn_time4:
                        time =timeType[2];
                        redate();
                        break;
                    case R.id.btn_time5:
                        time =timeType[3];
                        redate();
                        break;
                    case R.id.btn_time6:
                        time =timeType[4];
                        redate();
                        break;
                    case R.id.btn_time7:
                        time =timeType[5];
                        redate();
                        break;
                    case R.id.btn_time8:
                        time =timeType[6];
                        redate();
                        break;
                    case R.id.btn_time9:
                        time =timeType[7];
                        redate();
                        break;
                    case R.id.btn_time10:
                        time =timeType[8];
                        redate();
                        break;
                    case R.id.btn_time11:
                        time =timeType[9];
                        redate();
                        break;
                }
            }
        });
        group_diqu= (RadioGroup) view.findViewById(R.id.group_diqu);
        group_diqu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_diqu1:
                        diqu ="全部";
                        redate();
                        break;
                    case R.id.btn_diqu2:
                        diqu =diquType[0];
                        redate();
                        break;
                    case R.id.btn_diqu3:
                        diqu =diquType[1];
                        redate();
                        break;
                    case R.id.btn_diqu4:
                        diqu =diquType[2];
                        redate();
                        break;
                    case R.id.btn_diqu5:
                        diqu =diquType[3];
                        redate();
                        break;
                    case R.id.btn_diqu6:
                        diqu =diquType[4];
                        redate();
                        break;
                }
            }
        });
        group_dianshitype= (RadioGroup) view.findViewById(R.id.group_dianshitype);
        group_dianshitype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_dianshitype1:
                        dianshitype ="全部";
                        redate();
                        break;
                    case R.id.btn_dianshitype2:
                        dianshitype =dianshiType[0];
                        redate();
                        break;
                    case R.id.btn_dianshitype3:
                        dianshitype =dianshiType[1];
                        redate();
                        break;
                    case R.id.btn_dianshitype4:
                        dianshitype =dianshiType[2];
                        redate();
                        break;
                    case R.id.btn_dianshitype5:
                        dianshitype =dianshiType[3];
                        redate();
                        break;
                    case R.id.btn_dianshitype6:
                        dianshitype =dianshiType[4];
                        redate();
                        break;
                    case R.id.btn_dianshitype7:
                        dianshitype =dianshiType[5];
                        redate();
                        break;
                    case R.id.btn_dianshitype8:
                        dianshitype =dianshiType[6];
                        redate();
                        break;
                    case R.id.btn_dianshitype9:
                        dianshitype =dianshiType[7];
                        redate();
                        break;
                    case R.id.btn_dianshitype10:
                        dianshitype =dianshiType[8];
                        redate();
                        break;
                }
            }
        });
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_yingshi;
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
