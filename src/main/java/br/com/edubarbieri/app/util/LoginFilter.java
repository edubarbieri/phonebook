package br.com.edubarbieri.app.util;

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

public class LoginFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(LoginFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Object user = null;
		if (httpRequest.getSession() != null) {
			user = httpRequest.getSession().getAttribute(Constantes.LOGGED_USER_KEY);
		}
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (user != null) {
			if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().contains("login.xhtml")) {
				String path = httpRequest.getContextPath() + "/faces/default.xhtml";
				LOG.info("Usuário logado, redirecionando para tela de home!");
				httpResponse.sendRedirect(path);
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			if (pagaIsAllowed(httpRequest)) {
				filterChain.doFilter(request, response);
			} else {
				String path = httpRequest.getContextPath() + "/faces/login.xhtml";
				LOG.info("Usuário não logodo, redirecionando para tela de login!");
				httpResponse.sendRedirect(path);
			}

		}
	}

	private boolean pagaIsAllowed(HttpServletRequest httpRequest) {
		String pathInfo = httpRequest.getPathInfo();
		if (pathInfo == null || pathInfo.contains("javax.faces.resource") || pathInfo.contains("login.xhtml")) {
			return true;
		}
		return false;

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		LOG.debug("Initializing login filter...");
	}

}
