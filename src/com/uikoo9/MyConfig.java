package com.uikoo9;

import com.baidu.ueditor.util.QQiNiuUtil;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.uikoo9.index.IndexController;

/**
 * API引导式配置
 */
public class MyConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
	}
	
	public void configPlugin(Plugins me) {}
	public void configInterceptor(Interceptors me) {}
	public void configHandler(Handlers me) {}
	
	@Override
	public void afterJFinalStart() {
		QQiNiuUtil.genUptoken();
	}
}
