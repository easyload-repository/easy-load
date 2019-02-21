package com.oocl.easyload.monitor.dto;

import com.oocl.easyload.monitor.constant.Tips;
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
public class RoundStartDTO {
  private static final Logger logger = LoggerFactory.getLogger(RoundStartDTO.class);

  private static List<String> envs = Arrays.asList("qa1", "qa2", "qa3", "qa4");

  String activeId;
  String roundId;
  List<String> domains;
  int interval;
  String earliest;
  String latest;
  String env;

  public boolean validate() {
    if (this.getDomains().size() == 0) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_DOMAINS_NULL);
      return false;
    }
    if (StringUtils.isBlank(this.getActiveId())) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_ACTIVEID_NULL);
      return false;
    }
    if (StringUtils.isBlank(this.getRoundId())) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_ROUNDID_NULL);
      return false;
    }
    if (this.getInterval() == 0) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_INTERVAL_NULL);
      return false;
    }
    if (StringUtils.isBlank(this.getEarliest())) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_EARLIEST_NULL);
      return false;
    }
    if (StringUtils.isBlank(this.getLatest())) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_LATEST_NULL);
      return false;
    }
    if (StringUtils.isBlank(this.getEnv())) {
      logger.info("RoundStartDTO.java - validate - " + Tips.START_ROUND_ENVIRONMENT_NULL);
      return false;
    }
    if (!RoundStartDTO.envs.contains(this.getEnv())) {
      logger.info(
          "RoundStartDTO.java - validate - ["
              + this.getEnv()
              + "]"
              + Tips.START_ROUND_ENVIRONMENT_NOT_VALIDATE);
      return false;
    }
    return true;
  }
}
