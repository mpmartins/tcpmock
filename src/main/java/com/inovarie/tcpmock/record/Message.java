package com.inovarie.tcpmock.record;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Message implements Serializable {

	private static final long serialVersionUID = 4482188118887320728L;

	private Source source;
	private List<Integer> message;
	
	public Message (@NonNull Source source, @NonNull List<Integer> message) {
		this.source = source;
		this.message = message;
	}
	
}
