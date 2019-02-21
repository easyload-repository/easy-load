package com.oocl.easyload.monitor.factory;

import com.splunk.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * SplunkServiceFactory Tester.
 *
 * @author "Leon Zhang"
 * @since
 *     <pre>12/22/2018</pre>
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SplunkServiceFactoryTest {

  @Before
  public void before() throws Exception {}

  @After
  public void after() throws Exception {}

  @Test
  public void should_return_prod_service_when_create_service_given_prod_properties() {
    Service service = SplunkServiceFactory.of("prod");
    System.out.println(service);
    assertEquals("iris4_poweruser", service.getUsername());
  }

  @Test
  public void should_return_qa_service_when_create_service_given_prod_properties() {
    Service service = SplunkServiceFactory.of("qa");
    System.out.println(service);
    assertEquals("iris4view", service.getUsername());
  }
}
