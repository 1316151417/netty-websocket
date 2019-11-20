package com.tal.zj.websocket.adaptor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

public interface WebSocketListenerAdaptor {

    void handleOpen(ChannelHandlerContext ctx);

    void handleClose(ChannelHandlerContext ctx, CloseWebSocketFrame frame);

    void handlePing(ChannelHandlerContext ctx, PingWebSocketFrame frame);

    void handlePong(ChannelHandlerContext ctx, PongWebSocketFrame frame);

    void handleText(ChannelHandlerContext ctx, TextWebSocketFrame frame);

    void handleBinary(ChannelHandlerContext ctx, BinaryWebSocketFrame frame);

    void handleError(ChannelHandlerContext ctx, Throwable throwable);
}
