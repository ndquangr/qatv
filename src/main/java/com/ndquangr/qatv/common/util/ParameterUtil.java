package com.ndquangr.qatv.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class ParameterUtil {
	
	public static Map getParameterMapWithOutSession(HttpServletRequest request) {
		Map map = new HashMap();
		getParam(request, map);
		
		return map;
	}
	public static Map getParameterMap(HttpServletRequest request) {
		

		Map map = new HashMap();
		getParam(request, map);
		
		
		
        //2) 세션정보를 파라미터에 담는다.(사용자정보를 쿼리에서 사용하는 경우가 많으므로)
        //AuthController확인(SESS_USER라는 명칭으로 세션에 사용자정보 저장되어 있다고 가정)
		HttpSession session = request.getSession();
//            //AuthController확인(SESS_USER라는 명칭으로 세션에 사용자정보 저장되어 있다고 가정)
		if(session != null && session.getAttribute("SESS_USER") != null)
		{
		   Map user = (Map)session.getAttribute("SESS_USER");
		   map.put("session", user);
		   
		   map.put("REGI_EMP_NO", user.get("SESS_USER_ID"));
		   map.put("MDFY_EMP_NO", user.get("SESS_USER_ID"));
		   map.put("REGI_DT", new Date());
		   map.put("MDFY_DT", new Date());
		}
		return map;
	}
	/**
	 * 파라미터를 변형 해주는 기능
	 * @작성일    : 2015. 7. 15. 
	 * @작성자      : keim
	 * @프로그램설명 :
	 * @진행상태: TO-DO  
	 * recursive로 변형 예정
	 */
	private static void getParam(HttpServletRequest request, Map map) {
		int initCapacity = 1000;

        Map paramerterMap = request.getParameterMap(); // 1) 파라미터정보를 Map에 담는다.
        if(request instanceof MultipartHttpServletRequest){
        	// muliPart인 경우
        	Iterator<String> itr = ((MultipartHttpServletRequest) request).getFileNames();
        	
        	while(itr.hasNext()){
        		String uploadFileName = itr.next();
        		//System.out.println(uploadFileName);
        		List fileList = ((MultipartHttpServletRequest) request).getFiles(uploadFileName);
        		//System.out.println(fileList.size());
        		paramerterMap.put(uploadFileName, fileList);
        		
        	}
        }
		//System.out.println("================> request : " + request);
		//System.out.println("================> getParameterMap : " + paramerterMap);
        
        Iterator iter = paramerterMap.keySet().iterator();
        String key = null;
        Object value = null;
    	String modelKey;
		String listModel; 
		String column;      

		for (Map.Entry<String, Object> entry : ((Map<String, Object>) paramerterMap) .entrySet()){
			key = entry.getKey() ;
			//value =((String[]) entry.getValue())[0];
			//System.out.println(key);
			//System.out.println(value);

			if(entry.getValue() instanceof String[]){
				String[] tmp  = (String[]) entry.getValue();
				if(tmp.length <2){
				value =((String[]) entry.getValue())[0];
				}else{
					value =
					Arrays.asList(((String[]) entry.getValue()));
				}
				//System.out.println("문자열");
			}else if(entry.getValue() instanceof List) { 
				value =entry.getValue();
			}
			
			
			
			
			
			if(key.matches("[a-zA-Z](.*)\\[[0-9]{1,}\\]\\[(.*)\\]")){
    			// 여러건의 모델리스트일경우 List<Map>  //name =  "Hrm[0][DEPT_NM]"	
				Pattern p = Pattern.compile("([a-zA-Z].*)\\[([0-9]{1,})\\]\\[(.*)\\]"); 
				Matcher m = p.matcher(key);
				m.matches();
				
				System.out.println(m.group(1)); 
				System.out.println(m.group(2)); 
					System.out.println(m.group(3)); 
					
        		modelKey = m.group(1);
    			column = m.group(3);
    			int seq = Integer.parseInt(m.group(2));
//    			modelKey = modelKey.replaceAll("\\[[0-9]{1,}\\]", "");
    			if(!map.containsKey(modelKey)){ 
    				map.put(modelKey,initList(initCapacity) );
    			}else{
    				//넘어온 데이터가 초기용량을초기 할 경우 기존 새로운 List를 생성하고 기존 데이터를 새로 바인드 
    				if(seq >= initCapacity){
    					List tmpList = ((List)(map.get(modelKey)));
    					map.put(modelKey,initList(seq).addAll(tmpList) );
    				}
    			}
				if(((List)(map.get(modelKey))).get(seq) == null ){
					((List)(map.get(modelKey))).set(seq, new HashMap());
				}
				((Map)(((List)(map.get(modelKey))).get(seq))).put(column, value);
    		}
			
			else if(key.matches("[a-zA-Z](.*)\\[[0-9]{1,}\\]\\.(.*)")){
    			// 여러건의 모델리스트일경우 List<Map>  //name =  "Hrm[0].DEPT_NM"	
        		modelKey = key.substring(0, key.indexOf(".")); 
    			column = key.substring(key.indexOf(".")+1);
    			int seq = Integer.parseInt(modelKey.substring(modelKey.indexOf("[")+1,modelKey.indexOf("]")));
    			modelKey = modelKey.replaceAll("\\[[0-9]{1,}\\]", "");
    			if(!map.containsKey(modelKey)){ 
    				map.put(modelKey,initList(initCapacity) );
    			}else{
    				//넘어온 데이터가 초기용량을초기 할 경우 기존 새로운 List를 생성하고 기존 데이터를 새로 바인드 
    				if(seq >= initCapacity){
    					List tmpList = ((List)(map.get(modelKey)));
    					map.put(modelKey,initList(seq).addAll(tmpList) );
    				}
    			}
				if(((List)(map.get(modelKey))).get(seq) == null ){
					((List)(map.get(modelKey))).set(seq, new HashMap());
				}
				((Map)(((List)(map.get(modelKey))).get(seq))).put(column, value);
    		}else if(key.matches("[a-zA-Z](.*)\\.(.[^\\.]*)(.*)")){ 
	      			// 단건의Map 형태 //name =  "Hrm.DEPT_NM"
    	      		modelKey = key.substring(0, key.indexOf(".")); 
    	      		column = key.substring(key.indexOf(".")+1);
	    	      	//map.put(modelKey, value);
    	      		if(!map.containsKey(modelKey)){ 
    	      			map.put(modelKey, new HashMap());
    	      		}
    	      		((Map)(map.get(modelKey))).put(column, value);
    		}// Map<Map<Map>> 담기는 구조도 생성 해야함
    		else{
    			if(key.matches(".*\\[.*\\]")){
    				key = key.replace("[", "").replace("]", "");
    			}
    			// 단건의 데이터 //name =  "DEPT_NM"
    			 // 수정해야함
    			if(map.containsKey(key)){
    				String tmp = (String) map.get(key);
	      			
    				if(map.get(key) !=null && map.get(key) instanceof List){
    					((List)map.get(key)).add(value);
    				}else{
    					map.put(key, new ArrayList());
    				}
	      			
	      		}else{
	      			map.put(key,value);
	      		}
    			
    	    }
		}
		//map.get(modelKey)
		removeNullListFromMapObject(map);
		//System.out.println("===========> 가공된 데이터" + map);
	}
	

	
	private void generateObjectToMap(){
		
	}
	private static List initList(int size){
		List list = new ArrayList();
		for(int i =0 ; i < size; i++){
			list.add(i,null);
		}
		
		return list;
	}
	private static void removeNullList(List list ){
		list.removeAll(Collections.singleton(null));
	}
	private static void removeNullListFromMapObject(Map map ){
		for (Map.Entry<String, Object> entry : ((Map<String, Object>) map) .entrySet()){
			if(entry.getValue() instanceof List){
				removeNullList((List) entry.getValue());
			}
		}
	}
	
	public static String cleanXSS(Object object){
		String value;
		
		value= CastUtil.castToString(object);
		value = value.replaceAll("<","&lt;").replaceAll(">","&gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");         
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");         
		value = value.replaceAll("script", "");
		value = org.springframework.web.util.HtmlUtils.htmlEscape(value);
		value = StringEscapeUtils.escapeSql(value);
		return value;
	}
	
	public static Map getEmptyResult(String resultColumns) {
		Map map = null;
		if (resultColumns != null && !resultColumns.equals("")) {
			map = new HashMap();
			String[] columns = StringUtils.split(resultColumns, ",");
			for (int i = 0; i < columns.length; i++) {
				map.put(columns[i], "");
			}
		}
		return map;
	}

	/****************************************************************************************************
	 * 문자치환
	 *
	 * @param word
	 *            치환할 단어
	 * @param str1
	 *            찾을 문자
	 * @param str2
	 *            치환할 문자
	 * @return rs 치환한문자
	 ****************************************************************************************************/
	public static String replceStr(String word, String str1, String str2) {
		String txt = getEmptyResult2(word);
		String rs = txt.replaceAll(str1, str2);
		return rs;
	}

	/******************************************************************
	 * 널데이터 체크(String 형식)
	 *
	 * @param getVal
	 *            데이터값
	 * @param chgdata
	 *            널일때 교체할 값
	 ******************************************************************/
	public static String getEmptyResult2(String getVal, String chgdata) {
		String rVal = getVal;
		if (getVal == null || getVal.equals("") || getVal.equals("null")) {
			rVal = chgdata;
		} else {
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			// rVal = rVal.replaceAll("&", "&amp;");
		}

		return rVal;
	}

	/******************************************************************
	 * 널데이터 체크(String 형식)
	 *
	 * @param getVal
	 *            데이터값
	 ******************************************************************/
	public static String getEmptyResult2(String getVal) {
		String rVal = getVal;

		if (getVal == null || getVal.equals("") || getVal.equals("null")) {
			rVal = "";
		} else {
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			// rVal = rVal.replaceAll("&", "&amp;");
		}

		return rVal;
	}
}
