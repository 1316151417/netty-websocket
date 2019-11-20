package com.tal.zj.websocket.adaptor;

import com.tal.zj.websocket.listener.WebSocketListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

public class DefaultWebSocketListenerAdaptor implements WebSocketListenerAdaptor {

    private WebSocketListener listener;

    public DefaultWebSocketListenerAdaptor(WebSocketListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleOpen(ChannelHandlerContext ctx) {
        listener.onOpen(ctx.channel());
    }

    @Override
    public void handleClose(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        listener.onClose(ctx.channel());
        ctx.writeAndFlush(frame != null ? frame.retainedDuplicate() : new CloseWebSocketFrame())
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void handlePing(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        listener.onPing(ctx.channel());
        ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
    }

    @Override
    public void handlePong(ChannelHandlerContext ctx, PongWebSocketFrame frame) {
        listener.onPong(ctx.channel());
    }

    @Override
    public void handleText(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        listener.onText(ctx.channel(), frame.text());
    }

    @Override
    public void handleBinary(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) {
        listener.onBinary(ctx.channel(), frame.content().array());
    }

    @Override
    public void handleError(ChannelHandlerContext ctx, Throwable throwable) {
        listener.onError(ctx.channel(), throwable);
        ctx.channel().writeAndFlush(new CloseWebSocketFrame()).addListener(ChannelFutureListener.CLOSE);
    }
}
