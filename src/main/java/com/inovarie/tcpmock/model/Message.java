package com.inovarie.tcpmock.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Message implements Serializable {

	private static final long serialVersionUID = 4482188118887320728L;

	private Source source;
	private List<Integer> bytes;
	
	public Message (@NonNull Source source, @NonNull List<Integer> bytes) {
		this.source = source;
		this.bytes = bytes;
	}
	
}
