package com.ndquangr.qatv.common.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public final class ReqUtils {
	
	/******************************************************************
	 * 널데이터 체크(String 형식)
	 * @param getVal 데이터값  
	 * @param chgdata 널일때 교체할 값
	 ******************************************************************/
	public static String getEmptyResult(String getVal, String chgdata) {
		String rVal = getVal;
		if(getVal == null || getVal.equals("") || getVal.equals("null")){
			rVal = chgdata;
		}else{
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			//rVal = rVal.replaceAll("&", "&amp;");
		}
		
		return rVal;
	}
	
	/******************************************************************
	 * 널데이터 체크(String 형식)
	 * @param getVal 데이터값  
	 * @param chgdata 널일때 교체할 값
	 * 데이터 값에 대한 기본적인 태그 변경 및 제거 처리
	 ******************************************************************/
	public static String getEmptyResult(String getVal) {
		String rVal = getVal;
		
		if(getVal == null || getVal.equals("") || getVal.equals("null")){
			rVal = "";
		}else{
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			//rVal = rVal.replaceAll("&", "&amp;");
		}
		
		return rVal;
	}
		
	public static String getRequestIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
			    String ipAddress = inetAddress.getHostAddress();
			    ip = ipAddress;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ip;
	}
	

	/******************************************************************
	 * Key에 해당되는 정보 추출
	 * @param Data 데이터값  
	 * @param key 추출 대상 키
	 * 데이터(Map)의 Key에 해당되는 데이터List를 추출하여 반환
	 ******************************************************************/	
	public static List getList(Map Data, String key) {
		if(Data.containsKey(key)) {
			Object obj = Data.get(key);
			if (obj != null) {
				if (obj instanceof List) {
					return (List) obj;
				} else {
					List ls = new ArrayList();
					ls.add(obj);
					return (List) ls;
				}
			}
		}
		return null;
	}		
	
	/******************************************************************
	 * Key에 해당되는 정보 추출
	 * @param Data 데이터값  
	 * @param key 추출 대상 키
	 * 데이터(Map)의 Key에 해당되는 데이터List를 추출하여 반환
	 ******************************************************************/	
	public static String[] getArrays(Map Data, String key) {
		if(Data.containsKey(key)) {
			Object obj = Data.get(key);
			if (obj != null) {
				if (obj instanceof List) {
					List ls = (List)obj;
					return (String[])ls.toArray(new String[ls.size()]);
				} else {
					List ls = new ArrayList();
					ls.add(obj);
					return (String[])ls.toArray(new String[ls.size()]);
				} 

			}
		}
		return null;
	}			
}
