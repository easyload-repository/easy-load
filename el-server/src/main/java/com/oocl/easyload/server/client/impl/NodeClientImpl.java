package com.oocl.easyload.server.client.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.oocl.easyload.model.entity.ELScript;
import com.oocl.easyload.server.client.ClientUtil;
import com.oocl.easyload.server.client.NodeClient;
import com.oocl.easyload.server.client.dto.ResponseDTO;
import com.oocl.easyload.server.client.dto.ScriptDTO;
import com.oocl.easyload.server.client.mapper.ELScriptMapper;
import com.oocl.easyload.server.exception.CommandExecuteException;
import com.oocl.easyload.server.exception.NodeServerNotAvaiableException;
import com.oocl.easyload.server.repository.ELScriptTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** restful client for easyload_node */
@Component
public class NodeClientImpl implements NodeClient {
  private static Logger logger = LoggerFactory.getLogger(NodeClientImpl.class);

  public static final String SUCCESS = "SUCCESS";
  public static final String LOAD_RUNNER = "LoadRunner";
  public static final int TRAIL_RUN_DURATION_SECOND = 60;

  private RestTemplate restTemplate;
  private ELScriptTypeRepository elScriptTypeRepository;

  @Override
  public boolean isActivity(String host, int port) {
    String url = ClientUtil.BASE_URL + "/alive";
    Map<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class, vars);
    return Objects.equals(HttpStatus.OK, response.getStatusCode());
  }

  public boolean initialFolder(String host, int port, String domain) {
    String url = ClientUtil.BASE_URL + "/folder/initial";
    Map<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    ImmutableList<String> domains = ImmutableList.<String>builder().add(domain).build();
    ResponseEntity<Boolean> response =
        restTemplate.postForEntity(url, domains, Boolean.class, vars);
    if (!Objects.equals(HttpStatus.OK, response.getStatusCode())) {
      throw new NodeServerNotAvaiableException();
    }
    return response.getBody() != null && response.getBody();
  }

  @Nullable
  public List<ELScript> scanScript(String host, int port, String domain) {
    String url = ClientUtil.BASE_URL + "/script/domain/{domain}";
    Map<String, Object> vars = ClientUtil.createMapBuilder(host, port).put("domain", domain).build();
    ResponseEntity<List<ScriptDTO>> response =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ScriptDTO>>() {}, vars);
    if (!Objects.equals(HttpStatus.OK, response.getStatusCode())) {
      throw new NodeServerNotAvaiableException();
    }
    List<ELScript> elScripts = new ArrayList<>();
    for (ScriptDTO dto : response.getBody()) {
      ELScript script = ELScriptMapper.toEntity(dto);
      script.setType(elScriptTypeRepository.findByType(dto.getScriptType()));
      elScripts.add(script);
    }
    return elScripts;
  }

  @Override
  public void trailRun(String host, int port, List<ELScript> elScripts) {
    ScriptPartitionContainer container = partitionScript(elScripts);
    String loadRunnerUrl = ClientUtil.BASE_URL + "/node/command/lr/trail";
    String otherUrl = ClientUtil.BASE_URL + "/node/command/execute";
    ImmutableMap<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("duration", String.valueOf(TRAIL_RUN_DURATION_SECOND));
    // split to load runner and normal script

    sendRequest(loadRunnerUrl, vars, params, container.getLoadRunnerScripts());

    sendRequest(otherUrl, vars, params, container.getOtherScripts());
  }

  @Override
  public void execute(String host, int port, int durationSecond, List<ELScript> elScripts) {
    String loadRunnerUrl = ClientUtil.BASE_URL + "/node/command/lr/run";
    String otherUrl = ClientUtil.BASE_URL + "/node/command/execute";
    ImmutableMap<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("duration", String.valueOf(TRAIL_RUN_DURATION_SECOND));
    // split to load runner and normal script
    ScriptPartitionContainer container = partitionScript(elScripts);
    sendRequest(loadRunnerUrl, vars, params, container.getLoadRunnerScripts());
    sendRequest(otherUrl, vars, params, container.getOtherScripts());
  }

  private ScriptPartitionContainer partitionScript(List<ELScript> elScripts) {
    ScriptPartitionContainer container = new ScriptPartitionContainer();
    for (ELScript elScript : elScripts) {
      if (Objects.equals(elScript.getType().getType(), LOAD_RUNNER)) {
        ScriptDTO dto = new ScriptDTO();
        dto.setScriptId(elScript.getScriptId());
        dto.setScriptName(elScript.getName());
        dto.setScriptType(elScript.getType().getType());
        dto.setDomainName(elScript.getElServerFolder().getElAttender().getDomain());
        container.addToLoadRunnerScripts(dto);
      } else {
        ScriptDTO dto = new ScriptDTO();
        dto.setScriptId(elScript.getScriptId());
        dto.setScriptName(elScript.getName());
        dto.setScriptType(elScript.getType().getType());
        dto.setScriptCommand(elScript.getExecuteCmd());
        dto.setDomainName(elScript.getElServerFolder().getElAttender().getDomain());
        container.addToOtherScripts(dto);
      }
    }
    return container;
  }

  @Override
  public void stop(String host, int port, List<ELScript> elScripts) {
    String stopUrl = ClientUtil.BASE_URL + "/node/command/destroy";
    ImmutableMap<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    executeStop(elScripts, stopUrl, vars);
  }

  @Override
  public void stopAll(String host, int port) {
    String stopAllUrl = ClientUtil.BASE_URL + "/node/command/all/kill/7";
    ImmutableMap<String, Object> vars = ClientUtil.createMapBuilder(host, port).build();
    executeStop(null, stopAllUrl, vars);
  }

  private void executeStop(
      List<ELScript> elScripts, String url, ImmutableMap<String, Object> vars) {
    List<ScriptDTO> stopScript = null;
    if (elScripts != null) {
      stopScript = new ArrayList<>();
      for (ELScript elScript : elScripts) {
        ScriptDTO dto = new ScriptDTO();
        dto.setScriptId(elScript.getScriptId());
        stopScript.add(dto);
      }
    }
    if (url != null) {
      sendRequest(url, vars, null, stopScript);
    }
  }

  private void sendRequest(
      String url,
      Map<String, Object> vars,
      MultiValueMap<String, String> params,
      List<ScriptDTO> scriptDTOList) {
    if (scriptDTOList == null || scriptDTOList.isEmpty()) {
      return;
    }
    URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build(vars);
    ResponseEntity<ResponseDTO<List<ScriptDTO>>> stopResponse =
        exchange(
            RequestEntity.post(uri).body(scriptDTOList),
            new ParameterizedTypeReference<ResponseDTO<List<ScriptDTO>>>() {});
    if (!Objects.equals(stopResponse.getBody().getCode(), SUCCESS)) {
      logger.warn("node server can not execute command, uri:{}, body:{}", uri, scriptDTOList);
      throw new CommandExecuteException(stopResponse.getBody().getMsg());
    }
  }

  private <T> ResponseEntity<T> exchange(
      RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
    try {
      logger.debug("call node, {}", requestEntity);
      return restTemplate.exchange(requestEntity, responseType);
    } catch (RestClientException e) {
      logger.error("node server exception", e);
    }
    return null;
  }

  @Autowired
  public void setRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate =
        restTemplateBuilder
            .setReadTimeout(Duration.ofSeconds(10))
            .setConnectTimeout(Duration.ofSeconds(10))
            .build();
  }

  @Autowired
  public void setElScriptTypeRepository(ELScriptTypeRepository elScriptTypeRepository) {
    this.elScriptTypeRepository = elScriptTypeRepository;
  }

  private static class ScriptPartitionContainer {
    private List<ScriptDTO> loadRunnerScripts = new ArrayList<>();
    private List<ScriptDTO> otherScripts = new ArrayList<>();

    @Override
    public String toString() {
      return "ScriptPartitionContainer{"
          + "loadRunnerScripts="
          + loadRunnerScripts
          + ", otherScripts="
          + otherScripts
          + '}';
    }

    public void addToLoadRunnerScripts(ScriptDTO dto) {
      loadRunnerScripts.add(dto);
    }

    public List<ScriptDTO> getLoadRunnerScripts() {
      return loadRunnerScripts;
    }

    public void addToOtherScripts(ScriptDTO dto) {
      otherScripts.add(dto);
    }

    public List<ScriptDTO> getOtherScripts() {
      return otherScripts;
    }
  }
}
