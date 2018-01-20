package com.kunorz.filesmanager.adapter;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.io.*;
import java.util.*;
import android.util.*;
import com.kunorz.filesmanager.*;
import java.text.*;
import com.kunorz.filesmanager.util.*;

public class FileAdapter extends BaseAdapter
{

	private Context context;

	private List<FileData> files;

	public FileAdapter(Context context,List<FileData> file){
		this.context=context;
		this.files=file;
	}
	
	@Override
	public int getCount()
	{
		return files.size();
	}

	@Override
	public Object getItem(int p1)
	{
		return files.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		Item item=null;
		FileData file=files.get(p1);
		if(p2==null){
			p2=LayoutInflater.from(context).inflate(R.layout.item,null);
			item=new Item();
			item.title=(TextView)p2.findViewById(R.id.item_title);
			item.info=(TextView)p2.findViewById(R.id.item_info);
			item.img=(ImageView)p2.findViewById(R.id.item_img);
			p2.setTag(item);
		}else item=(Item)p2.getTag();
		item.title.setText(file.getName());
		String info=toTime(file.lastModified())+"  ";
		if(file.isFile()){
			item.img.setImageResource(R.drawable.type_default);
			info+=toFileSize(file.length());
		}else if(file.isDirectory()){
			item.img.setImageResource(R.drawable.type_folder);
			if(file.list()!=null)info+="包含"+file.list().length+"个项目";
			else info+="无权限";
		}
		item.info.setText(info);
		return p2;
	}
	
	private String toTime(long time){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date(time));
	}
	
	public String toFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (size ==0) {
			fileSizeString="0B";
		} else if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size< 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size< 1073741824) {
			fileSizeString = df.format((double) size/ 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size/ 1073741824) +"G";
		}
		return fileSizeString;
    }
	
	class Item{
		TextView title;
		TextView info;
		ImageView img;
	}
	
}
