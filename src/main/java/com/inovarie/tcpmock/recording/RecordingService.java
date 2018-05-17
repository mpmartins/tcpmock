package com.inovarie.tcpmock.recording;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

import com.inovarie.tcpmock.server.RecordingServer;

@Service
public class RecordingService {

	public String startRecording(@NotEmpty int serverPort, @NotEmpty String clientAddress, @NotEmpty int clientPort,
			@NotEmpty String fileName) {
		RecordingServer recordingServer = new RecordingServer(serverPort, clientAddress, clientPort, fileName);
		recordingServer.start();
		return "Recording Server Started.";
	}

}
