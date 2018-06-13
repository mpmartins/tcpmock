package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Source;

public class RecordingHandler extends Thread {

	private PrintStream output;
	private RecordingManager recordingManager;
	private Socket serverConnectionSocket;
	private String clientAddress;
	private int clientPort;

	public RecordingHandler(PrintStream output, RecordingManager recordingManager, Socket serverConnectionSocket, String clientAddress, int clientPort) {
		this.output = output;
		this.recordingManager = recordingManager;
		this.serverConnectionSocket = serverConnectionSocket;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}

	public void run() {
		try {
			
			Connection connection = recordingManager.startConnection();

			output.println("Establishing client connection with client ("+clientAddress+":"+clientPort+") ...");
			Socket clientConnectionSocket = new Socket(clientAddress, clientPort);
			
			Thread clientToServer = new Thread(new CommunicationMonitor(
					Source.CLIENT,
					connection,
					clientConnectionSocket.getInputStream(),
					serverConnectionSocket.getOutputStream()));
			clientToServer.start();

			Thread serverToClient = new Thread(new CommunicationMonitor(
					Source.SERVER,
					connection,
					serverConnectionSocket.getInputStream(),
					clientConnectionSocket.getOutputStream()));
			serverToClient.start();
			
			clientToServer.join();
			serverToClient.join();
			clientConnectionSocket.close();
			
			connection.endConnection();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
