package com.mhc.filters;

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

import com.mhc.dao.ClientsDAO;
import com.mhc.dao.HttpAccessLogsDAO;
import com.mhc.dao.InitDAO;
import com.mhc.dao.ParticipantDAO;
import com.mhc.dto.ClientDTO;
import com.mhc.dto.HttpAccessLogsDTO;
import com.mhc.dto.ParticipantsDTO;
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
	private ParticipantDAO participantDAO;
	private ClientsDAO clientsDAO;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
		messageSource = (MessageSource) beanFactory.getBean("messageSource");
		initDAO = (InitDAO) beanFactory.getBean("initDAO");
		httpAccessLogsDAO = (HttpAccessLogsDAO) beanFactory.getBean("httpAccessLogsDAO");
		participantDAO = (ParticipantDAO) beanFactory.getBean("participantDAO");
		clientsDAO = (ClientsDAO) beanFactory.getBean("clientsDAO");

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
			String method = httpServletRequest.getMethod();
			if (!method.equalsIgnoreCase("OPTIONS") ) {

				String token = httpServletRequest.getHeader(Constants.HEADER_TOKEN);
				String nonce = httpServletRequest.getHeader(Constants.HEADER_NONCE);
				String sk = httpServletRequest.getHeader(Constants.HEADER_SK);
				String clientId = httpServletRequest.getHeader(Constants.HEADER_CLIENT_ID);
				String patientId = httpServletRequest.getHeader(Constants.HEADER_PATIENT_ID);
				String requestedBy = httpServletRequest.getHeader(Constants.HEADER_REQUEST_BY);
				String url = getUri(httpServletRequest);

				if (StringUtils.isBlank(token)) {
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

				if (StringUtils.isBlank(nonce)) {
					throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_NONCE, null, null));
				}

				try {
					validateSignature(url, httpServletRequest, InitUtil.getSalt());
				} catch (Exception e) {
					throw new ServletException(messageSource.getMessage(Constants.ERROR_INVALID_API_SIG, null, null));
				}

				// Log For valid events only. Log before next filter chain.
				AESService aes = new AESServiceImpl();
				HttpAccessLogsDTO docLogDTO = new HttpAccessLogsDTO(aes.encrypt(InitUtil.getLogKey(), requestedBy),
						aes.encrypt(InitUtil.getLogKey(), httpServletRequest.getRemoteAddr()), clientId, nonce, url,
						httpServletRequest.getMethod());
				httpAccessLogsDAO.saveLogs(docLogDTO);

				if (StringUtils.isNotBlank(clientId)) {
					httpServletRequest.getSession().setAttribute(Constants.HEADER_CLIENT_ID, clientId);
				}
				if (StringUtils.isNotBlank(patientId)) {
					httpServletRequest.getSession().setAttribute(Constants.HEADER_PATIENT_ID, patientId);
				}
				if (StringUtils.isNotBlank(requestedBy)) {
					httpServletRequest.getSession().setAttribute(Constants.HEADER_REQUEST_BY, requestedBy);
				}

				String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
				String expiry = messageSource.getMessage(Constants.COOKIE_TIME, null, null);
				String uuid = UUID.randomUUID().toString();
				Cookie cookie = new Cookie(cookieName, EncryptService.encryptStringDB(uuid));
				HttpSession session = httpServletRequest.getSession(true);
				session.setAttribute(cookieName, uuid);
				cookie.setPath("/comed");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(Integer.parseInt(expiry));
				httpServletResponse.addCookie(cookie);
				String redirectUrl = decideUrlRedirect(httpServletRequest, httpServletResponse);
				httpServletResponse.setStatus(HttpServletResponse.SC_OK);
				httpServletResponse.setHeader("RedirectTO", redirectUrl);
				httpServletResponse.getWriter().flush();

			}
			chain.doFilter(postWraper, httpServletResponse);
		} catch (Exception ex) {
			LOG.error(null, ex);
			try {
				String angular = messageSource.getMessage(Constants.ANGULAR_URL, null, null);
				String forbidden = messageSource.getMessage(Constants.FORBIDDEN_URL, null, null);
				httpServletResponse.setStatus(HttpServletResponse.SC_OK);
				httpServletResponse.setHeader("RedirectTO", angular + forbidden);
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

	private String decideUrlRedirect(HttpServletRequest request, HttpServletResponse response) {
		String searchUrl = messageSource.getMessage(Constants.SEARCH_URL, null, null);
		String angular = messageSource.getMessage(Constants.ANGULAR_URL, null, null);
		String external_client_id = request.getHeader(Constants.HEADER_CLIENT_ID);
		String external_patient_id = request.getHeader(Constants.HEADER_PATIENT_ID);

		String redirectUrl;
		ClientDTO client = null;

		if (external_client_id != null) {
			client = clientsDAO.getClientIdFromPlaform(external_client_id);
			if (client == null) {
				redirectUrl = String.format("%s/%s/%s", angular,
						messageSource.getMessage(Constants.FORBIDDEN_URL, null, null), external_client_id);
				return redirectUrl;
			}
			response.setHeader(Constants.HEADER_CLIENT_ID, Long.toString(client.getId()));
		}

		if (external_patient_id != null) {
			Integer participantId = participantDAO.getParticipantByExternalId(client.getId(), external_patient_id);
			if (participantId == null) {
				// TODO: call sp to get patient data.
				ParticipantsDTO pdto = participantDAO.getParticipantFromSP(external_client_id, external_patient_id);
				if (pdto != null) {
					String name = EncryptService.decryptStringDB(pdto.getFirst_name());
					String lastname = EncryptService.decryptStringDB(pdto.getLast_name());
					String dow = pdto.getDate_of_birth().toLocaleString();
					String gender = EncryptService.decryptStringDB(pdto.getGender());
					if (StringUtils.equalsIgnoreCase(gender, Constants.GENDER_WORD_FEMALE)) {
						gender = Constants.GENDER_FEMALE;
					} else {
						gender = Constants.GENDER_MALE;
					}
					redirectUrl = String.format(" %s/%s/%s/%s/%s/%s/%s/%s", angular, searchUrl, external_client_id,
							external_patient_id, name, lastname, dow, gender);
					return redirectUrl;	
				} else {
					redirectUrl = String.format(" %s/%s/%s/%s", angular, searchUrl, external_client_id,
							external_patient_id);
					return redirectUrl;
				}				
			}
			Object[] args = { participantId.toString() };
			redirectUrl = String.format("%s/%s", angular,
					messageSource.getMessage(Constants.BIOMETRICS_URL, args, null));
			return redirectUrl;

		}
		redirectUrl = String.format("%s/%s", angular, messageSource.getMessage(Constants.FILE_UPLOAD_URL, null, null));
		return redirectUrl;

	}

	private static void validateSignature(String resourcePath, HttpServletRequest request, String privateKey)
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
