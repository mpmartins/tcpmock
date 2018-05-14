package com.inovarie.tcpmock.record;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class RecordManager {

	private static final String FILE_EXTENSION = ".record";

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
	
	@SuppressWarnings("unchecked")
	public void loadRecord(String name) {
		try {
			FileInputStream fin = new FileInputStream(name + FILE_EXTENSION);
			ObjectInputStream ois = new ObjectInputStream(fin);
			recordedMessages = (List<Message>) ois.readObject();
			ois.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void saveRecord(String name) {
		try {
			FileOutputStream fout = new FileOutputStream(name + FILE_EXTENSION);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(recordedMessages);
			oos.close();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
		
	public List<Message> getRecordedMessages() {
		return this.recordedMessages;
	}

	public void printRecord() {
		System.out.println(recordedMessages);
	}

}