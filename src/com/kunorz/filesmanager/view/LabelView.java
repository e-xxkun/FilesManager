package com.kunorz.filesmanager.view;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.View.*;
import android.widget.RadioGroup.*;
import android.widget.*;
import android.text.*;

public class LabelView extends View
{
	private int flag=0;
	private float width;
	private int height;
	private int height_rect;
	
	private String str1;
	private String str2;
	
	private int t1size;
	private int t2size;

	private boolean ischecked=false;

	private int color_rect=0xff85BEE9;
	private int color_rect_checked=0xff61a1d7;
	private int color_rect_uncheck=0xff85BEE9;
	private int color_t2=0x88ffffff;
	private int color_t1=0xffffffff;
	
	private LabelView.OnCheckedChangeListener oncheckedchangelistener;

	private LabelView.OnClickListener onclicklistener;
	
	public LabelView(Context context){
		super(context);
		init();
	}
	
	public LabelView(Context context,int flag){
		super(context);
		this.flag=flag;
		init();
	}
	
	public LabelView(Context context,AttributeSet attr){
		super(context,attr);
		init();
	}

	private void init()
	{
		t1size=sp2px(14);
		t2size=sp2px(10);
		height_rect=dip2px(6);
		if(!isClickable())setClickable(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width=MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint paint=new Paint();
		paint.setColor(color_rect);
		paint.setStrokeWidth(2);
		canvas.drawRect(0,0,width,height-height_rect,paint);
		if(ischecked)paint.setColor(color_rect_checked);
		else paint.setColor(color_rect_uncheck);
		canvas.drawRect(0,height-height_rect,width,height,paint);
		if(str1!=null){
			TextPaint tpaint=new TextPaint();
			tpaint.setAntiAlias(true);
			tpaint.setColor(color_t1);
			tpaint.setTextAlign(Paint.Align.CENTER);
			tpaint.setStrokeWidth(2);
			tpaint.setStyle(Paint.Style.FILL);
			tpaint.setTextSize(t1size);
			int h;
			if(str2==null)h=(height-height_rect+t1size)/2;
			else h=(3*(height-height_rect-t2size)+5*t1size)/8;
			String str=(String)TextUtils.ellipsize(str1,tpaint,3*width/4,TextUtils.TruncateAt.END);
			canvas.drawText(str,width/2,h,tpaint);
		}
		if(str2!=null){
			TextPaint tpaint=new TextPaint();
			tpaint.setAntiAlias(true);
			tpaint.setColor(color_t2);
			tpaint.setTextAlign(Paint.Align.CENTER);
			tpaint.setStrokeWidth(2);
			tpaint.setStyle(Paint.Style.FILL);
			tpaint.setTextSize(t2size);
			int h;
			if(str1==null)h=(height-height_rect+t2size)/2;
			else h=(5*(height-height_rect)+3*(t1size+t2size))/8;
			String str=(String)TextUtils.ellipsize(str2,tpaint,3*width/4,TextUtils.TruncateAt.START);
			canvas.drawText(str,width/2,h,tpaint);
		}
		
	}
	
	@Override
	public boolean performClick()
	{
		if(onclicklistener!=null){
			onclicklistener.OnClick(this,ischecked,flag);
		}
		if(!ischecked){
			ischecked=true;
			invalidate();
		}
		if(oncheckedchangelistener!=null){
			oncheckedchangelistener.OnCheckedChange(this,flag);
		}
		return super.performClick();
	}
	
	private int dip2px(float dpValue) {
		return (int) (dpValue * getResources().getDisplayMetrics().density + 0.5f);
	}
	
	private int sp2px(float spValue) {
        return (int) (spValue * getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }
	
	public void setText(String str1,String str2){
		this.str1=str1;
		this.str2=str2;
		invalidate();
	}
	
	public void setTextSize(int sp1,int sp2){
		this.t1size=sp2px(sp1);
		this.t2size=sp2px(sp2);
		invalidate();
	}
	
	public void setTextColor(int color1,int color2){
		this.color_t1=color1;
		this.color_t2=color2;
		invalidate();
	}
	
	public void setRectColor(int checkedcolor,int uncheckcolor){
		this.color_rect_checked=checkedcolor;
		this.color_rect_uncheck=uncheckcolor;
		invalidate();
	}
	
	public void setRectHeight(int height){
		this.height_rect=dip2px(height);
		invalidate();
	}
	
	public void checked(boolean b){
		this.ischecked=b;
		invalidate();
	}
	
	public void setCheck(boolean ischecked){
		if(oncheckedchangelistener!=null&&ischecked){
			oncheckedchangelistener.OnCheckedChange(this,flag);
		}
		this.ischecked=ischecked;
		invalidate();
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setFlag(int flag){
		this.flag=flag;
	}
	
	public boolean isChecked(){
		return ischecked;
	}
	
	public void setOnCheckedChangeListener(OnCheckedChangeListener oncheckedchangelistener){
		this.oncheckedchangelistener=oncheckedchangelistener;
	}
	
	public interface OnCheckedChangeListener{
		
		public void OnCheckedChange(LabelView label,int flag);
	}
	
	public void setOnClickListener(OnClickListener onclicklistener){
		this.onclicklistener=onclicklistener;
	}

	public interface OnClickListener{

		public void OnClick(LabelView label,boolean ischecked,int flag);
	}

}
