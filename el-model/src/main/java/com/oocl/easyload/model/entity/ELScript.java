package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/** {@link ELAttender} 可以执行的脚本单元。 */
@Entity
@Table(name = "el_script")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELScript {

  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String scriptId;

  private String name;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "typeId", referencedColumnName = "typeId")
  private ELScriptType type;

  private String path;

  @ManyToOne
  @JoinColumn(name = "server_folder_id", referencedColumnName = "folderId")
  private ELServerFolder elServerFolder;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "serverId", referencedColumnName = "serverId")
  private ELServer server;

  private String trailRunCmd;
  private String executeCmd;
  private String stopCmd;
  private int vUser;
  private float pacing;
  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;
  /** 说明该script被选择上了 */
  private Boolean lastExecute;

  public String getScriptId() {
    return scriptId;
  }

  public void setScriptId(String scriptId) {
    this.scriptId = scriptId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ELScriptType getType() {
    return type;
  }

  public void setType(ELScriptType type) {
    this.type = type;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ELServerFolder getElServerFolder() {
    return elServerFolder;
  }

  public void setElServerFolder(ELServerFolder elServerFolder) {
    this.elServerFolder = elServerFolder;
  }

  public ELServer getServer() {
    return server;
  }

  public void setServer(ELServer server) {
    this.server = server;
  }

  public String getTrailRunCmd() {
    return trailRunCmd;
  }

  public void setTrailRunCmd(String trailRunCmd) {
    this.trailRunCmd = trailRunCmd;
  }

  public String getExecuteCmd() {
    return executeCmd;
  }

  public void setExecuteCmd(String executeCmd) {
    this.executeCmd = executeCmd;
  }

  public String getStopCmd() {
    return stopCmd;
  }

  public void setStopCmd(String stopCmd) {
    this.stopCmd = stopCmd;
  }

  public int getvUser() {
    return vUser;
  }

  public void setvUser(int vUser) {
    this.vUser = vUser;
  }

  public float getPacing() {
    return pacing;
  }

  public void setPacing(float pacing) {
    this.pacing = pacing;
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

  public Boolean getLastExecute() {
    return lastExecute;
  }

  public void setLastExecute(Boolean lastExecute) {
    this.lastExecute = lastExecute;
  }
}
