package com.hua.activity.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.LogUtils;
import com.hua.utils.ToastUtil;
import com.hua.view.KitkatCompatWebview;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Version 1.0.1
 * @author duiba
 * 1、设置toolbar标题title为单行显示。
 * 2、设置tile最大宽度为200dp。
 * 3、修改toolbar高度为dip单位，20dip。
 * 4、修改返回图标为靠左居中，margin-left=10dp。
 * 5、添加dip2px()单位转换方法。
 */
/**
 * Version 1.0.2
 * @author duiba
 * 1、修复未登录用户登录后回到页面，后退到之前的页面时会刷新一次，去掉未登录状态。
 * 2、后退刷新方法修复。
 * 3、将栈内Activity对象改为CreditActivity。
 */
/**
 * Version 1.0.3
 * @author duiba fxt
 * 1、添加分享功能，支持分享的页面的导航栏会显示“分享”，需到兑吧管理后台配置并开启。
 * 2、添加各个功能的注释信息。
 * 3、分享接口和自动登录接口改为AlertDialog的展示形式。
 */
/**
 * Version 1.0.4
 * @author duiba fxt
 * 删除webview配置： settings.setLoadWithOverviewMode(true); 
 * 上面配置可能导致页面无法点击，页面适配等问题。
 */

/**
 * Version 1.0.5
 * @author duiba fxt
 * 在onConsume方法中加入刷新积分的js请求。如果页面含有onDBNewOpenBack()方法,则调用该js方法(刷新积分)
 * 根据api版本，4.4之后使用evaluateJavascript方法。
 */
@SuppressLint("NewApi")
@EActivity(R.layout.duiba)
public class CreditActivity extends Activity {
	private static String ua;
	private static Stack<CreditActivity> activityStack;
	public static final String MyVERSION ="1.0.5";
    public static CreditsListener creditsListener;

    public interface CreditsListener{
        /**
         * 当点击分享按钮被点击
         * @param shareUrl 分享的地址
         * @param shareThumbnail 分享的缩略图
         * @param shareTitle 分享的标题
         * @param shareSubtitle 分享的副标题
         */
        public void onShareClick(WebView webView, String shareUrl, String shareThumbnail, String shareTitle, String shareSubtitle);

        /**
         * 当点击登录
         * @param webView 用于登录成功后返回到当前的webview并刷新。
         * @param currentUrl 当前页面的url
         */
        public void onLoginClick(WebView webView, String currentUrl);
    }

    protected String shareUrl;			//分享的url
    protected String shareThumbnail;	//分享的缩略图
    protected String shareTitle;		//分享的标题
    protected String shareSubtitle;		//分享的副标题

    protected Boolean ifRefresh = false;
    protected Boolean delayRefresh = false;

    protected Long shareColor;
//    private ActivityManager activityManager;

    @ViewById
    KitkatCompatWebview duiba_wv;
    @ViewById
    ImageView back_img;
    @ViewById
    TextView duiba_title;
    @ViewById
    TextView login_tv;
    @ViewById
    ProgressBar progress_bar;
    @ViewById
    LinearLayout dialogbody;
    @Extra("firstIn")
    String firstIn;
    private boolean isFirst;

    @ViewById
    ViewStub vs_error;
    private View errorView;
//    private ErroeMessageUtil erroeMessageUtil;
    private boolean isSetWebSetting;

//    @Extra("urlPath")
    String urlPath ="http://www.duiba.com.cn/test/demoRedirectSAdfjosfdjdsa";
//    protected LinearLayout mLinearLayout;
//    protected RelativeLayout mNavigationBar;
//    protected TextView mTitle;
//    protected ImageView mBackView;
//    protected TextView mShare;

    private int RequestCode=100;

    @AfterInject
    void initVariable(){

//        if (activityStack == null) {
//            activityStack = new Stack<CreditActivity>();
//        }
//        activityStack.push(this);

    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init(){

        if (activityStack == null) {
            activityStack = new Stack<CreditActivity>();
        }
        activityStack.push(this);

//        代理
        if(ua==null){
            ua= duiba_wv.getSettings().getUserAgentString()+" Duiba/"+ MyVERSION;
//            ua=mWebView.getSettings().getUserAgentString()+" Duiba/"+VERSION;
        }


        duiba_wv.getSettings().setUserAgentString(ua);
            dialogbody.setVisibility(View.GONE);
        initWebView(null);

    }

    @Click
    void back_img(){
        onBackClick();
    }

    @Click
    void login_tv(){

    }

//    public void requestUrl(){
//            dialogbody.setVisibility(View.VISIBLE);
//            HashMap<String, String> params = new HashMap<String, String>();
//            params.put("uid", userInfoUtil.getUid());
//            params.put("hash",userInfoUtil.getHash());
//            String url = RequestHelper.buildCommonToken(UrlPath.DUI_BA_DELETE_THREAD, params);
//            MmRequest request = new MmRequest(url, new ResponseListener<String>(
//                   this) {
//                @Override
//                protected void onPtSucc(String url, String result) {
//                    datacallback(result);
//                    super.onPtSucc(url, result);
//                }
//
//                @Override
//                public void onNetworkComplete() {
//                    dialogbody.setVisibility(View.GONE);
////                    listview.refreshCompleted();
////                    listview.loadMoreCompleted();
//                    super.onNetworkComplete();
//                }
//
//                @Override
//                protected void onFail(int errorType, String errorDesc) {
//                    showErrorMessage(ErroeMessageUtil.ERROR_NO_NETWORK);
//                    // super.onFail(errorType, errorDesc);
//                }
//
//                @Override
//                protected void onPtError(String url, Result.ErrorMsg errorMsg) {
//                    if(errorMsg.getErrno() == -30){
////                        list.clear();
//                    }
////                    adapter.notifyDataSetChanged();
//                    showErrorMessage(ErroeMessageUtil.ERROR_NO_MASTER_DATA);
//                    //super.onPtError(urlPath, errorMsg);
//                }
//            });
//            addQueue(request);
//    }
//
//    public void  datacallback(String result){
//        if(result != null){
//            urlPath =  DataParser.getOneValueInData(result, "url");
//            if(isSetWebSetting) {
//                duiba_wv.loadUrl(urlPath);
//
//            }else {
//                initWebView(urlPath);
//            }
//        }
//
//    };

    //配置分享信息
    protected void setShareInfo(String shareUrl,String shareThumbnail,String shareTitle,String shareSubtitle){
    	this.shareUrl = shareUrl;
    	this.shareThumbnail = shareThumbnail;
    	this.shareSubtitle = shareSubtitle;
    	this.shareTitle = shareTitle;
    }
    
    protected void onBackClick(){
//        Intent intent=new Intent();
//        setResult(99, intent);
        finishActivity(this);
    }


    //初始化WebView配置
    protected void initWebView(String reposneUrl){

        WebSettings settings = duiba_wv.getSettings();

        // User settings
        settings.setJavaScriptEnabled(true);	//设置webview支持javascript
        settings.setLoadsImagesAutomatically(true);	//支持自动加载图片
        settings.setUseWideViewPort(true);	//设置webview推荐使用的窗口，使html界面自适应屏幕
        settings.setSaveFormData(true);	//设置webview保存表单数据
        settings.setSavePassword(true);	//设置webview保存密码
        settings.setDefaultZoom(ZoomDensity.MEDIUM);	//设置中等像素密度，medium=160dpi
        settings.setSupportZoom(true);	//支持缩放

        CookieManager.getInstance().setAcceptCookie(true);

        if (Build.VERSION.SDK_INT > 8) {
            settings.setPluginState(PluginState.ON_DEMAND);
        }

        // Technical settings
        settings.setSupportMultipleWindows(true);
        duiba_wv.setLongClickable(true);
        duiba_wv.setScrollbarFadingEnabled(true);
        duiba_wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        duiba_wv.setDrawingCacheEnabled(true);

        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);


        duiba_wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                CreditActivity.this.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progress_bar.setVisibility(View.VISIBLE);
                progress_bar.setProgress(newProgress);
                if (newProgress == 100) {
                    dialogbody.setVisibility(View.GONE);
                    progress_bar.setVisibility(View.GONE);
                }
            }

        });

        duiba_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrlByDuiba(view, url);
            }

            //页面加载结束时获取页面分享信息，如含分享信息，则导航栏上显示分享按钮
            @Override
            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:if(document.getElementById('duiba-share-url')){duiba_app.shareInfo(document.getElementById(\"duiba-share-url\").getAttribute(\"content\"));}");
//                view.loadUrl("javascript:setContactInfo('"+"ABC"+"')");
//                view.loadUrl("javascript:getShareInfo('"+ua+"')");
//                view.loadUrl("javascript:if(document.getElementById('duiba-share-url')){duiba_app.shareInfo(document.getElementById(\"duiba-share-url\").getAttribute(\"content\"));}");
                view.loadUrl("javascript:if(document.getElementById('duiba-share-url')){duiba_app.shareInfo(document.getElementById(\"duiba-share-url\").getAttribute(\"content\"));}");
                super.onPageFinished(view, url);
            }
        });

        //js调java代码接口。
        duiba_wv.addJavascriptInterface(new Object() {

            //用于回传分享url和title。
            @JavascriptInterface
            public void shareInfo(final String content) {
                if (content != null) {
                    String[] dd = content.split("\\|");
                    if (dd.length == 4) {
                        setShareInfo(dd[0], dd[1], dd[2], dd[3]);
//                        mShare.setVisibility(View.VISIBLE);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                login_tv.setVisibility(View.VISIBLE);
                                ToastUtil.showToast("123" + content);
                            }
                        });

                        LogUtils.d("MMM--");
                    }
                }
            }

            //用于跳转用户登录页面事件。
            @JavascriptInterface
            public void login() {
                if (creditsListener != null) {
                    duiba_wv.post(new Runnable() {
                        @Override
                        public void run() {
                            creditsListener.onLoginClick(duiba_wv, duiba_wv.getUrl());
                        }
                    });
                }
            }
        }, "duiba_app");

        isSetWebSetting = true;

//        if(firstIn != null && firstIn.equals("0") || reposneUrl != null){
//            duiba_wv.loadUrl(reposneUrl);
//        }else {
//            duiba_wv.loadUrl(urlPath);
//        }
        String url = "file:///android_asset/duibashare2.html";
//        duiba_wv.loadUrl(urlPath);
        duiba_wv.loadUrl(url);

    }

    protected void onReceivedTitle(WebView view,String title){
        duiba_title.setText(title);
    }

    /**
     * 拦截url请求，根据url结尾执行相应的动作。
     * 用途：模仿原生应用体验，管理页面历史栈。（重要）
     * @param view
     * @param url
     * @return
     */
    protected boolean shouldOverrideUrlByDuiba(WebView view,String url){
        if(this.urlPath.equals(url)){
            view.loadUrl(url);
            return true;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            return false;
        }
        if(url.contains("dbnewopen")){	//新开页面
            Intent intent = new Intent();
            intent.setClass(CreditActivity.this, CreditActivity.this.getClass());
            url = url.replace("dbnewopen", "none");
            intent.putExtra("urlPath", url);
            startActivityForResult(intent, RequestCode);
        }else if(url.contains("dbbackrefresh")){	//后退并刷新
            url = url.replace("dbbackrefresh", "none");
            Intent intent = new Intent();
            intent.putExtra("urlPath", url);
            setResult(RequestCode,intent);
            finishActivity(this);
        }else if (url.contains("dbbackrootrefresh")){	//回到积分商城首页并刷新
            url = url.replace("dbbackrootrefresh", "none");
            if(activityStack.size()==1){
            	finishActivity(this);
            }else{
            	activityStack.get(0).ifRefresh = true;
                finishUpActivity();
            }
        }else if (url.contains("dbbackroot")){	//回到积分商城首页
            url = url.replace("dbbackroot", "none");
            if(activityStack.size()==1){
            	finishActivity(this);
            }else{
                finishUpActivity();
            }
        }else if(url.contains("dbback")){	//后退
            url = url.replace("dbback", "none");
            finishActivity(this);
        }else{
        	if(url.endsWith(".apk") || url.contains(".apk?")){	//支持应用链接下载
                Uri uri = Uri.parse(url);
                Intent viewIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(viewIntent);
                return true;
            }
        	if(url.contains("autologin")&&activityStack.size()>0){	//未登录用户登录后返回，所有历史页面置为待刷新
        		//将所有已开Activity设置为onResume时刷新页面。
        		setAllActivityDelayRefresh();
        	}
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode==100){
            if(intent.getStringExtra("urlPath")!=null){
                this.urlPath =intent.getStringExtra("urlPath");
                duiba_wv.loadUrl(this.urlPath);
                ifRefresh = false;
            }
        }
    }

    @Override
	protected void onResume() {
		super.onResume();
		if (ifRefresh) {
//			this.urlPath = getIntent().getStringExtra("urlPath");
			duiba_wv.loadUrl(this.urlPath);
			ifRefresh = false;
		} else if (delayRefresh) {
			duiba_wv.reload();
			delayRefresh = false;
		} else {
	    	// 返回页面时，如果页面含有onDBNewOpenBack()方法,则调用该js方法。
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
				duiba_wv.evaluateJavascript("if(window.onDBNewOpenBack){onDBNewOpenBack()}", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.e("credits", "刷新积分ooooovalue="+value);
                    }
                });
			} else {
				duiba_wv.loadUrl("javascript:if(window.onDBNewOpenBack){onDBNewOpenBack()}");
			}
		}
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            onBackClick();
            finish();
            return  true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    
    //--------------------------------------------以下为工具方法----------------------------------------------

    /**
     * 结束除了最底部一个以外的所有Activity
     */
    public void finishUpActivity() {
        int size = activityStack.size();
        for (int i = 0;i < size-1; i++) {
            activityStack.pop().finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            if(activityStack.size() == 0){
                activityStack = null;
            }
        }
    }
    
    /**
     * 设置栈内所有Activity为返回待刷新。
     * 作用：未登录用户唤起登录后，将所有栈内的Activity设置为onResume时刷新页面。
     */
    public void setAllActivityDelayRefresh(){
    	int size = activityStack.size();
        for (int i = 0;i < size; i++) {
        	if(activityStack.get(i)!=this){
        		activityStack.get(i).delayRefresh = true;
        	}
        }
    }
    
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  

    /**
     * 查询手机内非系统应用
     * @param context
     * @return
     */
    public List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                apps.add(pak);
            }
        }
        return apps;
    }

    /**
     * 获取用户最近一次的地理位置，经纬度。
     * @param context
     * @return
     */
    public static String getLocation(Context context) {
        android.location.Location location = null;

        String provider = null;
        double latitude = 0;
        double longitude = 0;
        double accuracy = 0;

        String userLocation = null;

        LocationManager lManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        if (lManager == null) {
            Log.e("location","LocationManager is null");
            return null;
        }

        android.location.Location aLocation = null;

        // 开启了gps
        if (lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            aLocation = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (aLocation != null) {
                latitude = aLocation.getLatitude();
                longitude = aLocation.getLongitude();
                accuracy = aLocation.getAccuracy();
                userLocation = "location: latitude="+latitude+";longitude="+longitude+";accuracy="+accuracy;
                return userLocation;
            }
        }

        if (lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
            aLocation = lManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        // 如果net能取到位置，则返回
        if (aLocation != null) {
            latitude = aLocation.getLatitude();
            longitude = aLocation.getLongitude();
            accuracy = aLocation.getAccuracy();
            userLocation = "location: latitude="+latitude+";longitude="+longitude+";accuracy="+accuracy;
            return userLocation;
        }
        TelephonyManager telephonyManager=  (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 否则判断是否cmda定位
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
            if (cdmaCellLocation != null) {
                provider = "cdma";
                latitude = (double) cdmaCellLocation
                        .getBaseStationLatitude() / 14400;
                longitude = (double) cdmaCellLocation
                        .getBaseStationLongitude() / 14400;
                userLocation = "location: latitude="+latitude+";longitude="+longitude+";accuracy="+accuracy;
                return userLocation;
            }
        }

        return null;
    }


    private void showErrorMessage(int errorType) {
//        if (!StringUtil.isListNoNull(list)) {
//            if (vs_error != null && errorView == null) {
//                errorView = vs_error.inflate();
//            }
//            if (errorView != null) {
//                erroeMessageUtil.showErrorMessage(listView, dialogbody,
//                        errorView, errorType);
//            }
//        } else {
            if (errorView != null) {
                errorView.setVisibility(View.GONE);
//            }
        }
    }

}
