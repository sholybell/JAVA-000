package io.kimmking.rpcfx.aspects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import io.kimmking.rpcfx.annotations.RpcClient;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.errorhandler.RpcfxErrorHandler;
import io.kimmking.rpcfx.exception.RpcfxException;
import io.kimmking.rpcfx.util.HttpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RpcProxyFactoryAspect {

    @Autowired(required = false)
    @Qualifier("rpcfxErrorHandler")
    private RpcfxErrorHandler rpcfxErrorHandler;

    private Logger logger = LoggerFactory.getLogger(RpcProxyFactoryAspect.class);

    private XStream xstream = new XStream(new StaxDriver());

    {
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new String[]{"io.kimmking.rpcfx.api.RpcfxResponse", "io.kimmking.rpcfx.demo.api.model.Order", "io.kimmking.rpcfx.demo.api.model.User"});
    }

    // 是否降级处理
    @Value("${rpcfx.fallback}")
    private boolean fallback = Boolean.FALSE;

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }

    @Pointcut("execution(* io.kimmking.rpcfx.demo.api.service.*.*(..))")
    public void pointcut() {
        // 直接拦截指定包中的所有方法
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(joinPoint.getSignature().getDeclaringType().getInterfaces()[0]);
        request.setMethod(joinPoint.getSignature().getName());
        request.setParams(joinPoint.getArgs());
        String url = getUrl(joinPoint);

        if (StringUtils.isEmpty(url)) {
            throw new RpcfxException("请检查是否有 @RpcClient 注解，以及是否配置了url属性!");
        }

        try {
            RpcfxResponse response = post(request, url);
            if (response.isStatus()) {
                return response.getResult();
            }
            throw response.getException();
        } catch (Exception ex) {
            // 降级处理
            if (fallback) {
                return joinPoint.proceed();
            }
            // 如果有自定义的异常处理器，则捕获异常进行处理
            if (rpcfxErrorHandler != null) {
                rpcfxErrorHandler.handleError(ex);
                return null;
            }
            throw ex;
        }
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        if (logger.isInfoEnabled()) {
            logger.info("request json : {}", reqJson);
        }

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client

        // 使用HttpUtil作为可复用的client
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json; charset=utf-8");
        String respJson = HttpUtil.post(url, JSON.toJSONString(req), header, 30000);
        if (logger.isInfoEnabled()) {
            logger.info("resp json/xml : {}", respJson);
        }
        if (respJson.contains("<")) {    // TODO 如何更加优雅的判断XML类型?
            return (RpcfxResponse) xstream.fromXML(respJson);
        } else {
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }

    /*
     * 获取方法上面的注解对象
     */
    private String getUrl(ProceedingJoinPoint joinPoint) {
        RpcClient annotation = joinPoint.getTarget().getClass().getAnnotation(RpcClient.class);
        if (annotation != null) {
            return annotation.url();
        }
        return null;
    }

}

