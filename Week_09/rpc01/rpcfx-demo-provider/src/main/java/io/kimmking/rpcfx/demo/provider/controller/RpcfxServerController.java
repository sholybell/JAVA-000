package io.kimmking.rpcfx.demo.provider.controller;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpcfxServerController {

    @Autowired
    private RpcfxInvoker invoker;

    /**
     * 通过produces参数方法控制XML响应输出
     */
    @PostMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
//    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        return invoker.invoke(request);
    }
}
