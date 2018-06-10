package com.inovarie.tcpmock.recording;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Record;

import org.springframework.stereotype.Component;

@Component
public class RecordingManager {

	private RecordingManagerStatus status;
	private Record record;

	public RecordingManager() {
	    status = RecordingManagerStatus.PENDING;

	}

	//TODO: Deal with this synchronized
	public synchronized void startRecord(Record record) {
		status = RecordingManagerStatus.RECORDING;
		setRecord(record);
	}

	private void setRecord(Record record) {
		this.record = record;
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