package com.ndquangr.qatv.module.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ndquangr.qatv.authen.UserDetailsHelper;
import com.ndquangr.qatv.common.util.CommonUtil;
import com.ndquangr.qatv.common.util.PageNavigator;
import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.common.util.ReqUtils;
import com.ndquangr.qatv.module.file.FileManageDAOImpl;
import com.ndquangr.qatv.module.nom.NomManageDAOImpl;
import com.ndquangr.qatv.module.qatv.QATVController;
import com.ndquangr.qatv.module.qatv.QATVManageDAOImpl;

@Controller("SearchController")
@RequestMapping("/guest")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	private static final String viewModulePathName = "sys:user/search/";
	private static final String FILE_PATH = "/qatv/";
	private static final String ALL_CHARACTER = "A,À,Á,Ả,Ã,Ạ,Ă,Ằ,Ắ,Ẳ,Ẵ,Ặ,Â,Ầ,Ấ,Ẩ,Ẫ,Ậ,B,C,D,Đ,E,È,É,Ẻ,Ẽ,Ẹ,Ê,Ề,Ế,Ể,Ễ,Ệ,G,H,I,Ì,Í,Ỉ,Ĩ,Ị,K,L,M,N,O,Ò,Ó,Ỏ,Õ,Ọ,Ô,Ồ,Ố,Ổ,Ỗ,Ộ,Ơ,Ờ,Ớ,Ở,Ỡ,Ợ,P,Q,R,S,T,U,Ù,Ú,Ủ,Ũ,Ụ,Ư,Ừ,Ứ,Ử,Ữ,Ự,V,X,Y,Ỳ,Ý,Ỷ,Ỹ,Ỵ";
	
	@Autowired SearchManageDAOImpl dao;
	//@Autowired QATVManageDAOImpl qatvdao;
	@Autowired NomManageDAOImpl nomdao;
	@Autowired FileManageDAOImpl filedao;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();	
		Map parameter = ParameterUtil.getParameterMapWithOutSession(request);
		
		String type = (String) parameter.get("searchType");
		String text = (String) parameter.get("searchTxt");
		
		if (text != null && !text.trim().equals("")) {		
			String cPage = ReqUtils.getEmptyResult((String)parameter.get("cPage"), "1");
			String listCnt = ReqUtils.getEmptyResult((String)parameter.get("listCnt"), "15");
			
			int intPage = Integer.parseInt(cPage);			// 현재페이지 
			int intListCnt = Integer.parseInt(listCnt);		 //세로페이징(게시글수)
			int pageCnt = 10;								// 가로페이징(페이지수) 
			int totalCnt = 0;								// 총 건수  
			
			totalCnt = dao.getListCnt(parameter);
			
			String[] strKeys={"searchTxt","searchType"};
			String paramStr = "";
			for(int i=0;i<strKeys.length;i++){
				paramStr+="&"+strKeys[i]+"="+ReqUtils.getEmptyResult((String)parameter.get(strKeys[i]), "");
			}
			
			PageNavigator pageNavigator = new PageNavigator(
					intPage		
				   ,request.getRequestURI()
				   ,pageCnt		
				   ,intListCnt	
				   ,totalCnt	
				   ,paramStr); // 검색 파라미터
			int totalPage = (totalCnt - 1 ) / intListCnt +1 ; // 총 페이지 수 
			
			// 시작 인덱스
			int startIndex = pageNavigator.getRecordPerPage() * (intPage - 1);
			parameter.put("start_idx", startIndex);
			parameter.put("end_idx", pageNavigator.getRecordPerPage());
			
			//List list = 	dao.list("getList", parameter);		
			
			List list = dao.list("getList", parameter);		
			if (list != null && list.size() > 0) {
				String hn, def;
				
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map.get("CHAR_HNF") != null) {
						hn = (String) map.get("CHAR_HNF");
						String hnr = CommonUtil.changeNomCode(hn, nomdao);
						map.put("CHAR_HNF", hnr);
					}
					
					if (map.get("CHAR_DEF") != null) {
						def = (String) map.get("CHAR_DEF");
						String defr = CommonUtil.changeNomCode(def, nomdao);
						if (defr != null) {
							defr = defr.replace("[","<i>").replace("]", "</i>");
						}
						map.put("CHAR_DEF", defr);
					}
				}
			}
			//List list = dao.getListRsrcFldPaging(parameter,startIndex, pageNavigator.getRecordPerPage());		
			
			Map referenceMap = new HashMap();
			referenceMap.put("reqURI", request.getRequestURI());
			referenceMap.put("DATA", list);
			referenceMap.put("intPage", intPage);
			referenceMap.put("intListCnt", intListCnt);
			referenceMap.put("totalCnt", totalCnt);
			referenceMap.put("paramStr", paramStr);
			referenceMap.put("params", parameter);
			referenceMap.put("pageNavigator", pageNavigator.getMakePage());		
			mav.addAllObjects(referenceMap);
		}
		mav.addObject("start_chars", ALL_CHARACTER);
		mav.setViewName(viewModulePathName + "searchHome");
		return mav;

	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView getDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);		
		ModelAndView mav = new ModelAndView();
		String def, hn;
		if(parameter.get("id") != null && parameter.get("lv") != null) {
			Map main = dao.map("getMap", parameter);
			if (main.get("CHAR_HNF") != null) {
				hn = (String) main.get("CHAR_HNF");
				String hnr = CommonUtil.changeNomCode(hn, nomdao);
				main.put("CHAR_HNF", hnr);
			}
			
			if (main.get("CHAR_DEF") != null) {
				def = (String) main.get("CHAR_DEF");
				String defr = CommonUtil.changeNomCode(def, nomdao);
				if (defr != null) {
					defr = defr.replace("[","<i>").replace("]", "</i>");
				}
				main.put("CHAR_DEF", defr);
			}
			
			mav.addObject("MAIN", main);
			String lv = (String) parameter.get("lv");
			if (lv.equals("0")) {
				List list = dao.list("getChildren", parameter);
				if (list != null && list.size() > 0) {					
					for (int i = 0; i < list.size(); i++) {
						Map map = (Map) list.get(i);
						if (map.get("CHAR_HNF") != null) {
							hn = (String) map.get("CHAR_HNF");
							String hnr = CommonUtil.changeNomCode(hn, nomdao);
							map.put("CHAR_HNF", hnr);
						}
						
						if (map.get("CHAR_DEF") != null) {
							def = (String) map.get("CHAR_DEF");
							String defr = CommonUtil.changeNomCode(def, nomdao);
							if (defr != null) {
								defr = defr.replace("[","<i>").replace("]", "</i>");
							}
							map.put("CHAR_DEF", defr);
						}
					}
				}
				mav.addObject("SUB", list);	
			}	
		}

		mav.setViewName("popup:user/search/" + "searchDetail");
		return mav;

	}
}
