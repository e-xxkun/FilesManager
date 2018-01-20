package com.kunorz.filesmanager.fragment;
import android.support.v4.app.*;
import com.kunorz.filesmanager.*;
import android.view.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import java.io.*;
import java.util.*;
import android.view.View.*;
import android.widget.AdapterView.*;
import android.util.*;
import com.kunorz.filesmanager.adapter.*;
import com.kunorz.filesmanager.util.*;
import android.net.*;
import android.content.pm.*;
import com.kunorz.filesmanager.dialog.*;

public class FileFragment extends Fragment implements OnItemClickListener,OnItemLongClickListener
{
	private Context context;
	private OnItemClickListener listener;
	private ArrayList<FileData> fileslist;
	private FileAdapter fileadapter;
	
	private View layout;
	private ListView listview;
	private TextView info;
	private File file;
	private boolean isRoot=false;
	private boolean isShown=true;
	
	private int position;

	private View header;

	private View headerview;
	
	public FileFragment(Context context,File file,int position){
		this.context=context;
		this.file=file;
		this.position=position;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		fileslist=new ArrayList<FileData>();
		fileadapter=new FileAdapter(context,fileslist);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if(file.getParentFile()==null)isRoot=true;
		else isRoot=false;
		if(layout==null)layout=inflater.inflate(R.layout.fragment, null);
		if(info==null)info=(TextView)layout.findViewById(R.id.fragment_text);
		if(listview==null){
			listview=(ListView)layout.findViewById(R.id.fragment_list);
			header=inflater.inflate(R.layout.item,null);
			headerview=header.findViewById(R.id.item_item);
			listview.addHeaderView(header);
			listview.setAdapter(fileadapter);
			listview.setOnItemClickListener(this);
			listview.setOnItemLongClickListener(this);
		}
		fileslist.clear();
		if(file.listFiles()==null)info.setText("没有访问权限");
		else{
			fileslist.addAll(FileData.listFileDatas(file));
			Collections.sort(fileslist,StaticData.FileComparator);
		}
		fileadapter.notifyDataSetChanged();
		if(isRoot){
			headerview.setVisibility(View.GONE);
			isShown=false;
		}else{
			if(!isShown)headerview.setVisibility(View.VISIBLE);
		}
		return layout;
	}
	
	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		FileData itemfile;
		if(isRoot)itemfile=fileslist.get(p3-1);
		else if(p3==0)itemfile=new FileData(file.getParent());
		else itemfile=fileslist.get(p3-1);
		if(itemfile.isFile()){
			Intent intent = new Intent();    
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),"audio/*");
			PackageManager pm=getActivity().getPackageManager();
			List<ResolveInfo> resInfo =pm.queryIntentActivities(intent,0);
			for(int i=0;i<resInfo.size();i++){
		//		Log.d("llll",resInfo.get(i).loadLabel(pm).toString());
		//		Log.d("llll",resInfo.get(i).activityInfo.packageName+"\n"+resInfo.get(i).resolvePackageName);
			}
			intent.setPackage(resInfo.get(1).activityInfo.packageName);
			new SelectDialog(itemfile).show(getFragmentManager(),itemfile.getName());
	/*		File file = new File(savePAth + "/" + filename);
			List<ResolveInfo> resInfo =getActivity(). getPackageManager().queryIntentActivities(it,0);
			if(!resInfo.isEmpty()){
				List<Intent> targetedShareIntents =new ArrayList<Intent>();
				for(ResolveInfo info : resInfo){
					Intent targeted =new Intent(Intent.ACTION_VIEW);
					targeted.setType(mineType);
					ActivityInfo activityInfo = info.activityInfo;
					if(activityInfo.packageName.contains( getActivity().getPackageName())|| activityInfo.name.contains( getActivity().getPackageName())){
						continue;
					}
					targeted.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
					targeted.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Uri uri = Uri.fromFile(file);
					targeted.setDataAndType(uri, mineType);
					targeted.setPackage(activityInfo.packageName);
					targetedShareIntents.add(targeted);
				}
				Intent chooserIntent =Intent.createChooser(targetedShareIntents.remove(0), getActivity().	getTitle());
				if(chooserIntent ==null){
					return;
				}
				// A Parcelable[] of Intent or LabeledIntent objects as set with
				// putExtra(String, Parcelable[]) of additional activities to place
				// a the front of the list of choices, when shown to the user with a
				// ACTION_CHOOSER.
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
				try{
					startActivity(chooserIntent);
				}catch(android.content.ActivityNotFoundException ex){
					Toast.makeText(getActivity(),"Can't find view component to view",Toast.LENGTH_SHORT).show();
				}*/
		//	startActivity(intent);
		}else if(itemfile.isDirectory()){
			file=itemfile;
			listener.update(file);
		}
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		listener.addItem(fileslist.get(p3));
		return true;
	}
	
	public File back(){
		file=file.getParentFile();
		return file;
	}
	
	public int getFlag(){
		return position;
	}

	public void setOnItemClickListener(OnItemClickListener listener){
		this.listener=listener;
	}
	
	public interface OnItemClickListener{
		
		public void addItem(File file);
		
		public void update(File file);
		
	}
}
