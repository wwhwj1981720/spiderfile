package unicom;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import output.CFile;
import output.ResultOut;



import all.Area;
import all.DicTion;


public class SearchFileThreadBean extends Thread {
			
	DealFile dealfile=new DealFile();
	String outdir=DicTion.outdir;
	public static int readinglen=7;
	Map<String,List<String>> mobilemap=new HashMap<String,List<String>>();
		
	public SearchFileThreadBean(String outputdir ) {
		super();
		outdir=outputdir;
	}
	//使用工厂动态创建后需要修改构造函数
	public SearchFileThreadBean() {
		super();
	}

	public void run()
	{	
		File f=null;
		while(true)
		{
			
			f=this.getOneFile(GetDirFileBlock.queque);
			if(f==null) 
			{
				continue;
			}
			String fname=f.getName();
			try 
			{
				dealfile.readFile(f.getAbsolutePath(),mobilemap);
				synchronized(DicTion.filemap)
				{
					fileOutPut();
				}
				
				moveFile(f);//移动读完之后的文件
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f=null;				
			
		}
	}
	/*
	 * 将每个文件查到的信息输出到到对应的文件
	 * */
	public  void fileOutPut()
	{
		Iterator iter = mobilemap.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String mobile = (String)entry.getKey();
			CFile cf=DicTion.filemap.get(mobile);
			List<String> val = (List<String>) entry.getValue();
			cf.writeList(val);
			val.clear();//输出之后清空
		}
	}
	public  void fileOutPut(CFile cf)
	{
		Iterator iter = mobilemap.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String mobile = (String)entry.getKey();
			List<String> val = (List<String>) entry.getValue();
			cf.writeList(val);
			val.clear();//输出之后清空
		}
	}
	public File getOneFile(BlockingQueue<File> queque)
	{
		System.out.println(" write thread id is "+this.getId()+" size " +queque.size());
		File f=null;
		File renamef=null;
		try
		{
			System.out.println(queque.size());
			f=queque.poll(1,TimeUnit.MILLISECONDS);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(f!=null)
		{
			renamef=renameFile(f);
		}
		return renamef;
	}
	public File renameFile(File f)
	{
		File targetFile1=null;
		if(f.exists())
		{
			try 
			{
				 String path=f.getCanonicalPath();
				 targetFile1 = new File(path+"reading");
				 f.renameTo(targetFile1);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return targetFile1;
	}

	public boolean moveFile(File f) 
	{
		boolean flag = false;
		File targetFile1 = null;
		int len = 0;
		int sublen = 0;
		if (f.exists()) 
		{
			String name = f.getName();
			len = name.length();
			sublen = len - readinglen;
			if (sublen > 0) 
			{
				name = name.substring(0, sublen);
			} 
			else 
			{
				flag = false;
				return flag;
			}
			String tagetname = DicTion.movedirstr + name;
			targetFile1 = new File(tagetname);
			if(targetFile1.exists())
			{
				targetFile1.delete();
			}
			if (f.renameTo(targetFile1)) 
			{
				if (targetFile1.isFile()) 
				{
					flag = true;
				}
			}

		}
		return flag;
	}
	public static  void  main(String args[]) throws Throwable
	{
		
//		String dir="D:\\解码\\zjfee\\scdr\\test";
//		String outdir="D:\\\u89E3\u7801\\zjfee\\scdr\\move\\";
//		GetDirFile read=new GetDirFile(dir);
//		read.start();
//		for(int i=0;i<3;i++)
//		{
//			SCDRMutiThreadGetSubFileBean onethread=new SCDRMutiThreadGetSubFileBean(outdir);
//			onethread.start();
//		}
	}

 }



