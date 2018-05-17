package com.inovarie.tcpmock.playback;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

import com.inovarie.tcpmock.server.PlaybackServer;

@Service
public class PlaybackService {

	public String startPlayback(@NotEmpty int serverPort, @NotEmpty String fileName) {
		PlaybackServer playbackServer = new PlaybackServer(serverPort, fileName);
		playbackServer.start();
		return "Playback Server Started.";
	}

}
