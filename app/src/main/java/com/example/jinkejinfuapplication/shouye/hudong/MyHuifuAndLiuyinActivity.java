package com.example.jinkejinfuapplication.shouye.hudong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.bean.MyhuatiBean;
import com.example.jinkejinfuapplication.bean.MyliuyinBean;
import com.example.jinkejinfuapplication.utils.DisplayUtil;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SpaceItemDecoration;
import com.example.jinkejinfuapplication.utils.ToastUtil;

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

public class MyHuifuAndLiuyinActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    private Spinner spinner_paixu;
    private String[] jines=new String[]{"按时间排序","按浏览量排序","按点赞数排序"};
    private RecyclerView rec;
    private Hudong_myhuifuAdapter myhuifuAdapter;
    private Hudong_myliuyinAdapter myliuyinAdapter;
    private List<Map<String, String>> mList=new ArrayList<>();
    private List<MyliuyinBean> mhuatiList=new ArrayList<>();
    private PtrClassicFrameLayout ptrFrameLayout;
    private int page=0;
    private String paixu="1";
    private String type="1";
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_huifu_and_liuyin);
        setTitle("我的回复");
        radioGroup=findView(R.id.myrg);
        radioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyHuifuAndLiuyinActivity.this,AddLiuyinActivity.class);
                startActivity(intent);

            }
        });
        spinner_paixu= (Spinner) findViewById(R.id.spinner_paixu);
        Spinner_huatiAdapater spinnerAdapater=new Spinner_huatiAdapater(this,jines);
        spinner_paixu .setAdapter(spinnerAdapater);
        spinner_paixu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paixu=(i+1)+"";
                redate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        rec= (RecyclerView) findViewById(R.id.rec);
        LinearLayoutManager linManager1=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec.setLayoutManager(linManager1);
        rec.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(this,7),true));
        rec.setHasFixedSize(true);
        myhuifuAdapter=new Hudong_myhuifuAdapter(this,mList);
        rec.setAdapter(myhuifuAdapter);

        myliuyinAdapter=new Hudong_myliuyinAdapter(this,mhuatiList);

        ptrFrameLayout= (PtrClassicFrameLayout) findViewById(R.id.prtframelayout);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        moredate();
                    }
                },500);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redate();
                        ptrFrameLayout.refreshComplete();
                    }
                },500);
            }
        });
    }

    @Override
    protected void initData() {
        redate();
    }
    private void redate() {
        page=0;
        if (type.equals("1")){
            String url= AppBaseUrl.HUDONG_MYHUIFU_LIUYIN;
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
            params.addBodyParameter("str", page+"");
            params.addBodyParameter("str1", paixu);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LalaLog.i("话题",result);
                    mList.clear();
                    try {
                        JSONObject jObj = new JSONObject(result);
                        JSONArray jrry=jObj.getJSONArray("message");
                        for (int i=0;i<jrry.length();i++) {
                            JSONObject ob = jrry.getJSONObject(i);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("comment_photo",AppBaseUrl.isHttp(ob.getString("reply_pic")));
                            map.put("topic_datetime",ob.getString("datetime"));
                            map.put("comment_name",ob.getString("reply_name"));
                            map.put("comment_content",ob.getString("reply"));
                            map.put("topic_content",ob.getString("message_content"));
                            map.put("topic_name",ob.getString("message_name"));
                            map.put("topic_img",AppBaseUrl.isHttp(ob.getString("message_pic")));

                            mList.add(map);
                        }
                        myhuifuAdapter.setmList(mList);
                        myhuifuAdapter.notifyDataSetChanged();
                        rec.setAdapter(myhuifuAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LalaLog.d("错误详情",ex.toString());
                    ToastUtil.show(MyHuifuAndLiuyinActivity.this,"网络发生错误!");
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        }else {
            String url= AppBaseUrl.HUDONG_MYLIUYIN;
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
            params.addBodyParameter("str", page+"");
            params.addBodyParameter("str1", paixu);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LalaLog.i("话题1",result);
                    mhuatiList.clear();
                    try {
                        JSONObject jObj = new JSONObject(result);
                        jObj=jObj.getJSONObject("comment_list");
                        JSONArray jrry=jObj.getJSONArray("comment");
                        for (int i=0;i<jrry.length();i++) {
                            JSONObject temp = jrry.getJSONObject(i);
                            MyliuyinBean bean=new MyliuyinBean();
                            bean.setName(temp.getString("name"));
                            bean.setPic(temp.getString("pic"));
                            bean.setId(temp.getString("commentId"));
                            bean.setMessage(temp.getString("message"));
                            bean.setReplynum(temp.getString("replynum"));
                            bean.setLike(temp.getString("like"));
                            bean.setDatetime(temp.getString("datetime"));
                            mhuatiList.add(bean);
                        }

                        for (int n=0;n<mhuatiList.size();n++) {
                            List<Map<String, String>> mnewList=new ArrayList<>();
                            JSONArray jrry2=jObj.getJSONArray("reply");
                            for (int i=0;i<jrry2.length();i++) {
                                JSONObject ob = jrry2.getJSONObject(i);

                                if (ob.getString("commentId").equals(mhuatiList.get(n).getId())){
                                    Map<String,String> map=new HashMap<String, String>();
                                    map.put("pic",AppBaseUrl.isHttp(ob.getString("pic")));
                                    map.put("content",ob.getString("reply"));
                                    map.put("commentId",ob.getString("commentId"));
                                    mnewList.add(map);
                                }

                            }
                            mhuatiList.get(n).setGameList(mnewList);
                        }

                        myliuyinAdapter.setmList(mhuatiList);
                        myliuyinAdapter.notifyDataSetChanged();
                        rec.setAdapter(myliuyinAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LalaLog.d("错误详情",ex.toString());
                    ToastUtil.show(MyHuifuAndLiuyinActivity.this,"网络发生错误!");
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        }

    }
    private void moredate() {
        if (type.equals("1")){
            String url= AppBaseUrl.HUDONG_MYHUIFU_LIUYIN;
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
            params.addBodyParameter("str", page+"");
            params.addBodyParameter("str1", paixu);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LalaLog.i("话题",result);
                    try {
                        List<Map<String,String>> mNewslist=new ArrayList<>();
                        JSONObject jObj = new JSONObject(result);
                        JSONArray jrry=jObj.getJSONArray("message");
                        for (int i=0;i<jrry.length();i++) {
                            JSONObject ob = jrry.getJSONObject(i);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("comment_photo",AppBaseUrl.isHttp(ob.getString("reply_pic")));
                            map.put("topic_datetime",ob.getString("datetime"));
                            map.put("comment_name",ob.getString("reply_name"));
                            map.put("comment_content",ob.getString("reply"));
                            map.put("topic_content",ob.getString("message_content"));
                            map.put("topic_name",ob.getString("message_name"));
                            map.put("topic_img",AppBaseUrl.isHttp(ob.getString("message_pic")));
                            mNewslist.add(map);
                        }
                        if (mNewslist.size()==0)
                        {
                            ptrFrameLayout.refreshComplete();
                            ToastUtil.show(MyHuifuAndLiuyinActivity.this,"没有更多数据了");
                        }else
                        {
                            mList.addAll(mNewslist);
                            ptrFrameLayout.refreshComplete();
                            myhuifuAdapter.notifyItemRangeInserted(page*10,mNewslist.size());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LalaLog.d("错误详情",ex.toString());
                    ToastUtil.show(MyHuifuAndLiuyinActivity.this,"网络发生错误!");
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        }else {
            String url= AppBaseUrl.HUDONG_MYLIUYIN;
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
            params.addBodyParameter("sort", "topic");
            params.addBodyParameter("str", page+"");
            params.addBodyParameter("str1", paixu);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LalaLog.i("话题1",result);
                    try {
                        JSONObject jObj = new JSONObject(result);
                        List<MyliuyinBean> mhuatiList1=new ArrayList<>();
                        jObj=jObj.getJSONObject("comment_list");
                        JSONArray jrry=jObj.getJSONArray("comment");
                        for (int i=0;i<jrry.length();i++) {
                            JSONObject temp = jrry.getJSONObject(i);
                            MyliuyinBean bean=new MyliuyinBean();
                            bean.setName(temp.getString("name"));
                            bean.setPic(temp.getString("pic"));
                            bean.setId(temp.getString("commentId"));
                            bean.setMessage(temp.getString("message"));
                            bean.setReplynum(temp.getString("replynum"));
                            bean.setLike(temp.getString("like"));
                            bean.setDatetime(temp.getString("datetime"));
                            mhuatiList1.add(bean);
                        }
                        for (int n=0;n<mhuatiList1.size();n++) {
                            List<Map<String, String>> mnewList=new ArrayList<>();
                            JSONArray jrry2=jObj.getJSONArray("reply");
                            for (int i=0;i<jrry2.length();i++) {
                                JSONObject ob = jrry2.getJSONObject(i);

                                if (ob.getString("commentId").equals(mhuatiList1.get(n).getId())){
                                    Map<String,String> map=new HashMap<String, String>();
                                    map.put("pic",AppBaseUrl.isHttp(ob.getString("pic")));
                                    map.put("content",ob.getString("reply"));
                                    map.put("commentId",ob.getString("commentId"));
                                    mnewList.add(map);

                                }

                            }
                            mhuatiList1.get(n).setGameList(mnewList);
                        }

                        if (mhuatiList1.size()==0)
                        {
                            ptrFrameLayout.refreshComplete();
                            ToastUtil.show(MyHuifuAndLiuyinActivity.this,"没有更多数据了");
                        }else
                        {
                            mhuatiList.addAll(mhuatiList1);
                            ptrFrameLayout.refreshComplete();
                            myliuyinAdapter.notifyItemRangeInserted(mhuatiList.size()-mhuatiList1.size(),mhuatiList1.size());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LalaLog.d("错误详情",ex.toString());
                    ToastUtil.show(MyHuifuAndLiuyinActivity.this,"网络发生错误!");
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });
        }

    }
    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.btn_huati_huifu:
                setTitle("我的回复");
                type="1";
                redate();
                break;
            case R.id.btn_huati_liuyin:
                setTitle("我的留言");
                type="2";
                redate();
                break;
        }
    }
}
