package unicom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import all.DicTion;

public class DealFile {

	/**
	 * @param args
	 */
	public static int len=26;
	//private static final Lock lock = new ReentrantLock() ;
	
//	public Map<String, List<String>> getMobilemap() {
//		return mobilemap;
//	}
//	public void setMobilemap(Map<String, List<String>> mobilemap) {
//		this.mobilemap = mobilemap;
//	}
	/*
	 * 将 hashMap 重新初始化 
	 * 
	 * */
	public void resetHashMap()
	{
//		Iterator iter=mobilemap.entrySet().iterator();
//		while(iter.hasNext())
//		{
//			Map.Entry entry = (Map.Entry) iter.next();
//			String key = (String) entry.getKey();
//			List<String> val = (List<String>) entry.getValue();
//			val.clear();
//		}
//		for(Entry<String,List<String>> entry:mobilemap.entrySet())
//		{
//			String key = (String) entry.getKey();
//			List<String> val = (List<String>) entry.getValue();
//			val.clear();
//		}
	}
	/*
	 * 读一个话单文件，从中找到要过滤的号码同时放入对应的 list 
	 * 
	 * */
	public void readFile(String path,Map<String,List<String>> mobilemap)
	{
		String line=null;
		String column[]=null;
		String mobile=null;
		BufferedReader	br=null;
		FileReader reader=null;
		try {
			reader = new FileReader(new File(path));
			br=new BufferedReader(reader);
			for(;(line=br.readLine())!=null;)
			{
				column=line.split(",");
				if((column!=null)&&column.length>len)
				{
					if(column[25]!=null)
					{
						mobile=column[25];
					}
					if(DicTion.filterset.contains(mobile))
					{
						
						if(!mobilemap.containsKey(mobile))
						{
							List<String> mobilelist=new ArrayList<String>();
							mobilemap.put(mobile, mobilelist);
						}
						else 
						{
							List<String> mobielist = (List) mobilemap.get(mobile);
							if (mobielist != null) 
							{
								mobielist.add(line);
							}
						}
					}
						
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
