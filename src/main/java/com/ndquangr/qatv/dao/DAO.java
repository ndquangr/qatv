package com.ndquangr.qatv.dao;

import java.util.List;
import java.util.Map;

public interface DAO {
	
	/* List */
	List getList(String statement) throws Exception;
	List getList(String statement, Object parameter) throws Exception;
	List getList(String statement, Object parameter, 
			int offset, int limit) throws Exception;
	
	/* Map */
	Map getMap(String statement, String mapKey) throws Exception;
	Map getMap(String statement, Object parameter, String mapKey) 
			throws Exception;
	Map getMap(String statement, Object parameter, String mapKey, 
			int offset, int limit) throws Exception;
	
	/* Object */
	Object getObject(String statement) throws Exception;
	Object getObject(String statement, Object parameter) throws Exception;

	/* Insert */
	Object insert(String statement) throws Exception;
	Object insert(String statement, Object parameter) throws Exception;
	
	/* Update */
	Object update(String statement) throws Exception;
	Object update(String statement, Object parameter) throws Exception;
	
	/* Delete */
	Object delete(String statement) throws Exception;
	Object delete(String statement, Object parameter) throws Exception;

	/**
	 * 트랜잭션의 이용
	 * 
	 * 1) 트랜잭션 어노테이션용 클래스 Import
	 * import org.springframework.transaction.annotation.Propagation;
	 * import org.springframework.transaction.annotation.Transactional;
	 * 
	 * 2) 트랜잭션을 사용할 메소드에 어노테이션 적용
	 * @Transactional(propagation=Propagation.REQUIRED)
	 */	

}
