/**
 * Class Name  : MyDao.java
 * Description : 사용자 입력정보(아이디/패스워드)를 통한 사용자 정보를 DB에서 로딩
 * 
 * @author 원장희
 * @since 2011.11.29
 * @version 1.0
 */
package com.ndquangr.qatv.authen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ndquangr.qatv.common.util.ParameterUtil;
import com.ndquangr.qatv.module.user.memberManageDAO;

@SuppressWarnings("deprecation")
public class MyDao extends JdbcDaoSupport{

	@Resource(name="memberManageDAOImpl")
	private memberManageDAO dao;

//Password Null 일 경우 회원가입이 안된 상태임
	public static final String DEF_USERS_BY_USERNAME_QUERY =
        "SELECT USER_ID, USER_NM, PWD1 AS PWD, EML, MEMB_DEL_YN " +//, PWD AS ENCP_PWD " +
        "FROM CMTN_USER_INFO " +
        "WHERE PWD IS NOT NULL AND USER_ID = ?";
//      "SELECT USER_ID, USER_NM, FC_GET_DECRYPT(PWD) AS PWD, FC_GET_DECRYPT(EML) AS EML, MEMB_DEL_YN, USER_DVSN_CD, USER_ID_ENCP, PWD AS ENCP_PWD, FC_GET_DECRYPT(TEL) TEL, FC_GET_DECRYPT(CLPN) CLPN, MEMBER_YN " +
//    "FROM CMTN_USER_INFO " +
//    "WHERE PWD IS NOT NULL AND USER_ID = ?";

    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
        "SELECT USER_ID,ROL_MNG_ID " +
        "FROM CMTN_USER_ROL " +
        "WHERE USER_ID = ?";

    public static final String DEF_BUSINESSFUNCTION_BY_AUTHORITIES_QUERY =
    	"SELECT ROL_MNG_ID, MENU_ID "+
    	"FROM CMTN_MENU_ROL "+
    	"WHERE ROL_MNG_ID = ?";

	public UserDetails getUserDetails(String username) {
		//System.out.println("username :  "+ username);
		Map map = new HashMap();
		map.put("user_id", username);

		try {
			List usrList = dao.getUserDetails(map);

		} catch(Exception e) {
			e.printStackTrace();
		}

		//List<UserDetails> userDetailsList = getSqlSession().selectList(DEF_AUTHORITIES_BY_USERNAME_QUERY);
        List<UserDetails> userDetailsList = getJdbcTemplate().query(DEF_USERS_BY_USERNAME_QUERY, new String[] {username}, new RowMapper<UserDetails>() {
        	public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        		String userid = rs.getString(1);
        		String username = rs.getString(2);
        		String password = rs.getString(3);
        		String email = ParameterUtil.getEmptyResult2(rs.getString(4),"");
        		String del_yn = ParameterUtil.getEmptyResult2(rs.getString(5),"");
        		//String encpPwd = rs.getString(6);
        		//System.out.println("load session :  ");
        		//로그정보 넣기
        		getJdbcTemplate().update("INSERT INTO CMTN_ACCS_LOG (ACCS_SEQ, USER_ID, ACCS_URL_PATH, ACCS_CLSF_CD, ACCS_FUNC, ACCS_DTTM)" +
        				" VALUES " +
        				"((SELECT 'SYSLG' || TO_CHAR(SYSDATE,'YYYYMMDDHHMISS') || TRIM(TO_CHAR(NVL(SUBSTR(MAX(ACCS_SEQ),20,4),0)+1,'0000')) FROM CMTN_ACCS_LOG WHERE ACCS_SEQ LIKE  'SYSLG' || TO_CHAR(SYSDATE,'YYYYMMDDHHMISS') || '%'), ?, ?, ?, ?, SYSDATE)",
        				new Object[]{userid, "/pageAuth", "LOGIN", "L"},
        				new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        		//System.out.println("log input  ");
        		return new MyUserDetails(userid, username, password, email, del_yn, "");
        	}
        });

        if ( userDetailsList.size() == 1 ) {
        	return userDetailsList.get(0);
        } else {
        	return null;
        }
	}

	public UserDetails getUserDetails(String tablename, String username, boolean flag) {
		String DEF_USERS_BY_USERNAME_QUERY2 = "";

		if ( flag ) {
			DEF_USERS_BY_USERNAME_QUERY2 = "SELECT USER_ID, USER_NM, FC_GET_DECRYPT(PWD) AS PWD, EML, MEMB_DEL_YN " +
		        "FROM "+tablename+" "+
		        "WHERE USER_ID = ?";
		} else {
			DEF_USERS_BY_USERNAME_QUERY2 = "SELECT USER_ID, ENTP_NM_KOR, FC_GET_DECRYPT(PWD) AS PWD, FC_GET_DECRYPT(CHRG_EML) AS CHRG_EML, MEMB_DEL_YN " +
	        "FROM "+tablename+" "+
	        "WHERE USER_ID = ?";
		}

        List<UserDetails> userDetailsList = getJdbcTemplate().query(DEF_USERS_BY_USERNAME_QUERY2, new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String userid = rs.getString(1);
            	String username = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                String del_yn = rs.getString(5);
                return new MyUserDetails(userid, username, password, email, del_yn,"");
            }
        });

        if ( userDetailsList.size() == 1 ) {
        	return userDetailsList.get(0);
        } else {
        	return null;
        }
	}

	public Collection<GrantedAuthority> getAuthorities(String username) {
        return getJdbcTemplate().query(DEF_AUTHORITIES_BY_USERNAME_QUERY, new String[] {username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }
        });
	}

	/*public List<BusinessFunctionGrantedAuthority> getBusinessFunction(String role){
		return getJdbcTemplate().query(DEF_BUSINESSFUNCTION_BY_AUTHORITIES_QUERY, new String[] {role}, new RowMapper<BusinessFunctionGrantedAuthority>() {
            public BusinessFunctionGrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String func = rs.getString(2);
                return new BusinessFunctionGrantedAuthority(func);
            }
        });
	}*/

}