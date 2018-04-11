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

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;

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
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			if (!httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
				if (httpServletRequest.getCookies() == null) {
					throw new IOException();
				}
				int length = httpServletRequest.getCookies().length;
				String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
				boolean find = false;
				for (int i = 0; i < length; i++) {
					if (httpServletRequest.getCookies()[i].getName().equals(cookieName)) {
						find = true;
						break;
					}
				}
				if (!find) {
					throw new IOException();
				}
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
