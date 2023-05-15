package com.ndquangr.qatv.module.qatv;

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
public class QATVManageDAOImpl extends AbstractDAO {
	
	/*private static QATVManageDAOImpl instance = null;
	
	public static QATVManageDAOImpl getInstance(){
		if(instance == null){
			instance = new QATVManageDAOImpl();
		}
		return instance;
	}
	
	public QATVManageDAOImpl(){
		if(instance == null)
			instance = this;
	}*/
	
	/*@Override
	@Autowired
	public void setSqlSessionFactory(@Qualifier("defaultDS") SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		SqlSession sqlSession = getSqlSession();
		instance.setSqlSessionTemplate((SqlSessionTemplate)sqlSession);
	}*/
	
	private static final String PATH = "qatv.qatvManage.qatvManageDAO.";
	
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

	/**
	 * saveQatv
	 * @throws Exception 
	 * @작성일	: 2016. 8. 26. 
	 * @작성자	: ndquangr
	 * @기능설명 	:
	 * @진행상태	: TO-DO, DEBUG, TEST, COMPLETE  
	 */
	@Transactional
	public void saveQatv(Map parameter) throws Exception {
		boolean bm = TransactionSynchronizationManager.isActualTransactionActive();
		String userID = UserDetailsHelper.getAuthenticatedUser().getUserID();
		Map main = (Map) parameter.get("MAIN");		
		Map sub = (Map) parameter.get("SUB");
		
		String mainVstr = ((String) main.get("CHAR_VIET")).trim();
		String mainHstr = ((String) main.get("CHAR_HANNOM")).trim();
		if (mainVstr.equals("")) {
			return;
		}
		
		main.put("CHAR_VIET", mainVstr);
		main.put("CHAR_HANNOM", mainHstr);
		main.put("CHAR_VNF", mainVstr);
		main.put("CHAR_HNF", mainHstr);
		
		main.put("USER_ID", userID);
		main.put("CHAR_LVL", 0);
		if (main.containsKey("QATV_ID") && !main.get("QATV_ID").toString().trim().equals("")) {
			update("updateQATV", main);
		} else {
			insert("insertQATV", main);
		}
		
		if(parameter.containsKey("DELETED_ITEMS") && !parameter.get("DELETED_ITEMS").toString().trim().equals("")) {
			String [] dis = parameter.get("DELETED_ITEMS").toString().trim().split(",");
			Map dm = new HashMap();
			for (int i = 0; i < dis.length; i++) {
				if(!dis[i].trim().equals("")) {
					dm.put("id", dis[i]);
					delete("deleteQATV", dm);
				}
			}
		}
		
		if (sub != null && sub.get("CHAR_VIET") != null) {
			String [] char_id = ReqUtils.getArrays(sub, "QATV_ID");
			String [] char_viet = ReqUtils.getArrays(sub, "CHAR_VIET");
			String [] char_han = ReqUtils.getArrays(sub, "CHAR_HANNOM"); 
			String [] char_def = ReqUtils.getArrays(sub, "CHAR_DEF"); 
			String [] char_note = ReqUtils.getArrays(sub, "NOTE"); 
			String [] char_ord = ReqUtils.getArrays(sub, "CHAR_ORD"); 
			Map m = new HashMap();
			m.put("PRT_ID", main.get("QATV_ID"));
			m.put("CHAR_LVL", 1);
			m.put("USER_ID", userID);
			if (char_viet != null && char_viet.length > 0) {
				for (int i = 0; i < char_viet.length; i++) {
					m.put("CHAR_VIET", char_viet[i].trim());
					m.put("CHAR_VNF", char_viet[i].trim().replace("-", mainVstr).replace("—", mainVstr));
					m.put("CHAR_HANNOM", char_han[i].trim());
					m.put("CHAR_HNF", char_han[i].trim().replace("|", mainHstr));
					m.put("CHAR_DEF", char_def[i]);
					m.put("NOTE", char_note[i]);
					m.put("CHAR_ORD", char_ord[i]);				
					if (char_id[i] != null && !char_id[i].equals("")) {
						m.put("QATV_ID", char_id[i]);
						update("updateQATV", m);
					} else {
						insert("insertQATV", m);
					}
				}
			}
		}
	}

	public Object object(String string, Object parameter) throws Exception {
		return getObject(PATH + string, parameter);
	}

	/**
	 * getListCnt
	 * @작성일	: 2016. 9. 1. 
	 * @작성자	: ndquangr
	 * @기능설명 	:
	 * @진행상태	: TO-DO, DEBUG, TEST, COMPLETE  
	 */
	public int getListCnt(Map parameter) throws Exception {
		return (int) getObject(PATH + "getListCntQATV", parameter);
	}

}