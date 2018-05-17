package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.inovarie.tcpmock.model.RecordManager;
import com.inovarie.tcpmock.model.Source;

public class RecordingHandler extends Thread {

	Socket serverConnectionSocket;
	String clientAddress;
	int clientPort;
	String fileName;

	public RecordingHandler(Socket serverConnectionSocket, String clientAddress, int clientPort, String fileName) {
		this.serverConnectionSocket = serverConnectionSocket;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.fileName = fileName;
	}

	public void run() {
		try {

			System.out.println("Establishing client connection...");
			Socket clientConnectionSocket = new Socket(clientAddress, clientPort);

			RecordManager recordManager = RecordManager.getInstance();
			recordManager.startRecord();
			
			Thread clientToServer = new Thread(new CommunicationMonitor(
					Source.CLIENT,
					recordManager,
					clientConnectionSocket.getInputStream(),
					serverConnectionSocket.getOutputStream()));
			clientToServer.start();

			Thread serverToClient = new Thread(new CommunicationMonitor(
					Source.SERVER,
					recordManager,
					serverConnectionSocket.getInputStream(),
					clientConnectionSocket.getOutputStream()));
			serverToClient.start();
			
			Scanner userInputScanner = new Scanner(System.in);
			System.out.println("Press any key to stop recording...");
		    userInputScanner.nextLine();
			userInputScanner.close();
		    
			recordManager.stopRecord();
			recordManager.printRecord();
			recordManager.saveRecord(fileName);
			
			clientConnectionSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
