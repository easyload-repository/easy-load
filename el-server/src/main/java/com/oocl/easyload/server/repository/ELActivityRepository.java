package com.oocl.easyload.server.repository;

import com.oocl.easyload.model.entity.ELActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ELActivityRepository extends JpaRepository<ELActivity, String> {
  Page<ELActivity> findAllByOrderByCreateTimeDesc(Pageable pageable);
}
