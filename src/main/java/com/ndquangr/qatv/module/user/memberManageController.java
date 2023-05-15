package com.ndquangr.qatv.module.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ndquangr.qatv.authen.UserDetailsHelper;
import com.ndquangr.qatv.common.util.AriaUtil;
import com.ndquangr.qatv.common.util.PageNavigator;
import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.common.util.RedirectUtil;
import com.ndquangr.qatv.module.role.roleGroupManageDAO;
import com.ndquangr.qatv.module.userrole.userRoleManageDAO;

/**
 * 회원 관리 처리 정보 담당
 *
 * @author a2m
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Controller("memberManageController")
@RequestMapping("/sys/memberManage")
public class memberManageController {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	AriaUtil aria;

	private static final String viewModulePathName = "sys:sysManage/memberManage/";
	private static final String LIST_URL = "/sys/memberManage/members";
	@Resource(name = "memberManageDAOImpl")
	private memberManageDAO dao;

	@Resource(name = "roleGroupManageDAOImpl")
	private roleGroupManageDAO roleDao;

	@Resource(name = "userRoleManageDAOImpl")
	private userRoleManageDAO userRoleDao;
	

	/**
	 * 회원 관리 목록 조회
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/members", method = RequestMethod.GET)
	public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		String cPage = ParameterUtil.getEmptyResult2((String) parameter.get("cPage"), "1");
		String searchType = ParameterUtil.getEmptyResult2((String) parameter.get("searchType"), "user_nm");
		String searchTxt = ParameterUtil.getEmptyResult2((String) parameter.get("searchTxt"), "");
		String searchRol = ParameterUtil.getEmptyResult2((String) parameter.get("searchRol"), "");

		ModelAndView mav = new ModelAndView();

		mav = setCommonCode(mav);
		mav.setViewName(viewModulePathName + "memberManageList");

		int intPage = Integer.parseInt(cPage);			/* 현재페이지*/
		int intListCnt = 20;									/* 세로페이징(게시글수)*/
		int pageCnt = 10;									/* 가로페이징(페이지수) */
		int totalCnt = dao.getListCnt(parameter);

		String[] keyset = {"searchTxt", "searchType", "searchRol"};
		String serializedParams = "";
		for (String s : keyset) {
			if (parameter.get(s) != null)
				serializedParams += "&" + s + "=" + parameter.get(s);
		}

		PageNavigator pageNavigator = new PageNavigator(
				intPage
				, LIST_URL
				, pageCnt
				, intListCnt
				, totalCnt
				, serializedParams
		);

		parameter.put("start_idx", ((intPage - 1) * intListCnt));
		parameter.put("end_idx", intPage * intListCnt);
		List manageList = dao.getMemberList(parameter);
		List clientList = new ArrayList();

		for (int i = 0; i < manageList.size(); i++) {
			Map manager = (Map) manageList.get(i);

			String eml = ParameterUtil.getEmptyResult2((String) manager.get("EML"), "");
			if (!"".equals(eml)) {
				manager.put("EML", aria.Decrypt(eml));
			}
			String clpn1 = ParameterUtil.getEmptyResult2((String) manager.get("CLPN1"), "");
			if (!"".equals(clpn1)) {
				manager.put("CLPN1", aria.Decrypt(clpn1));
			}
			String clpn2 = ParameterUtil.getEmptyResult2((String) manager.get("CLPN2"), "");
			if (!"".equals(clpn2)) {
				manager.put("CLPN2", aria.Decrypt(clpn2));
			}
			String clpn3 = ParameterUtil.getEmptyResult2((String) manager.get("CLPN3"), "");
			if (!"".equals(clpn3)) {
				manager.put("CLPN3", aria.Decrypt(clpn3));
			}

			clientList.add(manager);
		}

		mav.addObject("searchType", searchType);
		mav.addObject("searchTxt", searchTxt);
		mav.addObject("searchRol", searchRol);
		mav.addObject("intListCnt", intListCnt);
		mav.addObject("cPage", cPage);
		mav.addObject("clientCnt", totalCnt);
		mav.addObject("clientList", clientList);
		mav.addObject("pageNavigator", pageNavigator.getMakePage());

		return mav;

	}

	/**
	 * 회원 관리 상세 페이지 이동
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/{user_id}", method = RequestMethod.GET)
	public ModelAndView selectView(@PathVariable String user_id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * 뷰페이지는 수정 페이지로 대체.
		 * Map parameter = ParameterUtil.getParameterMap(request);
		*/

		return null;
	}

	/**
	 * 회원 관리 입력 페이지 이동
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/write", method = RequestMethod.GET)
	public ModelAndView projectWrite(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav = setCommonCode(mav);

		mav.setViewName(viewModulePathName + "memberManageWrite");

		return mav;
	}

	/**
	 * 회원 관리 수정 페이지 이동
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param user_id  사용자 ID
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/{user_id:.*}/edit", method = RequestMethod.GET)
	public ModelAndView projectUpdate(HttpServletRequest request, HttpServletResponse response, @PathVariable String user_id) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);

		ModelAndView mav = new ModelAndView();
		mav = setCommonCode(mav);
		parameter.put("user_id", user_id);

		Map map = dao.getProfileObjectMap(parameter);

		//복호화 처리.
		String eml = ParameterUtil.getEmptyResult2((String) map.get("EML"), "");
		if (!"".equals(eml)) {
			map.put("EML", aria.Decrypt(eml));
		}
		String clpn1 = ParameterUtil.getEmptyResult2((String) map.get("CLPN1"), "");
		if (!"".equals(clpn1)) {
			map.put("CLPN1", aria.Decrypt(clpn1));
		}
		String clpn2 = ParameterUtil.getEmptyResult2((String) map.get("CLPN2"), "");
		if (!"".equals(clpn2)) {
			map.put("CLPN2", aria.Decrypt(clpn2));
		}
		String clpn3 = ParameterUtil.getEmptyResult2((String) map.get("CLPN3"), "");
		if (!"".equals(clpn3)) {
			map.put("CLPN3", aria.Decrypt(clpn3));
		}

		mav.setViewName(viewModulePathName + "memberManageEdit");

		if (map != null && !map.isEmpty()) {
			mav.addObject("user", map);
			mav.addObject("user_id", user_id);

			List authList = dao.getProfileAuthList(parameter);
			String rol_val = "";
			String rol_nm = "";

			for (int i = 0; i < authList.size(); i++) {
				if (i > 0) {
					rol_val += ",";
					rol_nm += ",";
				}

				Map authMap = (Map) authList.get(i);
				String rol_mng_id = ParameterUtil.getEmptyResult2((String) authMap.get("ROL_MNG_ID"), "");
				String rol_mng_nm = ParameterUtil.getEmptyResult2((String) authMap.get("ROL_MNG_NM"), "");
				rol_val += rol_mng_id;
				rol_nm += rol_mng_nm;
			}

			mav.addObject("rol_val", rol_val);
			mav.addObject("rol_nm", rol_nm);

			//System.out.println("user_id->" + user_id);
			parameter.put("del_yn", "N");
			parameter.put("chrg_user_id", user_id);

			// 게시판 관리 정보
			//List bbsList = boardDao.getUserBbsDefaultConfList(parameter);
			// 페이지 관리 정보
			//List pageList = pageManageDao.getList(parameter);

			//mav.addObject("bbsList", bbsList);
			//mav.addObject("pageList", pageList);

		} else {
			try {
				RedirectUtil.historyBack(response, "정보를 찾을 수 없습니다.", "Oops!!");
			} catch (Exception ex) {
			}
		}

		return mav;
	}

	/**
	 * 회원정보 삭제
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param user_id  사용자 ID
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/{user_id:.*}/delete", method = RequestMethod.POST)
	public ModelAndView projectDelete(HttpServletRequest request, HttpServletResponse response, @PathVariable String user_id) throws Exception {
		Map parameter = ParameterUtil.getParameterMap(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:" + LIST_URL);

		parameter.put("user_id", user_id);
		try {
			userRoleDao.delete(parameter);
			dao.deleteProfile(parameter);
		} catch (Exception e) {
			try {
				e.printStackTrace();
				RedirectUtil.historyBack(response, "작업 진행에 문제가 있습니다.", "Oops!!");
			} catch (Exception ex) {
			}
		}

		return mav;
	}

	/**
	 * 회원정보 등록
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public ModelAndView clientWriteSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		Map parameter = ParameterUtil.getParameterMap(request);

		String sms_recv_yn = ParameterUtil.getEmptyResult2((String) parameter.get("sms_recv_yn"), "N");
		parameter.put("sms_recv_yn", sms_recv_yn);

		ModelAndView mav = new ModelAndView();

		try {
			//암호화 처리.
			String eml = ParameterUtil.getEmptyResult2((String) parameter.get("eml"), "");
			if (!"".equals(eml)) {
				parameter.put("eml", aria.Encrypt(eml));
			}
			String clpn1 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn1"), "");
			if (!"".equals(clpn1)) {
				parameter.put("clpn1", aria.Encrypt(clpn1));
			}
			String clpn2 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn2"), "");
			if (!"".equals(clpn2)) {
				parameter.put("clpn2", aria.Encrypt(clpn2));
			}
			String clpn3 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn3"), "");
			if (!"".equals(clpn3)) {
				parameter.put("clpn3", aria.Encrypt(clpn3));
			}

			dao.insert(parameter);
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

	/**
	 * 신규 회원 등록
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/new", method = RequestMethod.POST)
	public ModelAndView projectWrite_submit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List mngIdList = UserDetailsHelper.getAuthenticatedUser().getAuthorities();
		String rgst_user_id = UserDetailsHelper.getAuthenticatedUser().getUserID();

		Map parameter = ParameterUtil.getParameterMap(request);
		String[] rol_mng_id = request.getParameterValues("rol_mng_id"); //사용자 권한
		String[] org_cd = request.getParameterValues("org_cd"); //소속부서
		String user_id = ParameterUtil.getEmptyResult2((String) parameter.get("user_id"), "");
		String pwd = ParameterUtil.getEmptyResult2((String) parameter.get("pwd"), "");
		String eml = ParameterUtil.getEmptyResult2((String) parameter.get("eml"), "");

		try {
			parameter.put("pwd", passwordEncoder.encode(pwd));

			//암호화 처리.
			if (!"".equals(eml)) {
				parameter.put("eml", aria.Encrypt(eml));
			}
			String clpn1 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn1"), "");
			if (!"".equals(clpn1)) {
				parameter.put("clpn1", aria.Encrypt(clpn1));
			}
			String clpn2 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn2"), "");
			if (!"".equals(clpn2)) {
				parameter.put("clpn2", aria.Encrypt(clpn2));
			}
			String clpn3 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn3"), "");
			if (!"".equals(clpn3)) {
				parameter.put("clpn3", aria.Encrypt(clpn3));
			}

			dao.insert(parameter);

			if (rol_mng_id != null && rol_mng_id.length > 0) {
				for (int i = 0; i < rol_mng_id.length; i++) {
					String role = rol_mng_id[i];
					parameter.put("rol_mng_id", role);
					userRoleDao.insert(parameter);
				}
			}

		} catch (Exception e) {
			try {
				e.printStackTrace();
				RedirectUtil.historyBack(response, "작업 진행에 문제가 있습니다.", "Oops!!");
			} catch (Exception ex) {
			}
		}
		mav.setViewName("redirect:/sys/memberManage/member/" + user_id + "/edit");
		return mav;
	}

	/**
	 * 회원정보 수정
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param user_id  사용자 ID
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/{user_id:.*}", method = RequestMethod.POST)
	public ModelAndView projectEdit_submit(HttpServletRequest request, HttpServletResponse response, String mode, @PathVariable String user_id) throws Exception {
		ModelAndView mav = new ModelAndView();
		List mngIdList = UserDetailsHelper.getAuthenticatedUser().getAuthorities();
		String rgst_user_id = UserDetailsHelper.getAuthenticatedUser().getUserID();

		Map parameter = ParameterUtil.getParameterMap(request);
		parameter.put("user_id", user_id);

		String[] rol_mng_id = request.getParameterValues("rol_mng_id"); //사용자 권한

		try {

			//권한 확인 - 관리자인경우에 조직도 정보등록 가능
			String org_yn = "N";
			if (mngIdList != null) {
				for (int i = 0; i < mngIdList.size(); i++) {
					String role = String.valueOf(mngIdList.get(i));
					if (role.equals("ROLE_ADMIN")) {
						org_yn = "Y";
						break;
					}
				}
			}

			if (org_yn.equals("Y")) {
				userRoleDao.delete(parameter);

				if (rol_mng_id != null && rol_mng_id.length > 0) {
					for (int i = 0; i < rol_mng_id.length; i++) {
						String role = rol_mng_id[i];
						parameter.put("rol_mng_id", role);
						userRoleDao.insert(parameter);
					}
				}
			}

			String pwd = ParameterUtil.getEmptyResult2((String) parameter.get("pwd"), "");
			if (org.springframework.util.StringUtils.hasText(pwd)) {
				String str = passwordEncoder.encode(pwd);
				parameter.put("pwd", str);
			}

			//암호화 처리.
			String eml = ParameterUtil.getEmptyResult2((String) parameter.get("eml"), "");
			if (!"".equals(eml)) {
				parameter.put("eml", aria.Encrypt(eml));
			}
			String clpn1 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn1"), "");
			if (!"".equals(clpn1)) {
				parameter.put("clpn1", aria.Encrypt(clpn1));
			}
			String clpn2 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn2"), "");
			if (!"".equals(clpn2)) {
				parameter.put("clpn2", aria.Encrypt(clpn2));
			}
			String clpn3 = ParameterUtil.getEmptyResult2((String) parameter.get("clpn3"), "");
			if (!"".equals(clpn3)) {
				parameter.put("clpn3", aria.Encrypt(clpn3));
			}

			dao.updateProfile(parameter);
		} catch (Exception e) {
			try {
				e.printStackTrace();
				RedirectUtil.historyBack(response, "작업 진행에 문제가 있습니다.", "Oops!!");
			} catch (Exception ex) {
			}
		}
		mav.setViewName("redirect:/sys/memberManage/member/" + user_id + "/edit");
		return mav;
	}

	private ModelAndView setCommonCode(ModelAndView mav) {
		try {
//			Map codeParams = new HashMap();
//			codeParams.put("ref_cd_no", CodeValueUtil.CODE_REGION);
//			List regionList = codeDao.getListSubCode(codeParams);
//
//			codeParams.put("ref_cd_no", CodeValueUtil.CODE_PSTN);
//			List pstnList = codeDao.getListSubCode(codeParams);
//
//			codeParams.put("ref_cd_no", CodeValueUtil.CODE_DUTY);
//			List dutyList = codeDao.getListSubCode(codeParams);
//
//			codeParams.put("ref_cd_no", CodeValueUtil.CODE_FIELD);
//			List fieldList = codeDao.getListSubCode(codeParams);

			List roleList = roleDao.getListRoleCode(null);

//			mav.addObject("regionList", regionList);
//			mav.addObject("pstnList", pstnList);
//			mav.addObject("dutyList", dutyList);
//			mav.addObject("fieldList", fieldList);
			mav.addObject("roleList", roleList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	/**
	 * 담당자 찾기 팝업 호출
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/find", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView findMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Map parameter = ParameterUtil.getParameterMap(request);
		String cPage = ParameterUtil.getEmptyResult2((String) parameter.get("cPage"), "1");
		String searchTxt = ParameterUtil.getEmptyResult2((String) parameter.get("searchTxt"), "");
		parameter.put("searchType", "user_nm");
		String userIdForm = ParameterUtil.getEmptyResult2((String) parameter.get("userIdForm"), "");
		String userNameForm = ParameterUtil.getEmptyResult2((String) parameter.get("userNameForm"), "");

		if (!searchTxt.equals("")) {

			int intPage = Integer.parseInt(cPage);			/* 현재페이지*/
			int intListCnt = 10;									/* 세로페이징(게시글수)*/
			int pageCnt = 10;									/* 가로페이징(페이지수) */
			int totalCnt = dao.getListCnt(parameter);

			String[] keyset = {"searchTxt", "searchInstType"};
			String serializedParams = "";
			for (String s : keyset) {
				if (parameter.get(s) != null)
					serializedParams += "&" + s + "=" + parameter.get(s);
			}

			PageNavigator pageNavigator = new PageNavigator(
					intPage
					, LIST_URL
					, pageCnt
					, intListCnt
					, totalCnt
					, serializedParams
			);
			parameter.put("start_idx", ((intPage - 1) * intListCnt) + 1);
			parameter.put("end_idx", intPage * intListCnt);
			List userList = dao.getMemberList(parameter);

			mav.addObject("searchTxt", searchTxt);
			mav.addObject("intListCnt", intListCnt);
			mav.addObject("cPage", cPage);
			mav.addObject("clientCnt", totalCnt);
			mav.addObject("userList", userList);
			mav.addObject("userIdForm", userIdForm);
			mav.addObject("userNameForm", userNameForm);
			mav.addObject("pageNavigator", pageNavigator.getMakePage());

		} else {

			List userList = dao.getMemberFindList(parameter);
			mav.addObject("userIdForm", userIdForm);
			mav.addObject("userNameForm", userNameForm);
			mav.addObject("userList", userList);
		}

		mav.setViewName(viewModulePathName + "findMemberList");
		return mav;
	}

	/**
	 * 담당자 삭제 팝업 호출
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/member/unchargePop", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView memberUnchargePop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		Map parameter = ParameterUtil.getParameterMap(request);

		String chrg_user_id = ParameterUtil.getEmptyResult2((String) parameter.get("chrg_user_id"), "");
		String userIdForm = ParameterUtil.getEmptyResult2((String) parameter.get("userIdForm"), "");
		String userNameForm = ParameterUtil.getEmptyResult2((String) parameter.get("userNameForm"), "");

		if (!StringUtils.isEmpty(chrg_user_id)) {
			String[] chrg_user_id_arr = chrg_user_id.split(",");

			parameter.put("chrg_user_id", chrg_user_id_arr);

			List userList = dao.getChargeMemberList(parameter);

			mav.addObject("userList", userList);
		}

		mav.addObject("userIdForm", userIdForm);
		mav.addObject("userNameForm", userNameForm);
		mav.setViewName(viewModulePathName + "memberUnchargePop");

		return mav;
	}

}