package com.kunorz.filesmanager.util;
import java.util.*;
import java.io.*;
import java.text.*;

public class FileComparator implements Comparator<FileData>
{
	public final static int SORT_BY_NAME=0;
	public final static int SORT_BY_TIME=1;
	public final static int SORT_BY_TYPE=2;
	public final static int SORT_BY_SIZE=3;
	public final static int SORT_SEQUENCE=4;
	public final static int SORT_SEQUENCE_INVERTED=5;
	
	private Collator collator = Collator.getInstance(java.util.Locale.CHINA);
	
	private int type=SORT_BY_NAME;
	private int sequence=SORT_SEQUENCE;
	
	public void setSortType(int type){
		this.type=type;
	}
	
	public void setSortSequence(int sequence){
		this.sequence=sequence;
	}
	
	@Override
	public int compare(FileData p1, FileData p2)
	{
		if(p1.isDirectory()&&p2.isFile())return -1;
		else if(p2.isDirectory()&&p1.isFile())return 1;
		FileData f1,f2;
		if(sequence==SORT_SEQUENCE){
			f1=p1;
			f2=p2;
		}else{
			f1=p2;
			f2=p1;
		}
		switch(type){
			case SORT_BY_NAME:
				return collator.compare(f1.getName(),f2.getName());
			case SORT_BY_TIME:
				return f1.lastModified()>=f2.lastModified()?-1:1;
			case SORT_BY_SIZE:
				if(f1.isDirectory())return collator.compare(f1.getName(),f2.getName());
				return f1.length()>=f2.length()?-1:1;
			case SORT_BY_TYPE:
				if(f1.isDirectory())return collator.compare(f1.getName(),f2.getName());
				String e1=f1.getExtension();
				String e2=f2.getExtension();
				if(e1==null)return 1;
				else if(e2==null)return -1;
				else return collator.compare(e1,e2);
		}
		return 0;
	}
}
