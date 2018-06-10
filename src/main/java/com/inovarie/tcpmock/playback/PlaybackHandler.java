package com.inovarie.tcpmock.playback;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PlaybackHandler implements Runnable {

	Socket serverConnectionSocket;
	Connection connection;

	public PlaybackHandler(Socket serverConnectionSocket, Connection connection) {
		this.serverConnectionSocket = serverConnectionSocket;
		this.connection = connection;
	}

	public void run() {
		try {

			
			InputStream in = serverConnectionSocket.getInputStream();
			OutputStream out = serverConnectionSocket.getOutputStream();
			
			for (Message message : connection.getMessages()) {
				List<Integer> bytes = message.getBytes();
					
				switch (message.getSource()) {
				case SERVER:
						List<Integer> bytesReceived = new ArrayList<>();
						for (int i = 0; i < bytes.size(); i++) {
							int integerReceived = in.read();
							bytesReceived.add(integerReceived);
						}
						System.out.println("RECORDED: " + bytes);
						if (bytes.equals(bytesReceived)) {
							System.out.println("RECEIVED: " + bytesReceived);
						} else {
							System.err.println("RECEIVED: " + bytesReceived);
						}
					break;

				case CLIENT:
						for (Integer integer : bytes) {
							out.write(integer);
						}
						System.out.println("SENT: " + bytes);
					break;
				}
				
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
