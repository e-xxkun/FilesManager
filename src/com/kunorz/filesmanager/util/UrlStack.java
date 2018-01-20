package com.kunorz.filesmanager.util;
import java.util.*;
import java.io.*;

public class UrlStack extends Stack<String>
{
	
	public UrlStack(){
		super();
	}
	
	public String getUrlStr(){
		String url="";
		for(int i=0;i<size();i++){
			url+=get(i);
		}
		return url;
	}
	
	public List<File> getFileList(){
		return Arrays.asList(new File(getUrlStr()).listFiles());
	}
}
