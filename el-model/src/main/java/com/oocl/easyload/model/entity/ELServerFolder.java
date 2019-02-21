package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "el_server_folder")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELServerFolder {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String folderId;

  @ManyToOne
  @JoinColumn(name = "server_id", referencedColumnName = "serverId")
  private ELServer elServer;

  @OneToOne
  @JoinColumn(name = "el_attender_id", referencedColumnName = "attenderId")
  private ELAttender elAttender;

  @OneToMany(
      mappedBy = "elServerFolder",
      cascade = {CascadeType.MERGE, CascadeType.PERSIST},
      orphanRemoval = true)
  private List<ELScript> elScripts = new ArrayList<>();

  private String scriptPath;

  /** 表示Node是否已经创建对应的文件夹 */
  private Boolean initFolder = false;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public String getFolderId() {
    return folderId;
  }

  public void setFolderId(String folderId) {
    this.folderId = folderId;
  }

  public List<ELScript> getElScripts() {
    return elScripts;
  }

  public void setElScripts(List<ELScript> elScripts) {
    this.elScripts = elScripts;
  }

  public void clearElScripts() {
    elScripts.forEach(elScript -> elScript.setElServerFolder(null));
    elScripts.clear();
  }

  public ELServer getElServer() {
    return elServer;
  }

  public void setElServer(ELServer elServer) {
    this.elServer = elServer;
  }

  public ELAttender getElAttender() {
    return elAttender;
  }

  public void setElAttender(ELAttender elAttender) {
    this.elAttender = elAttender;
  }

  public String getScriptPath() {
    return scriptPath;
  }

  public void setScriptPath(String scriptPath) {
    this.scriptPath = scriptPath;
  }

  public Boolean getInitFolder() {
    return initFolder;
  }

  public void setInitFolder(Boolean initFolder) {
    this.initFolder = initFolder;
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
