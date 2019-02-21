package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.server.dto.ELActivityDTO;
import com.oocl.easyload.server.dto.ELActivityPageDTO;
import com.oocl.easyload.server.mapper.ELActivityMapper;
import com.oocl.easyload.server.repository.ELActivityRepository;
import com.oocl.easyload.server.service.ELActivityService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ELActivityServiceImpl implements ELActivityService {
  @Resource private ELActivityRepository elActivityRepository;

  @Override
  public ELActivityDTO saveELActivity(ELActivityDTO elActivityDto) {
    return ELActivityMapper.getInstance()
        .toDto(elActivityRepository.save(ELActivityMapper.getInstance().toEntity(elActivityDto)));
  }

  @Override
  public ELActivityDTO findById(String elActivityId) {
    return ELActivityMapper.getInstance().toDto(elActivityRepository.findById(elActivityId).get());
  }

  @Override
  public Page<ELActivityPageDTO> findAllELActivity(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateTime"));
    Page<ELActivity> all = elActivityRepository.findAllByOrderByCreateTimeDesc(pageable);
    return new PageImpl<>(
        ELActivityMapper.getInstance().toDto(all.getContent()), pageable, all.getTotalElements());
  }

  @Override
  public Page<ELActivityPageDTO> getELActivityByExample(ELActivity example, Pageable pageable) {
    Page<ELActivity> activities =
        elActivityRepository.findAll(
            Example.of(
                example,
                ExampleMatcher.matching()
                    .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                    .withIgnoreNullValues()
                    .withIgnoreCase()),
            pageable);
    return new PageImpl<>(
        ELActivityMapper.getInstance().toDto(activities.getContent()),
        pageable,
        activities.getTotalElements());
  }

  @Override
  public ELActivity updateELActivity(ELActivity activity) {
    ELActivity activityReference = elActivityRepository.getOne(activity.getActivityId());
    Optional<ELActivity> activityOptional = Optional.of(activity);
    activityOptional.map(ELActivity::getActivityName).ifPresent(activityReference::setActivityName);
    return elActivityRepository.save(activityReference);
  }
}
