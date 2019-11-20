package com.tal.zj.listener;

import com.tal.zj.websocket.listener.WebSocketListener;
import io.netty.channel.Channel;

import java.util.Arrays;

public class EchoWebSocketEventListener implements WebSocketListener {
    @Override
    public void onOpen(Channel channel) {
        System.out.println("open");
    }

    @Override
    public void onClose(Channel channel) {
        System.out.println("close");
    }

    @Override
    public void onPing(Channel channel) {
        System.out.println("ping");
    }

    @Override
    public void onPong(Channel channel) {
        System.out.println("pong");
    }

    @Override
    public void onText(Channel channel, String text) {
        System.out.println(text);
    }

    @Override
    public void onBinary(Channel channel, byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
    }

    @Override
    public void onError(Channel channel, Throwable throwable) {
        throwable.printStackTrace();
    }
}
