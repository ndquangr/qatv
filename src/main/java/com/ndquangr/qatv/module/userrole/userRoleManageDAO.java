package com.ndquangr.qatv.module.userrole;

import java.util.Map;

/** 
 * 회원 조직도 정보 Data Access Object interface
 * @version 1.0
 * @author a2m
 */
@SuppressWarnings({"rawtypes"})
public interface userRoleManageDAO {
	
	/**
	 * 사용자별 권한정보 조회
	 * @return java.lang.String
	 * @throws Exception
	 */
	String getRoleAdminList() throws Exception;
	
	/**
	 * 사용자별 권한정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object insert(Object parameter) throws Exception;

	/**
	 * 사용자별 권한정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object update(Object parameter) throws Exception;

	/**
	 * 사용자별 권한정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object delete(Object parameter) throws Exception;

	/**
	 * CMS 신청관리, 게시글관리 권한 조회
	 * @param parameter parameter
	 * @return int
	 * @throws Exception
	 */
	Map getRolCnt(Map parameter) throws Exception;

}
