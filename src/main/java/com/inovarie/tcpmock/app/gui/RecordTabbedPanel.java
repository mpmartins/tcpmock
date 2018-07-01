package com.inovarie.tcpmock.app.gui;


import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.PrintStream;

/**
 * Recording panel.
 * No business rule here.
 */
@org.springframework.stereotype.Component
public class RecordTabbedPanel extends JPanel {

    private static final String START_RECORDIN_BUTTON = "Start Recording";
    private static final String STOP_RECORDIN_BUTTON = "Stop Recording";

    private static final String OPTIONS_STR = "Options";
    private static final String SERVER_PORT_STR = "Server Port:";
    private static final String CLIENT_PORT_STR = "Client Port:";

    private static final String SERVER_PORT_FIELD = "5555";
    private static final String CLIENT_PORT_FIELD = "3306";

    private static final String CLIENT_ADDRESS_STR = "Client Address:";
    private static final String CLIENT_ADDRESS_FIELD_STR = "localhost";
    private static final String FILE_NAME_STR = "Filename:";
    private static final String FILE_NAME_FIELD = "test";


    private final TCPMockController tcpMockController;

    private JButton startRecordinButton;

    private JPanel outputPanel;

    private JTextArea outputTextArea;
    private java.io.PrintStream output;

    private JComponent serverPortPanel;
    private JComponent serverPortLabel;
    private JTextField serverPortTextField;
    private JComponent optionsPanel;
    private JComponent clientAddressPanel;
    private JComponent clientAddressLabel;
    private JTextField clientAddressTextField;
    private JComponent upperPanel;
    private JComponent buttonPanel;
    private JComponent clientPortPanel;
    private JComponent clientPortLabel;
    private JTextField clientPortTextField;
    private JComponent fieldNamePanel;
    private JComponent fileNameLabel;
    private JTextField fileNameTextField;

    @Inject
    public RecordTabbedPanel(TCPMockController tcpMockController) {
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
            buttonPanel.add(getStartRecordinButton());
            buttonPanel.add(Box.createHorizontalGlue());
        }
        return buttonPanel;
    }


    private JButton getStartRecordinButton() {
        if (startRecordinButton == null) {
            startRecordinButton = new JButton(START_RECORDIN_BUTTON);
            startRecordinButton.setPreferredSize(GuiConstants.BIG_BUTTON_SIZE);
            startRecordinButton.setMinimumSize(GuiConstants.BIG_BUTTON_SIZE);
            startRecordinButton.setMaximumSize(GuiConstants.BIG_BUTTON_SIZE);
            startRecordinButton.addActionListener(evt -> processRecordingButton());
            startRecordinButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return startRecordinButton;
    }


    private Component getOptionsPanel() {
        if (optionsPanel == null) {
            optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
            optionsPanel.setBorder(BorderFactory.createTitledBorder(OPTIONS_STR));
            optionsPanel.add(getServerPortPanel());
            optionsPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
            optionsPanel.add(getClientAddressPanel());
            optionsPanel.add(Box.createRigidArea(GuiConstants.VERTICAL_SPACE_5_PX));
            optionsPanel.add(getClientPortPanel());
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

    private Component getClientPortPanel() {
        if (clientPortPanel == null) {
            clientPortPanel = new JPanel();
            clientPortPanel.setLayout(new BoxLayout(clientPortPanel, BoxLayout.LINE_AXIS));
            clientPortPanel.add(getClientPortLabel());
            clientPortPanel.add(Box.createHorizontalGlue());
            clientPortPanel.add(getClientPortTextField());
        }
        return clientPortPanel;
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

    private Component getClientAddressPanel() {
        if (clientAddressPanel == null) {
            clientAddressPanel = new JPanel();
            clientAddressPanel.setLayout(new BoxLayout(clientAddressPanel, BoxLayout.LINE_AXIS));
            clientAddressPanel.add(getClientAddressLabel());
            clientAddressPanel.add(Box.createHorizontalGlue());
            clientAddressPanel.add(getClientAddressTextField());
        }
        return clientAddressPanel;
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

    private JTextField getClientPortTextField() {
        if (clientPortTextField == null) {
            clientPortTextField = new JTextField(CLIENT_PORT_FIELD);
            clientPortTextField.setPreferredSize(GuiConstants.TEXT_FIELD_SIZE);
            clientPortTextField.setMinimumSize(GuiConstants.TEXT_FIELD_SIZE);
            clientPortTextField.setMaximumSize(GuiConstants.TEXT_FIELD_SIZE);
        }
        return clientPortTextField;
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
    private JTextField getClientAddressTextField() {
        if (clientAddressTextField == null) {
            clientAddressTextField = new JTextField(CLIENT_ADDRESS_FIELD_STR);
            clientAddressTextField.setPreferredSize(GuiConstants.TEXT_FIELD_SIZE);
            clientAddressTextField.setMinimumSize(GuiConstants.TEXT_FIELD_SIZE);
            clientAddressTextField.setMaximumSize(GuiConstants.TEXT_FIELD_SIZE);
        }
        return clientAddressTextField;
    }

    private Component getServerPortLabel() {
        if (serverPortLabel == null) {
            serverPortLabel = new JLabel(SERVER_PORT_STR);
            serverPortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return serverPortLabel;
    }

    private Component getClientPortLabel() {
        if (clientPortLabel == null) {
            clientPortLabel = new JLabel(CLIENT_PORT_STR);
            clientPortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return clientPortLabel;
    }

    private Component getFileNameLabel() {
        if (fileNameLabel == null) {
            fileNameLabel = new JLabel(FILE_NAME_STR);
            fileNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return fileNameLabel;
    }

    private Component getClientAddressLabel() {
        if (clientAddressLabel == null) {
            clientAddressLabel = new JLabel(CLIENT_ADDRESS_STR);
            clientAddressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return clientAddressLabel;
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

    protected void processRecordingButton() {
        (new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                tcpMockController.processRecordingButton(
                        getOutput(),
                        getFileNameTextField().getText(),
                        getClientAddressTextField().getText(),
                        Integer.parseInt(getServerPortTextField().getText()),
                        Integer.parseInt(getClientPortTextField().getText()));
                return null;
            }
            @Override
            protected void done() {

            }
        }).execute();
    }

    protected void recordStarted() {
        getStartRecordinButton().setText(STOP_RECORDIN_BUTTON);
//        getStartRecordinButton().setIcon(iconStop);
    }

    protected void recordStopped() {
        getStartRecordinButton().setText(START_RECORDIN_BUTTON);
//        getStartRecordinButton().setIcon(iconStart);
    }
}
