package com.ndquangr.qatv.module.qatv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ndquangr.qatv.common.util.CommonUtil;
import com.ndquangr.qatv.common.util.PageNavigator;
import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.common.util.ReqUtils;
import com.ndquangr.qatv.module.nom.NomManageDAOImpl;

@Controller("QATVController")
@RequestMapping("/staff/qatv")
public class QATVController {
	private static final String viewModulePathName = "sys:user/qatv/";
	@Autowired QATVManageDAOImpl dao;
	@Autowired NomManageDAOImpl nomdao;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		parameter.put("searchType", "level");
		parameter.put("searchTxt", "0");
		List list = dao.list("getListQATV", parameter);		
		if (list != null && list.size() > 0) {
			String hn, def;
			
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if (map.get("CHAR_HANNOM") != null) {
					hn = (String) map.get("CHAR_HANNOM");
					String hnr = changeNomCode(hn);
					map.put("CHAR_HANNOM", hnr);
				}
				
				if (map.get("CHAR_DEF") != null) {
					def = (String) map.get("CHAR_DEF");
					String defr = changeNomCode(def);
					map.put("CHAR_DEF", defr);
				}
			}
		}
		mav.setViewName(viewModulePathName + "qatvList");
		mav.addObject("DATA", list);				
		return mav;
*/
		ModelAndView mav = new ModelAndView();	
		Map parameter = ParameterUtil.getParameterMapWithOutSession(request);
		//parameter.put("searchType", "level");
		//parameter.put("searchTxt", "0");
		
		String type = (String) parameter.get("searchType");
		String cls = (String) parameter.get("cls");
		
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
		
		List list = dao.list("getListQATV", parameter);		
		if (list != null && list.size() > 0) {
			String hn, def;
			
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if (map.get("CHAR_HANNOM") != null) {
					hn = (String) map.get("CHAR_HANNOM");
					String hnr = CommonUtil.changeNomCode(hn, nomdao);
					map.put("CHAR_HANNOM", hnr);
				}
				
				if (map.get("CHAR_DEF") != null) {
					def = (String) map.get("CHAR_DEF");
					String defr = CommonUtil.changeNomCode(def, nomdao);
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
		
		mav.setViewName(viewModulePathName + "qatvList");
		return mav;
	}
	
	

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView writeNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);		
		ModelAndView mav = new ModelAndView();
		
		if(parameter.get("id") != null) {
			Map main = dao.map("getMap", parameter);
			mav.addObject("MAIN", main);	
			
			List sub = dao.list("getChildren", parameter);
			mav.addObject("SUB", sub);	
		}

		mav.setViewName(viewModulePathName + "qatvForm");
		return mav;

	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		dao.saveQatv(parameter);
		mav.setViewName("redirect:/staff/qatv/write");

		return mav;

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		if(parameter.get("id") != null) {
			dao.delete("deleteChildren", parameter);
			dao.delete("deleteQATV", parameter);
		}
		mav.setViewName("redirect:/staff/qatv/list");

		return mav;

	}
}
