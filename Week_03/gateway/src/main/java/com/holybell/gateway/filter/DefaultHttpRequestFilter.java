package com.holybell.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class DefaultHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void preFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        // 添加请求头
        fullRequest.headers().add("nio", "suyongbin");
    }

    @Override
    public void postFilter(FullHttpResponse fullResponse, ChannelHandlerContext ctx) {
        // 添加响应头
        fullResponse.headers().add("nio", "suyongbin");
    }
}
