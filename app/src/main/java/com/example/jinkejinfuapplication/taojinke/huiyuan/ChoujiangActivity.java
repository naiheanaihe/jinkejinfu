package com.example.jinkejinfuapplication.taojinke.huiyuan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.jinkejinfuapplication.MyApplication;
import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.AppBaseUrl;
import com.example.jinkejinfuapplication.base.BaseActivity;

public class ChoujiangActivity extends BaseActivity {
    private WebView webView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choujiang);
        setTitle("免费抽奖");

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(AppBaseUrl.CHOUJIANG+"?userId="+ MyApplication.getInstance().getYonghuBean().getUserId());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(AppBaseUrl.CHOUJIANG+"?userId="+ MyApplication.getInstance().getYonghuBean().getUserId());
                return false;
            }
        });
        webView.setWebChromeClient(new MyWebChromeClient());


    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView webView, String url) {
            webView.loadUrl(AppBaseUrl.CHOUJIANG+"?userId="+ MyApplication.getInstance().getYonghuBean().getUserId());
        }
    }
    class MyWebChromeClient extends WebChromeClient {

        /**
         * alert弹框
         *
         * @return
         */
        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            Log.d("main", "onJsAlert:" + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new AlertDialog.Builder(ChoujiangActivity.this)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    webView.reload();//重写刷新页面

                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();

                }
            });
            result.confirm();//这里必须调用，否则页面会阻塞造成假死
            return true;
        }
       /* public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(ChoujiangActivity.this, message , Toast.LENGTH_LONG).show();
            result.confirm();
            return true;
        }*/
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isShowToolBar() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView!=null){
            webView.onResume() ;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView!=null){
            webView.onPause(); ;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null){
            webView.destroy(); ;
        }
    }
}
