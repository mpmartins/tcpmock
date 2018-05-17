package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RecordingServer implements Runnable {

	private int serverPort;
	private String clientAddress;
	private int clientPort;
	private String fileName;

	public RecordingServer(int serverPort, String clientAddress, int clientPort, String fileName) {
		this.serverPort = serverPort;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.fileName = fileName;
	}

	public static void main(String[] args) {
		RecordingServer recordingServer = new RecordingServer(Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]), args[3]);
		recordingServer.run();
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("RecordingServer is starting on port " + serverPort + " ...");

			while (true) {
				Socket serverConnectionSocket = serverSocket.accept();
				System.out.println("Accepting Connection...");

				new RecordingHandler(serverConnectionSocket, clientAddress, clientPort, fileName).start();
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
