package com.tal.zj.websocket.handler;

import com.tal.zj.websocket.adaptor.DefaultWebSocketListenerAdaptor;
import com.tal.zj.websocket.config.WebSocketServerConfig;
import com.tal.zj.websocket.adaptor.WebSocketListenerAdaptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private WebSocketServerConfig config;
    private WebSocketListenerAdaptor adaptor;

    public HttpServerHandler(WebSocketServerConfig config) {
        this.config = config;
        this.adaptor = new DefaultWebSocketListenerAdaptor(config.getListener());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        try {
            handleHttpRequest(ctx, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //校验
        validate(request);
        //握手
        handshake(ctx, request);
    }

    private void validate(FullHttpRequest request) throws Exception {
        //校验解码结果
        if (!request.decoderResult().isSuccess()) {
            throw new Exception();
        }
        //校验请求方法
        if (!HttpMethod.GET.equals(request.method())) {
            throw new Exception();
        }
        //校验请求头
        if (!request.headers().contains(HttpHeaderNames.HOST) ||
                !request.headers().contains(HttpHeaderNames.CONNECTION) || !request.headers().get(HttpHeaderNames.CONNECTION).equals("Upgrade") ||
                !request.headers().contains(HttpHeaderNames.UPGRADE) || !request.headers().get(HttpHeaderNames.UPGRADE).equals("websocket") ||
                !request.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_VERSION) ||
                !request.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_KEY)) {
            throw new Exception();
        }
    }
    private String getWebSocketURL(FullHttpRequest request) {
        String location = request.headers().get(HttpHeaderNames.HOST) + request.uri();
        return "ws://" + location;
    }
    private void handshake(ChannelHandlerContext ctx, FullHttpRequest request) {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(request), null, false);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            return;
        }
        ctx.pipeline().remove(this).addLast(new WebSocketServerHandler(config, adaptor));
        handshaker.handshake(ctx.channel(), request).addListener((future) -> {
            if (future.isSuccess()) {
                adaptor.handleOpen(ctx);
            } else {
                handshaker.close(ctx.channel(), new CloseWebSocketFrame());
            }
        });
    }
}
