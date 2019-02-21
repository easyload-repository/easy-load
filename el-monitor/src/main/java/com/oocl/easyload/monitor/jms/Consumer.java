package com.oocl.easyload.monitor.jms;

import com.oocl.easyload.monitor.constant.Queue;
import com.oocl.easyload.monitor.constant.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

  @Autowired Producer producer;

  @JmsListener(destination = Topic.EL_MONITOR_LATEST_TOPIC)
  public void receiveQueue(String msg) {
    producer.sendMessage(Queue.EL_SMART_LATEST_QUEUE, msg);
    producer.sendMessage(Queue.EL_PROJECT_LATEST_QUEUE, msg);
    logger.info(
        "Consume Message -> [DESTINATION] : "
            + Topic.EL_MONITOR_LATEST_TOPIC
            + ",[Message] : "
            + msg);
  }
}
