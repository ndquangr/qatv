package com.ndquangr.qatv;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ndquangr.qatv.authen.UserDetailsHelper;
import com.ndquangr.qatv.module.qatv.QATVManageDAOImpl;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String ALL_CHARACTER = "A,À,Á,Ả,Ã,Ạ,Ă,Ằ,Ắ,Ẳ,Ẵ,Ặ,Â,Ầ,Ấ,Ẩ,Ẫ,Ậ,B,C,D,Đ,E,È,É,Ẻ,Ẽ,Ẹ,Ê,Ề,Ế,Ể,Ễ,Ệ,G,H,I,Ì,Í,Ỉ,Ĩ,Ị,K,L,M,N,O,Ò,Ó,Ỏ,Õ,Ọ,Ô,Ồ,Ố,Ổ,Ỗ,Ộ,Ơ,Ờ,Ớ,Ở,Ỡ,Ợ,P,Q,R,S,T,U,Ù,Ú,Ủ,Ũ,Ụ,Ư,Ừ,Ứ,Ử,Ữ,Ự,V,X,Y,Ỳ,Ý,Ỷ,Ỹ,Ỵ";
	
	@Autowired QATVManageDAOImpl qatvdao;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		//model.addAttribute("serverTime", formattedDate );
		
		return "redirect:/guest/search";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(Locale locale, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();
		String user_id = UserDetailsHelper.getAuthenticatedUser().getUserID();
		String forwardName;
		if ( StringUtils.isEmpty(user_id) ) {
			forwardName = "redirect:/pageAuth";
		} else {
			/*List<String> allCap = Arrays.asList(ALL_CHARACTER.split(","));
			List list = new ArrayList();
			Map param = new HashMap();
			param.put("searchType", "start");
			for (int i = 0; i < allCap.size(); i++) {				
				param.put("searchTxt", allCap.get(i));
				try {
					int cnt = (int) qatvdao.object("getListCntQATV", param);
					if (cnt > 0) {
						List qatvlist = qatvdao.list("getListQATV", param);
						if (qatvlist != null && list.size() > 0) {
							Map map = new HashMap();
							map.put("key", allCap.get(i));
							map.put("value", qatvlist);
							list.add(map);
						}	
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
							
			}
			mav.addObject("DATA", list);*/
			
			forwardName = "/home";
		}
		mav.setViewName(forwardName);
		return mav;
	}
	
	/**
	 * 관리자 로그인 요청 처리
	 * @return org.springframework.web.servlet.ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/pageAuth", method = RequestMethod.GET)
	public ModelAndView callLoginPage() throws Exception {
		ModelAndView mav = new ModelAndView();

		String forwardName;
		String user_id = UserDetailsHelper.getAuthenticatedUser().getUserID();

		if ( StringUtils.isEmpty(user_id) ) {
			forwardName = "/authPage";
		} else {
			forwardName = "redirect:/staff/nom/list";
		}
		
		mav.setViewName(forwardName);
		return mav;
	}
}
