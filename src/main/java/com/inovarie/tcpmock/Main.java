package com.inovarie.tcpmock;

import com.inovarie.tcpmock.playback.PlaybackService;
import com.inovarie.tcpmock.recording.RecordingService;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1) {
			printHelp();
		}

		if ("record".equals(args[0])) {
			if (args.length < 5) {
				printHelp();
			}
			RecordingService recordingService = new RecordingService();
			recordingService.startRecording(Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), args[4]);
		}
		
		if ("playback".equals(args[0])) {
			if (args.length < 3) {
				printHelp();
			}
			PlaybackService playbackService = new PlaybackService();
			playbackService.startPlayback(Integer.parseInt(args[1]), args[2]);
		}
	}

	private static void printHelp() {
		System.out.println("Missing parameters");
		System.out.println();
		System.out.println("Select the server mode to start:");
		System.out.println();
		System.out.println("record [server-port] [client-address] [client-port] [filename]");
		System.out.println("playback [server-port] [filename]");
		System.exit(0);
	}
}
