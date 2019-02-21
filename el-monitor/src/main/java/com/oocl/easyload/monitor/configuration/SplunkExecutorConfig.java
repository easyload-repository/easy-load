package com.oocl.easyload.monitor.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class SplunkExecutorConfig {

  private static final Logger logger = LoggerFactory.getLogger(SplunkExecutorConfig.class);
  private static final int COREPOOLSIZE = 10;
  private static final int MAXPOOLSIZE = 10;
  private static final String THREADNAME = "splunk-monitor";
  private static final int QUEUECAPACITY = 99999;

  @Bean
  public Executor asyncServiceExecutor() {
    logger.info("start asyncServiceExecutor");
    ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
    // 配置核心线程数
    executor.setCorePoolSize(COREPOOLSIZE);
    // 配置最大线程数
    executor.setMaxPoolSize(MAXPOOLSIZE);
    // 配置队列大小
    executor.setQueueCapacity(QUEUECAPACITY);
    // 配置线程池中的线程的名称前缀
    executor.setThreadNamePrefix(THREADNAME);
    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // 执行初始化
    executor.initialize();
    return executor;
  }
}
