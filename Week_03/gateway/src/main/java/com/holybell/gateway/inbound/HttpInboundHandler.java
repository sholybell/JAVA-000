package com.holybell.gateway.inbound;

import com.holybell.gateway.filter.HttpRequestFilter;
import com.holybell.gateway.outbound.OutBoundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    private OutBoundHandler handler;
    private HttpRequestFilter httpRequestFilter;

    public HttpInboundHandler(OutBoundHandler outBoundhandler, HttpRequestFilter filter) {
        this.handler = outBoundhandler;
        this.httpRequestFilter = filter;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
//            String uri = fullRequest.uri();
//            //logger.info("接收到的请求url为{}", uri);
//            if (uri.contains("/test")) {
//                handlerTest(fullRequest, ctx);
//            }

            httpRequestFilter.preFilter(fullRequest, ctx);
            handler.handle(fullRequest, ctx);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

////    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
////        FullHttpResponse response = null;
////        try {
////            String value = "hello,kimmking";
////            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
////            response.headers().set("Content-Type", "application/json");
////            response.headers().setInt("Content-Length", response.content().readableBytes());
////
////        } catch (Exception e) {
////            logger.error("处理测试接口出错", e);
////            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
////        } finally {
////            if (fullRequest != null) {
////                if (!HttpUtil.isKeepAlive(fullRequest)) {
////                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
////                } else {
////                    response.headers().set(CONNECTION, KEEP_ALIVE);
////                    ctx.write(response);
////                }
////            }
////        }
////    }
////
////    @Override
////    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
////        cause.printStackTrace();
////        ctx.close();
////    }
//
}
