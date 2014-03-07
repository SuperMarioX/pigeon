package com.dianping.pigeon.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dianping.pigeon.config.ConfigManager;
import com.dianping.pigeon.extension.ExtensionLoader;
import com.dianping.pigeon.log.LoggerLoader;

public class ConfigLoader {

	private static final String PROPERTIES_PATH = "config/applicationContext.properties";

	private static final Logger logger = LoggerLoader.getLogger(ConfigLoader.class);

	public static void init() {
		Properties properties = new Properties();
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_PATH);
		if (input != null) {
			try {
				properties.load(input);
				input.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		ConfigManager configManager = ExtensionLoader.getExtension(ConfigManager.class);
		if (configManager != null && "dev".equalsIgnoreCase(configManager.getEnv())) {
			try {
				configManager.init(properties);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
}