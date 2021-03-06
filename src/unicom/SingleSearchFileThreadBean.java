package unicom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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


public class SingleSearchFileThreadBean extends Thread {
			
	DealFile dealfile=new DealFile();
	String outdir=DicTion.outdir;
	public static int readinglen=7;
	List<String> mobilelist=new ArrayList<String>();
	public static CFile mobilefile;
	public static int len=26;
	static 
	{
		for(String mobile:DicTion.filterset)
		{
			mobilefile=new CFile(DicTion.mobiledir+mobile+".csv");
		}
	}	
	public SingleSearchFileThreadBean(String outputdir ) {
		super();
		outdir=outputdir;
	}
	//使用工厂动态创建后需要修改构造函数
	public SingleSearchFileThreadBean() {
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
				readFile(f.getAbsolutePath());

				synchronized(SingleSearchFileThreadBean.mobilefile)
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
		Iterator iter = mobilelist.iterator();
		while (iter.hasNext()) 
		{
			try {
				SingleSearchFileThreadBean.mobilefile.writeRow((String)iter.next());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iter.remove();
			
		}
		
	}
	public void readFile(String path)
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
						mobilelist.add(line);				
						
					}
						
				}
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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



