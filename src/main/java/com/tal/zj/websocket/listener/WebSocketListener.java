package com.tal.zj.websocket.listener;

import io.netty.channel.Channel;

public interface WebSocketListener {

    void onOpen(Channel channel);

    void onClose(Channel channel);

    void onPing(Channel channel);

    void onPong(Channel channel);

    void onText(Channel channel, String text);

    void onBinary(Channel channel, byte[] bytes);

    void onError(Channel channel, Throwable throwable);
}
