package com.ndquangr.qatv.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * All common function using in this project
 *
 */
public class CommonUtils {

	// Date time format
	public static final String patternDate = "yyyyMMdd";

	// timeout
	public final static String TOKEN = "651c983a40fb18d64e36dcbbfeacc871a3d8bacf";
	// api code
	public final static int SUCCESS = 0;
	public final static int FAIL = 1;
	public final static int EXPIRED = 2;
	public final static int INVALID_TOKEN = 3;
	public final static int WRONG_PARAM = 4;
	// license type
	public final static Character TRIAL = '0';
	public final static Character PAID = '1';
	// trial day
	public final static int TRIAL_DAY = 30;

	public static Logger doLog(Class<?> c) {
		return Logger.getLogger(c);
	}

	/**
	 * encrypt md5
	 * 
	 * @param input
	 * @return
	 */
	public static String encryptMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * convert string to date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date convertStringToDate(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(date);

		} catch (ParseException e) {
			doLog(CommonUtils.class).error(
					"Date ParseException:" + e.getMessage());
		}
		return null;
	}

	/**
	 * convert date to string
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String convertDateToString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		if (date == null) {
			return "";
		}
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			doLog(CommonUtils.class).error(
					"Date ParseException:" + e.getMessage());
		}
		return "";
	}

	/**
	 * check object null or empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * generate random string
	 * 
	 * @param length
	 * @return
	 */
	public static String makeRandom(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * check trial is expire or not
	 * 
	 * @param licenseDate
	 * @return
	 */
	public static boolean isExpired(Date licenseDate) {
		boolean isExpire = true;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -TRIAL_DAY);
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		Date expireDate = cal.getTime();
		if (expireDate.compareTo(licenseDate) <= 0) {
			isExpire = false;
		}
		return isExpire;
	}

	/**
	 * check user role
	 * 
	 * @param role
	 * @return
	 */
	public static boolean hasRole(String role) {
		Collection<GrantedAuthority> authorities = getAuthorities();
		String rolename;
		for (GrantedAuthority authority : authorities) {
			rolename = authority.getAuthority();
			if (role.equals(rolename)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get list authorities of logged user
	 * 
	 * @return
	 */
	private static Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			authorities = ((User) principal).getAuthorities();
		}
		return authorities;
	}

	/**
	 * compare url
	 * 
	 * @param url1
	 * @param url2
	 * @return
	 */
	public static boolean compareUrl(String url1, String url2) {
		url1 = url1.toLowerCase().replaceAll("^(https?)://", "").replaceAll("^www.", "");
		url2 = url2.toLowerCase().replaceAll("^(https?)://", "").replaceAll("^www.", "");
		return url1.equals(url2);
	}

	/**
	 * get domain string, remove http:// or www
	 * 
	 * @param url
	 * @return
	 */
	public static String reBuildUrl(String url) {
		return url.toLowerCase().replaceAll("^(https?)://", "").replaceAll("^www.", "");
	}
}
