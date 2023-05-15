package com.ndquangr.qatv.module.role;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.common.util.RedirectUtil;

/**
 * 시스템 이용자 권한 그룹 관리 처리 담당
 *
 * @author a2m
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller("roleGroupManageController")
@RequestMapping("/sys/config")
public class roleGroupManageController {

	private static final String viewModulePathName = "/sysManage/roleManage/";
	private static final String LIST_URL = "/sys/config/roles";

	@Resource(name = "roleGroupManageDAOImpl")
	private roleGroupManageDAO dao;

	/**
	 * 권한정보 조회
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/roles")
	public ModelAndView doList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * Parameter
		 */
		Map parameter = ParameterUtil.getParameterMap(request);
		/**
		 * Business Logic
		 */
		List list = dao.getListRoleCode(parameter);
		/**
		 * ModelAndView
		 */
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewModulePathName + "roleManageList");
		mav.addObject("list", list);
		return mav;
	}


	/**
	 * 권한정보 삭제
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param rol_mng_id 권한관리 ID
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	/* 사용안함(2016.04.26)
	@RequestMapping(value="/role/{rol_mng_id}", method=RequestMethod.DELETE)
	public ModelAndView  projectDelete(HttpServletRequest request,   
            HttpServletResponse response, @PathVariable String rol_mng_id) throws Exception {
		
		Map parameter = ParameterUtil.getParameterMap(request);
		rol_mng_id = LucyXssFilter.getinstance().checkFilter(rol_mng_id);
		parameter.put("rol_mng_id", rol_mng_id);
		
		ModelAndView mav = new ModelAndView();

		try{
			dao.deleteRole(parameter);
			mav.setViewName("redirect:" + LIST_URL);
		}catch(Exception ex){
			ex.getStackTrace();
			RedirectUtil.historyBack( response, "정보를 찾을 수 없습니다.","Oops!!");
		}
		
		return mav;
	}
	*/

	/**
	 * 권한코드 중복 조회
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/role/checkRolMngId", method = RequestMethod.GET)
	@ResponseBody
	public boolean checkRolMngId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Return Value.
		boolean result = true;

		Map parameter = ParameterUtil.getParameterMap(request);

		int duplCnt = dao.getRolMngIdCnt(parameter);

		// 이미 등록되어 있는 경우
		if (duplCnt > 0) {
			result = false;
		}

		return result;
	}

	/**
	 * 권한정보 등록
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	public ModelAndView doWrite_submit(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * Parameter
		 */
		Map parameter = ParameterUtil.getParameterMap(request);
		/**
		 * Business Logic
		 */
//		List list = dao.getListRoleCode(parameter);
		dao.insertRole(parameter);
		/**
		 * ModelAndView
		 */
		//String viewModulePathName = "/sysManage/roleManage/";
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:" + LIST_URL);
		return mav;
	}

	/**
	 * 권한정보 수정
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/role/{rol_mng_id}/update", method = RequestMethod.POST)
	public ModelAndView projectWrite_submit(HttpServletRequest request, HttpServletResponse response, @PathVariable String rol_mng_id) throws Exception {

		Map parameter = ParameterUtil.getParameterMap(request);
		parameter.put("rol_mng_id", rol_mng_id);
		ModelAndView mav = new ModelAndView();

		try {
			dao.updateRole(parameter);
			mav.setViewName("redirect:" + LIST_URL);
		} catch (Exception e) {
			try {
				e.printStackTrace();
				RedirectUtil.historyBack(response, "작업 진행에 문제가 있습니다.", "Oops!!");
			} catch (Exception ex) {
			}
		}

		return mav;
	}

}