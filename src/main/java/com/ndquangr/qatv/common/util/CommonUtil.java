package com.ndquangr.qatv.common.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ndquangr.qatv.module.nom.NomManageDAOImpl;

/**
 * 공통 Utility 클래스.
 * @FileName  : CommonUtil.java
 * @Project     : mis_java
 * @최초작성일 : 2014. 9. 26. 
 * @프로그램설명 : 공통적인 데이터 변경등의 기능을 수행하는 단위 컴포넌트
 */
/**
 * @author keim
 *
 */
/**
 * 기능명 
 * @작성일    : 2015. 6. 22. 
 * @작성자      : keim
 * @프로그램설명 :
 * @진행상태: TO-DO, DEBUG, TEST, COMPLETE  
 */
public final class CommonUtil
{
	/** 
	 * 도메인
	*/
    public static String getDomain(HttpServletRequest req) {
        StringBuffer sb = new StringBuffer();
        sb.append(req.getScheme());
        sb.append("://");
        sb.append(req.getServerName());
        sb.append(":");
        sb.append(req.getServerPort());
        sb.append(req.getContextPath());
        return sb.toString();
    }  

	/**
	 * Map에 대한 데이타 가져오기
	 * @param map - 대상Map
	 * @param key - 데이터를 가져올 Key
	 * @param defaultValue - 데이터가 없는 경우 기본값
	 * @return String
	 */
    public static String getMapValue(Map map, String key, String defaultValue){
        if (map == null) return "";
        Object value = map.get(key);
        if (value == null) return defaultValue;
        if (StringUtils.isEmpty("" + value)) return defaultValue;
        return "" + value;
    }

    /**
     * NUll 체크 로직 
     * @작성일    : 2014. 9. 26. 
     * @작성자      : keim
     * @프로그램설명 :
     * @진행상태설명:  TEST - LOGIC TEST 이후 수정 필   
     */
    public static boolean isNull(Object obj){
    	if(obj ==null){
    		return true;
    	}
    	return false;
    }
    

    /**
     * 타입상관없이 null 체크  
     * @작성일    : 2015. 5. 8. 
     * @작성자      : keim
     * @프로그램설명 :
     * @진행상태: TO-DO, DEBUG, TEST, COMPLETE  
     */
    public static boolean isNotEmpty(Object obj){
    	String tmp = CastUtil.castToString(obj);
    	if(tmp !=null && !tmp.equals("")){
    		return true;
    	}
    	return false;
    }
    
    /**
     * byte 체크 
     * @작성일    : 2015. 4. 27. 
     * @작성자      : keim
     * @프로그램설명 : 바이트 체크 
     * @진행상태설명:  TO-DO :  
     */
	public static String rightBytes(String strValue, int iByte) {
		byte[] result = null;

		if(strValue == null){
			return null;
		}else if(strValue.equals("") || iByte < 0){
			return new String("");
		}else{
			byte[] source = strValue.getBytes();

			if(source.length < iByte){
				return new String(strValue);
			}else{
				result = new byte[iByte];
				System.arraycopy(source, source.length - iByte, result, 0, iByte);
				return new String(result);
			}
		}
	}
	
	/**
	 * changeNomCode
	 * @작성일	: 2016. 9. 1. 
	 * @작성자	: ndquangr
	 * @기능설명 	:
	 * @진행상태	: TO-DO, DEBUG, TEST, COMPLETE  
	 */
	public static String changeNomCode(String str, NomManageDAOImpl nomdao) {
		List nomcode = new ArrayList();
		String patternString = "\\{N\\d{8}\\}";
		Pattern r = Pattern.compile(patternString);
		
		Matcher m = r.matcher(str);
		String tmp;
		while (m.find()) {			
			tmp = m.group();
			if (!nomcode.contains(tmp)) {
				nomcode.add(tmp); // tmp.substring(2,tmp.length() - 2)
			}
		}
		String newstr = str;
		Map param = new HashMap();
		Map res;
		String code;
		if(nomcode.size() > 0) {
			for (int i = 0; i < nomcode.size(); i++) {
				tmp = (String) nomcode.get(i);
				code = tmp.substring(1,tmp.length() - 1);
				param.put("code", code);				
				try {
					res = nomdao.map("getMap", param);
					if (res != null && res.containsKey("FILE_NAME") && res.get("FILE_NAME") != null) {
						newstr = newstr.replace(tmp, "<img src=\"/qatv/" + res.get("FILE_NAME") + "\" alt=\"" + res.get("CHAR_VIET") + "\" height=\"20\" width=\"20\" />");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return newstr;
	}
    
	/**
     * 지정한 문자열로 지정한 길이만큼 반복해서 채운다. 
     * @작성일    : 2015. 4. 27. 
     * @작성자      : keim
     * @프로그램설명 : 
     * fill("#", 5) return : "#####"
	 * fill("abc", 7) return : "abcabca"
	 * fill("abc", 2) return : "ab" 
     * @진행상태설명:  TO-DO :  
     */
	public static String fill(String strPattern, int iLen){
		StringBuffer sb = new StringBuffer();

		if( strPattern == null){
			return null;
		}else if(strPattern.equals("") || iLen < 0){
			return "";
		}else{
			for(int i = 0; i < iLen; i = i + strPattern.length()){
				sb.append(strPattern);
			}
		}

		return sb.substring(0, iLen);
	}
	
	
	
    /**
     * 인스턴스 타입 리턴 
     * @작성일    : 2014. 9. 26. 
     * @작성자      : keim
     * @프로그램설명 : pameter Object 에 대하여 클래스 유형을 
     * @진행상태설명:  TO-DO : 미정의된 클래스에 대하여 reflection으로 클래스를 찾아 타입 리턴 
     */
    public static Class getInstanceType(Object obj){
    	Object cls = new Object();
    	if(obj != null ){
	    	if(obj instanceof String){
	    		cls= new String();
	    	}else if(obj instanceof Integer){
	    		cls= new Integer(0);
	    	}else if(obj instanceof Double){
	    		cls= new Double(0);
	    	}else if(obj instanceof Float){
	    		cls= new Float(0);
	    	}else if(obj instanceof HashMap){
	    		cls= new HashMap();
	    	}else if(obj instanceof ArrayList){
	    		cls= new ArrayList();
	    	}else if(obj instanceof BigDecimal){
	    		cls= new BigDecimal(0);
	    	}else if(obj instanceof Date){
	    		cls= new Date();
	    	}else {
	    		cls = obj;
//	    		Reflection refex = new Reflection();
	    	}
    	}
    	return cls.getClass();
    }
    
    
    
    /**
     * 구동되는 상대 경로 추출
     * @작성일    : 2014. 10. 24. 
     * @작성자      : keim
     * @프로그램설명 :
     * @진행상태설명:  TEST 리눅스 환경 window 환경 테스트 필요 
     */
    public static String  getDirectoryPath(String proName){
    	Class cls = CommonUtil.class;
//    	String proName = "mis_java";
    	
    	String path = cls.getClassLoader().getResource("").getPath();
//    	String path = "/web/mis/WEB-INF/class";
    	String directory = "";
//    	ResourceUtil.getMessage
//    	ResourceBundle bundle= ResourceBundle.getBundle("kr.or.innopolis.mis.config.properties.system",Locale.KOREAN);
    	Map messageMap = ResourceUtil.getMessageMap("system.dir.upload");
    	
//    	if(proName.equals("excelDownload")){
//    		directory = bundle.getString("dir.download.excel");
//    	}
    	directory = (String) messageMap.get(proName);
    	
    	
//		String proName = "mis_java";
//    	System.out.println(path);
//    	System.out.println(proName);
    	
    	if( path.indexOf("WEB-INF") > 0){
    		path = path.substring(0,path.indexOf("WEB-INF"));
    		if(path.indexOf("/") == 0 ){
    			path = path.substring(1);
    		}
    	}else{
    		path = "/web/mis";
    	}
    	
    	
//    	path = path.substring(0,path.lastIndexOf("/",path.lastIndexOf("/",path.lastIndexOf("/")-1)-1));
    	path += directory;
    	return path;
    }
    
    public static String getDirectoryDownloadExcel(){
    	return getDirectoryPath("excelDownload");
    }
    
    public static String getDirectoryUploadDefault(){
    	return getDirectoryPath("defaultUpload");
    }
    
    public static String getDirectoryUploadExcel(){
    	return getDirectoryPath("excelUpload");
    }
    
    public static String getDirectoryDownloadHomeTax(){
    	return getDirectoryPath("hometaxDownload");
    }
    public static String getDirectoryUploadEmpImg(){
    	
    	
    	
//    	Class cls = CommonUtil.class;
//    	String path = cls.getClassLoader().getResource("").getPath();
//    	String directory = "";
//    	ResourceBundle bundle= ResourceBundle.getBundle("kr.or.innopolis.mis.config.properties.system",Locale.KOREAN);
//    	
//    	
//    	directory = bundle.getString("dir.upload.img");
//    	logger.error(path);
//    	path = path.substring(0,path.indexOf("WEB-INF"));
//    	path += directory;
    	
    	return getDirectoryPath("empimg");
    }
    
    public static void printMap(Map map){
    	for (Map.Entry<String, Object> entry : ((Map<String, Object>) map) .entrySet()) {
    		System.out.println(entry.getKey()+" : "+ entry.getValue());
    	}
    }
    

    /**
     * 파라미터를 확인하여 Null이면 공백문자 반환
     * @param parameter - Object
     * @return - String(default="")
     */
    public static String toString(Object parameter){
        return (parameter == null) ? "" : parameter.toString();
    }

    /**
     * 파라미터를 확인하여 Null이면 대체문자열(replacement)반환
     * @param parameter - Object
     * @param replacement - String : Null인경우 대체문자열
     * @return - String
     */
    public static String toString(Object parameter, String replacement){
    	return (parameter == null) ? replacement : parameter.toString();
    }

    /**
     * 파라미터를 확인하여 Null이면 0 반환
     * @param parameter - Object
     * @return - int
     */
    public static int toInt(Object parameter){
    	if(parameter == null || parameter.toString().trim().equals("")) return 0;
    	else return Integer.parseInt(parameter.toString().trim()) ;
    }

    /**
     * 파라미터를 확인하여 Null이면 0 반환
     * @param parameter - Object
     * @return - long
     */
    public static long toLong(Object parameter){
    	if(parameter == null || parameter.toString().trim().equals("")) return 0;
    	else return Long.parseLong(parameter.toString().trim()) ;
    }
    
    /**
     * 파라미터를 확인하여 Null이면 0 반환
     * @param parameter - Object
     * @return - float
     */
    public static float toFloat(Object parameter){
    	if(parameter == null || parameter.toString().trim().equals("")) return 0;
    	else return Float.parseFloat(parameter.toString().trim()) ;
    }
    
    /**
     * 파라미터를 확인하여 Null이면 0 반환
     * @param parameter - Object
     * @return - double
     */
    public static double toDouble(Object parameter){
    	if(parameter == null || parameter.toString().trim().equals("")) return 0;
    	else return Double.parseDouble(parameter.toString().trim()) ;
    }

    /**
	 * 숫자형 데이터를 회계단위로 표현
	 * @param num - String 텍스트형 숫자 데이타(10000)
	 * @return String 변형된 데이타(10,000)
	 */
	public static String addComma(String num){
		NumberFormat nf = NumberFormat.getInstance();
		if(num == null || num.trim().equals("")) num = "0";
		return nf.format(Long.parseLong(num));
	}   
	
	/**
	* 구분자가 있는 문자열을 배열로 반환(StringTokenizer)
	* @param value - String : 대상문자열(ex.  "A01,A02,A03")
	* @param separator - String : 구분자(ex. ",")
	* @return  String[]		
	*/
	public static String[] toArray(String parameter, String separator){
		String[] array = null;
		if(parameter != null && !parameter.trim().equals("")){
			StringTokenizer st = new StringTokenizer(parameter, separator);
			array = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()){
				array[i] = st.nextToken();
				i++;
			}
		}	
		return array;
	}	

    /**
     * 파라미터에서 regex 찾아 제거하고 반환
     * @param parameter - Object regex가 포함된 변환대상
     * @param regex - String 제거할 문자 ex) '-' , ','
     * @return String : regex가 제거된 변환된 문자열
     */
    public static String stripExp(Object parameter, String regex) {
        return toString(parameter).replaceAll(regex, "");
    }   
    
    /**
     * 파라미터를 구분(cls)에 따라 포맷하여 반환
     * @param parameter - Object 포맷대상
     * @param cls - String 포맷종류(JUMIN|POST|DATE)
     * @param format - String 포맷문자(ex. "-", "/")
     * @return String : 포맷된 문자열
     */
    public static String formatString(Object parameter, String cls, String format){
    	String formattedStr = "";
    	formattedStr = toString(parameter);
    	formattedStr = stripExp(formattedStr, format);
    	if(cls == null || cls.trim().equals("") || 
    			format == null || format.trim().equals("")){
    		//할수있는게 없음
    	}else if(cls.equals("JUMIN")){
    		if(formattedStr.trim().length() == 13){
    			formattedStr = formattedStr.substring(0,6) + format 
    					       + formattedStr.substring(6, 13);
    		}
		}else if(cls.equals("POST")){
			if(formattedStr.trim().length() == 6){
				formattedStr = formattedStr.substring(0,3) + format 
						      + formattedStr.substring(3, 6);
    		}		
		}else if(cls.equals("DATE")){
			if(formattedStr.trim().length() == 8){
				formattedStr = formattedStr.substring(0,4) + format 
						      + formattedStr.substring(4, 6) + format 
						      + formattedStr.substring(6, 8);
			}else if (formattedStr.trim().length() == 6) {
				formattedStr = formattedStr.substring(0,4) + format 
						       + formattedStr.substring(4, 6);
			}
		}
    	return formattedStr;
    }
    
    /**
     * StringTokenizer를 이용해서 문자열을 분리해서
     * 쿼리용(IN ())으로 문자열 조정
     * @param str String : 대상문자열 
     * @param delim String : 분리자
     */
    public static String makeQueryString(String str, String delim){
    	StringTokenizer stz = new StringTokenizer(str, delim);
    	String inStr = "";
    	int i = 0;
    	while(stz.hasMoreTokens()){
    		inStr = inStr+ (i==0?"":",") + "'"+stz.nextToken().trim()+"'";
    		i++;
    	}
    	return inStr;
    }
    
    /**
	 * humanReadableByteCount 이용해서 파일의 사이즈를 인간 읽을 수 있게 만든다.
	 * @param byteStr String : 사이즈 (byte)
	 * @param si boolean : kB 단위
	 */
	public static String humanReadableByteCount(String byteStr, boolean si){
		long bytes = 0;
		try {
			bytes= Long.parseLong(byteStr);
		} catch (Exception e) {}
		int unit = si ? 1024 : 1000;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		//String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		String pre = "KMGTPE".charAt(exp-1) + "";
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}