package com.oocl.easyload.model.entity.el_monitor;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "el_monitor_splunk_search_details")
@EntityListeners(AuditingEntityListener.class)
public class SplunkSearchDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(columnDefinition = "text")
  private String intervalUrl;

  @Column(columnDefinition = "text")
  private String roundUrl;

  public SplunkSearchDetails() {}

  public SplunkSearchDetails(String intervalUrl, String roundUrl) {
    this.intervalUrl = intervalUrl;
    this.roundUrl = roundUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIntervalUrl() {
    return intervalUrl;
  }

  public void setIntervalUrl(String intervalUrl) {
    this.intervalUrl = intervalUrl;
  }

  public String getRoundUrl() {
    return roundUrl;
  }

  public void setRoundUrl(String roundUrl) {
    this.roundUrl = roundUrl;
  }
}
