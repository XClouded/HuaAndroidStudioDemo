package com.hua.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hua.R;
import com.hua.activity.animator.CategoryListActivity;
import com.hua.activity.html.upload.HtmlUpLoadActivity;
import com.hua.activity.input.InputMethodActivity;
import com.hua.activity.js.HTMLActivityDemo;
import com.hua.activity.notification.NotificationMainActivity;
import com.hua.activity.qqlogin.TreePartLoginActivity;
import com.hua.activity.taiwanAd.BaseActivity;
import com.hua.activity.test.AT_Activity;
import com.hua.activity.test.ActivityWithExtra_;
import com.hua.activity.test.ChoiceInterest3_;
import com.hua.activity.test.ChoiceInterest_;
import com.hua.activity.test.CreamDetail_;
import com.hua.activity.test.CreditActivity_;
import com.hua.activity.test.HTMLActivity;
import com.hua.activity.test.ListPsoitionChange_;
import com.hua.activity.test.MultipleItemsList;
import com.hua.activity.test.PersonFragmentNew_;
import com.hua.activity.test.PostsActvity_;
import com.hua.activity.test.SpannableActivity;
import com.hua.activity.test.TestEmojicoTextView_;
import com.hua.activity.test.WebViewPlayer_;
import com.hua.activity.test.XuanFuActivity;
import com.hua.dynamicload.ProxyActivity;
import com.hua.utils.ActivityManager;
import com.hua.utils.PublicMethod;
import com.hua.utils.QuickReturnUtils;
import com.hua.utils.StringUtil;
import com.hua.view.RefleshListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/***
 * test合拼
 */
@EActivity(R.layout.activity_main2)
public class MainActivity extends BaseActivity {


    public static final String MY_STRING_EXTRA = "myStringExtra";
    public static final String MY_DATE_EXTRA = "myDateExtra";
    public static final String MY_INT_EXTRA = "myIntExtra";

    @ViewById
    RefleshListView listView;
    MAdapter adapter;

    @ViewById
    ImageView iv_top;

    @StringArrayRes(R.array.home_item)
    String[] home_item;

    @Extra(MY_INT_EXTRA)
    String classCastExceptionExtra = "啦啦啦啦啦";

    List<String> list = new ArrayList<>();


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @AfterViews
    void initViews() {

        list = Arrays.asList(home_item);
        adapter = new MAdapter(list);
        listView.setPreLoadMore(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {

                    case 0:
                        intent = new Intent(MainActivity.this,ChoiceInterest_.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(MainActivity.this,ActivityWithExtra_.class);
                        intent.putExtra(MY_STRING_EXTRA, "from AT_Activity");
                        intent.putExtra(MY_DATE_EXTRA, new Date());
                        startActivity(intent);
                        break;
                    case 2:
                        //测试scheme，这样可以打开另一个app
//                        intent = new Intent(NotificationMainActivity.this,HTMLSchemeActivity.class);
//                        startActivity(intent);
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("i2w2mmq://crystalexpress")));
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("i2w2mmq://crystalexpress?action=adpreview&adid=402")));
// mama://crystalexpress?action=adpreview&adid=402

                        break;
                    case 3:
                        intent = new Intent(MainActivity.this,WebViewPlayer_.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this,MultipleItemsList.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this,CreamDetail_.class);
                        startActivity(intent);
                        break;
                    case 6:
                        //兴趣
                        intent = new Intent(MainActivity.this,ChoiceInterest3_.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this,PostsActvity_.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this,CreditActivity_.class);
                        startActivity(intent);
                        break;
                    case 9:
                        intent = new Intent(MainActivity.this,HTMLActivity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        //精华
                        intent = new Intent(MainActivity.this,PersonFragmentNew_.class);
                        startActivity(intent);
                        break;
                    case 11:
                        intent = new Intent(MainActivity.this,SpannableActivity.class);
                        startActivity(intent);
                        break;
                    case 12:
                        intent = new Intent(MainActivity.this,AT_Activity.class);
                        startActivity(intent);
                        break;
                    case 13:
                        intent = new Intent(MainActivity.this,XuanFuActivity.class);
                        startActivity(intent);
                        break;
                    case 14:
                        intent = new Intent(MainActivity.this,TestEmojicoTextView_.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 15:
                        intent = new Intent(MainActivity.this,HTMLActivityDemo.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 16:
                        intent = new Intent(MainActivity.this,HTMLActivityDemo.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 17:
                        intent = new Intent(MainActivity.this, com.hua.activity.xuanfu.XuanFuActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 18:
                        intent = new Intent(MainActivity.this, com.hua.activity.pay.PayTestActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 19:
                        //台湾AD
                        intent = new Intent(MainActivity.this, com.hua.activity.taiwanAd.SingleDeferAdapterActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 20:
                        intent = new Intent(MainActivity.this, ListPsoitionChange_.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 21:
                        intent = new Intent(MainActivity.this, TreePartLoginActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 22:
                        intent = new Intent(MainActivity.this, CategoryListActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;
                    case 23:
                        /**图片上传*/
                        intent = new Intent(MainActivity.this, HtmlUpLoadActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);

                        break;
                    case 24:
                        /**启动未安装的apk*/
                        intent = new Intent(MainActivity.this, ProxyActivity.class);

                        intent.putExtra(ProxyActivity.EXTRA_DEX_PATH, "/mnt/sdcard/DynamicLoadHost/plugin.apk");
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;

                    case 25:
                        /**另类的通知显示*/
                        intent = new Intent(MainActivity.this, NotificationMainActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;


                    case 26:
                        /**另类的通知显示*/
                        intent = new Intent(MainActivity.this, InputMethodActivity.class);
                        ActivityManager.getManager().goTo(MainActivity.this, intent);
                        break;

                }

            }
        });

        listView.setOnRefreshListener(new RefleshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        list.add("添加新一行-----");
                        listView.refreshCompleted();
                    }
                }, 500);

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    Log.d("Disance","SCROLL_STATE_TOUCH_SCROLL == "+SCROLL_STATE_TOUCH_SCROLL);
                    QuickReturnUtils.getInstance(MainActivity.this).computeStartPoint(view);
                }

                    if(scrollState == SCROLL_STATE_IDLE) Glide.with(MainActivity.this).resumeRequests();

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                Glide.with(MainActivity.this).pauseRequests();

                if(QuickReturnUtils.getInstance(MainActivity.this).isShowbackIco(view,firstVisibleItem)){

                    PublicMethod.showBackView(MainActivity.this,iv_top);
                }else{
                    PublicMethod.hideBackView(MainActivity.this,iv_top);
                }

            }
        });


    }

    class MAdapter extends BaseAdapter {

        List<String> listData;

        public MAdapter(List l) {
            this.listData = l;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView = new TextView(getApplication());
            textView.setTextSize(24);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setPadding(20, 20, 20, 20);
            textView.setGravity(Gravity.CENTER);
            if (position % 4 == 1) {
                textView.setBackgroundColor(Color.parseColor("#59B791"));
            } else if (position % 4 == 2) {
                textView.setBackgroundColor(Color.parseColor("#83F084"));
            } else if (position % 4 == 3) {
                textView.setBackgroundColor(Color.parseColor("#3498DB"));
            } else {
                textView.setBackgroundColor(Color.parseColor("#F2676A"));
            }

            if(position == 0){
                textView.setText(Html.fromHtml("我是<font size=\"60\" color=\"#fb798f\">妈妈圈</font>"),TextView.BufferType.SPANNABLE);
            }else if(position == 1){
//                String text = "妈M国草哦擦啊[color]妈妈[/color]M9M[color]h啦啦啦啦a[/color]M妈妈啊苏打撒撒旦妈妈咪";
//                StringUtil.setSpecifiedTextsColor(textView,text,0xfffb798f,"[color]","\\[/color\\]");
//                String text = "妈M国草哦擦啊<font>妈妈</font>M9M<font>h啦啦啦啦a</font>M妈妈啊苏打撒撒旦妈妈咪";
                String text = "妈M国草哦擦啊<font>妈妈</font>M妈妈啊苏打50%撒撒旦妈妈咪";
                String start = "<font>";
                String end = "</font>";
                StringUtil.setSpecifiedTextsColor(textView,text,0xfffb798f,start,end);
//                StringUtil.setTextsColorAndSize(textView,text,getResources().getColor(R.color.gray12),start,end,1.5f);

                StringUtil.setTextsColorByNum(textView,text,"50%",getResources().getColor(R.color.gray12),1.5f);
            }else{
                textView.setText(listData.get(position));
            }

            return textView;
        }
    }


}
