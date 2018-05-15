package com.example.jinkejinfuapplication.shouye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.jinkejinfuapplication.R;
import com.example.jinkejinfuapplication.base.BaseActivity;

public class Zhibo_xiangqActivity extends BaseActivity {
    private WebView mWebView;
    private String url;
    private ProgressBar mPbar;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zhibo_xiangq);
        setTitle("直播详情");

        url=this.getIntent().getStringExtra("url");
        mPbar = (ProgressBar) findViewById(R.id.progressBar1);
        mWebView= (WebView) findViewById(R.id.webview);

        WebChromeClient client = new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                mPbar.setProgress(newProgress);
                if(newProgress==100)
                {
                    mPbar.setVisibility(View.GONE);
                }
            }
        };
        mWebView.setWebChromeClient(client);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置要加载的网页
        mWebView.loadUrl(url);
        mWebView.getSettings().setDomStorageEnabled(true);
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


}
