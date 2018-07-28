package com.inovarie.tcpmock.app.gui;

import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Container;
import java.awt.Dimension;

import org.springframework.stereotype.Component;

@Component
public class TCPMockView extends JFrame {
    private static final long serialVersionUID = 106395023477654820L;

    private static final String WINDOW_TITLE = "tcpmock";

    private static final Dimension WINDOW_SIZE = new Dimension(475, 520);
    private static final String RECORDING_STR = "Recording";
    private static final String PLAYBACK_STR = "Playback";

    private Container mainPanel;

    private final RecordTabbedPanel recordTabbedPanel;
    private final PlaybackTabbedPanel playbackTabbedPanel;

    private JTabbedPane mainTabbedPanel;

    @Inject
    public TCPMockView(RecordTabbedPanel recordTabbedPanel, PlaybackTabbedPanel playbackTabbedPanel) {
        this.recordTabbedPanel = recordTabbedPanel;
        this.playbackTabbedPanel = playbackTabbedPanel;
    }

    public void init() {
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setIconImage("GIVE ME AN ICON");
        setResizable(true);
        buildPanels();
        pack();
    }

    private void buildPanels() {
        getMainPanel().add(getMainTabbedPanel());
    }

    private JTabbedPane getMainTabbedPanel() {
        if (mainTabbedPanel == null) {
            mainTabbedPanel = new JTabbedPane();
            recordTabbedPanel.initComponents(WINDOW_SIZE);
            mainTabbedPanel.addTab(RECORDING_STR, recordTabbedPanel);
            playbackTabbedPanel.initComponents(WINDOW_SIZE);
            mainTabbedPanel.addTab(PLAYBACK_STR, playbackTabbedPanel);

        }
        return mainTabbedPanel;
    }

    private Container getMainPanel() {
        if (mainPanel == null) {
            mainPanel = this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
            mainPanel.setPreferredSize(WINDOW_SIZE);
        }
        return mainPanel;
    }

    protected void recordStarted() {
        recordTabbedPanel.recordStarted();
    }

    protected void recordStopped() {
        recordTabbedPanel.recordStopped();
    }

    protected void playbackStarted() { playbackTabbedPanel.playbackStarted();  }

    protected void playbackStopped() { playbackTabbedPanel.playbackStopped();  }

}

