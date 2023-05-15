package com.ndquangr.qatv.authen;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * This class for checking ajax call when timeout
 *
 */
public class AjaxAwareAuthenticationEntryPoint
		extends
			LoginUrlAuthenticationEntryPoint {
	/**
	 * Constructor
	 * 
	 * @param loginFormUrl
	 */
	public AjaxAwareAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	/**
	 * override method
	 */
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		if (isAjaxRequest(request)) {
			((HttpServletResponse) response).sendError(601, "Session timeout");
		} else {
			super.commence(request, response, authException);
		}
	}

	/**
	 * Is ajax request
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {

		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
				? true
				: false;
	}
}
