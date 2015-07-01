package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;



public class FastFileOutput {
	
	public void FileOutPut(String fname,String content)
	{
		//fout=fname+"hex.txt";
		File f = new File(fname);
		//if(f.isFile()&&f.exists())
		FileChannel pageFc=null;
		try {
			pageFc = new FileOutputStream(f, true).getChannel();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			IOUtil.write(pageFc, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
