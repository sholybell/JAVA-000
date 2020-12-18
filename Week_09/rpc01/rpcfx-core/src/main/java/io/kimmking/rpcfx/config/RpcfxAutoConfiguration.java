package io.kimmking.rpcfx.config;

import io.kimmking.rpcfx.aspects.RpcProxyFactoryAspect;
import io.kimmking.rpcfx.errorhandler.DefaultRpxfxErrorHandler;
import io.kimmking.rpcfx.errorhandler.RpcfxErrorHandler;
import io.kimmking.rpcfx.registry.RpcServerRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcfxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RpcfxErrorHandler rpcfxErrorHandler() {
        return new DefaultRpxfxErrorHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    // 必须有提供此属性才会加载AOP切面类
    @ConditionalOnProperty(prefix = "rpcfx.aspect", value = "enabled", havingValue = "true", matchIfMissing = false)
    public RpcProxyFactoryAspect rpcProxyFactoryAspect() {
        return new RpcProxyFactoryAspect();
    }

    /**
     * 判断类是否存在@RpcServer注解
     * TODO cnsumer工程这个bean是多余的，加参数控制是否注册Bean?
     */
    @Bean
    public RpcServerRegister rpcServerRegister() {
        return new RpcServerRegister();
    }
}
