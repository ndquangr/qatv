package com.ndquangr.qatv.module.userrole;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ndquangr.qatv.dao.AbstractDAO;

/** 
 * 회원 조직도 정보 Data Access Object interface implement
 * @version 1.0
 * @author a2m
 */
@SuppressWarnings({"rawtypes"})
@Component("userRoleManageDAOImpl")
public class userRoleManageDAOImpl extends AbstractDAO implements userRoleManageDAO {
	
	private static final String PATH = "sysManage.userRoleManage.userRoleManageDAO.";
	
	/**
	 * 사용자별 권한정보 조회
	 * @return java.lang.String
	 * @throws Exception
	 */
	@Override
	public String getRoleAdminList() throws Exception {
		return (String)getObject(PATH + "getRoleAdminList");
	}
	
	/**
	 * 사용자별 권한정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	@Override
	public Object insert(Object parameter) throws Exception{
		return insert(PATH + "insert", parameter);
	}
	
	/**
	 * 사용자별 권한정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	@Override
	public Object update(Object parameter) throws Exception {
		return update(PATH + "update", parameter);
	}

	/**
	 * 사용자별 권한정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	@Override
	public Object delete(Object parameter) throws Exception {
		return delete(PATH + "delete", parameter);
	}

	/**
	 * CMS 신청관리, 게시글관리 권한 조회
	 * @param parameter parameter
	 * @return int
	 * @throws Exception
	 */
	@Override
	public Map getRolCnt(Map parameter) throws Exception {
		return (Map) getObject(PATH + "getRolCnt", parameter);
	}
	
}