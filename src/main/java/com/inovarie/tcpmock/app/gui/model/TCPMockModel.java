package com.inovarie.tcpmock.app.gui.model;

import com.inovarie.tcpmock.app.gui.TCPMockModelObserver;
import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;
import com.inovarie.tcpmock.recording.RecordingManager;
import com.inovarie.tcpmock.recording.RecordingServer;

import javax.inject.Inject;
import java.io.PrintStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.springframework.stereotype.Component;

/**
 * Model for this Project. All good stuff goes here.
 */
@Component
public class TCPMockModel {

    private final TCPMockModelObserver tcpMockModelObserver;
    private final RecordingManager recordingManager;
    private final RecordFileManager recordFileManager;
    private final Executor executor;
    private final ForkJoinPool forkJoinPool;

    private boolean isRecording;

    @Inject
    public TCPMockModel(RecordingManager recordingManager,
                        TCPMockModelObserver tcpMockModelObserver,
                        RecordFileManager recordFileManager,
                        Executor executor,
                        ForkJoinPool forkJoinPool) {
        this.tcpMockModelObserver = tcpMockModelObserver;
        this.recordingManager = recordingManager;
        this.recordFileManager = recordFileManager;
        this.executor = executor;
        this.forkJoinPool = forkJoinPool;
        setRecording(false);
    }

    public void processRecordingButton(PrintStream output,
                                       String fileName,
                                       String clientAddress,
                                       int serverPort,
                                       int clientPort) {
        if (!isRecording()) {
            tcpMockModelObserver.recordStarted();

            startRecording(output, fileName, clientAddress, serverPort, clientPort);
        } else {
            tcpMockModelObserver.recordStopped();
            stopRecording();
        }
        setRecording(!isRecording());
    }

    private boolean isRecording() {
        return isRecording;
    }

    private void setRecording(boolean recording) {
        isRecording = recording;
    }

    private void startRecording(
            PrintStream output,
            String fileName,
            String clientAddress,
            int serverPort,
            int clientPort) {
        Record record = new Record(fileName);

        recordingManager.startRecord(record);
        executor.execute(new RecordingServer(
                forkJoinPool,
                output,
                recordingManager,
                serverPort,
                clientAddress,
                clientPort));
    }

    private void stopRecording() {
        recordingManager.stopRecord();
        recordFileManager.saveRecord(recordingManager.getRecord());
    }
}
