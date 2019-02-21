package com.oocl.easyload.monitor.repository;

import com.oocl.easyload.model.entity.el_monitor.SplunkSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplunkSearchRepository extends JpaRepository<SplunkSearch, String> {}
