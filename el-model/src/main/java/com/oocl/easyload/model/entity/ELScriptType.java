package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_script_type")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELScriptType {

  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String typeId;

  @Column(length = 30)
  private String type;

  private String trailRunTemplate;
  private String executeTemplate;
  private String stopTemplate;
  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTrailRunTemplate() {
    return trailRunTemplate;
  }

  public void setTrailRunTemplate(String trailRunTemplate) {
    this.trailRunTemplate = trailRunTemplate;
  }

  public String getExecuteTemplate() {
    return executeTemplate;
  }

  public void setExecuteTemplate(String executeTemplate) {
    this.executeTemplate = executeTemplate;
  }

  public String getStopTemplate() {
    return stopTemplate;
  }

  public void setStopTemplate(String stopTemplate) {
    this.stopTemplate = stopTemplate;
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
