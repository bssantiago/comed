package com.mhc.rest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import com.mhc.services.ApplicationContextProvider;
import com.mhc.util.Constants;

@Path("authenticate")
@Produces("application/json")
public class Authentication {

	@GET
	public Response authenticateGet(@Context HttpServletRequest request) {
		return null;
	}

	@POST
	public Response authenticatePost(@Context HttpServletRequest request) {
		return null;
	}

	@DELETE
	@Path("exit")
	public Response deleteCookie(@Context HttpServletRequest request) {
		ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
		MessageSource messageSource = (MessageSource) beanFactory.getBean("messageSource");
		String cookieName = messageSource.getMessage(Constants.COOKIE_NAME, null, null);
		HttpSession session = request.getSession();
		if (session != null)
			  session.invalidate(); // so it expires immediatly
		return Response
				.ok()
				.cookie(new NewCookie(cookieName, "", "", "/comed", "delete cookie", 0, true) )
				.build();
	}

}
