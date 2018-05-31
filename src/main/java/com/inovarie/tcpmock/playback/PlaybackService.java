package com.inovarie.tcpmock.playback;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;

public class PlaybackService {

	public void startPlaybackDetached(int serverPort, String fileName) {
		Record record = RecordFileManager.loadRecord(fileName);
		System.out.println(record);
		new Thread(new PlaybackServer(serverPort, record)).start();
	}

	public void startPlayback(int serverPort, String fileName) {
		Record record = RecordFileManager.loadRecord(fileName);
		System.out.println(record);
		new PlaybackServer(serverPort, record).run();
	}

}
