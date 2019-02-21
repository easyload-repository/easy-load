package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_check_rule")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELCheckRule {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String checkRuleId;

  @Column(length = 30)
  private String type;

  @Column(length = 30)
  private String description;

  @Column(length = 30)
  private String rule;

  @Column(length = 30)
  private String comparator;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  @Column(length = 30)
  private String level;

  public String getCheckRuleId() {
    return checkRuleId;
  }

  public void setCheckRuleId(String checkRuleId) {
    this.checkRuleId = checkRuleId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getComparator() {
    return comparator;
  }

  public void setComparator(String comparator) {
    this.comparator = comparator;
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

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }
}
