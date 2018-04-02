package com.mhc.filters;

import java.util.Calendar;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mhc.dao.HttpAccessLogsDAO;
import com.mhc.dao.InitDAO;
import com.mhc.dto.HttpAccessLogsDTO;
import com.mhc.exceptions.RequestIncorrectlySignedException;
import com.mhc.exceptions.RequestUnsignedException;
import com.mhc.services.AESService;
import com.mhc.services.AESServiceImpl;
import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;
import com.mhc.util.InitUtil;
import com.mhc.util.Signer;

public class ExtSignFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(ExtSignFilter.class);

	private MessageSource messageSource;
	private InitDAO initDAO;
	private HttpAccessLogsDAO httpAccessLogsDAO;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
		messageSource = (MessageSource) beanFactory.getBean("messageSource");
		initDAO = (InitDAO) beanFactory.getBean("initDAO");
		httpAccessLogsDAO = (HttpAccessLogsDAO) beanFactory.getBean("httpAccessLogsDAO");

		// Update keys
		SqlRowSet srs = initDAO.getSecurityKeys();
		if (srs.next()) {
			AESService aes = new AESServiceImpl();
			InitUtil.setSalt(aes.decrypt(Constants.getCORESERVICES_PROPERTY_SALT(), srs.getString("salt")));
			InitUtil.setDocKey(aes.decrypt(Constants.getCORESERVICES_PROPERTY_SALT(), srs.getString("doc_key")));
			InitUtil.setLogKey(aes.decrypt(Constants.getCORESERVICES_PROPERTY_SALT(), srs.getString("log_key")));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		PostHttpServletRequestWrapper postWraper = new PostHttpServletRequestWrapper(httpServletRequest);
		try {
			String token = httpServletRequest.getHeader("token");
			String nonce = httpServletRequest.getHeader("nonce");
			String sk = httpServletRequest.getHeader("sk");
			String documentId = httpServletRequest.getHeader("documentId");
			String requestedBy = httpServletRequest.getHeader("requested-by");
			String url = getUri(httpServletRequest);

			/*if (StringUtils.isBlank(token)) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_TOKEN, null, null));
			}
			try {
				new AESServiceImpl().decrypt(InitUtil.getSalt(), token);
			} catch (Exception e1) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_TOKEN, null, null));
			}

			if (StringUtils.isBlank(sk)) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_SK, null, null));
			}
			Long currentTime = System.currentTimeMillis();
			long twoHours = TimeUnit.HOURS.toMillis(2);
			if ((Long.valueOf(sk) < currentTime - twoHours) || (Long.valueOf(sk) > currentTime + twoHours)) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_SK_OUT_OF_TIME, null, null));
			}

			
			 * if (!VerificationUtil.validateIPs(httpServletRequest)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_IP, null,
			 * null)); }
			 
			if (StringUtils.isBlank(nonce)) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_NONCE, null, null));
			}

			try {
				validateSignature(url, httpServletRequest, InitUtil.getSalt());
			} catch (Exception e) {
				throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_API_SIG, null, null));
			}*/

			// Log For valid events only. Log before next filter chain.
			/*AESService aes = new AESServiceImpl();
			HttpAccessLogsDTO docLogDTO = new HttpAccessLogsDTO(aes.encrypt(InitUtil.getLogKey(), requestedBy),
					aes.encrypt(InitUtil.getLogKey(), httpServletRequest.getRemoteAddr()), documentId, nonce, url,
					httpServletRequest.getMethod());
			httpAccessLogsDAO.saveLogs(docLogDTO);*/
			String uuid = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(messageSource.getMessage(Constants.COOKIE_NAME, null, null), uuid);
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute(uuid, "something");
			httpServletResponse.addCookie(cookie);
			httpServletResponse.sendRedirect(messageSource.getMessage(Constants.BIOMETRICS_URL, null, null));
			chain.doFilter(postWraper, httpServletResponse);
		} catch (Exception ex) {
			LOG.error(null, ex);
			String message = "A general exception has occurred please try again at a later time";
			String ID = "500";

			String[] messageParts = null;
			if (StringUtils.isNotBlank(ex.getMessage())
					&& (messageParts = ex.getMessage().split("\\|\\|")).length > 1) {
				ID = messageParts[0];
				message = messageParts[1];
			}
			try {
				/*httpServletResponse.setStatus(401);
				httpServletResponse.getWriter()
						.write("{\"ID\" : \"" + ID + "\" , \"Description\" : \"" + message + "\" }");
				httpServletResponse.getWriter().flush();*/
				message = messageSource.getMessage(Constants.FORBIDDEN_URL, null, null);
				//httpServletResponse.sendRedirect(message);
				//httpServletResponse.getWriter().flush();
				httpServletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
				httpServletResponse.setHeader("Location", message);
				httpServletResponse.getWriter().flush();
			} catch (Throwable bad) {
				LOG.error("Exception thrown while trying to handle ServerException", ex);
				LOG.error(" new exception", bad);
				throw new ServletException(bad);
			}
		}
	}

	private String getUri(final HttpServletRequest request) {
		String path = request.getRequestURI();

		if (path.startsWith(request.getContextPath())) {
			path = path.substring(request.getContextPath().length());
		}
		return path;
	}

	public static void validateSignature(String resourcePath, HttpServletRequest request, String privateKey)
			throws Exception {

		String providedSignature = request.getParameter("api_sig");

		if (providedSignature == null) {
			throw new RequestUnsignedException();
		}
		StringBuilder sb = new StringBuilder(privateKey);
		sb.append(resourcePath);

		// headers
		if (StringUtils.isNotBlank(request.getHeader("documentId"))) {
			sb.append("documentId").append(request.getHeader("documentId"));
		}
		sb.append("nonce").append(request.getHeader("nonce"));
		if (StringUtils.isNotBlank(request.getHeader("patientId"))) {
			sb.append("patientId").append(request.getHeader("patientId"));
		}
		sb.append("requested-by").append(request.getHeader("requested-by"));
		sb.append("sk").append(request.getHeader("sk"));
		sb.append("token").append(request.getHeader("token"));

		// params
		SortedMap<String, String[]> sortedParameters = new TreeMap<String, String[]>();
		for (Object o : request.getParameterMap().entrySet()) {
			Map.Entry<String, String[]> e = (Map.Entry<String, String[]>) o;
			if (!"api_sig".equals(e.getKey())) {
				sortedParameters.put(e.getKey(), e.getValue());
			}
		}
		for (Map.Entry<String, String[]> e : sortedParameters.entrySet()) {
			sb.append(e.getKey());
			for (String s : e.getValue()) {
				sb.append(s);
			}
		}

		String toSign = sb.toString();
		String expectedSignature = null;

		expectedSignature = Signer.hexEncode256(toSign);

		boolean result = expectedSignature.equals(providedSignature);
		if (!result) {
			if (LOG.isInfoEnabled()) {
				LOG.info("provided signature on request: " + providedSignature);
				LOG.info("expected signature: " + expectedSignature); // TODO remove after signing working
			}
			throw new RequestIncorrectlySignedException(expectedSignature);
		}
	}

	@Override
	public void destroy() {

	}

}
