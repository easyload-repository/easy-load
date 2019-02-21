package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "el_round_attender")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELRoundAttender {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String roundAttenderId;

  @ManyToOne
  @JoinColumn(name = "el_round_id", referencedColumnName = "roundId")
  private ELRound elRound;

  @ManyToOne
  @JoinColumn(name = "attenderId", referencedColumnName = "attenderId")
  private ELAttender attender;

  /** NEW / IN_PROCESS / FAILED / COMPLETE */
  @Column(length = 30)
  @Enumerated(value = EnumType.STRING)
  private ELStatus runningStatus = ELStatus.NEW;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "roundAttenderId", referencedColumnName = "roundAttenderId")
  private List<ELRoundSplunk> checkPoints;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public String getRoundAttenderId() {
    return roundAttenderId;
  }

  public void setRoundAttenderId(String roundAttenderId) {
    this.roundAttenderId = roundAttenderId;
  }

  public ELRound getElRound() {
    return elRound;
  }

  public void setElRound(ELRound elRound) {
    this.elRound = elRound;
  }

  public ELAttender getAttender() {
    return attender;
  }

  public void setAttender(ELAttender attender) {
    this.attender = attender;
  }

  public ELStatus getRunningStatus() {
    return runningStatus;
  }

  public void setRunningStatus(ELStatus runningStatus) {
    this.runningStatus = runningStatus;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
