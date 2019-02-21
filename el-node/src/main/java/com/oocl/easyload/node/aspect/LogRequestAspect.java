package com.oocl.easyload.node.aspect;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class LogRequestAspect {

  private static final Logger logger = LoggerFactory.getLogger(LogRequestAspect.class);

  @Pointcut("execution(* com.oocl.easyload.node.controller..*.*(..))")
  public void execute() {}

  @Around("execute()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();

    String url = request.getRequestURL().toString();
    String method = request.getMethod();
    String queryString = request.getQueryString();
    Object[] args = pjp.getArgs();
    String params = "";

    if (args.length > 0) {
      if ("GET".equals(method)) {
        params = queryString;
      } else {
        Object object = args[0];
        params = JSON.toJSONString(object);
      }
    }

    logger.info("request url: " + url);
    logger.info("request type: " + method);
    logger.info("request params: " + params);
    logger.info("request queryString: " + queryString);

    Object result = pjp.proceed();
    Gson gson = new Gson();
    logger.info("request result: " + gson.toJson(result));
    return result;
  }

  public static Map<String, Object> getKeyAndValue(Object obj) {

    Map<String, Object> map = new HashMap<>();
    Class userCla = obj.getClass();
    Field[] fs = userCla.getDeclaredFields();
    for (int i = 0; i < fs.length; i++) {
      Field f = fs[i];
      f.setAccessible(true);
      Object val;
      try {
        val = f.get(obj);
        map.put(f.getName(), val);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return map;
  }
}
