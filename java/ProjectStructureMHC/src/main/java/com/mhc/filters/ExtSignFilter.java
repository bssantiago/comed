package com.mhc.filters;

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
import com.mhc.services.EncryptService;
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
			String token = httpServletRequest.getHeader(Constants.HEADER_TOKEN);
			String nonce = httpServletRequest.getHeader(Constants.HEADER_NONCE);
			String sk = httpServletRequest.getHeader(Constants.HEADER_SK);
			String clientId = httpServletRequest.getHeader(Constants.HEADER_CLIENT_ID);
			String patientId = httpServletRequest.getHeader(Constants.HEADER_PATIENT_ID);
			String requestedBy = httpServletRequest.getHeader(Constants.HEADER_REQUEST_BY);
			String url = getUri(httpServletRequest);

			/*
			 * if (StringUtils.isBlank(token)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_TOKEN,
			 * null, null)); } try { new AESServiceImpl().decrypt(InitUtil.getSalt(),
			 * token); } catch (Exception e1) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_TOKEN,
			 * null, null)); }
			 * 
			 * if (StringUtils.isBlank(sk)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_SK, null,
			 * null)); } Long currentTime = System.currentTimeMillis(); long twoHours =
			 * TimeUnit.HOURS.toMillis(2); if ((Long.valueOf(sk) < currentTime - twoHours)
			 * || (Long.valueOf(sk) > currentTime + twoHours)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_SK_OUT_OF_TIME,
			 * null, null)); }
			 * 
			 * 
			 * if (!VerificationUtil.validateIPs(httpServletRequest)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_IP, null,
			 * null)); }
			 * 
			 * if (StringUtils.isBlank(nonce)) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_NONCE,
			 * null, null)); }
			 * 
			 * try { validateSignature(url, httpServletRequest, InitUtil.getSalt()); } catch
			 * (Exception e) { throw new
			 * ServletException(messageSource.getMessage(Constants.ERROR_INVALID_API_SIG,
			 * null, null)); }
			 * 
			 * // Log For valid events only. Log before next filter chain. AESService aes =
			 * new AESServiceImpl(); HttpAccessLogsDTO docLogDTO = new
			 * HttpAccessLogsDTO(aes.encrypt(InitUtil.getLogKey(), requestedBy),
			 * aes.encrypt(InitUtil.getLogKey(), httpServletRequest.getRemoteAddr()),
			 * clientId, nonce, url, httpServletRequest.getMethod());
			 * httpAccessLogsDAO.saveLogs(docLogDTO);
			 */
			
			if (StringUtils.isNotBlank(httpServletRequest.getHeader(Constants.HEADER_CLIENT_ID))) {
				httpServletRequest.getSession().setAttribute(Constants.HEADER_CLIENT_ID,
						httpServletRequest.getHeader(Constants.HEADER_CLIENT_ID));
			}
			if (StringUtils.isNotBlank(httpServletRequest.getHeader(Constants.HEADER_PATIENT_ID))) {
				httpServletRequest.getSession().setAttribute(Constants.HEADER_PATIENT_ID,
						httpServletRequest.getHeader(Constants.HEADER_PATIENT_ID));
			}
			if (StringUtils.isNotBlank(httpServletRequest.getHeader(Constants.HEADER_REQUEST_BY))) {
				httpServletRequest.getSession().setAttribute(Constants.HEADER_REQUEST_BY,
						httpServletRequest.getHeader(Constants.HEADER_REQUEST_BY));
			}
			
			String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
			String uuid = UUID.randomUUID().toString();
			Cookie cookie = new Cookie(cookieName, EncryptService.encryptStringDB(uuid));
			cookie.setHttpOnly(true);
			httpServletRequest.getSession().setAttribute(cookieName,uuid);
			cookie.setPath("comed/");
			httpServletResponse.addCookie(cookie);
			// httpServletResponse.sendRedirect(messageSource.getMessage(Constants.BIOMETRICS_URL,
			// null, null));
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
				message = messageSource.getMessage(Constants.FORBIDDEN_URL, null, null);
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

		String providedSignature = request.getHeader(Constants.HEADER_API_SIGNATURE);

		if (providedSignature == null) {
			throw new RequestUnsignedException();
		}
		StringBuilder sb = new StringBuilder(privateKey);
		sb.append(resourcePath);

		// headers
		if (StringUtils.isNotBlank(request.getHeader(Constants.HEADER_CLIENT_ID))) {
			sb.append(Constants.HEADER_CLIENT_ID).append(request.getHeader(Constants.HEADER_CLIENT_ID));
		}
		sb.append(Constants.HEADER_NONCE).append(request.getHeader(Constants.HEADER_NONCE));
		if (StringUtils.isNotBlank(request.getHeader(Constants.HEADER_PATIENT_ID))) {
			sb.append(Constants.HEADER_PATIENT_ID).append(request.getHeader(Constants.HEADER_PATIENT_ID));
		}
		sb.append(Constants.HEADER_REQUEST_BY).append(request.getHeader(Constants.HEADER_REQUEST_BY));
		sb.append(Constants.HEADER_SK).append(request.getHeader(Constants.HEADER_SK));
		sb.append(Constants.HEADER_TOKEN).append(request.getHeader(Constants.HEADER_TOKEN));

		String toSign = sb.toString();
		String expectedSignature = null;

		expectedSignature = Signer.hexEncode256(toSign);

		boolean result = expectedSignature.equals(providedSignature);
		if (!result) {
			throw new RequestIncorrectlySignedException(expectedSignature);
		}

	}

	@Override
	public void destroy() {

	}

}
