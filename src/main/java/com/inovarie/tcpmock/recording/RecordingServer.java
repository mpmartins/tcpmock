package com.inovarie.tcpmock.recording;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

public class RecordingServer implements Runnable {

	private final PrintStream output;
	private final RecordingManager recordManager;
	private final int serverPort;
	private final String clientAddress;
	private final int clientPort;
	private final ForkJoinPool forkJoinPool;
	
	public RecordingServer(
	        ForkJoinPool forkJoinPool,
	        PrintStream output,
            RecordingManager recordManager,
            int serverPort,
            String clientAddress,
            int clientPort) {
		this.output = output;
		this.recordManager = recordManager;
		this.serverPort = serverPort;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
		this.forkJoinPool = forkJoinPool;
	}

	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
			output.println("RecordingServer is starting on port " + serverPort + " ...");
			while (RecordingManagerStatus.RECORDING == recordManager.getStatus()) {
				Socket serverConnectionSocket = serverSocket.accept();

				output.println("Accepting Connection...");
				forkJoinPool.execute(new RecordingHandler(
				        forkJoinPool,
                        output,
                        recordManager,
                        serverConnectionSocket,
                        clientAddress,
                        clientPort));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.println("The server is shut down!");
		}
	}
}
