package com.kunorz.filesmanager.view;
import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.view.View.*;
import android.graphics.drawable.*;
import android.graphics.*;
import java.util.*;
import android.support.v4.view.*;

public class LabelSrcollView extends HorizontalScrollView implements LabelView.OnCheckedChangeListener,LabelView.OnClickListener
{
	private LinearLayout.LayoutParams param;
	
	private Context context;
	
	private List<LabelView> labels;
	private LinearLayout linearlayout;

	private int width_label=80;
	private int width;
	private int height;
	private int position=-1;

	private ViewPager viewpager;

	private OnClickListener onclicklistener;

	public LabelSrcollView(Context context,AttributeSet attr){
		super(context,attr);
		this.context=context;
		init();
	}

	private void init()
	{
		setHorizontalScrollBarEnabled(false);
		setOverScrollMode(OVER_SCROLL_NEVER);
		param=new LinearLayout.LayoutParams(dip2px(width_label),LayoutParams.MATCH_PARENT,1.0f);
		linearlayout=new LinearLayout(context);
		linearlayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		linearlayout.setHorizontalGravity(LinearLayout.HORIZONTAL);
		addView(linearlayout);
		labels=new ArrayList<LabelView>();
		setFillViewport(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width=MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
	}
	
	public void addLabel(String str1,String str2){
		addLabel(str1,str2,position+1);
	}
	
	public void addLabel(String str1,String str2,int position){
		final LabelView label=new LabelView(context,position);
		label.setText(str1,str2);
		label.setOnCheckedChangeListener(this);
		label.setOnClickListener(this);
		labels.add(position,label);
		for(int i=position+1;i<labels.size();i++)labels.get(i).setFlag(i);
		linearlayout.addView(label,position,param);
		post(new Runnable(){

				@Override
				public void run()
				{
					label.setCheck(true);
				}
				
		});
	}
	
	public void addLabel(LabelView label){
		label.setOnCheckedChangeListener(this);
		labels.add(label);
		linearlayout.addView(label,param);
	}
	
	public void setLabelTitle(String str1,String str2){
		labels.get(position).setText(str1,str2);
	}
	
	public int getLabelCount(){
		return labels.size();
	}
	
	public int getCheckedPosition(){
		return position;
	}
	
	public void setCheck(int position){
		labels.get(position).setCheck(true);
	}
	
	public void remove(int position){
		linearlayout.removeViewAt(position);
		labels.remove(position);
		int len=labels.size();
		for(int i=position-1;i<labels.size();i++)labels.get(i).setFlag(i);
		if(position<len){
			labels.get(position).checked(true);
			this.position=position;
		}else{
			labels.get(len-1).checked(true);
			this.position=len-1;
		}
	}
	
	public void setViewPager(ViewPager viewpager){
		this.viewpager=viewpager;
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int p1, float p2, int p3)
				{
					// TODO: Implement this method
				}

				@Override
				public void onPageSelected(int p1)
				{
					setCheck(p1);
				}

				@Override
				public void onPageScrollStateChanged(int p1)
				{
					// TODO: Implement this method
				}
				
		});
	}
	
	@Override
	public void OnClick(LabelView label, boolean ischecked, int flag)
	{
		onclicklistener.OnClick(this,ischecked,flag);
	}
	
	@Override
	public void OnCheckedChange(LabelView lv, int flag)
	{
		if(flag>=labels.size())return;
		if(flag!=position){
			if(position>=0)labels.get(position).checked(false);
			if(viewpager!=null){
				if(flag<position-1)viewpager.setCurrentItem(flag+1,false);
				else if(flag>position+1)viewpager.setCurrentItem(flag-1,false);
				viewpager.setCurrentItem(flag,true);
			}
			position=flag;
		}
		if(labels.size()>4){
			smoothScrollTo((int)lv.getX()-width/2+lv.getWidth()/2,0);
		}
	}
	
	public void setLabelMiniWidth(int dp){
		this.width_label=dp;
		param=new LinearLayout.LayoutParams(dip2px(width_label),LayoutParams.MATCH_PARENT,1.0f);
		for(int i=0;i<labels.size();i++)labels.get(i).setLayoutParams(param);
	}
	
	private int dip2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public void setOnClickListener(OnClickListener onclicklistener){
		this.onclicklistener=onclicklistener;
	}

	public interface OnClickListener{

		public void OnClick(LabelSrcollView labelsrcoll,boolean again,int position);
	}
}
