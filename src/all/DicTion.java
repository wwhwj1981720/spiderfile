package all;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import output.CFile;


import tools.Dir;

public class DicTion {
	
	public static String scdrhead="recordType,imsi,imei,sgsnip,msNetworkCapability,routingArea,lac,cellid,chargingID,ggsnip,apn,pdpType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,consolidationResult,filename";
	public static String sgwhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,cAMELChargingInformation,servingNodeType,pGWAddressUsed,pGWPLMNIdentifier,startTime,stopTime,pDNConnectionChargingID,filename";
	public static String pgwhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,cAMELChargingInformation,servingNodeType,pGWAddressUsed,pGWPLMNIdentifier,startTime,stopTime,pDNConnectionChargingID,filename";
	public static String egcdrhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,filename";
	public static String suffix=".csv"; 
	public static int fieldsize=0;
	public static int scdrfieldsize=32;
	public static int zxscdrfieldsize=32;
	public static int sgwrfieldsize=44;
	public static String movedirstr="";//需要移动到的目录；
	public static String outdir=null;//解码生成的decode文件的目录；
	public static Dir movedir=null;
	public static String mobiledir="d:\\BSSdecode\\zjfee\\scdr\\";
	//public static Map filtermap=new HashMap();
	public static Set<String> filterset=new HashSet<String>();
	public static Map<String,CFile> filemap=new HashMap<String,CFile>();
	
	public static CFile onefile=new CFile(mobiledir+"one.csv");
	static 
	{
		readTag("chosemobile");
		int i=0;
		for(String mobile:DicTion.filterset)
		{
			CFile mobilefile=new CFile(mobiledir+mobile+".csv");
			filemap.put(mobile, mobilefile);
			i++;
		}
		
		
	}
	/*
	 * 读取配置文件 初始化 20 个要过滤的号码，以及每个号码查到的详单记录 mobilelist
	 * */
	public static void readTag(String name)
	{
		InputStream in;
		BufferedReader bf;
		in=DicTion.class.getResourceAsStream("/"+name+".txt");
		bf=new BufferedReader(new InputStreamReader(in));
		String line=null;
		byte []tagb=null;
		int way=0;
		int pos=0;
		try {
			for(;(line=bf.readLine())!=null;)
			{
				if(!filterset.contains((line)))
				{
					filterset.add(line);
				}
				
				
					
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
