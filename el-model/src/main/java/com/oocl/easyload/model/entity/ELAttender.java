package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_attender")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELAttender {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String attenderId;

  @Column(length = 32)
  private String domain;

  @Column(length = 30)
  private String owner;

  @Column(length = 30)
  private String cluster;

  @OneToOne(mappedBy = "elAttender")
  private ELServerFolder elServerFolder;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public String getAttenderId() {
    return attenderId;
  }

  public void setAttenderId(String attenderId) {
    this.attenderId = attenderId;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
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

  public ELServerFolder getElServerFolder() {
    return elServerFolder;
  }

  public void setElServerFolder(ELServerFolder elServerFolder) {
    this.elServerFolder = elServerFolder;
  }

  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }
}
