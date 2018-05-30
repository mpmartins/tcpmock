package com.inovarie.tcpmock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Connection implements Serializable {
	
	private static final long serialVersionUID = 5529731646617697L;

	private transient Source currentSource;
	private transient List<Integer> currentBytes;
	
	private List<Message> messages;

	public Connection() {
		messages = new ArrayList<>();
	}
	
	public void endConnection() {
		if (currentSource != null) {
			messages.add(new Message(currentSource, currentBytes));
		}
		currentSource = null;
		currentBytes = new ArrayList<>();
	}
	
	public synchronized void write(@NonNull Source s, int b) { 
		if (s == currentSource) {
			currentBytes.add(b);
		} else {
			if (currentSource != null) {
				messages.add(new Message(currentSource, currentBytes));
			}
			currentSource = s;
			currentBytes = new ArrayList<>();
			currentBytes.add(b);
		}
	}
}
