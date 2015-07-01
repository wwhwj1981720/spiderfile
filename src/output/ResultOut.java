package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import all.DicTion;

public class ResultOut 
{
	
	public static void fileOutPut(Map<String,List<String>> map)
	{
		Iterator iter = map.entrySet().iterator();
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

}


