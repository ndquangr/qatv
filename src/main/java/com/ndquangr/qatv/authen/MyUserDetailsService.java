/**
 * Class Name  : MyUserDetailsService.java
 * Description : 사용자 로그인 정보 에 사용자 권한을 부여
 * 
 * @author 원장희
 * @since 2011.11.29
 * @version 1.0
 */
package com.ndquangr.qatv.authen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ndquangr.qatv.module.role.roleGroupManageDAOImpl;
import com.ndquangr.qatv.module.user.memberManageDAO;
import com.ndquangr.qatv.module.user.memberManageDAOImpl;

@Component("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService, IUserAuthenticator{

	private static MyUserDetailsService instance = null;
	private static MyDao myDao = null;
	
	private memberManageDAO dao = memberManageDAOImpl.getInstance();

	private roleGroupManageDAOImpl roleDao = roleGroupManageDAOImpl.getInstance();
	
	private AuthenticationManager authenticateManager = null;	
	
	@Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		getInstace().authenticateManager = authenticationManager;		
    }
	
	public AuthenticationManager getAuthenticationManager(){
		return authenticateManager;
	}
	
	@Override
    public void login(String user_id, String userPassword){// 회원가입 후에 자동로그인 처리
    	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user_id, userPassword);
    	Authentication authentication = getAuthenticationManager().authenticate(authToken);
    	SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
	}
	
	public static MyUserDetailsService getInstace(){
		if(instance != null)
			return instance;
		else{
			return new MyUserDetailsService();
		}
	}
	
	public static MyDao getDao(){
		return myDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		MyUserDetails userDetails = null;
		try{
			Map paramMap = new HashMap();
			paramMap.put("user_id", username);
			List userList = dao.getUserDetails(paramMap);
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
			
			if(userList != null && userList.size() == 1){
				Map userMap = (Map)userList.get(0);
				String user_id = (String)userMap.get("USER_ID");
				String user_nm = (String)userMap.get("USER_NM");
				String password = (String)userMap.get("PWD");
				String email = (String)userMap.get("EML");
				String del_yn = (String)userMap.get("MEMB_DEL_YN");
				String err_cnt = (String)userMap.get("PWD_ERR_CNT");
				
				userDetails = new MyUserDetails(user_id, user_nm, password, email, del_yn, err_cnt);
				
				
				session.setAttribute("input_user_name", user_id);
				session.setAttribute("pwd_err_cnt", err_cnt);
				session.setAttribute("login_id_locked", false);
				 
	        }else{
	        	System.out.println("없는 사용자");
	        }

			if(userDetails != null){
				if(Integer.parseInt(userDetails.getErrCount()) < 5){
					List<GrantedAuthority> authorities = new ArrayList();
					List roleList = roleDao.getUserDetailRole(paramMap);
					for(int i=0; i<roleList.size(); i++){
						Map authMap = (Map)roleList.get(i);
						GrantedAuthority grantAuth = new SimpleGrantedAuthority((String)authMap.get("ROL_MNG_ID"));
						
						authorities.add(grantAuth);
					}
					
					userDetails.getAuthorities().addAll(authorities);
					
				}
				else{
					userDetails = null;
					session.setAttribute("login_id_locked", true);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	
		return userDetails;
	}

	public MyUserDetails loadUserByUsername(String tablename, String username)
			throws UsernameNotFoundException, DataAccessException {
		boolean entpFlag = true;
		if(tablename.equals("ebtn_entp_gnrl_stat") )	// 기업회원테이블이면 false로 변경
			entpFlag = false;
		MyUserDetails userDetails = (MyUserDetails)myDao.getUserDetails(tablename, username, entpFlag);
		Collection<GrantedAuthority> authorities = myDao.getAuthorities(username);
		userDetails.getAuthorities().addAll(authorities);
		
		List<GrantedAuthority> newAuthorityList = userDetails.getAuthorities();
        

		return userDetails;
	}

	public void setMyDao(MyDao myDao) {
		instance = this;
		this.myDao = myDao;
	}
}
