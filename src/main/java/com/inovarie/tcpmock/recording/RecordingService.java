package com.inovarie.tcpmock.recording;

import java.util.Scanner;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;

public class RecordingService {

	public void startRecording(int serverPort, String clientAddress, int clientPort,
			String fileName) {
		
		Record record = new Record(fileName);
		
		RecordingManager recordManager = RecordingManager.getInstance(record);
		recordManager.startRecord();

		new Thread(new RecordingServer(recordManager, serverPort, clientAddress, clientPort)).start();

		Scanner userInputScanner = new Scanner(System.in);
		System.out.println("Press any key to stop recording...");
	    userInputScanner.nextLine();
		userInputScanner.close();
	    
		recordManager.stopRecord();
		
		System.out.println(record);
		
		RecordFileManager.saveRecord(recordManager.getRecord());
		
	}
	
}
