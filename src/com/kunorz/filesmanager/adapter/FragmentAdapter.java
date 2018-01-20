package com.kunorz.filesmanager.adapter;

import android.support.v4.app.*;
import java.util.*;
import com.kunorz.filesmanager.util.*;
import com.kunorz.filesmanager.fragment.*;
import android.content.*;
import java.io.*;
import android.support.v4.view.*;
import android.view.*;
import android.view.View.*;
import com.kunorz.filesmanager.view.*;

public class FragmentAdapter extends FragmentPagerAdapter implements FileFragment.OnItemClickListener,LabelSrcollView.OnClickListener
{
	private LabelSrcollView label;
	private List<FileFragment> list;
	private Context context;
	private FragmentManager fm;
	private int flag=0;
	
	public FragmentAdapter(Context context,FragmentManager fm){
		super(fm);
		this.fm=fm;
		this.context=context;
		list=new ArrayList<FileFragment>();
	}
	
	@Override
	public Fragment getItem(int p1)
	{
		return list.get(p1);
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	public void setLabel(LabelSrcollView label){
		this.label=label;
		label.setOnClickListener(this);
	}
	
	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	@Override
	public long getItemId(int position)
	{
		return list.get(position).getFlag();
	}
	
	@Override
	public void addItem(File file){
		addItem(file,label.getCheckedPosition());
	}
	
	public void addItem(File file,int position){
		label.addLabel(file.getName(),file.getParent());
		FileFragment fragment=new FileFragment(context,file,flag);
		fragment.setOnItemClickListener(this);
		if(position<=0)list.add(fragment);
		else list.add(position+1,fragment);
		flag++;
		notifyDataSetChanged();
	}
	
	@Override
	public void OnClick(LabelSrcollView labelsrcoll, boolean again, int position)
	{
		if(again)remove(position);
	}
	
	public void remove(int position){
		label.remove(position);
		FragmentTransaction bt=fm.beginTransaction();
		bt.remove(list.get(position));
		bt.commit();
		list.remove(position);
		notifyDataSetChanged();
	}

	public boolean back(int position){
		File file=list.get(position).back();
		if(file==null)return false;
		update(file);
		return true;
	}
	
	@Override
	public void update(File file)
	{
		if(file.getParentFile()==null)label.setLabelTitle("根目录",null);
		else label.setLabelTitle(file.getName(),file.getParent());
		notifyDataSetChanged();
	}
	
	
}
