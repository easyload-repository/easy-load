package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.dto.ELRoundAttenderDTO;
import com.oocl.easyload.server.dto.ELRoundDTO;
import com.oocl.easyload.server.service.ELRoundAttenderService;
import com.oocl.easyload.server.service.ELRoundService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

public class ELRoundServiceImplTest {
  @Mock private ELRoundAttenderService elRoundAttenderService;
  @InjectMocks private ELRoundService elRoundService;

  @Before
  public void setup() {
    elRoundService = new ELRoundServiceImpl();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void
      should_return_removed_round_attender_when_getRemoveELAttenders_given_removed_round_attender()
          throws Exception {
    // Given
    when(elRoundAttenderService.findByRoundId(any())).thenReturn(getOldElRoundAttenders());
    // When
    List<ELRoundAttender> actuallyResult =
        Whitebox.invokeMethod(elRoundService, "getRemoveELAttenders", getNewElRoundDTO());
    // Then
    assertEquals(1, actuallyResult.size());
    assertEquals("roundAttender2", actuallyResult.get(0).getRoundAttenderId());
  }

  @Test
  public void should_return_empty_list_when_getRemoveELAttenders_given_no_removed_round_attender()
      throws Exception {
    // Given
    ELRoundAttender elRoundAttender1 = new ELRoundAttender();
    elRoundAttender1.setRoundAttenderId("roundAttender1");
    when(elRoundAttenderService.findByRoundId(any())).thenReturn(Arrays.asList(elRoundAttender1));
    // When
    List<ELRoundAttender> actuallyResult =
        Whitebox.invokeMethod(elRoundService, "getRemoveELAttenders", getNewElRoundDTO());
    // Then
    assertEquals(0, actuallyResult.size());
  }

  private ELRoundDTO getNewElRoundDTO() {
    ELRoundDTO newElRoundDTO = new ELRoundDTO();
    newElRoundDTO.setActivityId("activity id");
    newElRoundDTO.setActivityName("activity name");
    newElRoundDTO.setStatus(ELStatus.NEW);
    ELRoundAttenderDTO elRoundAttenderDTO1 = new ELRoundAttenderDTO();
    List<ELRoundAttenderDTO> elRoundAttenderDtos = Arrays.asList(elRoundAttenderDTO1);
    elRoundAttenderDTO1.setRoundAttenderId("roundAttender1");
    newElRoundDTO.setElRoundAttenderDtos(elRoundAttenderDtos);
    return newElRoundDTO;
  }

  private List<ELRoundAttender> getOldElRoundAttenders() {
    ELRoundAttender elRoundAttender1 = new ELRoundAttender();
    ELRoundAttender elRoundAttender2 = new ELRoundAttender();
    elRoundAttender1.setRoundAttenderId("roundAttender1");
    elRoundAttender2.setRoundAttenderId("roundAttender2");
    return Arrays.asList(elRoundAttender1, elRoundAttender2);
  }
}
