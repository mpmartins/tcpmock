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

    private JTabbedPane mainTabbedPanel;

    private JComponent playTabbedPanel;

    @Inject
    public TCPMockView(RecordTabbedPanel recordTabbedPanel) {
        this.recordTabbedPanel = recordTabbedPanel;
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
            mainTabbedPanel.addTab(PLAYBACK_STR, getPlayTabbedPanel());

        }
        return mainTabbedPanel;
    }

    private JComponent getPlayTabbedPanel() {
        if (playTabbedPanel == null) {
            playTabbedPanel = new JPanel();
            playTabbedPanel.setLayout(new BoxLayout(playTabbedPanel, BoxLayout.PAGE_AXIS));
            playTabbedPanel.setPreferredSize(WINDOW_SIZE);
            playTabbedPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
            playTabbedPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
            playTabbedPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
        }
        return playTabbedPanel;
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
}

