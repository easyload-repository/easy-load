package com.oocl.easyload.monitor.splunk;

import com.oocl.easyload.monitor.exceptions.UnexpectedException;
import com.splunk.Service;
import com.splunk.ServiceArgs;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrdSplunkService {
  private static final Logger logger = LoggerFactory.getLogger(PrdSplunkService.class);

  public static String username = "";
  public static String password = "";
  public static String host = "";
  public static String port = "";
  static final String configPath = "/splunk-sdk.properties";

  static {
    Properties properties = new Properties();
    InputStream stream = PrdSplunkService.class.getResourceAsStream(configPath);
    if (stream == null) {
      throw new UnexpectedException();
    }
    try {
      properties.load(stream);
      username = properties.getProperty("splunk.prod.username");
      if (StringUtils.isNotBlank(username)) username = username.trim();
      password = properties.getProperty("splunk.prod.password");
      if (StringUtils.isNotBlank(password)) password = password.trim();
      host = properties.getProperty("splunk.prod.host");
      if (StringUtils.isNotBlank(host)) host = host.trim();
      port = properties.getProperty("splunk.prod.port");
      if (StringUtils.isNotBlank(port)) port = port.trim();
      stream.close();
      logger.info("PrdSplunkService.java - static - Prod splunk properties initWs successful!");
    } catch (IOException e) {
      e.printStackTrace();
      throw new UnexpectedException();
    }
  }

  public static Service create() {
    try {
      // Create a map of arguments and add login parameters
      ServiceArgs serviceArgs = new ServiceArgs();
      serviceArgs.setUsername(username);
      serviceArgs.setPassword(password);
      serviceArgs.setHost(host);
      serviceArgs.setPort(Integer.parseInt(port));
      // Create a Service instance and log in with the argument map
      Service service = Service.connect(serviceArgs);
      return service;
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("PrdSplunkService.java - create - Prod Splunk service initWs fail!");
      throw new UnexpectedException();
    }
  }
}
