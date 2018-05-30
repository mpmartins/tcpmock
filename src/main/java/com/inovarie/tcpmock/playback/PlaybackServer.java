package com.inovarie.tcpmock.playback;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Record;

public class PlaybackServer implements Runnable {

	private int serverPort;
	private Record record;

	public PlaybackServer(int serverPort, Record record) {
		this.serverPort = serverPort;
		this.record = record;
	}

	public void run() {

		List<Connection> connections = record.getConnections();
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("PlaybackServer is starting on port " + serverPort + " ...");

			for (Connection connection : connections) {
				Socket serverConnectionSocket = serverSocket.accept();
				System.out.println("Accepting Connection...");

				new PlaybackHandler(serverConnectionSocket, connection).start();
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("The server is shut down!");
		}

	}

}