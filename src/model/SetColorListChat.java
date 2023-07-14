package Model;

import javax.swing.*;
import java.awt.*;

public class SetColorListChat extends DefaultListCellRenderer {

	private String name;

	public SetColorListChat(String name) {
		this.name = name;
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String text = label.getText();
		if (text.contains(name)) {
			label.setForeground(Color.blue);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
		}

		else {
			label.setForeground(list.getForeground());
			label.setHorizontalAlignment(SwingConstants.LEADING);
		}
		
		if (index > 0) {
            Object previousValue = list.getModel().getElementAt(index - 1);
            if (previousValue.toString().contains(name)) {
                label.setHorizontalAlignment(SwingConstants.RIGHT);
            }
        }

		return label;
	}
}
