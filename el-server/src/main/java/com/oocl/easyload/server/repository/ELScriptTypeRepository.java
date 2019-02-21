package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELScriptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ELScriptTypeRepository extends JpaRepository<ELScriptType, String> {
  ELScriptType findByType(String type);
}
