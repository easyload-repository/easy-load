package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ELRoundRepository extends JpaRepository<ELRound, String> {
  ELRound findFirstByStatus(ELStatus status);
  /**
   * 根据activityId获取最近一次Round
   *
   * @param activityId
   * @return
   */
  ELRound findFirstByElActivity_ActivityIdOrderByCreateTimeDesc(String activityId);

  ELRound findFirstByExpectedStartTimeAfterOrderByExpectedStartTimeAsc(Date after);

  @Query(
      "select r from ELRound r where r.elActivity.activityId = ?1 and r.times = ?2 order by r.version desc")
  List<ELRound> findByELActivityIdAndTimes(String activityId, String times);

  Sort EXPECTED_START_TIME_ASC = Sort.by(Sort.Order.asc("expectedStartTime"));

  @Query(
      "select r.roundId from ELRound r where r.autoTrigger = false  and r.expectedStartTime between :begin and :end and not exists "
          + "(select s from com.oocl.easyload.model.entity.ELRoundAttender s where s.elRound = r and "
          + "(s.runningStatus is null or s.runningStatus <> :status))")
  Optional<String> findIdByExpectedExecuteDateAfterAndNotExistNoReadyAttender(
      @Param("begin") Date begin,
      @Param("end") Date end,
      @Param("status") ELStatus status,
      Sort sort);
}
