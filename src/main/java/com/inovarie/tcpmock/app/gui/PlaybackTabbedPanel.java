package com.inovarie.tcpmock.app.gui;


import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/**
 * Playback panel.
 * No business rule here.
 */
@SuppressWarnings("serial")
@org.springframework.stereotype.Component
public class PlaybackTabbedPanel extends JPanel {

    private static final String START_PLAYBACK_BUTTON = "Start Playback";
    private static final String STOP_PLAYBACK_BUTTON = "Stop Playback";

    private static final String OPTIONS_STR = "Options";
    private static final String SERVER_PORT_STR = "Server Port:";
    private static final String SERVER_PORT_FIELD = "5555";

    private static final String FILE_NAME_STR = "Filename:";
    private static final String FILE_NAME_FIELD = "test";


    private final TCPMockController tcpMockController;

    private JButton startPlaybackButton;

    private JPanel outputPanel;

    private JTextArea outputTextArea;
    private PrintStream output;

    private JComponent serverPortPanel;
    private JComponent serverPortLabel;
    private JTextField serverPortTextField;
    private JComponent optionsPanel;
    private JComponent upperPanel;
    private JComponent buttonPanel;
    private JComponent fieldNamePanel;
    private JComponent fileNameLabel;
    private JTextField fileNameTextField;

    @Inject
    public PlaybackTabbedPanel(TCPMockController tcpMockController) {
        this.tcpMockController = tcpMockController;
    }

    protected void initComponents(Dimension windowSize) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(windowSize);
        this.add(getUpperPanel());
        this.add(getOutputPanel());
    }

    private Component getUpperPanel() {
        if (upperPanel == null) {
            upperPanel = new JPanel();
            upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.LINE_AXIS));
            upperPanel.add(getOptionsPanel());
            upperPanel.add(Box.createRigidArea(GuiConstants.HORIZONTAL_SPACE_5_PX));
            upperPanel.add(getButtonPanel());
        }
        return upperPanel;
    }

    private Component getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
            buttonPanel.add(getStartPlaybackButton());
            buttonPanel.add(Box.createHorizontalGlue());
        }
        return buttonPanel;
    }


    private JButton getStartPlaybackButton() {
        if (startPlaybackButton == null) {
            startPlaybackButton = new JButton(START_PLAYBACK_BUTTON);
            startPlaybackButton.setPreferredSize(GuiConstants.BIG_BUTTON_SIZE);
            startPlaybackButton.setMinimumSize(GuiConstants.BIG_BUTTON_SIZE);
            startPlaybackButton.setMaximumSize(GuiConstants.BIG_BUTTON_SIZE);
            startPlaybackButton.addActionListener(evt -> processPlaybackButton());
            startPlaybackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return startPlaybackButton;
    }


    private Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
            optionsPanel.setBorder(BorderFactory.createTitledBorder(OPTIONS_STR));
            optionsPanel.add(getServerPortPanel());
            optionsPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
            optionsPanel.add(getFileNamePanel());
        }
        return optionsPanel;
    }

    private Component getServerPortPanel() {
        if (serverPortPanel == null) {
            serverPortPanel = new JPanel();
            serverPortPanel.setLayout(new BoxLayout(serverPortPanel, BoxLayout.LINE_AXIS));
            serverPortPanel.add(getServerPortLabel());
            serverPortPanel.add(Box.createHorizontalGlue());
            serverPortPanel.add(getServerPortTextField());
        }
        return serverPortPanel;
    }

    private Component getFileNamePanel() {
        if (fieldNamePanel == null) {
            fieldNamePanel = new JPanel();
            fieldNamePanel.setLayout(new BoxLayout(fieldNamePanel, BoxLayout.LINE_AXIS));
            fieldNamePanel.add(getFileNameLabel());
            fieldNamePanel.add(Box.createHorizontalGlue());
            fieldNamePanel.add(getFileNameTextField());
        }
        return fieldNamePanel;
    }

    private JTextField getServerPortTextField() {
        if (serverPortTextField == null) {
            serverPortTextField = new JTextField(SERVER_PORT_FIELD);
            serverPortTextField.setPreferredSize(GuiConstants.TEXT_FIELD_SIZE);
            serverPortTextField.setMinimumSize(GuiConstants.TEXT_FIELD_SIZE);
            serverPortTextField.setMaximumSize(GuiConstants.TEXT_FIELD_SIZE);
        }
        return serverPortTextField;
    }

    private JTextField getFileNameTextField() {
        if (fileNameTextField == null) {
            fileNameTextField = new JTextField(FILE_NAME_FIELD);
            fileNameTextField.setPreferredSize(GuiConstants.TEXT_FIELD_SIZE);
            fileNameTextField.setMinimumSize(GuiConstants.TEXT_FIELD_SIZE);
            fileNameTextField.setMaximumSize(GuiConstants.TEXT_FIELD_SIZE);
        }
        return fileNameTextField;
    }

    private Component getServerPortLabel() {
        if (serverPortLabel == null) {
            serverPortLabel = new JLabel(SERVER_PORT_STR);
            serverPortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return serverPortLabel;
    }

    private Component getFileNameLabel() {
        if (fileNameLabel == null) {
            fileNameLabel = new JLabel(FILE_NAME_STR);
            fileNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return fileNameLabel;
    }

    private JComponent getOutputPanel() {
        if (outputPanel == null) {
            outputPanel = new JPanel();
            outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.LINE_AXIS));
            outputPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            JScrollPane outputScrollPane = new JScrollPane(getOutputTextArea());

            outputPanel.add(outputScrollPane);
//            outputPanel.add(getCheckboxPanel());
        }
        return outputPanel;
    }

    private PrintStream getOutput() {
        if (output == null) {
            output = new PrintStream(new TextAreaOutputStream(getOutputTextArea()));
        }
        return output;
    }

    private JTextArea getOutputTextArea() {
        if (outputTextArea == null) {
            outputTextArea = new JTextArea();

            outputTextArea.setAutoscrolls(true);
            outputTextArea.setEditable(false);
        }
        return outputTextArea;
    }

    protected void processPlaybackButton() {
        (new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                tcpMockController.processPlaybackButton(
                        getOutput(),
                        getFileNameTextField().getText(),
                        Integer.parseInt(getServerPortTextField().getText()));
                return null;
            }
            @Override
            protected void done() {

            }
        }).execute();
    }

    protected void playbackStarted() {
        getStartPlaybackButton().setText(STOP_PLAYBACK_BUTTON);
//        getStartPlaybackButton().setIcon(iconStop);
    }

    protected void playbackStopped() {
        getStartPlaybackButton().setText(START_PLAYBACK_BUTTON);
//        getStartPlaybackButton().setIcon(iconStart);
    }
}
