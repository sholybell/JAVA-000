package com.holybell.gateway.router;

import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机根据后台服务列表访问
 */
public class RandomRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        Assert.state(endpoints != null && endpoints.size() > 0, "后端服务列表为空!");
        int num = ThreadLocalRandom.current().nextInt(0, endpoints.size());
        return endpoints.get(num);
    }
}
