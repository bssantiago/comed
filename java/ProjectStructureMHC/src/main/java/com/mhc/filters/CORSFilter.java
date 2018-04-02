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

import com.mhc.dao.HttpAccessLogsDAO;
import com.mhc.dao.InitDAO;
import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;

public class CORSFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(CORSFilter.class);

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
		httpServletResponse.setHeader("Access-Control-Allow-Origin", messageSource.getMessage(Constants.HEADER_ALLOW_ORIGIN, null, null));
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", messageSource.getMessage(Constants.HEADER_CREDENTIAL, null, null));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", messageSource.getMessage(Constants.HEADER_METHODS, null, null));
		httpServletResponse.setHeader("Access-Control-Allow-Headers", messageSource.getMessage(Constants.HEADER_CONTENT_TYPE, null, null));
		try {
			chain.doFilter(postWraper, httpServletResponse);
		} catch (IOException e) {
			LOG.error("Exception thrown while trying to handle ServerException", e);
			LOG.error(" new exception", e);
			throw new ServletException(e);
		}
	}

	private String getUri(final HttpServletRequest request) {
		String path = request.getRequestURI();

		if (path.startsWith(request.getContextPath())) {
			path = path.substring(request.getContextPath().length());
		}
		return path;
	}
	
	@Override
	public void destroy() {

	}

}
