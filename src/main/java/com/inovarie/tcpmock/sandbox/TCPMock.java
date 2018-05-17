package com.inovarie.tcpmock.sandbox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class TCPMock extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TCPMock().setVisible(true);
            }
        });
	}
	
	public TCPMock() {
        super("TCP Mock");
        initComponents();
    }

	private void initComponents() {
		JTabbedPane mainPanel = new JTabbedPane();
        mainPanel.add("Recording", buildRecordingPanel());
        mainPanel.add("Playback", buildPlaybackPanel());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
	}

	private JPanel buildRecordingPanel() {
		JPanel recordingPanel = new JPanel(new BorderLayout());
        
    	JPanel controlPanel = new JPanel(new GridLayout(2, 2));
    		JPanel recordingForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	recordingForm.setBorder(BorderFactory.createTitledBorder("Options"));
		    	JLabel portLabel =  new JLabel("Server Port: ");
		        recordingForm.add(portLabel);
		        JTextField portText = new JTextField("5555");
		        portText.setPreferredSize(new Dimension(100, 20));
	        recordingForm.add(portText);
	    controlPanel.add(recordingForm);
	        
	    	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        	JButton startRecordingButton = new JButton("Start Recording");
	        buttonsPanel.add(startRecordingButton);
	    controlPanel.add(buttonsPanel);
	    

	    
        recordingPanel.add(controlPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane();
	        JTextArea output = new JTextArea();
	        output.setAutoscrolls(true);
	    scrollPane.add(output);
        recordingPanel.add(scrollPane, BorderLayout.CENTER);
        
		return recordingPanel;
	}
	
	private JPanel buildPlaybackPanel() {
		return new JPanel(new BorderLayout());
	}

}