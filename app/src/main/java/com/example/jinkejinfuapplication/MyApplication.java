package com.example.jinkejinfuapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss_android_sdk.BuildConfig;
import com.example.jinkejinfuapplication.bean.YonghuBean;
import com.example.jinkejinfuapplication.utils.LalaLog;
import com.example.jinkejinfuapplication.utils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Administrator on 2017/9/20.
 */

public class MyApplication extends Application
{
    //OSS的Bucket
    public static final String OSS_BUCKET = "shunxe";
    //设置OSS数据中心域名或者cname域名
    public static final String OSS_BUCKET_HOST_ID = "http://oss-cn-shenzhen.aliyuncs.com";
    //Key
    private static final String accessKey = "9p0YXjt1J654vrX2";
    private static final String screctKey = "nIr4FVcUmJvcuR8TdcnmFRDZs2iqDB";
    public static OSS oss;

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private YonghuBean yonghuBean;

    public YonghuBean getYonghuBean() {
        return yonghuBean;
    }

    public void setYonghuBean(YonghuBean yonghuBean) {
        this.yonghuBean = yonghuBean;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        LalaLog.setDebug(true);
        //初始化OSS配置
        initOSSConfig();
        instance=this;
        Config.DEBUG = true;
        UMShareAPI.get(this);
        PlatformConfig.setQQZone("1106558092", "FLkh67bibtfrfvIe");
        PlatformConfig.setWeixin("wx0284fd47de760c90","70ae12b798bfbbeae54318709cc30eb2");
        PlatformConfig.setSinaWeibo("1474348540","4cc204e248a1e8b288ede2814e64e6cd",null);
        UMConfigure.init(this, "5a20c20df29d9879b200018c", "jinkejinfu", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL );
        UMConfigure.setEncryptEnabled (true);

        int loginstate= Integer.parseInt((String) SharedPreferencesUtils.getParam(this,"user","loginstate","1"));
        if (loginstate==2)
        {
            MainActivity.LOGINSTATE=MainActivity.LOGINSTATE_LOGIN;
            loadUserInfor(this);
        }
    }
    private void loadUserInfor(Context context)
    {
        YonghuBean bean=new YonghuBean();
        bean.setUserId((String) SharedPreferencesUtils.getParam(this,"user","userId",""));
        bean.setUsername((String) SharedPreferencesUtils.getParam(this,"user","username",""));
        bean.setNickname((String) SharedPreferencesUtils.getParam(this,"user","nickname",""));
        bean.setPhoto((String) SharedPreferencesUtils.getParam(this,"user","photo",""));
        bean.setMobilephone((String) SharedPreferencesUtils.getParam(this,"user","mobilephone",""));
        bean.setDD((String) SharedPreferencesUtils.getParam(this,"user","DD",""));
        bean.setTao((String) SharedPreferencesUtils.getParam(this,"user","tao",""));
        bean.setDada((String) SharedPreferencesUtils.getParam(this,"user","dada",""));
        bean.setType((String) SharedPreferencesUtils.getParam(this,"user","type",""));
        bean.setType1((String) SharedPreferencesUtils.getParam(this,"user","type1",""));
        bean.setStatus((String) SharedPreferencesUtils.getParam(this,"user","status",""));
        bean.setUdcode((String) SharedPreferencesUtils.getParam(this,"user","udcode",""));
        setYonghuBean(bean);
    }


    private void initOSSConfig(){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKey, screctKey);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        if(BuildConfig.DEBUG){
            OSSLog.enableLog();
        }
        oss = new OSSClient(getApplicationContext(), MyApplication.OSS_BUCKET_HOST_ID, credentialProvider, conf);
    }
}
