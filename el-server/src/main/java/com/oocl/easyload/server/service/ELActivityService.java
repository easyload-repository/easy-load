package com.oocl.easyload.server.service;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.server.dto.ELActivityDTO;
import com.oocl.easyload.server.dto.ELActivityPageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ELActivityService {
  ELActivityDTO saveELActivity(ELActivityDTO elActivity);

  ELActivityDTO findById(String elActivityId);

  Page<ELActivityPageDTO> findAllELActivity(Integer page, Integer size);

  Page<ELActivityPageDTO> getELActivityByExample(ELActivity example, Pageable pageable);

  ELActivity updateELActivity(ELActivity activity);
}
