package model;

import javax.swing.*;
import java.awt.*;

public class CustomListCellRenderer extends DefaultListCellRenderer {
	
	private String name;
	

    public CustomListCellRenderer(String name) {
		this.name = name;
	}


	@Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        String text = label.getText();
        if (text.contains(name)) {
            label.setForeground(Color.blue);
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

