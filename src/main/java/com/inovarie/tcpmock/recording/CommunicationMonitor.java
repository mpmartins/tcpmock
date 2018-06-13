package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Source;

public class CommunicationMonitor implements Runnable {

	private Source source;
	private Connection connection;
	private InputStream in;
	private OutputStream out;

	public CommunicationMonitor(Source source, Connection connection, InputStream in, OutputStream out) {
		this.source = source;
		this.connection = connection;
		this.in = in;
		this.out = out;
	}

	public void run() {
		try {
			boolean connected = true;
			while (connected) {
				int input = in.read();

				connection.write(source, input);
				out.write(input);

				if (input == -1) {
					connected = false;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
