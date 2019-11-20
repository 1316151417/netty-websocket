package com.tal.zj.listener;

import com.tal.zj.websocket.listener.WebSocketListener;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatWebSocketEventListener implements WebSocketListener {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void onOpen(Channel channel) {
        channelGroup.add(channel);
        channelGroup.writeAndFlush(new TextWebSocketFrame(String.format("[用户%s]进入了聊天室...", channel.id())));
    }

    @Override
    public void onClose(Channel channel) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(String.format("[用户%s]离开了聊天室...", channel.id())));
        channelGroup.remove(channel);
    }

    @Override
    public void onPing(Channel channel) {
    }

    @Override
    public void onPong(Channel channel) {
    }

    @Override
    public void onText(Channel channel, String text) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(String.format("[用户%s]说:%s", channel.id(), text)));
    }

    @Override
    public void onBinary(Channel channel, byte[] bytes) {
    }

    @Override
    public void onError(Channel channel, Throwable throwable) {
        throwable.printStackTrace();
    }
}
