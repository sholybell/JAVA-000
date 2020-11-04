package com.holybell.gateway.router;

import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// 轮询路由器
public class RoundRobinRouter implements HttpEndpointRouter {

    // 记录调用次数，取模轮询
    private AtomicLong num = new AtomicLong(0);

    public String route(List<String> endpoints) {
        Assert.state(endpoints != null && endpoints.size() > 0, "后端服务列表为空!");
        // 原子自增1
        return endpoints.get((int) num.getAndIncrement() % endpoints.size());
    }

}
