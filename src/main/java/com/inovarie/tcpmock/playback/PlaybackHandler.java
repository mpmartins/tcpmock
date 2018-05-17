package com.inovarie.tcpmock.playback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.inovarie.tcpmock.model.Message;
import com.inovarie.tcpmock.model.RecordManager;

public class PlaybackHandler extends Thread {

	Socket serverConnectionSocket;
	String recordFile;

	public PlaybackHandler(Socket serverConnectionSocket, String recordFile) {
		this.serverConnectionSocket = serverConnectionSocket;
		this.recordFile = recordFile;
	}

	public void run() {
		try {

			RecordManager recordManager = RecordManager.getInstance();
			recordManager.loadRecord(this.recordFile);
			recordManager.printRecord();
			List<Message> messages = recordManager.getRecordedMessages();
			
			InputStream in = serverConnectionSocket.getInputStream();
			OutputStream out = serverConnectionSocket.getOutputStream();
			
			for (Message message : messages) {
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
