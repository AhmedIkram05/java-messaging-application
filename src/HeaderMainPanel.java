import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-28
 */
public class HeaderMainPanel extends JPanel {

	private static final String CHATS = "Chats";
	private static final String SETTINGS = "....";
	private static final String CONTACTS = "Contacts";
	private JLabel avatar;
	private JLabel lblNameChat;

	private JButton btnEdit;
	private JButton btnContacts;
	private JButton btnChat;
	private User user;

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 */
	public HeaderMainPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	private void initComponents() {
		add(Box.createRigidArea(new Dimension(5, 0)));
		avatar = new JLabel();
		add(avatar);
		add(Box.createHorizontalGlue());
		add(Box.createRigidArea(new Dimension(5, 0)));
		lblNameChat = new JLabel();
		add(lblNameChat);
		add(Box.createHorizontalGlue());
		btnContacts = addButton(CONTACTS);
		add(Box.createRigidArea(new Dimension(5, 0)));
		btnChat = addButton(CHATS);
		add(Box.createRigidArea(new Dimension(5, 0)));
		btnEdit = addButton(SETTINGS);
		add(Box.createRigidArea(new Dimension(5, 0)));
	}

	private JButton addButton(final String caption) {
		JButton btn = new JFlatButton(caption);
//		btn.setContentAreaFilled(false);
//		btn.setOpaque(true);
//		btn.addActionListener(this);
		add(btn);
		return btn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
		showUserInfo();
	}

	public void showUserInfo() {
		if (Objects.nonNull(user)) {
			avatar.setIcon(GraphicsUtils.Images.resizeIcon(user.getProfileImage(), 40, 40));
			lblNameChat.setText(user.getName());
			this.revalidate();
			this.repaint();
		}
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
