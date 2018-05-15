package com.example.jinkejinfuapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jinkejinfuapplication.VR.VRFragment;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.AppStatusConstant;
import com.example.jinkejinfuapplication.base.BaseActivity;
import com.example.jinkejinfuapplication.dujia.DujiaFragment;
import com.example.jinkejinfuapplication.haiwai.HaiwaiFragment;
import com.example.jinkejinfuapplication.mine.LoginActivity;
import com.example.jinkejinfuapplication.mine.MineActivity;
import com.example.jinkejinfuapplication.shiping.ShipingFragment;
import com.example.jinkejinfuapplication.shouye.SeachActivity;
import com.example.jinkejinfuapplication.shouye.ShouyeFragment;
import com.example.jinkejinfuapplication.shouye.Shouye_gameAdapter;
import com.example.jinkejinfuapplication.view.FullDialog;
import com.example.jinkejinfuapplication.utils.ImageOptions;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.example.jinkejinfuapplication.utils.ToastUtil;
import com.example.jinkejinfuapplication.xiangq.StartActivity;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    public static final int LOGINSTATE_NOTLOGIN=1; //未登录
    public static final int LOGINSTATE_LOGIN=2; //已登录
    public static int LOGINSTATE=LOGINSTATE_NOTLOGIN; //登录状态
    protected final int SHOUYE_FLAG = 0x101;
    protected final int HAIWAI_FLAG = 0x102;
    protected final int VR_FLAG = 0x103;
    protected final int DUJIA_FLAG = 0x104;
    protected final int SHIPING_FLAG = 0x105;
    private MyApplication app;
    public RadioGroup mGroup;
    public  ShouyeFragment shouyeFragment;
    public  HaiwaiFragment haiwaiFragment;
    public  VRFragment vrFragment;
    public  DujiaFragment dujiaFragment;
    public  ShipingFragment shipingFragment;
    private FragmentManager mFarg;
    private FragmentTransaction ft;
    private ImageView mine,search;
    private TextView myname;
    //更新
    private int currentV=0, newV=0;
    private JSONObject jo_v;
    private String newVContent = "";
    private ProgressBar pb;
    private TextView tv;
    public static int loading_process;
    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE=100;
    String path= Environment.getExternalStorageDirectory().toString();
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        app= (MyApplication) getApplication();
        mine=findView(R.id.mine);
        myname=findView(R.id.myname);
        myname.setOnClickListener(this);
        mine.setOnClickListener(this);
        search=findView(R.id.search);
        search.setOnClickListener(this);

        if (StartActivity.instance!=null){
            StartActivity.instance.finish();
        }
        mGroup=findView(R.id.rg_tabs);
        mGroup.setOnCheckedChangeListener(this);
        mFarg=getSupportFragmentManager();
        showContentFragment(SHOUYE_FLAG, null);

        if(Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this,mPermissionList,123);
            }else {
                initupload();
            }
        }else {
            initupload();
        }

        if (LOGINSTATE==LOGINSTATE_LOGIN){
            initxinxi();
        }

        initgame();

    }

    private void initgame() {
        String url1 = AppBaseUrl.UPDATE_GAME;
        RequestParams params1 = new RequestParams(url1);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result.toString());
                String s = result.toString();
                try {
                    JSONObject job = new JSONObject(s);
                    job=job.getJSONObject("game");
                    String update=job.getString("update");
                    if (update.equals("1")){
                        final FullDialog dialog = new FullDialog(MainActivity.this);
                        final View views=View.inflate(MainActivity.this, R.layout.update_game_dialog,null);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(views);

                        JSONArray jrry=job.getJSONArray("game_list");
                        ArrayList<Map<String,String>> mNewslist=new ArrayList<>();
                        for (int i=0;i<jrry.length();i++)
                        {
                            JSONObject temp=jrry.getJSONObject(i);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("game_name",temp.getString("game_name"));
                            map.put("id",temp.getString("id"));
                            map.put("game_pic",AppBaseUrl.BASEURL+temp.getString("game_pic"));
                            map.put("type",temp.getString("type"));
                            mNewslist.add(map);
                        }
                        RecyclerView rec_xiangsi= (RecyclerView) views.findViewById(R.id.rec_xiangsi);
                        LinearLayoutManager linManager1=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
                        rec_xiangsi.setLayoutManager(linManager1);
                        Shouye_gameAdapter shouye_gameAdapter=new Shouye_gameAdapter(MainActivity.this, mNewslist);
                        rec_xiangsi.setAdapter(shouye_gameAdapter);

                        ImageView cha= (ImageView) views.findViewById(R.id.cha);
                        cha.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        TextView xiangq= (TextView) views.findViewById(R.id.xiangq);
                        xiangq.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                mGroup.check(R.id.rb_dujia);
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
                ToastUtil.show(MainActivity.this, "获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void initxinxi() {
        String url1 = AppBaseUrl.UPDATE_XINXI;
        RequestParams params1 = new RequestParams(url1);
        params1.addBodyParameter("userId", MyApplication.getInstance().getYonghuBean().getUserId());
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LalaLog.d("信息",result.toString());
                String s = result.toString();
                try {
                    JSONObject job = new JSONObject(s);
                    job=job.getJSONObject("user");
                    String nickname=job.getString("nickname");
                    String photo=AppBaseUrl.isHttp(job.getString("photo"));
                    String udcode=job.getString("udcode");
                    MyApplication.getInstance().getYonghuBean().setNickname(nickname);
                    MyApplication.getInstance().getYonghuBean().setPhoto(photo);
                    MyApplication.getInstance().getYonghuBean().setUdcode(udcode);
                    SharedPreferencesUtils.setParam(MainActivity.this,"user","nickname",nickname);
                    SharedPreferencesUtils.setParam(MainActivity.this,"user","photo",photo);
                    SharedPreferencesUtils.setParam(MainActivity.this,"user","udcode",udcode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show(MainActivity.this, "获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void initupload() {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                initconnect();
            }
        },2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch(requestCode)
        {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 123:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initupload();
                } else{
                    // 没有获取到权限，做特殊处理
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void initData() {
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected boolean isShowToolBar() {
        return false;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i)
        {
            case R.id.rb_shouye:
                showContentFragment(SHOUYE_FLAG,null);
                break;
            case R.id.rb_haiwai:
                showContentFragment(HAIWAI_FLAG,null);
                break;
            case R.id.rb_vr:
                showContentFragment(VR_FLAG,null);
                break;
            case R.id.rb_dujia:
                showContentFragment(DUJIA_FLAG,null);
                break;
            case R.id.rb_shiping:
                showContentFragment(SHIPING_FLAG,null);
                break;
        }
    }
    public  void showContentFragment(int index, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ft = mFarg.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case SHOUYE_FLAG:
                if (shouyeFragment == null) {
                    shouyeFragment = new ShouyeFragment();
                    shouyeFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, shouyeFragment);
                } else {
                    ft.show(shouyeFragment);
                }
                break;
            case HAIWAI_FLAG:
                if (haiwaiFragment == null) {
                    haiwaiFragment = new HaiwaiFragment();
                    haiwaiFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, haiwaiFragment);
                } else {
                    ft.show(haiwaiFragment);
                }
                break;
            case VR_FLAG:
                if (vrFragment == null) {
                    vrFragment = new VRFragment();
                    vrFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, vrFragment);
                } else {
                    ft.show(vrFragment);
                }
                break;
            case DUJIA_FLAG:
                if (dujiaFragment == null) {
                    dujiaFragment = new DujiaFragment();
                    dujiaFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, dujiaFragment);
                } else {
                    ft.show(dujiaFragment);
                }
                break;
            case SHIPING_FLAG:
                if (shipingFragment == null) {
                    shipingFragment = new ShipingFragment();
                    shipingFragment.setArguments(bundle);
                    ft.add(R.id.fl_content, shipingFragment);
                } else {
                    ft.show(shipingFragment);
                }
                break;
        }
        ft.commit();
    }
    private void hideFragment(FragmentTransaction ft) {
        if (shouyeFragment != null) {
            ft.hide(shouyeFragment);
        }
        if (haiwaiFragment != null) {
            ft.hide(haiwaiFragment);
        }
        if (vrFragment != null) {
            ft.hide(vrFragment);
        }
        if (dujiaFragment != null) {
            ft.hide(dujiaFragment);
        }
        if (shipingFragment != null) {
            ft.hide(shipingFragment);
        }
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;


    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToastMsg("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            /*ExitAppliation.getInstance().exit();*/
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.mine:
                if (LOGINSTATE==LOGINSTATE_NOTLOGIN){
                    intent=new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent=new Intent(this, MineActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search:
                intent=new Intent(this, SeachActivity.class);
                startActivity(intent);
                break;
            case R.id.myname:
                if (LOGINSTATE==LOGINSTATE_LOGIN){
                    intent=new Intent(this, MineActivity.class);
                    startActivity(intent);
                }else if (LOGINSTATE==LOGINSTATE_NOTLOGIN){
                    intent=new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LOGINSTATE==LOGINSTATE_LOGIN){
            x.image().bind(mine,app.getYonghuBean().getPhoto(), ImageOptions.getimageOptions());
            myname.setText(app.getYonghuBean().getNickname());
            MobclickAgent.onProfileSignIn(app.getYonghuBean().getUserId());
        }else if (LOGINSTATE==LOGINSTATE_NOTLOGIN){
            myname.setText("登录/注册");
            mine.setImageResource(R.drawable.ic_defult_head);
        }
    }

    private void initconnect()
    {

        if (isConnect(this)) {
            currentV = getVerCode(this, "com.example.jinkejinfuapplication");
            String url1 = AppBaseUrl.UPLOAD;
            RequestParams params1 = new RequestParams(url1);
            x.http().post(params1, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result.toString());
                    String s = result.toString();
                    try {
                        JSONObject job = new JSONObject(s);
                        jo_v = job.getJSONObject("upload");
                        newV = Integer.parseInt(jo_v.getString("version"));
                        if (newV > currentV) {
                            Message msg = BroadcastHandler.obtainMessage();
                            BroadcastHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtil.show(MainActivity.this, "获取数据失败");
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
    private void sendMsg(int flag,int c) {
        Message msg = new Message();
        msg.what = flag;msg.arg1=c;
        handler.sendMessage(msg);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1:
                        pb.setProgress(msg.arg1);
                        loading_process = msg.arg1;
                        tv.setText("已为您加载了：" + loading_process + "%");
                        break;
                    case 2:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File( Environment.getExternalStorageDirectory().getPath(), "goldcs.apk")),
                                "application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };
    private Handler BroadcastHandler = new Handler() {
        public void handleMessage(Message msg) {

            try {
                if (jo_v.getString("version_introduce")!=null)
                {
                    String s=jo_v.getString("version_introduce");
                    String[] ss=s.split(" ");
                    for (int i=0;i<ss.length;i++)
                    {
                        newVContent+=(i+1)+"."+ss[i]+"\n";
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Dialog dialog = new AlertDialog.Builder(
                    MainActivity.this)
                    .setTitle("新版本更新")
                    .setMessage(newVContent)
                    .setPositiveButton("马上更新",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Beginning();
                                    dialog.dismiss();
                                }
                            })
                    .setNegativeButton("以后再说",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();
                                }
                            }).create();
            dialog.show();
        }
    };
    public void Beginning(){
        LinearLayout ll = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
                R.layout.layout_loadapk, null);
        pb = (ProgressBar) ll.findViewById(R.id.down_pb);
        tv = (TextView) ll.findViewById(R.id.tv);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(ll);builder.setTitle("版本更新进度提示");
        builder.setNegativeButton("后台下载",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(MainActivity.this, VersionService.class);
                        startService(intent);
                        dialog.dismiss();
                    }
                });

        builder.show();
        new Thread() {
            public void run() {
                loadFile(AppBaseUrl.BASEURL+"static/app/goldcs.apk");
            }
        }.start();
    }

    private void loadFile(String url) {
        RequestParams params1 = new RequestParams(url);
        params1.setSaveFilePath(Environment.getExternalStorageDirectory().getPath()+File.separator+"goldcs.apk");
        x.http().post(params1, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                sendMsg(1,(int) (current*100/total));
            }

            @Override
            public void onSuccess(File result) {
                sendMsg(2,0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToastMsg("网络连接错误！");
                LalaLog.d("下载错误",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public boolean isConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
    public int getVerCode(Context _context,String _package) {
        int verCode = -1;
        try {
            verCode = _context.getPackageManager().getPackageInfo(
                    _package, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }

    @Override
    protected void setUpViewAndData() {
        super.setUpViewAndData();
    }

    @Override
    protected void restartApp() {
        /*Toast.makeText(getApplicationContext(),"应用被回收重启",Toast.LENGTH_LONG).show();*/
        startActivity(new Intent(this,StartActivity.class));
        finish();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(AppStatusConstant.KEY_HOME_ACTION,AppStatusConstant.ACTION_BACK_TO_HOME);
        switch(action) {
            case AppStatusConstant.ACTION_RESTART_APP:
            restartApp();
            break;
        }
    }

}
