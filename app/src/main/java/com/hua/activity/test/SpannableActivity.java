package com.hua.activity.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.R;
import com.hua.utils.LogUtils;


public class SpannableActivity extends Activity{

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spannable);
		textView = (TextView) findViewById(R.id.test_spannable);
		//创建一个 SpannableString对象      
//		SpannableString msp = new SpannableString("字体测试字体大小一半两倍\n前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合");     
		SpannableStringBuilder msp = new SpannableStringBuilder("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合");    
		      //设置字体(default,default-bold,monospace,serif,sans-serif)    
		      msp.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
		      msp.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
		          
		      //设置字体大小（绝对值,单位：像素）     
		      msp.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
		      msp.setSpan(new AbsoluteSizeSpan(20,true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。    
		          
		      //设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍    
		      msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //0.5f表示默认字体大小的一半    
		      msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //2.0f表示默认字体大小的两倍    
		          
		      //设置字体前景色    
		      msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色    
		          
		      //设置字体背景色    
		      msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置背景色为青色    
		       
		      //设置字体样式正常，粗体，斜体，粗斜体    
		      msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //正常    
		      msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗体    
		      msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体    
		      msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //粗斜体    
		          
		      //设置下划线    
		      msp.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
		          
		      //设置删除线    
		      msp.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);    
		          
		      //设置上下标    
		      msp.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //下标       
		      msp.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标                
		          
		      //超级链接（需要添加setMovementMethod方法附加响应）    
		      msp.setSpan(new URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //电话       
		      msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //邮件       
		      msp.setSpan(new MyUrlSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //网络       
		      msp.setSpan(new URLSpan("sms:4155551212"), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //短信   使用sms:或者smsto:    
		      msp.setSpan(new URLSpan("mms:4155551212"), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //彩信   使用mms:或者mmsto:    
		      msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);     //地图       
		          
		      //设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍    
		      msp.setSpan(new ScaleXSpan(2.0f), 49, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变    
		      msp.append("</a>");
		      msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), msp.length()-4, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		      msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), msp.length()-2, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		     
		      msp.append("\n\n啦啦啦换行");
		      
		      //设置项目符号    
		      msp.setSpan(new BulletSpan(BulletSpan.STANDARD_GAP_WIDTH,Color.GREEN), 0 ,53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //第一个参数表示项目符号占用的宽度，第二个参数为项目符号的颜色
		      
		      String eString = "";
		      eString = eString.trim();
		      LogUtils.d("eString-" + eString);
		      
		      textView.setText(msp);  
		      //记得家这句 ，不然链接无效
		      textView.setMovementMethod(LinkMovementMethod.getInstance()); 
		
	}
	
	
	class MyUrlSpan extends URLSpan{

		String mUrl = "";
		public MyUrlSpan(String url) {
			super(url);
			this.mUrl = url;
		}
		
		@Override
		public void onClick(View widget) {
			
			Toast.makeText(getApplication(), ""+mUrl, 300).show();
			
		}
		
	}
	
	
}
