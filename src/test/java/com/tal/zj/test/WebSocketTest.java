package com.tal.zj.test;

import com.tal.zj.websocket.WebSocketServer;
import com.tal.zj.websocket.config.WebSocketServerConfig;
import com.tal.zj.listener.ChatWebSocketEventListener;
import com.tal.zj.listener.EchoWebSocketEventListener;
import org.junit.Test;

public class WebSocketTest {

    @Test
    public void echoServerTest() {
        WebSocketServerConfig config = new WebSocketServerConfig();
        config.setPort(8081);
        config.setListener(new EchoWebSocketEventListener());
        new WebSocketServer(config).start();
    }

    @Test
    public void chatServerTest() {
        WebSocketServerConfig config = new WebSocketServerConfig();
        config.setPort(8081);
        config.setListener(new ChatWebSocketEventListener());
        new WebSocketServer(config).start();
    }

}
