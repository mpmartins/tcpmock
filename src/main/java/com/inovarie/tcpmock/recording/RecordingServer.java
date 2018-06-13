package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RecordingServer implements Runnable {

	private PrintStream output;
	private RecordingManager recordManager;
	private int serverPort;
	private String clientAddress;
	private int clientPort;
	
	public RecordingServer(PrintStream output, RecordingManager recordManager, int serverPort, String clientAddress, int clientPort) {
		this.output = output;
		this.recordManager = recordManager;
		this.serverPort = serverPort;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}


	public void run() {
		ServerSocket serverSocket = null;
		try {
			
			serverSocket = new ServerSocket(serverPort);
			output.println("RecordingServer is starting on port " + serverPort + " ...");

			while (RecordingManagerStatus.RECORDING == recordManager.getStatus()) {
				Socket serverConnectionSocket = serverSocket.accept();
				output.println("Accepting Connection...");
				
				new RecordingHandler(output, recordManager, serverConnectionSocket, clientAddress, clientPort).start();
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			output.println("The server is shut down!");
		}
	}

}
