package com.ndquangr.qatv.module.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ndquangr.qatv.authen.UserDetailsHelper;
import com.ndquangr.qatv.common.util.ReqUtils;
import com.ndquangr.qatv.dao.AbstractDAO;


/** 
 * 회원정보 Data Access Object interface implement
 * @version 1.0
 * @author a2m
 */
@Repository
public class FileManageDAOImpl extends AbstractDAO {	
	
	
	private static final String PATH = "qatv.qatvManage.fileManageDAO.";
	
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
	
	public List list(String path, Object parameter) throws Exception{
		return getList(PATH + path, parameter);
	}
	
	public Map map(String path, Object parameter) throws Exception{
		return (Map) getObject(PATH + path, parameter);
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
	
	public Object object(String string, Object parameter) throws Exception {
		return getObject(PATH + string, parameter);
	}

	@Transactional
	public void save(Map parameter) throws Exception {
		// TODO Auto-generated method stub
		
	}

}