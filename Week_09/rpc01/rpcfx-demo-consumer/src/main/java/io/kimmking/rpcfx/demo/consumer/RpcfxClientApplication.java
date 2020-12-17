package io.kimmking.rpcfx.demo.consumer;

import io.kimmking.rpcfx.aspects.RpcProxyFactoryAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
@Import(RpcProxyFactoryAspect.class)
public class RpcfxClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcfxClientApplication.class, args);
    }
}
