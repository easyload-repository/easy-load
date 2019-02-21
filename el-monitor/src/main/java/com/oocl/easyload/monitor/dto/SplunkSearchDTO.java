package com.oocl.easyload.monitor.dto;

import com.oocl.easyload.monitor.constant.Tips;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SplunkSearchDTO {
  private static List<String> envs = Arrays.asList("qa1", "qa2", "qa3", "qa4", "prod");
  private static final Logger logger = LoggerFactory.getLogger(SplunkSearchDTO.class);

  private String env;
  private String criteria;
  private String type;

  public boolean validate() {
    if (StringUtils.isBlank(this.getEnv())) {
      logger.info("SplunkSearchDTO.java - validate - " + Tips.SPLUNK_SEARCH_ENV_NULL);
      return false;
    }
    if (StringUtils.isBlank(criteria)) {
      logger.info("SplunkSearchDTO.java - validate - " + Tips.SPLUNK_SEARCH_CRITERIA_NULL);
      return false;
    }
    if (StringUtils.isBlank(type)) {
      logger.info("SplunkSearchDTO.java - validate - " + Tips.SPLUNK_SEARCH_TYPE_NULL);
      return false;
    }
    if (!SplunkSearchDTO.envs.contains(this.getEnv())) {
      logger.info("SplunkSearchDTO.java - validate - " + Tips.SPLUNK_SEARCH_ENV_NOT_VALIDATION);
      return false;
    }
    return true;
  }
}
