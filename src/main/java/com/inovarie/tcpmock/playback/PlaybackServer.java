package com.inovarie.tcpmock.playback;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executor;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Record;

public class PlaybackServer implements Runnable {

	private final int serverPort;
	private final Record record;
	private final Executor executor;

	public PlaybackServer(Executor executor, int serverPort, Record record) {
		this.serverPort = serverPort;
		this.record = record;
		this.executor = executor;
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
				executor.execute(new PlaybackHandler(serverConnectionSocket, connection));
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