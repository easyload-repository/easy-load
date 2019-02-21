package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELServerFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ELServerFolderRepository extends JpaRepository<ELServerFolder, String> {
  ELServerFolder findByElAttender_AttenderId(String attenderId);
}
