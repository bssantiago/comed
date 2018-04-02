package com.mhc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.mhc.dao.HttpAccessLogsDAO;
import com.mhc.dao.InitDAO;
import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;

public class AuthorizationFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class);

	private MessageSource messageSource;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
		messageSource = (MessageSource) beanFactory.getBean("messageSource");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		PostHttpServletRequestWrapper postWraper = new PostHttpServletRequestWrapper(httpServletRequest);

		try {
			if (httpServletRequest.getCookies() == null) {
				throw new IOException();
			}
			int length = httpServletRequest.getCookies().length;
			String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
			boolean find = false;
			for (int i = 0; i < length; i++) {
				if (httpServletRequest.getCookies()[i].getName().equals(cookieName)) {
					HttpSession session = httpServletRequest.getSession();
					if (session.getAttribute(httpServletRequest.getCookies()[i].getValue()) != null) {
						find = true;
						break;
					} else {
						throw new IOException();
					}
				}
			}
			if (!find) {
				throw new IOException();
			}
			chain.doFilter(postWraper, httpServletResponse);
		} catch (IOException e) {
			LOG.error("Exception thrown while trying to handle ServerException", e);
			LOG.error(" new exception", e);
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {

	}

}
