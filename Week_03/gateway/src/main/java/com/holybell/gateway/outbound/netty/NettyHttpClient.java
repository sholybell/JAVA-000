package com.holybell.gateway.outbound.netty;

import com.holybell.gateway.filter.HttpRequestFilter;
import com.holybell.gateway.router.HttpEndpointRouter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * 使用Netty模拟HTTP客户端进行请求操作
 */
public class NettyHttpClient {

    private HttpEndpointRouter router;
    private List<String> backendServices;
    private HttpRequestFilter httpRequestFilter;

    public NettyHttpClient() {

    }

    public NettyHttpClient(HttpEndpointRouter router, List<String> backendServices, HttpRequestFilter httpRequestFilter) {
        this.router = router;
        this.backendServices = backendServices;
        this.httpRequestFilter = httpRequestFilter;
    }

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
//                    ch.pipeline().addLast(new HttpResponseDecoder());
//                    //客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
//                    ch.pipeline().addLast(new HttpRequestEncoder());
//                    ch.pipeline().addLast(new NettyHttpClientOutboundHandler());

                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(65536));
                    ch.pipeline().addLast(new HttpContentDecompressor());
                    ch.pipeline().addLast(new NettyHttpClientOutboundHandler());

                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

//            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        NettyHttpClient client = new NettyHttpClient();
        client.connect("127.0.0.1", 8802);
    }
}