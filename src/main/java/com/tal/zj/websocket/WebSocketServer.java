package com.tal.zj.websocket;

import com.tal.zj.websocket.config.WebSocketServerConfig;
import com.tal.zj.websocket.handler.HttpServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketServer {

    private WebSocketServerConfig config;

    public WebSocketServer(WebSocketServerConfig config) {
        this.config = config;
    }

    public void start() {
        EventLoopGroup parentGroup = config.getParentGroupThreadCount() == null ?
                new NioEventLoopGroup() : new NioEventLoopGroup(config.getParentGroupThreadCount());
        EventLoopGroup childGroup = config.getChildGroupThreadCount() == null ?
                new NioEventLoopGroup() : new NioEventLoopGroup(config.getChildGroupThreadCount());
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new HttpServerCodec())
                                    .addLast(new HttpObjectAggregator(Integer.MAX_VALUE))
                                    .addLast(new HttpServerHandler(config));
                        }
                    });
            config.option(serverBootstrap);
            ChannelFuture future = null;
            if(config.getHost() == null) {
                future = serverBootstrap.bind(config.getPort()).sync();
            } else {
                future = serverBootstrap.bind(config.getHost(), config.getPort()).sync();
            }
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
