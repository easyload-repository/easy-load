package com.oocl.easyload.server.repository;

import com.google.common.collect.ImmutableList;
import com.oocl.easyload.model.entity.ELAttender;
import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.model.entity.ELStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ELRoundAttenderRepository extends JpaRepository<ELRoundAttender, String> {

  @Query("select ra from ELRoundAttender ra where ra.elRound.roundId = ?1")
  List<ELRoundAttender> findByRoundId(String elRoundId);

  @Query(
      "select ra from ELRoundAttender ra where ra.elRound.roundId = ?1 and ra.attender.attenderId = ?2")
  ELRoundAttender findByRoundIdAAndAttenderId(String roundId, String attenderId);

  /**
   * 下面2个常量是为这个方法服务的
   *
   * @param attender
   * @param statuses using
   * @param sort
   * @return
   */
  ELRoundAttender findFirstByAttenderAndRunningStatusIn(
          ELAttender attender, List<ELStatus> statuses, Sort sort);

  Sort CREATE_TIME_ASC = Sort.by(Sort.Order.desc("createTime"));

  ImmutableList<ELStatus> IN_PROCESS_STATUSES =
      ImmutableList.<ELStatus>builder()
          .add(ELStatus.IN_PROCESS, ELStatus.TRIAL_RUN_IN_PROGRESS)
          .build();
}
