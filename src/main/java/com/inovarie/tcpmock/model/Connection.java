package com.inovarie.tcpmock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Connection implements Serializable {
	
	private static final long serialVersionUID = 5529731646617697L;

	private transient Source currentSource;
	private transient List<Integer> currentBytes;
	
	private final List<Message> messages;

	public Connection() {
		messages = new ArrayList<>();
	}
	
	public void endConnection() {
		if (currentSource != null) {
			messages.add(new Message(currentSource, currentBytes));
		}
		currentSource = null;
		currentBytes.clear();
	}
	
	public synchronized void write(Source s, int b) { 
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

	public List<Message> getMessages() {
		return messages;
	}

	@Override
	public String toString() {
		return "Connection [messages=" + messages + "]";
	}

}
