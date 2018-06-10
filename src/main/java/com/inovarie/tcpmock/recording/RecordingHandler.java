package com.inovarie.tcpmock.recording;

import com.inovarie.tcpmock.model.Connection;
import com.inovarie.tcpmock.model.Source;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

public class RecordingHandler implements Runnable {

    private final ForkJoinPool forkJoinPool;
	private final PrintStream output;
	private final RecordingManager recordingManager;
	private final Socket serverConnectionSocket;
	private final String clientAddress;
	private final int clientPort;

	public RecordingHandler(
            ForkJoinPool forkJoinPool,
			PrintStream output,
			RecordingManager recordingManager,
			Socket serverConnectionSocket,
			String clientAddress,
			int clientPort) {
	    this.forkJoinPool = forkJoinPool;
		this.output = output;
		this.recordingManager = recordingManager;
		this.serverConnectionSocket = serverConnectionSocket;
		this.clientAddress = clientAddress;
		this.clientPort = clientPort;
	}

	public void run() {
		try {
			Connection connection = recordingManager.startConnection();

			output.println("Establishing client connection with client ("+clientAddress+":"+clientPort+") ...");
			Socket clientConnectionSocket = new Socket(clientAddress, clientPort);

            ForkJoinTask clientToServer =  forkJoinPool.submit(new CommunicationMonitor(
                    Source.CLIENT,
                    connection,
                    clientConnectionSocket.getInputStream(),
                    serverConnectionSocket.getOutputStream()));


            ForkJoinTask serverToClient = forkJoinPool.submit(new CommunicationMonitor(
					Source.SERVER,
					connection,
					serverConnectionSocket.getInputStream(),
					clientConnectionSocket.getOutputStream()));
			
			clientToServer.join();
			serverToClient.join();
			clientConnectionSocket.close();
			
			connection.endConnection();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
