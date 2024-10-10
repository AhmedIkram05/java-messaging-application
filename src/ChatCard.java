import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.joining;

/**
 * @Author _se.ho
 * @create 2024-03-29
 */
public class ChatCard extends JPanel
	implements MouseListener, ActionListener {

	public static final String CHATCARD_CHANGED = "ChatCard Changed";
	public static final String CHATCARD_ACTIVECHAT = "ChatCard Active Chat";
	public static final String CHATCARD_REMOVECHAT = "ChatCard Remove Chat";
	final String ICON_REMOVE = "src/man-remove.256.png";
	private Chat chat;
	private JLabel avatar;
	private JLabel chatName;
	private Integer countUnreadMessages;
	private JLabel lblCountUnreadMessages;
	private JLabel lblLastActivities;
	private JButton btnRemove;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

	private Dimension preferredSize;

	public ChatCard(Chat chat) {
		this.chat = chat;
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
//		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout(5, 5));
	}

	private void initComponents() {
		avatar = new JLabel(GraphicsUtils.Images.resizeIcon(this.chat.getOwner().getProfileImage(), 40, 50));
		add(avatar, BorderLayout.WEST);
		JPanel p = new JPanel(new BorderLayout());
		chatName = new JLabel(getChatName());
		p.add(chatName, BorderLayout.CENTER);
		lblCountUnreadMessages = new JLabel(" ");
		countUnreadMessages = 0;
		btnRemove = new JFlatButton(GraphicsUtils.Images.resizeIcon(new ImageIcon(ICON_REMOVE), 40, 50));
		btnRemove.addActionListener(this);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(btnRemove, BorderLayout.CENTER);
		p1.add(lblCountUnreadMessages, BorderLayout.EAST);
		add(p1, BorderLayout.EAST);
		lblLastActivities = new JLabel(chat.getLastActivities().format(formatter));
		p.add(lblLastActivities, BorderLayout.PAGE_END);
		add(p, BorderLayout.CENTER);
		preferredSize = new Dimension(250, 50);
		addMouseListener(this);
	}

	private String getChatName() {
		String chatName = chat.getMembers().stream().map(Contact::getName).collect(joining(", "));
		if (chatName.length() > 20) {
			// The last comma found was replaced with "..."
			chatName = chatName.substring(0, 17) + " ...";
		}
		return chatName;
	}

	public void updateView(){
		avatar.setIcon(GraphicsUtils.Images.resizeIcon(this.chat.getOwner().getProfileImage(), 40, 50));
		chatName.setText(getChatName());
		lblLastActivities.setText(chat.getLastActivities().format(formatter));
	}

	public Chat getChat() {
		return chat;
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public Dimension getMinimumSize() {
		return preferredSize;
	}

	public Dimension getMaximumSize() {
		return preferredSize;
	}

	private void setCountUnreadMessages(final Integer countUnreadMessages) {
		this.countUnreadMessages = countUnreadMessages;
		lblCountUnreadMessages.setText(String.valueOf(this.countUnreadMessages));
	}

	public void setLastActivities(LocalDateTime lastActivities) {
//		chat.setLastActivities(lastActivities);
		lblLastActivities.setText(chat.getLastActivities().format(formatter));
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		System.out.println("ChatCard::" + e);
		//		https://docs.oracle.com/javase/6/docs/api/javax/swing/SwingUtilities.html#getAncestorOfClass%28java.lang.Class,%20java.awt.Component%29
// Need to find MessageBoard and indicate that MessageCard has been deleted
//		final Container ancestorOfClass = SwingUtilities.getAncestorOfClass(ChatsPanel.class, this.getParent());
//		if (Objects.nonNull(ancestorOfClass)){
//			if (ancestorOfClass instanceof ChatsPanel cp){
//				// Notify the MessagePanel about the need to remove the MessageCard
//				setLastActivities(this);
//			}
//		}
		firePropertyChange(CHATCARD_ACTIVECHAT, null, this);
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(final MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseEntered(final MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseExited(final MouseEvent e) {

	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		firePropertyChange(CHATCARD_REMOVECHAT, null, this);
	}
}
