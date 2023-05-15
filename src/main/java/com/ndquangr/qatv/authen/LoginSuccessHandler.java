/**
 * Class Name  : LoginSuccessHandler.java
 * Description : 로그인 후 페이지 이동 처리
 * 
 * @author 원장희
 * @since 2011.11.29
 * @version 1.0
 */
package com.ndquangr.qatv.authen;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.ndquangr.qatv.module.user.memberManageDAO;
import com.ndquangr.qatv.module.user.memberManageDAOImpl;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	//	public static String DEFAULT_TARGET_PARAMETER = "spring-security-redirect";
	//	private String targetUrlParameter = DEFAULT_TARGET_PARAMETER;
	//	AbstractAuthenticationTargetUrlRequestHandler aa;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private memberManageDAO dao = memberManageDAOImpl.getInstance();
	
	@Override
	protected String determineTargetUrl(HttpServletRequest request,	HttpServletResponse response){		
		String s = request.getParameter("referer");
		//	String targetUrl = request.getParameter(targetUrlParameter);
       
		//로그인이 되었으므로 로그정보에 넣기
    	String userID = UserDetailsHelper.getAuthenticatedUser().getUserID();
    	String errCnt = UserDetailsHelper.getAuthenticatedUser().getErrCount();
		
		try{
			if(Integer.parseInt(errCnt) > 0){
				Map loginInfo = new HashMap();
				loginInfo.put("user_id", userID);
				loginInfo.put("pwd_err_cnt", "0");
				
				dao.updatePwdErrCnt(loginInfo);
			}
		}catch(Exception ex){}

		return "/";
        /*if(s != null && !s.equals(""))
        	return s;
        else         
        	return super.determineTargetUrl(request, response);*/
        	
        
	}
}
