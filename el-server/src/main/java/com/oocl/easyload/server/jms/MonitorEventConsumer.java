package com.oocl.easyload.server.jms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.model.entity.el_monitor.SplunkSearch;
import com.oocl.easyload.server.cache.ExceptionSearchCache;
import com.oocl.easyload.server.cache.WebServiceSearchCache;
import com.oocl.easyload.server.common.Queue;
import com.oocl.easyload.server.repository.SplunkSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class MonitorEventConsumer {

  private static final Logger logger = LoggerFactory.getLogger(MonitorEventConsumer.class);

  @Autowired private SplunkSearchRepository splunkSearchRepository;

  @JmsListener(destination = Queue.INTERNAL_EVENT_QUEUE)
  public void receiveEvent(String msg) throws IOException {
    logger.info(
        "Consume Message -> [DESTINATION] : " + Queue.INTERNAL_EVENT_QUEUE + "[Message] : " + msg);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(msg);
    String searchId = node.get("searchId").asText();

    Optional<SplunkSearch> splunkSearchOpt = splunkSearchRepository.findById(searchId);
    if (splunkSearchOpt.isPresent()) {
      if ("exception".equals(splunkSearchOpt.get().getType())) {
        ExceptionSearchCache.putIfAbsent(splunkSearchOpt.get());
      } else if ("ws".equals(splunkSearchOpt.get().getType())) {
        WebServiceSearchCache.putIfAbsent(splunkSearchOpt.get());
      }
    }
  }
}
