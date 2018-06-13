package com.inovarie.tcpmock.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
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
        mainPanel.add("Recording", new RecordingPanel());
        mainPanel.add("Playback", new PlaybackPanel());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
	}

}