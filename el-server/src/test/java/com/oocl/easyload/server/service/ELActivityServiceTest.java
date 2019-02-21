package com.oocl.easyload.server.service;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.server.dto.ELActivityDTO;
import com.oocl.easyload.server.mapper.ELActivityMapper;
import com.oocl.easyload.server.repository.ELActivityRepository;
import com.oocl.easyload.server.service.impl.ELActivityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ELActivityServiceTest {

  @Mock private ELActivityRepository elActivityRepository;

  @InjectMocks private ELActivityServiceImpl elActivityService;

  @Before
  public void setup() {
    elActivityService = new ELActivityServiceImpl();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void should_return_elactivity_when_saveELActivity_given_activity_name() {
    // Given
    ELActivity elActivity = new ELActivity();
    elActivity.setActivityName("kateActivity");
    ELActivity expectedELActivity = new ELActivity();
    expectedELActivity.setActivityName("kateActivity");
    expectedELActivity.setActivityId("id");
    when(elActivityRepository.save(any())).thenReturn(expectedELActivity);
    // when
    ELActivityDTO actualResult =
        elActivityService.saveELActivity(ELActivityMapper.getInstance().toDto(elActivity));
    // Then
    assertEquals("kateActivity", actualResult.getActivityName());
    assertEquals("id", actualResult.getActivityId());
  }
}
