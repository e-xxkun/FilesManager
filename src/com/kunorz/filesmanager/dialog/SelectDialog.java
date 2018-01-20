package com.kunorz.filesmanager.dialog;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import com.kunorz.filesmanager.*;
import android.widget.*;
import android.content.*;
import android.content.pm.*;
import java.util.*;
import android.util.*;
import android.support.v4.view.*;
import com.kunorz.filesmanager.util.*;
import android.widget.LinearLayout.*;

public class SelectDialog extends DialogFragment
{

	private View layout;

	private RadioGroup radiogroup;
	private Context context;
	private ViewPager viewpager;
	private List<ResolveInfo> resinfo;

	private FileData filedata;

	private Intent intent;

	private PackageManager pm;
	
	public SelectDialog(FileData filedata){
		this.filedata=filedata;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context=getContext();
		intent = new Intent(android.content.Intent.ACTION_VIEW);
		String mime;
		Log.d("llll","ok0");
		if(filedata.getExtension()==null)mime="*/*";
		else mime=StaticData.MIME.get(filedata.getExtension());
		Log.d("llll","ok1");
		if(mime==null)mime="*/*";
		intent.setType(mime);
		Log.d("llll","ok2"+mime);
		pm=getActivity().getPackageManager();
		resinfo =pm.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
		Log.d("llll","ok3"+resinfo.size());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		layout=inflater.inflate(R.layout.dialog_select,null);
		TextView title=(TextView)layout.findViewById(R.id.dialog_title);
		title.setText("打开方式: "+filedata.getName());
		radiogroup=(RadioGroup)layout.findViewById(R.id.dialog_radiogroup);
		radiogroup.check(R.id.dialog_r1);
		Log.d("llll","ok4");
		viewpager=(ViewPager)layout.findViewById(R.id.dialog_pager);
		viewpager.setAdapter(new DialogPagerAdapter());
		Log.d("llll","ok5");
		return layout;
	}
	
	public class DialogPagerAdapter extends PagerAdapter
	{
		
		private ListView[] listviews=new ListView[3];

		private View listlayout;

		@Override
		public int getCount()
		{
			return 3;
		}

		@Override
		public boolean isViewFromObject(View p1, Object p2)
		{
			return p1==p2;
		}

		@Override
		public Object instantiateItem(View container, int position)
		{
			ListView listview=listviews[position];
			if(listview==null){
				listlayout=LayoutInflater.from(context).inflate(R.layout.dialog_list,null); //new ListView(context);
				
		//		listview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				listviews[position]=listview=(ListView)layout.findViewById(R.id.dialog_list);
				if(position==0)listview.setAdapter(new DialogListAdapter(resinfo,0));
				Log.d("llll","okp1"+listview.getHeight());
			}
			return listlayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
		}
	}
	
	public class DialogListAdapter extends BaseAdapter
	{

		private List<ResolveInfo> resinfo;
		private int type;
		
		public DialogListAdapter(List<ResolveInfo> resinfo,int type){
			this.resinfo=resinfo;
			this.type=type;
			Log.d("llll","uu"+resinfo.size());
		}

		@Override
		public int getCount()
		{
			Log.d("llll",type+"type");
			if(type==0)return resinfo.size();
			else return 0;
		}

		@Override
		public Object getItem(int p1)
		{
			Log.d("llll",type+"type");
			if(type==0)return resinfo.get(p1);
			else return null;
		}

		@Override
		public long getItemId(int p1)
		{
			return p1;
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			String t;
			Log.d("llll","okk0");
			if(type==0)t=resinfo.get(p1).loadLabel(pm).toString();
			else t="oob";
			if(p2==null){
				TextView tv=new TextView(context);
				tv.setText(t);
				return tv;
			}
			Log.d("llll","okk1");
			return p2;
		}
	}
	
	class ViewHolder{
		ImageView img;
		TextView text;
	}
}
