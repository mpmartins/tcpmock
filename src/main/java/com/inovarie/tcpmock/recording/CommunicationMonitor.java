package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.inovarie.tcpmock.model.RecordManager;
import com.inovarie.tcpmock.model.Source;

public class CommunicationMonitor implements Runnable {

	Source source;
	RecordManager recordManager;
	InputStream in;
	OutputStream out;

	public CommunicationMonitor(Source source, RecordManager recordManager, InputStream in, OutputStream out) {
		this.source = source;
		this.recordManager = recordManager;
		this.in = in;
		this.out = out;
	}

	public void run() {
		boolean connected = true;
		while (connected) {
			try {
				int input = in.read();
				if (input == -1) {
					connected = false;
					break;
				}
				
				System.out.print(input);
				recordManager.write(source, input);

				out.write(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
