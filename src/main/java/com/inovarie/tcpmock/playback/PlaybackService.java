package com.inovarie.tcpmock.playback;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;

import javax.inject.Inject;
import java.util.concurrent.Executor;

import org.springframework.stereotype.Component;

@Component
public class PlaybackService {

    private final RecordFileManager recordFileManager;
    private final Executor executor;

    @Inject
    public PlaybackService(Executor executor, RecordFileManager recordFileManager) {
        this.recordFileManager = recordFileManager;
        this.executor = executor;
    }

	public void startPlaybackDetached(int serverPort, String fileName) {
		Record record = recordFileManager.loadRecord(fileName);

		System.out.println(record);
		executor.execute(new PlaybackServer(executor, serverPort, record));
	}

	public void startPlayback(int serverPort, String fileName) {
		Record record = recordFileManager.loadRecord(fileName);

		System.out.println(record);
		//FIXME: WTF?!?!?!?!
		new PlaybackServer(executor, serverPort, record).run();
	}

}
