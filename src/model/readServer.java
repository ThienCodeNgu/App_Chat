package Model;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;

import View.ServerFrame;

public class ReadServer extends Thread {
	private Socket socket;

	public ReadServer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socket.getInputStream());

			while (true) {
				String type = dis.readUTF();
				if (type.equals("Online users")) {
					String sms = "";
					for (Account acc : ServerThread.listAccount) {
						sms += ",";
						sms += acc.getFullname();
					}
					for (Socket item : ServerThread.listSK) {

						DataOutputStream dos = new DataOutputStream(item.getOutputStream());
						dos.writeUTF("Online users");
						dos.writeUTF(sms);
						dos.flush();

					}
				} else if (type.equals("text")) {
					String sms = dis.readUTF();
					for (Socket item : ServerThread.listSK) {
						if (item.getPort() != socket.getPort()) {
							DataOutputStream dos = new DataOutputStream(item.getOutputStream());
							dos.writeUTF("text");
							dos.writeUTF(sms);

						}
					}

				} else if (type.equals("Emoji")) {
					String sms = dis.readUTF();
					String name = dis.readUTF();
					for (Socket item : ServerThread.listSK) {
						if (item.getPort() != socket.getPort()) {
							DataOutputStream dos = new DataOutputStream(item.getOutputStream());
							dos.writeUTF("Emoji");
							dos.writeUTF(sms);
							dos.writeUTF(name);

						}
					}

				} else if (type.equals("file")) {
					String name = dis.readUTF();
					String nameFile = dis.readUTF();

					int fileNameLength = dis.readInt();

					byte[] fileNameBytes = new byte[fileNameLength];
					dis.readFully(fileNameBytes, 0, fileNameBytes.length);
					String fileName = new String(fileNameBytes);

					int fileContentLength = dis.readInt();

					byte[] fileContentBytes = new byte[fileContentLength];
					dis.readFully(fileContentBytes, 0, fileContentLength);

					for (Socket item : ServerThread.listSK) {
						if (item.getPort() != socket.getPort()) {
							DataOutputStream dos = new DataOutputStream(item.getOutputStream());
							dos.writeUTF("file");
							dos.writeUTF(name);
							dos.writeUTF(nameFile);

							// Gửi độ dài của tên file
							dos.writeInt(fileNameLength);
							dos.write(fileNameBytes);

							// Gửi độ dài của nội dung file
							dos.writeInt(fileContentLength);
							dos.write(fileContentBytes);

							dos.flush();

						}
					}

				}

				else if (type.equals("log")) {
					System.out.println("server nhận " + type);
					String name = dis.readUTF();
					String username = dis.readUTF();
					Account a = new Account(name, socket.getPort());
					ServerThread.listAccount.remove(a);

					ServerFrame.NumConnect.setText(ServerThread.listAccount.size() + "");

					acc account = new acc(username);
					LoginServer.listLogin.remove(account);

					String sms = "";
					for (Account acc : ServerThread.listAccount) {
						sms += ",";
						sms += acc.getFullname();
					}

					for (Socket item : ServerThread.listSK) {

						DataOutputStream dos = new DataOutputStream(item.getOutputStream());
						dos.writeUTF("log");
						dos.writeUTF(sms);
						dos.flush();

					}

				}

			}

		} catch (IOException e) {
			try {
				dis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
