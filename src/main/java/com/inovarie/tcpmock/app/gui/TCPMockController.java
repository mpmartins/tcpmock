package com.inovarie.tcpmock.app.gui;

import com.inovarie.tcpmock.app.gui.model.TCPMockModel;

import java.io.PrintStream;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Controller for the application.
 * This class acts as gui bridge between {@link com.inovarie.tcpmock.app.gui.TCPMockView}
 * and {@link com.inovarie.tcpmock.app.gui.model.TCPMockModel}.
 *
 */
@Component
public class TCPMockController implements TCPMockModelObserver {

    private TCPMockModel tcpMockModel;
    private TCPMockView tcpMockView;

    public TCPMockController() {

    }

    public void init(@NonNull TCPMockModel tcpMockModel, @NonNull TCPMockView tcpMockView) {
        setTcpMockView(tcpMockView);
        setTcpMockModel(tcpMockModel);
        tcpMockView.init();
        tcpMockView.setVisible(true);
    }

    public void processRecordingButton(PrintStream output,
                                       String fileName,
                                       String clientAddress,
                                       int serverPort,
                                       int clientPort) {
        getTcpMockModel().processRecordingButton(output, fileName, clientAddress, serverPort, clientPort);
    }

    public void processPlaybackButton(PrintStream output, String fileName, int serverPort) {
        getTcpMockModel().processPlaybackButton(output, fileName, serverPort);
    }

    @NonNull
    public TCPMockModel getTcpMockModel() {
        return tcpMockModel;
    }

    public void setTcpMockModel(@NonNull TCPMockModel tcpMockModel) {
        this.tcpMockModel = tcpMockModel;
    }

    @NonNull
    public TCPMockView getTcpMockView() {
        return tcpMockView;
    }

    public void setTcpMockView(@NonNull TCPMockView tcpMockView) {
        this.tcpMockView = tcpMockView;
    }

    @Override
    public void recordStarted() {
        getTcpMockView().recordStarted();
    }

    @Override
    public void recordStopped() {
        getTcpMockView().recordStopped();
    }

    @Override
    public void playbackStarted() {
        getTcpMockView().playbackStarted();
    }

    @Override
    public void playbackStopped() {
        getTcpMockView().playbackStopped();
    }


}
