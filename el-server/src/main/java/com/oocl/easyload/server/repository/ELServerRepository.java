package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ELServerRepository extends JpaRepository<ELServer, String> {}
