package com.holybell.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpRequestFilter {

    void preFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

    void postFilter(FullHttpResponse fullResponse, ChannelHandlerContext ctx);
}
