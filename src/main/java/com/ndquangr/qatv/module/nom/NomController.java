package com.ndquangr.qatv.module.nom;

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
import com.ndquangr.qatv.common.util.PageNavigator;
import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.common.util.ReqUtils;
import com.ndquangr.qatv.module.file.FileManageDAOImpl;

@Controller("NomController")
@RequestMapping("/staff/nom")
public class NomController {
	private static final Logger logger = LoggerFactory.getLogger(NomController.class);
	private static final String viewModulePathName = "sys:user/nom/";
	private static final String FILE_PATH = "/qatv/";
	
	@Autowired NomManageDAOImpl dao;
	@Autowired FileManageDAOImpl filedao;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();		
		List list = dao.list("getList", parameter);		
		mav.setViewName(viewModulePathName + "nomList");
		mav.addObject("DATA", list);	*/
		
		ModelAndView mav = new ModelAndView();	
		Map parameter = ParameterUtil.getParameterMapWithOutSession(request);
		
		String type = (String) parameter.get("searchType");
		String cls = (String) parameter.get("cls");
		
		String cPage = ReqUtils.getEmptyResult((String)parameter.get("cPage"), "1");
		String listCnt = ReqUtils.getEmptyResult((String)parameter.get("listCnt"), "10");
		
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
		List list = 	dao.list("getList", parameter);		
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
		
		mav.setViewName(viewModulePathName + "nomList");
		return mav;

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView writeNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);		
		ModelAndView mav = new ModelAndView();
		
		if(parameter.get("id") != null) {
			Map main = dao.map("getMap", parameter);
			mav.addObject("DATA", main);				
		}
		mav.setViewName("jsonView");
		return mav;

	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView createNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		//dao.save(parameter);
		String path = request.getSession().getServletContext().getRealPath(FILE_PATH);
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
        }
		String userID = UserDetailsHelper.getAuthenticatedUser().getUserID();
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

        Iterator<String> iterator = multiRequest.getFileNames();
        MultipartFile file;
        Map fileMap = new HashMap();
        if (iterator.hasNext()) {
            {
            	file = multiRequest.getFile(iterator.next());
            	if (file.getSize() != 0 && !file.getOriginalFilename().equals("")) {
            		String filename = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + ".jpg";                
                    File f = new File(path + "/" + filename);
                    
                    logger.debug("file.getOriginalFilename()::" + file.getOriginalFilename());
                    try {
                        OutputStream os = new FileOutputStream(f);
                        InputStream is = file.getInputStream();
                        byte[] bytes = new byte[2048];
                        int read;
                        while ((read = is.read(bytes)) != -1)
                            os.write(bytes, 0, read);
                        os.close();
                        fileMap.put("FILE_ORG_NAME", file.getOriginalFilename());
                        fileMap.put("FILE_NAME", filename);
                        fileMap.put("FILE_PATH", path);
                        fileMap.put("USER_ID", userID);
                        filedao.insert(fileMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            	}
                
            }
        }
        
        Map form = (Map) parameter.get("MAIN");
        form.put("USER_ID", userID);
        if (fileMap.containsKey("FILE_ID")) {
        	form.put("FILE_ID", fileMap.get("FILE_ID"));
        }

        if (form.get("NOM_ID") == null || form.get("NOM_ID").equals("")) {
            dao.insert(form);
            Map m = new HashMap();
            m.put("NOM_ID", form.get("NOM_ID"));
            dao.update(m);
        } else {
            dao.update(form);
        }
		mav.setViewName("redirect:/staff/nom/list");
		return mav;

	}
	
	/**
	 * 과힉기술분야 팝업
	 * @작성일    : 2016. 8. 03. 
	 * @작성자      : "jinyb"
	 * @프로그램설명 :
	 * @진행상태: TO-DO, DEBUG, TEST, COMPLETE  
	 */
	@RequestMapping("/popup")
	public ModelAndView doRsrcFldPopup(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();	
		Map parameter = ParameterUtil.getParameterMapWithOutSession(request);
		
		String type = (String) parameter.get("searchType");
		String cls = (String) parameter.get("cls");
		
		String cPage = ReqUtils.getEmptyResult((String)parameter.get("cPage"), "1");
		String listCnt = ReqUtils.getEmptyResult((String)parameter.get("listCnt"), "10");
		
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
		List list = 	dao.list("getList", parameter);		
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
		
		mav.setViewName("popup:user/nom/" + "popupNom");
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		if(parameter.get("id") != null) {
			dao.delete(parameter);
		}
		mav.setViewName("redirect:/staff/nom/list");

		return mav;

	}
}
