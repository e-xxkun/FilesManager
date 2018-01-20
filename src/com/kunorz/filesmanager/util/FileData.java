package com.kunorz.filesmanager.util;
import java.io.*;
import java.util.*;
import android.util.*;

public class FileData extends File
{
	private String extension=null;
	
	public FileData(String url){
		super(url);
	}
	
	public FileData(File file,String url){
		super(file,url);
	}
	
	public String getExtension(){
		if(isFile()&&extension==null){
			String name=getName();
			int n=name.lastIndexOf(".");
			if(n>0&&n<name.length()-1)extension=name.substring(n+1);
		}
		return extension;
	}
	
	public static List<FileData> listFileDatas(File file){
		String[] filenames=file.list();
		ArrayList<FileData> filedatas=new ArrayList<FileData>();
		for(int i=0;i<filenames.length;i++){
			filedatas.add(new FileData(file,filenames[i]));
		}
		return filedatas;
	}
}

