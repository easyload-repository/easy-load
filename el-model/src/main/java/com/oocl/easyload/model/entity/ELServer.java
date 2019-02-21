package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "el_server")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELServer {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String serverId;

  @Column(length = 30)
  private String name;

  @Column(length = 30)
  private String host;

  private int port;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "elServer")
  private List<ELServerFolder> folders = new ArrayList<>();

  /** Node节点的workspace路径。 */
  private String path;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ELServer elServer = (ELServer) o;
    return Objects.equals(serverId, elServer.serverId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverId);
  }

  public String getServerId() {
    return serverId;
  }

  public void setServerId(String serverId) {
    this.serverId = serverId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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

  public List<ELServerFolder> getFolders() {
    return folders;
  }

  public void setFolders(List<ELServerFolder> folders) {
    this.folders = folders;
  }
}
