package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import view.serverFrame;


public class readServer extends Thread {
	private Socket socket;

public readServer(Socket socket) {
		this.socket = socket;
	}

public void run() {
	DataInputStream dis = null;
	try {
		dis = new DataInputStream(socket.getInputStream());
		while (true) {
			String sms = dis.readUTF();
			for (Socket item : serverThread.listSK) {
				if (item.getPort() != socket.getPort()) {
					DataOutputStream dos = new DataOutputStream(item.getOutputStream());
					dos.writeUTF(sms);
				}
			}
			serverFrame.model.addElement(sms);
		}
	} catch (Exception e) {
		try {
			dis.close();
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
}
