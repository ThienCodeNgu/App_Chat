package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LoginServer extends Thread {
	
	private int port;
	public static ArrayList<acc> listLogin;
	private Socket socket;
	private ServerSocket sv;
	private DataOutputStream dos;

	public LoginServer(int port) {
		this.port = port;
	}


	public void run() {
		System.out.println("Login server is waiting in port: "+port);
		
		try {
			sv = new ServerSocket(port);
			while (true) {
				socket = sv.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				String username = dis.readUTF();
				int kq = 0;
				for (acc a : listLogin) {
					if (a.getUsername().equals(username)) {
						kq = 1;
						break;
					}else {
						kq = 0;
						break;
					}
				}
				if (kq == 1) {
					dos.writeUTF("no");
					dos.flush();
					socket.close();
				}else {
					dos.writeUTF("yes");
					dos.flush();
					acc a = new acc(username);
					listLogin.add(a);
					socket.close();
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
