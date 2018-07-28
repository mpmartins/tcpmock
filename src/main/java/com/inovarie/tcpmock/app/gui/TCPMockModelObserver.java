package com.inovarie.tcpmock.app.gui;

/**
 * Interface to observer {@link com.inovarie.tcpmock.app.gui.model.TCPMockModel}.
 */
public interface TCPMockModelObserver {

    /**
     * Notify observers the recording process just started.
     */
    void recordStarted();

    /**
     * Notify observers the recording process just stopped.
     */
    void recordStopped();

    /**
     * Notify observers the playback process just started.
     */
    void playbackStarted();

    /**
     * Notify observers the playback process just stopped.
     */
    void playbackStopped();
}
