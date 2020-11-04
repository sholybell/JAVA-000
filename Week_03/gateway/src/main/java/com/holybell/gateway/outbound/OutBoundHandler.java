package com.holybell.gateway.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface OutBoundHandler {

    void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
}
