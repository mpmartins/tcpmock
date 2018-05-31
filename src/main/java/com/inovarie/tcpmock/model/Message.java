package com.inovarie.tcpmock.model;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	private static final long serialVersionUID = 4482188118887320728L;

	private Source source;
	private List<Integer> bytes;
	
	public Message (Source source, List<Integer> bytes) {
		this.source = source;
		this.bytes = bytes;
	}

	public Source getSource() {
		return source;
	}

	public List<Integer> getBytes() {
		return bytes;
	}

	@Override
	public String toString() {
		return "Message [source=" + source + ", bytes=" + bytes + "]";
	}

}
