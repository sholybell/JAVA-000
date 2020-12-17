package io.kimmking.rpcfx.config;

import io.kimmking.rpcfx.errorhandler.DefaultRpxfxErrorHandler;
import io.kimmking.rpcfx.errorhandler.RpcfxErrorHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcfxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RpcfxErrorHandler rpcfxErrorHandler() {
        return new DefaultRpxfxErrorHandler();
    }
}
