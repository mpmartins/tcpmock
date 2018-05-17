package com.inovarie.tcpmock.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.inovarie.tcpmock.playback.PlaybackHandler;

public class PlaybackServer {

	private int serverPort;
	private String fileName;

	public PlaybackServer(int serverPort, String fileName) {
		this.serverPort = serverPort;
		this.fileName = fileName;
	}

	public static void main(String[] args) {
		PlaybackServer playbackServer = new PlaybackServer(Integer.parseInt(args[0]), args[1]);
		playbackServer.start();
	}

	public void start() {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("PlaybackServer is starting on port " + serverPort + " ...");

			while (true) {
				Socket serverConnectionSocket = serverSocket.accept();
				System.out.println("Accepting Connection...");

				new PlaybackHandler(serverConnectionSocket, fileName).start();
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