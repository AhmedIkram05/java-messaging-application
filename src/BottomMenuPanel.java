import javax.swing.*;
import java.awt.*;

/**
 * @Author _se.ho
 * @create 2024-03-23
 */
public class BottomMenuPanel extends JPanel {

	private static final String EDIT = "Edit";
	private static final String CONTACTS = "Contacts";
	private static final String CHAT = "Chat";
	private JButton btnEdit;
	private JButton btnContacts;
	private JButton btnChat;

	public BottomMenuPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new GridLayout(1, 3, 5, 50));
	}

	private void initComponents() {
		btnEdit = new JFlatButton();
		addButtons(EDIT, btnEdit);
		btnContacts = new JFlatButton();
		addButtons(CONTACTS, btnContacts);
		btnChat = new JFlatButton();
		addButtons(CHAT, btnChat);
	}

	private void addButtons(final String caption, JButton btn) {
		btn.setText(caption);

		add(btn);
	}

	public JButton getBtnEdit() {
		return btnEdit;
	}

	public JButton getBtnContacts() {
		return btnContacts;
	}

	public JButton getBtnChat() {
		return btnChat;
	}
}
