package Model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;

import View.homeFrame;

public class IconClient extends MouseAdapter {
	private String emoji;
	private String name;

	public IconClient(String emoji, String name) {
		this.emoji = emoji;
		this.name = name;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (homeFrame.sendBtn.isEnabled() == true) {

			try {
				ClientThread.dos.writeUTF("Emoji");
				ClientThread.dos.writeUTF(this.emoji);
				ClientThread.dos.writeUTF(name);
				ClientThread.dos.flush();
				homeFrame.modelListChat.addElement(name);
				homeFrame.modelListChat.addElement(new ImageIcon(this.emoji));
			} catch (IOException e1) {
				homeFrame.modelListChat.addElement("Không thể gửi icon!!!");
			}

		}
	}

}
