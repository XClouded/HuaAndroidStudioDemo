package com.hua.activity.taiwanAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.desmond.parallaxviewpager.LogUtil;
import com.hua.R;
import com.intowow.sdk.DeferStreamAdapter;

import java.util.List;

public class ExtendDeferStreamAdapter extends DeferStreamAdapter {//XXX#Stream-DeferStreamAdapter#

	List<Object> mList;
	Activity mContext;
	LayoutInflater mInflater;
	int[] mPics = null;
	SparseArray<LinearLayout> adViewList = new SparseArray<LinearLayout>();
	int mStreamListItemPaddingTopButtom = 0;
	int mStreamListItemPaddingLeftRight = 0;
	int mStreamListItemHeight = 0;
	
	public ExtendDeferStreamAdapter(Activity c, 
			String placement, 
			List<Object> list){//XXX#Stream-placement#
		
		//	set the placement and the DeferStreamAdapter
		//	will auto create the StreamHelper
		//
		super(c, placement);
		
		//***********************************************
		//	common UI
		//
		mContext = c;
		mInflater = LayoutInflater.from(c);
		mList = list;
		
		mPics = new int[5];
		mPics[0]= R.drawable.business_1;
		mPics[1]=R.drawable.business_2;
		mPics[2]=R.drawable.business_3;
		mPics[3]=R.drawable.business_4;
		mPics[4]=R.drawable.business_5;
		
		LayoutManager lm = LayoutManager.getInstance(mContext);
		
		mStreamListItemPaddingLeftRight = 
				lm.getMetric(LayoutManager.LayoutID.STREAM_LIST_ITEM_PADDING_LEFT_RIGHT);
		mStreamListItemPaddingTopButtom = 
				lm.getMetric(LayoutManager.LayoutID.STREAM_LIST_ITEM_PADDING_TOP_BUTTOM);
		mStreamListItemHeight = 
				lm.getMetric(LayoutManager.LayoutID.STREAM_LIST_ITEM_HEIGHT);
	}
	
	public void setList(List<Object> list) {
		mList = list;
	}

	//TODO
/***********************************************************/
/*	
	//XXX@Stream-getItemViewType@#Stream-getItemViewType#
	@Override
	public int getItemViewType(int position) {
		//	if you have implemented getItemViewType(), 
		//	be sure to check if the item is an ad 
		//	in this position.
		if(isAd(position)) {
			return super.getItemViewType(position);
		}else{
			//	return your view type here
			//
			//
		}
	}
	//end
*/
/***********************************************************/

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int typeCount = getViewTypeCount();
		LogUtil.d("ListView","ListView  typeCount == "+typeCount);
		int type = getItemViewType(position);
		LogUtil.d("ListView","ListView type == "+type);

		//XXX@Stream-getView@#Stream-getView#
		// Get ad view if possible
		final View adView =  getAD(position);	
		
		//	or you can resize the ad width by this way
		//	final View adView =  getAD(position, someIntWidth);
		//
		
		//	or remove the background
		//	final View adView =  getAD(position, false);
		//
		
		if(adView != null) {
			//	you can set the background
			//	such as
			//	adView.setBackgroundColor(Color.BLACK);
			//	adView.setBackgroundResource(your resid);
			//	adView.setBackground(your background drawable);
			//	adView.setBackgroundDrawable(your background drawable);
			LogUtil.d("Test",adView.getHeight()+"");
			return adView;
		}
		//end

		if(position== 4){
			View view = new TextView(mContext);
			view.setLayoutParams(new AbsListView.LayoutParams(1,1));
//			textView.setText("123456");
			view.setBackgroundColor(Color.parseColor("#CCC000"));
			return  view;
		}

		Holder holder = null;
		View view = convertView;
        if (view == null || view.getTag() == null) { 
        	holder = new Holder();
        	view = holder.inflate(mInflater);
        	view.setTag(holder);
        }
        else{
        	holder = (Holder) view.getTag();
        }
        view.setTag(holder);
        
        holder.setData(position);        
		return view;
	}
	
	@SuppressLint("UseSparseArrays") 
	SparseArray<Integer> mResizeHistory = new SparseArray<Integer>();

	class Holder {				
		ImageView testA ;
		RelativeLayout relativeLayout;
		boolean hasSetHeight = false;
		
		public void setData(int position) {	
			
			int index =  (position%mPics.length) -1;
			if(index<0){
				index = mPics.length-1;
			}
			
			testA.setBackgroundResource(mPics[index]);
			
			if(!hasSetHeight){
				testA.getLayoutParams().height  = mStreamListItemHeight;
				hasSetHeight = true;
			}
		}
		
		@SuppressLint("InflateParams")
		public View inflate(LayoutInflater inflater){
			relativeLayout = (RelativeLayout)inflater.inflate(R.layout.adapter_instream, null);
			testA = (ImageView)relativeLayout.findViewById(R.id.testA);
			testA.setScaleType(ScaleType.MATRIX);
			relativeLayout.setPadding(
					mStreamListItemPaddingLeftRight, mStreamListItemPaddingTopButtom,
					mStreamListItemPaddingLeftRight, mStreamListItemPaddingTopButtom);
			return relativeLayout;
		}
	}
	
	@Override
	public int getCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mList != null ? mList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * to tell the SDK which position you want to add
	 * */
	//XXX@Stream-onADLoaded@#Stream-onADLoaded#
	@Override
	public int onADLoaded(int position) {
		//
		// 	when one stream ad has been loaded,
		//	the SDK will need to know which position 
		//	can show this ad in your DataSet.
		//	so the SDK will call onADLoaded(position) for getting 
		//	one position that you have already allocate in your DataSet.
		//	then, if you call getAD(position) in the getView() later,
		//	the SDK will return one ad or null refer to onADLoaded's 
		//	return value.
		//
		//	if you return "-1", it means that the ad is not added in your 
		//	DataSet.
		
		//	for example:
		//	if you return "5" from the onADLoaded(position) first,
		//	and you request a ad by calling the getAD(5) in the getView() 
		//	later,
		//	the SDK will know that this position(5) can response a ad for 
		//	the App 
		//	
		
		position = 2;//getDefaultMinPosition(position);
		
		if (mList != null && mList.size() >  position) {	
			
			// just allocate one position for stream ad
			//
			mList.add(position, null);
			
			//	be sure to call the notifyDataSetChanged()
			//
			notifyDataSetChanged();
			
			return position;
		}
		else {				
			return -1;
		}
	}
	//end
	
	//XXX@Stream-notifyDataSetChanged@#Stream-notifyDataSetChanged#
	@Override
	public void notifyDataSetChanged() {
		//	if your DataSet has been changed
		//	the SDK will need to re-allocate the ad 
		//	which you have added in the DataSet before
		//
		for (Integer pos : getAddedPosition()) {
			if(mList == null || pos > mList.size()) {
				return;
			}
			
			//	check ad case
			//
			if(mList.get(pos) == null || mList.get(pos).equals("null")) {
				continue;
			}
			
			mList.add(pos , null);
		}
		super.notifyDataSetChanged();
	}
	//end
	
	//XXX@Stream-getDefaultMinPosition@#Stream-getDefaultMinPosition#
	@Override
	public int getDefaultMinPosition(int position) {
		// Don't place ad at the first place
		return Math.max(1, position);
	}
	//end

}
