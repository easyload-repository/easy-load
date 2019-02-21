package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELAttender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ELAttenderRepository extends JpaRepository<ELAttender, String> {

  @Query("select at from ELAttender at where at.domain = ?1")
  List<ELAttender> findByDomain(String domain);
}
