package com.ndquangr.qatv.module.role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ndquangr.qatv.dao.AbstractDAO;


/** 
 * 권한정보 Data Access Object interface implement
 * @version 1.0
 * @author a2m
 */
@SuppressWarnings("rawtypes")
@Component("roleGroupManageDAOImpl")
public class roleGroupManageDAOImpl extends AbstractDAO implements roleGroupManageDAO {

	private static final String PATH = "sysManage.roleManage.roleManageDAO.";

	private static roleGroupManageDAOImpl instance = null;

	public roleGroupManageDAOImpl() {
		if ( instance == null ) {
			instance = this;
		}
	}

	public static roleGroupManageDAOImpl getInstance() {
		if ( instance == null ) {
			instance = new roleGroupManageDAOImpl();
		}

		return instance;
	}

	@Override
	@Autowired
	public void setSqlSessionFactory(@Qualifier("defaultDS") SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		SqlSession sqlSession = getSqlSession();
		instance.setSqlSessionTemplate((SqlSessionTemplate) sqlSession);
	}

	/**
	 * 권한 코드 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	public List getListRoleCode(Object parameter) throws Exception {
		return getList(PATH + "getListRoleCode", parameter);
	}

	/**
	 * 권한정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object insertRole(Object parameter) throws Exception{
		return getList(PATH + "insertRole", parameter);
	}

	/**
	 * 권한정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object updateRole(Object parameter) throws Exception{
		return getList(PATH + "updateRole", parameter);
	}

	/**
	 * 권한정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object deleteRole(Object parameter) throws Exception{
		return getList(PATH + "deleteRole", parameter);
	}

	/**
	 * 사용자별 권한정보 조회
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public List getUserDetailRole(Object parameter) throws Exception{
		return getList(PATH + "getUserDetailRole", parameter);
	}

	/**
	 * 권한코드 중복 조회
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public int getRolMngIdCnt(Map parameter) throws Exception {
		return (Integer)getObject(PATH + "getRolMngIdCnt", parameter);
	}

}