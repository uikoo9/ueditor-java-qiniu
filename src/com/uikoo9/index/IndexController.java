package com.uikoo9.index;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index() {
		setAttr("base", getHttpPath(getRequest()));
		render("/WEB-INF/view/index.ftl");
	}
	
	private String getHttpPath(HttpServletRequest request){
		StringBuilder path = new StringBuilder();
		
		path.append(request.getScheme() + "://");
		path.append(request.getServerName() + ":");
		path.append(request.getServerPort());
		path.append(request.getContextPath());
		
		return path.toString();
	}
}