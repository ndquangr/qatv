/**
 * Class Name  : UserDetailsHelper.java
 * Description : 스프링 시큐리티에서 사용자 정보 획득
 * 
 * @author 원장희
 * @since 2011.11.29
 * @version 1.0
 */
package com.ndquangr.qatv.authen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ndquangr.qatv.common.util.ObjectUtil;

public class UserDetailsHelper {
	
	/**
	 * 인증된 사용자객체를 가져온다.
	 * @return Object - 사용자 MyUserDetails
	 */
	public static MyUserDetails getAuthenticatedUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		
		if (ObjectUtil.isNull(authentication)) {
			MyUserDetails detail = new MyUserDetails();
			return detail;
		}
		if(authentication.getPrincipal().equals("anonymousUser")){
			MyUserDetails detail = new MyUserDetails();
			return detail;
		}

		MyUserDetails details = (MyUserDetails) authentication.getPrincipal();
				
		// log.debug("## EgovUserDetailsHelper.getAuthenticatedUser : AuthenticatedUser is " + details.getUsername());
		return details;
	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 * 예) [ROLE_ADMIN, ROLE_USER, WRIT_ROLE, COMT_WRIT, ARTL_DEL, LIST_INQR, ANSR_WRIT, DETL_INQR, FILE_DWLD
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		List<String> listAuth = new ArrayList<String>();
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		
		if (ObjectUtil.isNull(authentication)) {
			// log.debug("## authentication object is null!!");
			return null;
		}
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator it = authorities.iterator();
		while(it.hasNext()){
			GrantedAuthority ga = (GrantedAuthority)it.next();
			listAuth.add(ga.getAuthority());
		}
//		for (int i = 0; i < authorities.size(); i++) {
//			authorities.iterator().
//			listAuth.add(authorities[i].getAuthority());
//
//			// log.debug("## EgovUserDetailsHelper.getAuthorities : Authority is " + authorities[i].getAuthority());
//		}

		return listAuth;
	}
	
	/**
	 * 인증된 사용자 여부를 체크한다.(로그인 했는지를 체크)
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)	
	 */
	public static Boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		
		if (ObjectUtil.isNull(authentication)) {
			// log.debug("## authentication object is null!!");
			return Boolean.FALSE;
		}
		
		String username = authentication.getName();
		if (username.equals("anonymousUser")) {
			// log.debug("## username is " + username);
			return Boolean.FALSE;
		}

		Object principal = authentication.getPrincipal();
		
		return Boolean.valueOf(!ObjectUtil.isNull(principal));
	}
	
	public static Map paramAuthorization(Map params) throws Exception{
		return null;
	}
	
	
	public static String getAuthLev(Map params) throws Exception{
		
		List<String> auths = getAuthorities();
		String authLev = "";
			
			for(int i=0; i<auths.size(); i++){
				String str = auths.get(i);
				if(str.equals("ROLE_ADMIN")){
					authLev = "ROLE_ADMIN";
				}else{
					
				}
			}
		
		return authLev;
	}


	public static boolean checkAdmin()throws Exception{
		boolean bAdmin = false;
		List auth = getAuthenticatedUser().getAuthorities();
		if(auth.contains("ROLE_ADMIN") || auth.contains("ROLE_DIST_ADMIN")){
			bAdmin = true;
		}
		
		return bAdmin;
	}
}
