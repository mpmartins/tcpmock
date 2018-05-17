package com.inovarie.tcpmock;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.inovarie.tcpmock.playback.PlaybackService;
import com.inovarie.tcpmock.recording.RecordingService;

@ShellComponent
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	private final RecordingService recordingService;
	private final PlaybackService playbackService;

	@Autowired
	public Main(RecordingService recordingService, PlaybackService playbackService) {
		this.recordingService = recordingService;
		this.playbackService = playbackService;
	}

	@ShellMethod("Starts server and record communication.")
	public void record(
			@ShellOption int serverPort, 
			@ShellOption(defaultValue = "localhost") String clientAddress,
			@ShellOption int clientPort, 
			@ShellOption @NotEmpty String fileName, 
			@ShellOption Boolean detached) {
		if (detached == null) {
			detached = false;
		}
		if (detached) {
			recordingService.startRecordingDetached(serverPort, clientAddress, clientPort, fileName);
		} else {
			recordingService.startRecording(serverPort, clientAddress, clientPort, fileName);
		}
	}

	@ShellMethod("Starts server and replay specified communication file.")
	public void playback(
			@ShellOption int serverPort, 
			@ShellOption @NotEmpty String fileName,
			@ShellOption Boolean detached) {
		if (detached == null) {
			detached = false;
		}
		if (detached) {
			playbackService.startPlaybackDetached(serverPort, fileName);
		} else {
			playbackService.startPlayback(serverPort, fileName);
		}
	}
}
