package com.ndquangr.qatv.module.user;

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
 * 회원정보 Data Access Object interface implement
 * @version 1.0
 * @author a2m
 */
@Component("memberManageDAOImpl")
public class memberManageDAOImpl extends AbstractDAO implements memberManageDAO {
	
	private static memberManageDAOImpl instance = null;
	
	public static memberManageDAOImpl getInstance(){
		if(instance == null){
			instance = new memberManageDAOImpl();
		}
		return instance;
	}
	
	public memberManageDAOImpl(){
		if(instance == null)
			instance = this;
	}
	
	@Override
	@Autowired
	public void setSqlSessionFactory(@Qualifier("defaultDS") SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		SqlSession sqlSession = getSqlSession();
		instance.setSqlSessionTemplate((SqlSessionTemplate)sqlSession);
	}
	
	private static final String PATH = "sysManage.memberManage.memberManageDAO.";
	
	/**
	 * 사용자 ID 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */	
	public Map getUserId(Object parameter) throws Exception{
		return (Map)getObject(PATH + "getUserId", parameter);
	}
	
	/**
	 * 회원수 조회
	 * @param parameter parameter
	 * @return int
	 * @throws Exception
	 */
	public int getListCnt(Object parameter) throws Exception {
		return (Integer)getObject(PATH + "getListCnt", parameter);
	}
	
	/**
	 * 회원정보 목록 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	public List getMemberList(Object parameter) throws Exception {
		return getList(PATH + "getMemberList", parameter);
	}
	
	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	public Map getMember(Object parameter) throws Exception {
		return (Map)getObject(PATH + "getMember", parameter);
	}

	/**
	 * 회원정보 등록
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object insert(Object parameter) throws Exception{
		return insert(PATH + "insert", parameter);
	}
	
	/**
	 * 회원정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object update(Object parameter) throws Exception{
		return update(PATH + "update", parameter);
	}
	
	/**
	 * 회원정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	public Object delete(Object parameter) throws Exception{
		return update(PATH + "delete", parameter);
	}
	
	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	@Override
	//@cacheble(cachename="userProfile")
	public Map getProfileObjectMap(Object parameter) throws Exception {
		return (Map)getObject(PATH + "getProfileObjectMap", parameter);
	}

	/**
	 * 회원정보 권한 조회
	 * @param parameter parameter
	 * @return java.util.Map
	 * @throws Exception
	 */
	@Override
	public List getProfileAuthList(Object parameter) throws Exception {
		return getList(PATH + "getProfileAuthList", parameter);
	}

	/**
	 * 회원정보 수정
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	@Override
	public Object updateProfile(Object parameter) throws Exception{
		return update(PATH + "updateProfile", parameter);
	}

	/**
	 * 회원정보 삭제
	 * @param parameter parameter
	 * @return java.lang.Object
	 * @throws Exception
	 */
	@Override
	public Object deleteProfile(Object parameter) throws Exception{
		return delete(PATH + "deleteProfile", parameter);
	}

	/**
	 * 회원정보 상세 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	public List getUserDetails(Object parameter) throws Exception {
		return getList(PATH + "getUserDetails", parameter);
	}

	/**
	 * 회원정보 조회
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	@Override
	public List getChargeMemberList(Object parameter) throws Exception {
		return getList(PATH + "getChargeMemberList", parameter);
	}
	
	/**
	 * 회원정보 성명 검색
	 * @param parameter parameter
	 * @return java.util.List
	 * @throws Exception
	 */
	@Override
	public List getMemberFindList(Object parameter) throws Exception {
		return getList(PATH + "getMemberFindList", parameter);
	}

	public Object updatePwdErrCnt(Object parameter) throws Exception{
		return update(PATH + "updatePwdErrCnt", parameter);
	}

	public Object updatePwdErrCntUp(Object parameter) throws Exception{
		return update(PATH + "updatePwdErrCntUp", parameter);
	}
}