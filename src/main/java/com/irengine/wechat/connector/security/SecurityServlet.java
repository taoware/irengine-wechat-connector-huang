package com.irengine.wechat.connector.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecurityServlet implements Filter {

	private static Logger logger = LoggerFactory
			.getLogger(SecurityServlet.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		HttpSession session=httpRequest.getSession();
		List<String> uris=new ArrayList<String>();
		uris.add("/web/index");
		uris.add("/web/ads");
		uris.add("/web/draw");
		uris.add("/web/game");
		uris.add("/web/imgtext");
		uris.add("/web/listads");
		uris.add("/web/listdraw");
		uris.add("/web/listgame");
		uris.add("/web/listimgtext");
		uris.add("/web/listtext");
		uris.add("/web/userads");
		if(uris.indexOf(httpRequest.getRequestURI())!=-1){
			if(session.getAttribute("username")==null){
				logger.debug("未登录,跳转到登录页面");
				((HttpServletResponse) response).sendRedirect("http://vps1.taoware.com/web/home");
			}else{
				logger.debug("用户已登录");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
