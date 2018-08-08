package com.inovarie.tcpmock.reactive.playback;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Message;
import com.inovarie.tcpmock.model.Source;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlaybackHandler extends ChannelInboundHandlerAdapter {

    private Iterator<Connection> connectionsIterator;
    private Connection currentConnection;

    private Iterator<Message> messagesIterator;
    private Message currentMessage;


    public PlaybackHandler(List<Connection> connections) {
        System.out.println("Handler Contructor...");
        this.connectionsIterator = connections.iterator();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        currentConnection = connectionsIterator.next();
        messagesIterator = currentConnection.getMessages().iterator();
        currentMessage = messagesIterator.next();

        if (Source.CLIENT == currentMessage.getSource()) {
            sendMessage(ctx, currentMessage);
        }


        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        ByteBuf inBuffer = (ByteBuf) msg;

        while (this.messagesIterator.hasNext()) {
            this.currentMessage = this.messagesIterator.next();

            if (Source.SERVER == currentMessage.getSource()) {
                checkMessage(inBuffer, currentMessage);
            } else if (Source.CLIENT == currentMessage.getSource()) {
                sendMessage(ctx, currentMessage);
                break;
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    private void checkMessage(ByteBuf inBuffer, Message message) {
        List<Integer> bytes = message.getBytes();
        List<Integer> bytesReceived = new ArrayList<>();
        for (int i = 0; i < bytes.size(); i++) {
            int integerReceived = inBuffer.readByte();
            bytesReceived.add(integerReceived);
        }
        System.out.println("RECORDED: " + bytes);
        if (bytes.equals(bytesReceived)) {
            System.out.println("RECEIVED: " + bytesReceived);
        } else {
            System.err.println("RECEIVED: " + bytesReceived);
        }
    }

    private void sendMessage(ChannelHandlerContext ctx, Message message) {
        List<Integer> bytes = message.getBytes();
        byte[] bytesArray = new byte[bytes.size()];
        int i = 0;
        for (Integer integer : bytes) {
            bytesArray[i++] = integer.byteValue();
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer(bytesArray));
        System.out.println("SENT: " + bytes);
    }

}
