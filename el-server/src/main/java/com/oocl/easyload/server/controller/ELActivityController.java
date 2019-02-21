package com.oocl.easyload.server.controller;

import com.oocl.easyload.model.entity.ELActivity;
import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.server.dto.ELActivityDTO;
import com.oocl.easyload.server.dto.ELActivityPageDTO;
import com.oocl.easyload.server.mapper.ELActivityMapper;
import com.oocl.easyload.server.service.ELActivityService;
import com.oocl.easyload.server.service.ELRoundAttenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activity")
public class ELActivityController {
  @Autowired private ELActivityService elActivityService;
  @Autowired private ELRoundAttenderService elRoundAttenderService;

  @PostMapping
  public ELActivityDTO createELActivity(@RequestBody ELActivityDTO requestDto) {
    return elActivityService.saveELActivity(requestDto);
  }

  @PutMapping
  public ELActivityDTO updateELActivity(@RequestBody ELActivityDTO requestDto) {
    return elActivityService.saveELActivity(requestDto);
  }

  @PatchMapping
  public ELActivityDTO patchUpdateELActivity(@RequestBody ELActivityDTO dto) {
    ELActivity activity = ELActivityMapper.getInstance().toEntity(dto);
    ELActivity activityInDb = elActivityService.updateELActivity(activity);
    return ELActivityMapper.getInstance().toDto(activityInDb);
  }

  @GetMapping
  public Page<ELActivityPageDTO> findAllELActivity(Integer page, Integer size) {
    return elActivityService.findAllELActivity(page, size);
  }

  @GetMapping("/{elActivityId}")
  public ELActivityDTO findById(@PathVariable String elActivityId) {
    return elActivityService.findById(elActivityId);
  }

  @PostMapping("/search")
  public Page<ELActivityPageDTO> searchELActivityByExample(
      int page, int size, @RequestBody ELActivityDTO dto) {
    ELActivity example = ELActivityMapper.getInstance().toEntity(dto);
    Page<ELActivityPageDTO> elActivities =
        elActivityService.getELActivityByExample(
            example, PageRequest.of(page, size, Sort.by(Sort.Order.desc("updateTime"))));
    return elActivities;
  }

  @GetMapping("/{elActivityId}/round/latest/attender")
  public List<String> getLatestRound(@PathVariable String elActivityId) {
    List<ELRoundAttender> elRoundAttenders =
        elRoundAttenderService.getLatestByActivity(elActivityId);
    return elRoundAttenders.stream()
        .map(elRoundAttender -> elRoundAttender.getAttender().getAttenderId())
        .collect(Collectors.toList());
  }
}
