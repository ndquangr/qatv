package com.ndquangr.qatv.module.user;

import java.util.List;
import java.util.Map;

/** 
 * 회원정보 Data Access Object interface
 * @version 1.0
 * @author a2m
 */
public interface memberManageDAO {
	
	/**
	 * 사용자 ID 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	Map getUserId(Object parameter) throws Exception;
	
	/**
	 * 회원수 조회
	 * @param parameter parameter
	 * @return int
	 * @throws Exception
	 */
	int getListCnt(Object parameter) throws Exception;

	/**
	 * 회원정보 목록 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	List getMemberList(Object parameter) throws Exception;
	
	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	Map getMember(Object parameter) throws Exception;
	
	/**
	 * 회원정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object insert(Object parameter) throws Exception;
	
	/**
	 * 회원정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object update(Object parameter) throws Exception;

	/**
	 * 회원정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object delete(Object parameter) throws Exception;
	
	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	Map getProfileObjectMap(Object parameter) throws Exception;

	/**
	 * 회원정보 권한 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	public List getProfileAuthList(Object parameter) throws Exception; 
	
	/**
	 * 회원정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object updateProfile(Object parameter) throws Exception;
	
	/**
	 * 회원정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object deleteProfile(Object parameter) throws Exception;

	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	List getUserDetails(Object parameter) throws Exception;

	/**
	 * 회원정보 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	public List getChargeMemberList(Object parameter) throws Exception;
	
	/**
	 * 회원정보 성명 검색
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	List getMemberFindList(Object parameter) throws Exception;

	Object updatePwdErrCnt(Object parameter) throws Exception;

	Object updatePwdErrCntUp(Object parameter) throws Exception;

}
