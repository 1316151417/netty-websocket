package com.tal.zj.websocket.config;

import com.tal.zj.websocket.listener.WebSocketListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;

public class WebSocketServerConfig {

    /**
     * 线程数
     */
    private Integer parentGroupThreadCount;
    private Integer childGroupThreadCount;

    /**
     * 选项
     */
    private Integer connectTimeoutMillis;
    private Integer soBacklog;
    private Boolean tcpNodelay;
    private Boolean soKeepalive;

    /**
     * 地址
     */
    private String host;
    private Integer port;

    /**
     * 监听
     */
    private WebSocketListener listener;

    /**
     * 配置选项
     * @param serverBootstrap
     */
    public void option(ServerBootstrap serverBootstrap) {
        if (connectTimeoutMillis != null) {
            serverBootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis);
        }
        if (soBacklog != null) {
            serverBootstrap.option(ChannelOption.SO_BACKLOG, soBacklog);
        }
        if (tcpNodelay != null) {
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, tcpNodelay);
        }
        if (soKeepalive != null) {
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, soKeepalive);
        }
    }

    /**
     * get set
     */

    public Integer getParentGroupThreadCount() {
        return parentGroupThreadCount;
    }

    public void setParentGroupThreadCount(Integer parentGroupThreadCount) {
        this.parentGroupThreadCount = parentGroupThreadCount;
    }

    public Integer getChildGroupThreadCount() {
        return childGroupThreadCount;
    }

    public void setChildGroupThreadCount(Integer childGroupThreadCount) {
        this.childGroupThreadCount = childGroupThreadCount;
    }

    public Integer getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(Integer connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public Integer getSoBacklog() {
        return soBacklog;
    }

    public void setSoBacklog(Integer soBacklog) {
        this.soBacklog = soBacklog;
    }

    public Boolean getTcpNodelay() {
        return tcpNodelay;
    }

    public void setTcpNodelay(Boolean tcpNodelay) {
        this.tcpNodelay = tcpNodelay;
    }

    public Boolean getSoKeepalive() {
        return soKeepalive;
    }

    public void setSoKeepalive(Boolean soKeepalive) {
        this.soKeepalive = soKeepalive;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public WebSocketListener getListener() {
        return listener;
    }

    public void setListener(WebSocketListener listener) {
        this.listener = listener;
    }
}
