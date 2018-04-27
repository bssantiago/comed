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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.mhc.exceptions.EncryptionException;
import com.mhc.services.ApplicationContextProvider;
import com.mhc.services.EncryptService;
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		PostHttpServletRequestWrapper postWraper = new PostHttpServletRequestWrapper(httpServletRequest);
		
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			if (!httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
				HttpSession session = httpServletRequest.getSession();
				if(session == null) {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The session is invalid");
				}
				if (httpServletRequest.getCookies() == null) {
					throw new IOException();
				}
				int length = httpServletRequest.getCookies().length;
				String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
				boolean find = false;
				for (int i = 0; i < length; i++) {
					if (httpServletRequest.getCookies()[i].getName().equals(cookieName)) {
						String value = httpServletRequest.getCookies()[i].getValue();
						value = EncryptService.decryptStringDB(value);
						String originalValue = (String) httpServletRequest.getSession().getAttribute(cookieName);						
						find = StringUtils.equalsIgnoreCase(value, originalValue);
						break;
					}
				}
				if (!find) {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The cookie " + cookieName  + " is not in the request.");
				}
			}

			chain.doFilter(postWraper, httpServletResponse);
		} catch (IOException e) {
			LOG.error("Exception thrown while trying to handle ServerException", e);
			LOG.error(" new exception", e);
			throw new ServletException(e);
		} catch (EncryptionException ex) {
			LOG.error("Invalid Session", ex);
			LOG.error(" new exception", ex);
			throw new ServletException(ex);
		}
	}

	@Override
	public void destroy() {

	}

}
