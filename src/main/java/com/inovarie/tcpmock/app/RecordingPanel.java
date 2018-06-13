package com.inovarie.tcpmock.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.inovarie.tcpmock.file.RecordFileManager;
import com.inovarie.tcpmock.model.Record;
import com.inovarie.tcpmock.recording.RecordingManager;
import com.inovarie.tcpmock.recording.RecordingServer;

@SuppressWarnings("serial")
public class RecordingPanel extends JPanel implements ActionListener {

	private JTextField serverPortText;
	private JTextField clientAddressText;
	private JTextField clientPortText;
	private JTextField fileNameText;

	private PrintStream output;

	private ImageIcon iconRecord = new ImageIcon(getClass().getResource("/images/Record.gif"));
	private ImageIcon iconStop = new ImageIcon(getClass().getResource("/images/Stop.gif"));
	private JButton recordingButton;

	private boolean isRecording = false;
	private RecordingManager recordManager;

	public RecordingPanel() {
		initComponents();
	}

	public void initComponents() {
		this.setLayout(new BorderLayout());

		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JPanel recordingForm = new JPanel(new GridLayout(4, 2, 5, 5));
		recordingForm.setBorder(BorderFactory.createTitledBorder("Options"));

		recordingForm.add(new JLabel("Server Port: "));
		serverPortText = new JTextField("5555");
		serverPortText.setPreferredSize(new Dimension(50, 20));
		recordingForm.add(serverPortText);

		recordingForm.add(new JLabel("Client Address: "));
		clientAddressText = new JTextField("localhost");
		clientAddressText.setPreferredSize(new Dimension(200, 20));
		recordingForm.add(clientAddressText);

		recordingForm.add(new JLabel("Client Port: "));
		clientPortText = new JTextField("3306");
		clientPortText.setPreferredSize(new Dimension(50, 20));
		recordingForm.add(clientPortText);

		recordingForm.add(new JLabel("Filename: "));
		fileNameText = new JTextField("test");
		fileNameText.setPreferredSize(new Dimension(100, 20));
		recordingForm.add(fileNameText);

		controlPanel.add(recordingForm);

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		recordingButton = new JButton("Start Recording");
		recordingButton.setPreferredSize(new Dimension(160, 40));
		recordingButton.setFont(new Font("Sans", Font.BOLD, 14));
		recordingButton.setIcon(iconRecord);
		recordingButton.addActionListener(this);
		buttonsPanel.add(recordingButton);
		controlPanel.add(buttonsPanel);
		this.add(controlPanel, BorderLayout.NORTH);

		JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
		outputPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JTextArea outputTextArea = new JTextArea();
		outputTextArea.setAutoscrolls(true);
		outputTextArea.setEditable(false);
		output = new PrintStream(new TextAreaOutputStream(outputTextArea));
		JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
		outputPanel.add(outputScrollPane);
		this.add(outputPanel, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if (button == recordingButton) {
			if (!isRecording) {
				isRecording = true;
				recordingButton.setText("Stop Recording");
				recordingButton.setIcon(iconStop);

				startRecording();
			} else {
				isRecording = false;
				recordingButton.setText("Start Recording");
				recordingButton.setIcon(iconRecord);

				stopRecording();
			}
		}
	}

	private void startRecording() {
		Record record = new Record(fileNameText.getText());

		recordManager = RecordingManager.getInstance(record);
		recordManager.startRecord();

		new Thread(new RecordingServer(
				output, recordManager, 
				Integer.parseInt(serverPortText.getText()),
				clientAddressText.getText(), 
				Integer.parseInt(clientPortText.getText()))).start();
	}

	private void stopRecording() {
		recordManager.stopRecord();
		RecordFileManager.saveRecord(recordManager.getRecord());
	}

}
