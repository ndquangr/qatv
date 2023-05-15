/**
 * Class Name  : MyUserDetails.java
 * Description : 사용자 로그인 정보
 * 
 * @author 원장희
 * @since 2011.11.29
 * @version 1.0
 */
package com.ndquangr.qatv.authen;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class MyUserDetails implements UserDetails{
	private String userID;
	private String userPwd;
	private String userName;
	private String userEmail;
	private String userDelYn;
	private String userDvsn;
	private String userEncpPwd;
	private String userTel;
	private String userClpn;
	private String memberYn;
	private String userSeq;
	private String errCount;
	
	private boolean isEnabled = true;
	private boolean isAccountNonExpired = true;
	private boolean isAccountNonLocked = true;
	private boolean isCredentialsNonExpired = true;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	 
	public MyUserDetails(){
		this.userID = "";
		this.userName = "";
		this.userPwd = "";
		this.userEmail = "";
		this.userDelYn = "";
		this.userDvsn = "";
		this.userEncpPwd = "";
		this.userTel="";
		this.userClpn="";
		this.memberYn="";
		this.errCount = "0";
	}

	public MyUserDetails(String userID, String userName, String userPwd, String userEmail, String del_yn, String err_cnt) {
		this.userID = userID;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userEmail = userEmail;
		this.userDelYn = del_yn;
		this.errCount = err_cnt;
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userPwd;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public String getUserID() {
		return userID;
	}

	public String getErrCount() {
		// TODO Auto-generated method stub
		return errCount;
	}

	public Object getUserEmail() {
		// TODO Auto-generated method stub
		return userEmail;
	}
}
