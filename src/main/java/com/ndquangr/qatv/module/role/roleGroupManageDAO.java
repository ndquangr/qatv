package com.ndquangr.qatv.module.role;

import java.util.List;
import java.util.Map;

/** 
 * 권한정보 Data Access Object interface
 * @version 1.0
 * @author a2m
 */
@SuppressWarnings("rawtypes")
public interface roleGroupManageDAO {

	/**
	 * 권한 코드 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	List getListRoleCode(Object parameter) throws Exception;
	
	/**
	 * 권한정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object insertRole(Object parameter) throws Exception;
	
	/**
	 * 권한정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object updateRole(Object parameter) throws Exception;
	
	/**
	 * 권한정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	Object deleteRole(Object parameter) throws Exception;

	/**
	 * 사용자별 권한정보 조회
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	List getUserDetailRole(Object parameter) throws Exception;

	/**
	 * 권한코드 중복 조회
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	int getRolMngIdCnt(Map parameter) throws Exception;

}
