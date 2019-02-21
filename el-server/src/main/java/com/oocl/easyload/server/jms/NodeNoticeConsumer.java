package com.oocl.easyload.server.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.server.dto.NodeNoticeDTO;
import com.oocl.easyload.server.exception.IdInvalidException;
import com.oocl.easyload.server.service.ELScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NodeNoticeConsumer {
  private static Logger logger = LoggerFactory.getLogger(NodeNoticeConsumer.class);
  private ELScriptService elScriptService;
  private ObjectMapper objectMapper;

  @JmsListener(destination = "el.node.notice.queue")
  public void receiveEvent(String dtoStr) {
    logger.debug("receive note notice: {}", dtoStr);
    NodeNoticeDTO dto = null;
    try {
      dto = objectMapper.readValue(dtoStr, NodeNoticeDTO.class);
      elScriptService.handleNodeNotice(dto.getScriptId(), dto.getScriptStatus());
    } catch (IOException e) {
      logger.error("can not parse json message. {}", dtoStr);
    } catch (IdInvalidException e) {
      logger.error("do not have scriptId:{} in db", dto.getScriptId());
    }
  }

  @Autowired
  public void setElScriptService(ELScriptService elScriptService) {
    this.elScriptService = elScriptService;
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
