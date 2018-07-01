package com.inovarie.tcpmock.recording;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;

import javax.inject.Inject;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.springframework.stereotype.Component;

@Component
public class RecordingService {

    private final RecordingManager recordingManager;
    private final RecordFileManager recordFileManager;
    private final Executor executor;
    private final ForkJoinPool forkJoinPool;

    @Inject
    public RecordingService(
            ForkJoinPool forkJoinPool,
            RecordingManager recordingManager,
            RecordFileManager recordFileManager,
            Executor executor) {
        this.recordFileManager = recordFileManager;
        this.recordingManager = recordingManager;
        this.executor = executor;
        this.forkJoinPool = forkJoinPool;
    }

	public void startRecording(int serverPort, String clientAddress, int clientPort,
			String fileName) {
		Record record = new Record(fileName);

		recordingManager.startRecord(record);
		executor.execute(new RecordingServer(
		        forkJoinPool,
                System.out,
                recordingManager,
                serverPort,
                clientAddress,
                clientPort));
		Scanner userInputScanner = new Scanner(System.in);

		System.out.println("Press any key to stop recording...");
	    userInputScanner.nextLine();
		userInputScanner.close();
		recordingManager.stopRecord();
		System.out.println(record);
		recordFileManager.saveRecord(recordingManager.getRecord());
	}
}
