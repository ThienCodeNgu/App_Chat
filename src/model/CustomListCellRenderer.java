package Model;

import javax.swing.*;
import java.awt.*;

public class customListCellRenderer extends DefaultListCellRenderer {
	
	private String name;
	

    public customListCellRenderer(String name) {
		this.name = name;
	}


	@Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String text = label.getText();
        if (text.contains(name)) {
            label.setForeground(Color.blue);
            label.setText("Bạn");
        }
        else if(text.contains("Server is stopped")) {
        	label.setForeground(Color.red);
        }
        
        else {
            label.setForeground(list.getForeground());
        }
        
        return label;
    }
}

