package com.ghc.appversion.web;

import org.springframework.util.StringUtils;

import com.ghc.appversion.util.LogUtil;

public class ConfigurationService {
	private static final String DEFAULT_PORT = "8089";
	private String port;

	public ConfigurationService() {
		port = System.getenv("PORT");
		if (!StringUtils.hasLength(port)) {
			port = DEFAULT_PORT;
		}
		LogUtil.debug("Port: %s", port);
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
