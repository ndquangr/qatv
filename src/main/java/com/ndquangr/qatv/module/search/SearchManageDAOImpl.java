package com.ndquangr.qatv.module.search;

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
public class SearchManageDAOImpl extends AbstractDAO {	
	
	
	private static final String PATH = "qatv.qatvManage.searchManageDAO.";
	
	public List list(String path, Object parameter) throws Exception{
		return getList(PATH + path, parameter);
	}
	
	public Map map(String path, Object parameter) throws Exception{
		return (Map) getObject(PATH + path, parameter);
	}
	
	/**
	 * getListCnt
	 * @throws Exception 
	 * @작성일	: 2016. 9. 1. 
	 * @작성자	: ndquangr
	 * @기능설명 	:
	 * @진행상태	: TO-DO, DEBUG, TEST, COMPLETE  
	 */
	public int getListCnt(Map parameter) throws Exception {
		return (int) getObject(PATH + "getListCnt", parameter);
	}

}