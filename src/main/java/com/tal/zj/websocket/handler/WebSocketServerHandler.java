package com.tal.zj.websocket.handler;

import com.tal.zj.websocket.config.WebSocketServerConfig;
import com.tal.zj.websocket.adaptor.WebSocketListenerAdaptor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private WebSocketServerConfig config;
    private WebSocketListenerAdaptor adaptor;

    public WebSocketServerHandler(WebSocketServerConfig config, WebSocketListenerAdaptor eventHandler) {
        this.config = config;
        this.adaptor = eventHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        handleWebSocketFrame(ctx, frame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        adaptor.handleError(ctx, cause);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //连接关闭
        if (frame instanceof CloseWebSocketFrame) {
            adaptor.handleClose(ctx, (CloseWebSocketFrame) frame);
        }
        //ping
        if (frame instanceof PingWebSocketFrame) {
            adaptor.handlePing(ctx, (PingWebSocketFrame) frame);
        }
        //pong
        if (frame instanceof PongWebSocketFrame) {
            adaptor.handlePong(ctx, (PongWebSocketFrame) frame);
        }
        //文本
        if (frame instanceof TextWebSocketFrame) {
            adaptor.handleText(ctx, (TextWebSocketFrame) frame);
        }
        //二进制
        if (frame instanceof BinaryWebSocketFrame) {
            adaptor.handleBinary(ctx, (BinaryWebSocketFrame) frame);
        }
    }
}
