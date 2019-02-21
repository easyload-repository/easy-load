package com.oocl.easyload.server.mapper;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.dto.ELActivityDTO;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ELActivityMapperTest {

  private ELActivityMapper elActivityMapper;

  @Before
  public void setup() {
    elActivityMapper = ELActivityMapper.getInstance();
  }

  @Test
  public void should_return_latest_round_when_getLatestRounds_given_all_new_status_round_list()
      throws Exception {
    // Given
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ELRound r1 = getElRound("round1", format.parse("2018-12-20 08:00:00"), ELStatus.NEW);
    ELRound r2 = getElRound("round2", format.parse("2018-12-20 09:00:00"), ELStatus.NEW);
    ELRound r3 = getElRound("round3", format.parse("2018-12-20 10:00:00"), ELStatus.NEW);
    List<ELRound> rounds = Arrays.asList(r1, r3, r2);
    // When
    ELRound actuallyResult = Whitebox.invokeMethod(elActivityMapper, "getLatestRound", rounds);
    // Then
    assertEquals("2018-12-20 10:00:00", format.format(actuallyResult.getExpectedStartTime()));
  }

  @Test
  public void should_return_latest_round_when_getLatestRounds_given_no_new_status_round_list()
      throws Exception {
    // Given
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ELRound r1 = getElRound("round1", format.parse("2018-12-20 08:00:00"), ELStatus.COMPLETE);
    ELRound r2 = getElRound("round2", format.parse("2018-12-20 09:00:00"), ELStatus.COMPLETE);
    ELRound r3 = getElRound("round3", format.parse("2018-12-20 10:00:00"), ELStatus.COMPLETE);
    List<ELRound> rounds = Arrays.asList(r1, r3, r2);
    // When
    ELRound actuallyResult = Whitebox.invokeMethod(elActivityMapper, "getLatestRound", rounds);
    // Then
    assertNull(actuallyResult);
  }

  @Test
  public void
      should_return_latest_round_when_getLatestRounds_given_one_in_process_status_round_list()
          throws Exception {
    // Given
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ELRound r1 = getElRound("round1", format.parse("2018-12-20 08:00:00"), ELStatus.IN_PROCESS);
    ELRound r2 = getElRound("round2", format.parse("2018-12-20 09:00:00"), ELStatus.COMPLETE);
    ELRound r3 = getElRound("round3", format.parse("2018-12-20 10:00:00"), ELStatus.COMPLETE);
    List<ELRound> rounds = Arrays.asList(r1, r3, r2);
    // When
    ELRound actuallyResult = Whitebox.invokeMethod(elActivityMapper, "getLatestRound", rounds);
    // Then
    assertEquals("2018-12-20 08:00:00", format.format(actuallyResult.getExpectedStartTime()));
  }

  @Test
  public void should_return_activity_dto_with_round_when_toDto_given_activity_with_rounds() {
    // Given
    ELActivity elActivity = getElActivity("id1", "name1");
    // When
    ELActivityDTO actuallyDto = elActivityMapper.toDto(elActivity);
    // Then
    assertEquals(3, actuallyDto.getRounds().size());
    assertEquals("id1", actuallyDto.getRounds().get(0).getActivityId());
    assertEquals("id1", actuallyDto.getRounds().get(1).getActivityId());
    assertEquals("name1", actuallyDto.getRounds().get(0).getActivityName());
    assertEquals("name1", actuallyDto.getRounds().get(1).getActivityName());
  }

  private ELActivity getElActivity(String id, String name) {
    ELActivity elActivity = new ELActivity();
    elActivity.setActivityId(id);
    elActivity.setActivityName(name);
    ELRound r1 = getElRound("round1", new Date(), ELStatus.IN_PROCESS);
    ELRound r2 = getElRound("round2", new Date(), ELStatus.COMPLETE);
    ELRound r3 = getElRound("round3", new Date(), ELStatus.COMPLETE);
    r1.setElActivity(elActivity);
    r2.setElActivity(elActivity);
    r3.setElActivity(elActivity);
    elActivity.setRounds(Arrays.asList(r1, r2, r3));
    return elActivity;
  }

  private ELRound getElRound(String roundName, Date date, ELStatus status) {
    ELRound r1 = new ELRound();
    r1.setRoundName(roundName);
    r1.setExpectedStartTime(date);
    r1.setStatus(status);
    return r1;
  }
}
