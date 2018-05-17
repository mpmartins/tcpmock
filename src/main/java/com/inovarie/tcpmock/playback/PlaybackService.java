package com.inovarie.tcpmock.playback;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;

@Service
public class PlaybackService {

	public void startPlaybackDetached(@NotEmpty int serverPort, @NotEmpty String fileName) {
		new Thread(new PlaybackServer(serverPort, fileName)).start();
	}

	public void startPlayback(@NotEmpty int serverPort, @NotEmpty String fileName) {
		new PlaybackServer(serverPort, fileName).run();
	}

}
