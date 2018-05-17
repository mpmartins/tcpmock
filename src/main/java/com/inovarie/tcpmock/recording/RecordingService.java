package com.inovarie.tcpmock.recording;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

@Service
public class RecordingService {

	public void startRecordingDetached(@NotEmpty int serverPort, @NotEmpty String clientAddress, @NotEmpty int clientPort,
			@NotEmpty String fileName) {
		new Thread(new RecordingServer(serverPort, clientAddress, clientPort, fileName)).start();
	}
	
	public void startRecording(@NotEmpty int serverPort, @NotEmpty String clientAddress, @NotEmpty int clientPort,
			@NotEmpty String fileName) {
		new RecordingServer(serverPort, clientAddress, clientPort, fileName).run();
	}

}
