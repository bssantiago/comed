package com.mhc.filters;

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

public class ExitFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(ExitFilter.class);
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		PostHttpServletRequestWrapper postWraper = new PostHttpServletRequestWrapper(httpServletRequest);
		try {
			if (!httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
				HttpSession session = httpServletRequest.getSession(false);
				if (session != null) {
					session.invalidate();
				}
			}
			chain.doFilter(postWraper, httpServletResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error(ex);
		}
	}


	@Override
	public void destroy() {

	}

}
