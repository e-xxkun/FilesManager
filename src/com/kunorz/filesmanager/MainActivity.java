package com.kunorz.filesmanager;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.content.*;
import android.view.*;
import android.util.*;
import android.support.v4.view.ViewPager;
import android.support.v4.app.*;
import com.kunorz.filesmanager.view.*;
import com.kunorz.filesmanager.adapter.*;
import com.kunorz.filesmanager.util.*;
import android.view.View.*;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private Button back;
	private ViewPager viewpager;
	private LabelSrcollView lsv;
	int i=0;

	private FragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
		lsv=(LabelSrcollView)findViewById(R.id.labelsc);
		viewpager=(ViewPager)findViewById(R.id.pager);
		adapter=new FragmentAdapter(this,getSupportFragmentManager());
		lsv.setViewPager(viewpager);
		adapter.setLabel(lsv);
		viewpager.setAdapter(adapter);
		
		adapter.addItem(new File("/sdcard"));
		StaticData.FileComparator.setSortType(FileComparator.SORT_BY_NAME);
		lsv.post(new Runnable(){

				@Override
				public void run()
				{
				//	lsv.setCheck(6);
				}
			
		});
	}
	
	@Override
	public void onClick(View p1)
	{
		
	}

	@Override
	public void onBackPressed()
	{
		if(adapter.back(viewpager.getCurrentItem()));
		else super.onBackPressed();
	}
	
}
