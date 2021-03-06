package com.hua.activity.test;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hua.R;
import com.hua.utils.AnimationUtil;
import com.hua.utils.LogUtils;
import com.hua.utils.StringUtil;
import com.hua.utils.secret.DES;
import com.hua.utils.secret.DESSecret;
import com.hua.view.OnWheelChangedListener;
import com.hua.view.OnWheelScrollListener;
import com.hua.view.WheelView;
import com.hua.view.wheelview_adapter.NumericWheelAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_with_extra)
public class ActivityWithExtra extends com.hua.activity.taiwanAd.BaseActivity {

    public static final String MY_STRING_EXTRA = "myStringExtra";
    public static final String MY_DATE_EXTRA = "myDateExtra";
    public static final String MY_INT_EXTRA = "myIntExtra";

    @ViewById
    TextView extraTextView;

    @ViewById
    WheelView passw_1;

    @ViewById
    WheelView passw_2;

    @ViewById
    TextView share_tv1;

    @ViewById
    TextView text_color;

    @ViewById
    TextView title;

    @ViewById
    EditText edit;

    @Extra(MY_STRING_EXTRA)
    String myMessage;

    @Extra(MY_DATE_EXTRA)
    Date myDate;

    @Extra("unboundExtra")
    String unboundExtra = "如果这边赋值，则以这边为准";

    /**
     * The logs will output a classcast exception, but the program flow won't be interrupted
     */
    @Extra(MY_INT_EXTRA)
    String classCastExceptionExtra /*= "classCastExceptionExtraDefaultValue"*/;


    int wifiString = ConnectivityManager.TYPE_WIFI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        LogUtils.e("the MY_STRING_EXTRA==" + intent.getStringExtra(MY_STRING_EXTRA));

    }

    @AfterViews
    protected void init() {
        extraTextView.setText("妈妈求助啊啦啦啦啦fasf啦啦啦啦德玛西亚a啦啦啦啦德玛西亚a啦啦啦啦德玛西亚a"+myMessage + " " + myDate + " " + unboundExtra + " " + classCastExceptionExtra);
        extraTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        /**android:lineSpacingExtra="3dp"   这个属性会影响显示上下位置*/
        Spannable spannable = new SpannableStringBuilder("1"+extraTextView.getText());
        Drawable drawable = getResources().getDrawable(R.drawable.vote_tip);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannable.setSpan(new ImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        extraTextView.setText(spannable);

        title.setText(spannable);

        /***/
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
//                    String editable = edit.getText().toString();
//                    String str = StringUtil.stringFilter(editable.toString());
//                    if(!editable.equals(str)){
//                        editable = editable.substring(0,editable.length() - 1);
//                        edit.setText(editable);
//                        //设置新的光标所在位置
//                        edit.setSelection(editable.length());
//                        Toast.makeText(ActivityWithExtra.this,"不可以输入特殊字符",Toast.LENGTH_LONG).show();
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initWheel();
        share_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                passw_2.scroll(25, 2000);
//                passw_1.scroll(-20, 2000);
                passw_1.setCurrentItem(8,true);

                DESSecret.testSecret();

                DES.test();

                StringUtil.setData("啦啦啦mm");

                String ed = edit.getText().toString();
                if(ed != null){
                    StringUtil.setKeysColor(text_color,text_color.getText().toString(),ed,getResources().getColor(R.color.red));
                }

                List<Long> longList = new ArrayList<Long>();
                longList.add(500l);
                longList.add(5550l);
                longList.add(8850l);
                longList.toString();
                longList.toString().replace("[","").replace("]","");


            }
        });

    }

    private PopupWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.sign_pw, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView light = (ImageView) contentView.findViewById(R.id.light);
        ImageView gold = (ImageView) contentView.findViewById(R.id.gold);
        TextView score = (TextView) contentView.findViewById(R.id.score);
        TextView prcent = (TextView) contentView.findViewById(R.id.test);
        ImageView banner_desc = (ImageView) contentView.findViewById(R.id.banner_desc);
        TextView add_oil_desc = (TextView) contentView.findViewById(R.id.add_oil_desc);



        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(extraTextView, Gravity.CENTER_VERTICAL, 0, 0);
        AnimationUtil.signAnima(this, light, gold, score, prcent);

//        AnimationUtil.signAnima(this, banner_desc,light, gold, score, prcent,add_oil_desc);

    }

    /**
     * Initializes wheel
     */
    private void initWheel() {
        passw_1.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
        passw_1.setCurrentItem((int)(Math.random() * 10));
        passw_1.addChangingListener(changedListener);
        passw_1.addScrollingListener(scrolledListener);
        passw_1.setCyclic(true);
        passw_1.setInterpolator(new AnticipateOvershootInterpolator());

        passw_2.setViewAdapter(new NumericWheelAdapter(this, 0, 9));
//        passw_2.setCurrentItem((int)(Math.random() * 10));
        passw_2.setCurrentItem(3);

        passw_2.addChangingListener(changedListener);
        passw_2.addScrollingListener(scrolledListener);
        passw_2.setCyclic(true);
        passw_2.setInterpolator(new AnticipateOvershootInterpolator());

    }

    // Wheel scrolled flag
    private boolean wheelScrolled = false;

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }
    };

    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                updateStatus();
            }
        }
    };

    /**
     * Updates entered PIN status
     */
    private void updateStatus() {
            extraTextView.setText("Congratulation!");
    }

}
