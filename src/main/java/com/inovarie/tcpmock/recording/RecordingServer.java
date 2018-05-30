package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RecordingServer implements Runnable {

	private RecordingManager recordManager;
	private int serverPort;
	private String clientAddress;
	private int clientPort;

	public RecordingServer(RecordingManager recordManager, int serverPort, String clientAddress, int clientPort) {
		this.recordManager = recordManager;
		this.serverPort = serverPort;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}


	public void run() {
		ServerSocket serverSocket = null;
		try {
			
			serverSocket = new ServerSocket(serverPort);
			System.out.println("RecordingServer is starting on port " + serverPort + " ...");

			while (RecordingManagerStatus.RECORDING == recordManager.getStatus()) {
				Socket serverConnectionSocket = serverSocket.accept();
				System.out.println("Accepting Connection...");
				
				new RecordingHandler(recordManager, serverConnectionSocket, clientAddress, clientPort).start();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("The server is shut down!");
		}
	}

}
