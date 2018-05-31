package com.inovarie.tcpmock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable {

	private static final long serialVersionUID = -1638436672550721377L;

	private String name;
	private List<Connection> connections;

	public Record(String name) {
		this.name = name;
		connections =  new ArrayList<>();
	}

	public synchronized Connection newConnection() {
		Connection connection = new Connection();
		connections.add(connection);
		return connection;
	}

	public String getName() {
		return name;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	@Override
	public String toString() {
		return "Record [name=" + name + ", connections=" + connections + "]";
	}

}
