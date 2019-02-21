package com.oocl.easyload.server.jms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.model.entity.ELAttender;
import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.cache.RunningStatusMonitorCache;
import com.oocl.easyload.server.common.Queue;
import com.oocl.easyload.server.repository.ELAttenderRepository;
import com.oocl.easyload.server.repository.ELRoundAttenderRepository;
import com.oocl.easyload.server.repository.ELRoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class FinishMonitorConsumer {

  private static final Logger logger = LoggerFactory.getLogger(FinishMonitorConsumer.class);

  @Autowired private ELRoundAttenderRepository roundAttenderRepository;

  @Autowired private ELAttenderRepository attenderRepository;

  @Autowired private ELRoundRepository elRoundRepository;

  @JmsListener(destination = Queue.LAST_EVENT_QUEUE)
  @Transactional
  public void receiveEvent(String msg) throws IOException {
    logger.info(
        "Last event consume Message -> [DESTINATION] : "
            + Queue.INTERNAL_EVENT_QUEUE
            + "[Message] : "
            + msg);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(msg);
    String roundId = node.get("roundId").asText();
    String domain = node.get("domain").asText();
    boolean domainStatus = node.get("domainStatus").asBoolean();

    if (domainStatus) {
      List<ELAttender> attenders = attenderRepository.findByDomain(domain);
      if (attenders.size() > 0) {
        ELAttender attender = attenders.get(0);
        String attenderId = attender.getAttenderId();
        ELRoundAttender roundAttender =
            roundAttenderRepository.findByRoundIdAAndAttenderId(roundId, attenderId);

        roundAttender.setRunningStatus(ELStatus.COMPLETE);
        roundAttenderRepository.save(roundAttender);

        // update cache
        RunningStatusMonitorCache.markToComplete(domain);

        // check all domain is completed?
        boolean isAllComplteted = true;
        ELRound elRound = roundAttender.getElRound();
        for (ELRoundAttender rAttender : elRound.getElRoundAttenders()) {
          if (ELStatus.IN_PROCESS.equals(rAttender.getRunningStatus())) {
            isAllComplteted = false;
            break;
          }
        }
        if (isAllComplteted) {
          elRound.setStatus(ELStatus.COMPLETE);
          elRound.setActuallyEndTime(new Date());
          elRoundRepository.save(elRound);
        }
      }
    }
  }
}
