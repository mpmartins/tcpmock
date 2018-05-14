package com.inovarie.tcpmock;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.inovarie.tcpmock.record.RecordManager;
import com.inovarie.tcpmock.record.Source;

public class RecordingHandler extends Thread {

	Socket serverConnectionSocket;
	Socket clientConnectionSocket;

	public RecordingHandler(Socket serverConnectionSocket) {
		this.serverConnectionSocket = serverConnectionSocket;
	}

	public void run() {
		try {

			System.out.println("Establishing client connection...");
			clientConnectionSocket = new Socket("localhost", 3306);

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
			System.out.println("Type the name of the file and hit enter when you're done recording: ");
		    String recordName = userInputScanner.nextLine();
			userInputScanner.close();
		    
			recordManager.stopRecord();
			recordManager.printRecord();
			recordManager.saveRecord(recordName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
