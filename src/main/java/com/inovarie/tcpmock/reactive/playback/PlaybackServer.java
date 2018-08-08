package com.inovarie.tcpmock.reactive.playback;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Record;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.Future;

public class PlaybackServer {

    private final int serverPort;
    private final Record record;

    public PlaybackServer(int serverPort, Record record) {
        this.serverPort = serverPort;
        this.record = record;
    }

    public Future start() throws InterruptedException {

        ChannelFuture channelFuture = null;
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("localhost", this.serverPort));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel chanel) throws Exception {
                    System.out.println("initChannel...");
                    chanel.pipeline().addLast(new PlaybackHandler(record.getConnections()));
                }
            });
            channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }

        return channelFuture;
    }

    public static void main(String[] args) {
        Record record = new RecordFileManager().loadRecord("test");
        try {
            new PlaybackServer(5555, record).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
