package com.inovarie.tcpmock.record;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class RecordManager {

	private static RecordManager instance = null;
	
	private RecordManager() {
	}
	
	public static RecordManager getInstance() {
		if (instance == null) {
			instance = new RecordManager();
		}
		return instance;
	}

	private List<Message> recordedMessages;
	
	private Source currentSource;
	private List<Integer> currentMessage;
	
	public synchronized void startRecord() {
		recordedMessages = new ArrayList<>();
		
		currentSource = null;
		currentMessage = new ArrayList<>();
	}
	
	public synchronized void write(@NonNull Source s, int b) { 
		if (s == currentSource) {
			currentMessage.add(b);
		} else {
			if (currentSource != null) {
				recordedMessages.add(new Message(currentSource, currentMessage));
			}
			currentSource = s;
			currentMessage = new ArrayList<>();
			currentMessage.add(b);
		}
	}
	
	public void stopRecord() {
		recordedMessages.add(new Message(currentSource, currentMessage));
		currentSource = null;
		currentMessage = null;
	}
	
	public void saveRecord(String name) {
		try {
			FileOutputStream fout = new FileOutputStream(name + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(recordedMessages);
			oos.close();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void printRecord() {
		System.out.println(recordedMessages);
	}
	
}
