package com.inovarie.tcpmock;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PlaybackServer {

	public static void main(String[] args) {

		int portNumber = 5555;
		String recordFile = args[0];
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("PlaybackServer is starting on port " + portNumber + " ...");
		} catch (IOException e) {
			System.out.println("Error on socket creation!");
			e.printStackTrace();
		}

		try{
			while (true) {
				try {
					Socket serverConnectionSocket = serverSocket.accept();
					System.out.println("Accepting Connection...");
	
					new PlaybackHandler(serverConnectionSocket, recordFile).start();
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.out.println("The server is shut down!");
	    }
		

	}

}
