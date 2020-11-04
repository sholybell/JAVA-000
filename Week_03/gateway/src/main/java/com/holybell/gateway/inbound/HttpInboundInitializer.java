package com.holybell.gateway.inbound;

import com.holybell.gateway.filter.HttpRequestFilter;
import com.holybell.gateway.outbound.OutBoundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private OutBoundHandler outBoundhandler;
    private HttpRequestFilter httpRequestFilter;

    public HttpInboundInitializer(OutBoundHandler outBoundhandler, HttpRequestFilter httpRequestFilter) {
        this.outBoundhandler = outBoundhandler;
        this.httpRequestFilter = httpRequestFilter;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpInboundHandler(this.outBoundhandler, this.httpRequestFilter));
    }
}
