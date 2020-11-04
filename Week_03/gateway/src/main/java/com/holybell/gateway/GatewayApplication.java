package com.holybell.gateway;

import com.holybell.gateway.filter.DefaultHttpRequestFilter;
import com.holybell.gateway.outbound.httpclient.HttpClientOutBoundHandler;
import com.holybell.gateway.router.RoundRobinRouter;
import com.holybell.gateway.server.HttpServer;

import java.util.Arrays;
import java.util.List;

public class GatewayApplication {

    public static void main(String[] args) throws Exception {
        // 后台服务列表
        List<String> backendServices = Arrays.asList("http://localhost:8801", "http://localhost:8802", "http://localhost:8803");
        DefaultHttpRequestFilter defaultHttpRequestFilter = new DefaultHttpRequestFilter();
        HttpServer httpServer = new HttpServer(8888,
                new HttpClientOutBoundHandler(new RoundRobinRouter(), backendServices, defaultHttpRequestFilter),
                defaultHttpRequestFilter);
        httpServer.run();
    }
}
