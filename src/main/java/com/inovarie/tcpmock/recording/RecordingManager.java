package com.inovarie.tcpmock.recording;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Record;

import lombok.NonNull;

public class RecordingManager {

	private RecordingManagerStatus status = RecordingManagerStatus.PENDING;
	private Record record;
	
	private static RecordingManager instance = null;
	
	private RecordingManager(@NonNull Record record) {
		this.record = record;
	}
	
	public static RecordingManager getInstance(@NonNull Record record) {
		if (instance == null) {
			instance = new RecordingManager(record);
		}
		return instance;
	}

	public synchronized void startRecord() {
		status = RecordingManagerStatus.RECORDING;
	}
	
	public void stopRecord() {
		for (Connection connection : record.getConnections()) {
			connection.endConnection();
		}
		status = RecordingManagerStatus.STOPPED;
	}
	
	public Connection startConnection() {
		return record.newConnection();
	}
	
	public Record getRecord() {
		return record;
	}

	public RecordingManagerStatus getStatus() {
		return status;
	}
	
}