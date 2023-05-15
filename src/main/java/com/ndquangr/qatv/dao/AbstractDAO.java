package com.ndquangr.qatv.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AbstractDAO extends SqlSessionDaoSupport implements DAO{
	
	/**
	 * DataSource를 @Autowired, @Qualifier 를 통해 지정해줌
	 * 
	 *  -> AbstractDAO를 extends 받는 경우 defaultDS로 연결됨
	 */
	@Override
	@Autowired
	public void setSqlSessionFactory(@Qualifier("defaultDS") SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	/* List */
	public List getList(String statement) 
			throws Exception {
		return getSqlSession().selectList(statement);
	}

	public List getList(String statement, Object parameter) 
			throws Exception {
		return getSqlSession().selectList(statement, parameter);
	}

	public List getList(String statement, Object parameter, 
			int offset, int limit) throws Exception {
		//RowBounds - 특정 개수 만큼의 레코드를 건너띄게 한다.
		//<select>의 resultSetType은 FORWARD_ONLY|SCROLL_SENSITIVE|SCROLL_INSENSITIVE 중
		//SCROLL_SENSITIVE|SCROLL_INSENSITIVE를 사용하는 것 추천
		RowBounds rowBounds = new RowBounds(offset, limit);
		return getSqlSession().selectList(statement, parameter, rowBounds);
	}
	
	/* Map */
	public Map getMap(String statement, String mapKey) 
			throws Exception {
		return getSqlSession().selectMap(statement, mapKey);
	}
	
	public Map getMap(String statement, Object parameter, String mapKey) 
			throws Exception {
		return getSqlSession().selectMap(statement, parameter, mapKey);
	}
	
	public Map getMap(String statement, Object parameter, String mapKey, 
			int offset, int limit) throws Exception {
		//RowBounds - 특정 개수 만큼의 레코드를 건너띄게 한다.
		//<select>의 resultSetType은 FORWARD_ONLY|SCROLL_SENSITIVE|SCROLL_INSENSITIVE 중
		//SCROLL_SENSITIVE|SCROLL_INSENSITIVE를 사용하는 것 추천
		RowBounds rowBounds = new RowBounds(offset, limit);
		return getSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
	}

	/* Object */
	public Object getObject(String statement) throws Exception {
		return getSqlSession().selectOne(statement);
	}
	
	public Object getObject(String statement, Object parameter) throws Exception {
		return getSqlSession().selectOne(statement, parameter);
	}

	
	/* Insert */
	public Object insert(String statement) throws Exception {
		return getSqlSession().insert(statement);
	}
	public Object insert(String statement, Object parameter) throws Exception {
		return getSqlSession().insert(statement, parameter);
	}
	
	/* Update */
	public Object update(String statement) throws Exception {
		return getSqlSession().update(statement);
	}
	public Object update(String statement, Object parameter) throws Exception {
		return getSqlSession().update(statement, parameter);
	}
	
	/* Delete */
	public Object delete(String statement) throws Exception {
		return getSqlSession().delete(statement);
	}
	public Object delete(String statement, Object parameter) throws Exception {
		return getSqlSession().delete(statement, parameter);
	}

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
