package com.holybell.gateway.outbound.httpclient;

import com.holybell.gateway.filter.HttpRequestFilter;
import com.holybell.gateway.outbound.OutBoundHandler;
import com.holybell.gateway.router.HttpEndpointRouter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpClientOutBoundHandler implements OutBoundHandler {

    private final Logger logger = LoggerFactory.getLogger(HttpClientOutBoundHandler.class);

    private HttpEndpointRouter router;
    private List<String> backendServices;
    private HttpRequestFilter httpRequestFilter;

    public HttpClientOutBoundHandler(HttpEndpointRouter router, List<String> backendServices, HttpRequestFilter httpRequestFilter) {
        this.router = router;
        this.backendServices = backendServices;
        this.httpRequestFilter = httpRequestFilter;
    }

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = this.router.route(this.backendServices);
        if (logger.isInfoEnabled()) {
            logger.info("本次访问的后端服务地址为 : {}", url);
        }
        handleResponse(fullRequest, ctx, doGet(url, fullRequest));
    }

    private void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, byte[] body) {
        FullHttpResponse response = null;
        try {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(body);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);

            // 添加响应头
            httpRequestFilter.postFilter(response, ctx);

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", body.length);

        } catch (Exception e) {
            logger.warn("访问后端服务异常", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }

    private byte[] doGet(String url, FullHttpRequest fullRequest) {
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头
        HttpHeaders headers = fullRequest.headers();
        headers.forEach(header -> {
            httpGet.addHeader(header.getKey(), header.getValue());
            if (logger.isInfoEnabled()) {
                logger.info("添加请求头 key : {} value : {}", header.getKey(), header.getValue());
            }
        });

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            return EntityUtils.toByteArray(response.getEntity());
//            System.out.println("响应状态为:" + response.getStatusLine());
//            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
//            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
