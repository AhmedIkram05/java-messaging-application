import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-24
 */
public class MessageCard extends JPanel implements ActionListener {
	private static final String DELETE_MESSAGE = "Delete message";
	private static final String EDIT_MESSAGE = "Edit message";
	private static final String SAVE = "Save";
	private static final String OK = "Ok";
	private static final String CANCEL = "Cancel";
	private JLabel lblAuthor;
	private JTextArea txtMessage;
//	private JTextField txtMessage;

	private Map<Emoji, JLabel> emojiJLabelMap;
	private Message message;

	private boolean editable;

	private JMenuItem itemEdit;

	private boolean isHighlighted = false;
	private final Border border = GraphicsUtils.Borders.createLoweredEtchedBorder();
	private final Border redBorder = BorderFactory.createLineBorder(Color.RED, 5);

	private Timer highligter = null;

	public MessageCard() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(border);
		setLayout(new BorderLayout(5, 5));
	}

	private void initComponents() {
		JPanel pAuthor = new JPanel(new BorderLayout());
		pAuthor.setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		lblAuthor = new JLabel("Author Name");
		pAuthor.add(lblAuthor, BorderLayout.CENTER);
		add(pAuthor, BorderLayout.PAGE_START);

		JPanel pMess = new JPanel(new BorderLayout());
		pMess.setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		txtMessage = new JTextArea("Message Text", 6, 30);
//		txtMessage.setColumns(30);
		txtMessage.setLineWrap(true);
		txtMessage.setWrapStyleWord(true);
		txtMessage.setEditable(false);
		txtMessage.setBackground(new Color(255, 236, 236, 255));
		JScrollPane
			sp =
			new JScrollPane(
				txtMessage,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
//		sp.setPreferredSize(new Dimension(272, 250));
		sp.getVerticalScrollBar().setUnitIncrement(5);
		sp.setViewportBorder(
			BorderFactory.createCompoundBorder());
		pMess.add(sp, BorderLayout.CENTER);
//		pMess.add(txtMessage, BorderLayout.CENTER);
		add(pMess, BorderLayout.CENTER);

		JPanel pEmoji = new JPanel();
//		pEmoji.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		pEmoji.setLayout(new BoxLayout(pEmoji, BoxLayout.X_AXIS));
//		pEmoji.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pEmoji.setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		emojiJLabelMap = new HashMap<>();
		Arrays.stream(Emoji.values()).forEach(e -> {
			JPanel p = new JPanel();
			JLabel l = new JLabel();
			emojiJLabelMap.put(e, l);
			p.setAlignmentX(Component.RIGHT_ALIGNMENT);
			p.add(l);
			pEmoji.add(p);
		});
//		pEmoji.setAlignmentX(Component.RIGHT_ALIGNMENT);
		createPopupMenu(pEmoji);
		add(pEmoji, BorderLayout.PAGE_END);
	}

	public void Highlighted() {
		setBorder(redBorder);
		this.revalidate();
		this.repaint();
		if (Objects.isNull(highligter)) {
			highligter = new Timer(600, this);
			highligter.setActionCommand("Hide highligter");
			highligter.setRepeats(false);
		}
		highligter.start();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(final Message message) {
		this.message = message;
		showContent();
		lblAuthor.setText(this.message.getAuthor().getName());
		this.message.getEmojies()
			.forEach(this::showEmoji);
	}

	private void showContent() {
		txtMessage.setText(this.message.getContent());
	}

	public void showEmoji(Emoji emoji) {
		emojiJLabelMap.get(emoji).setIcon(emoji.getEmoji());
		message.addEmoji(emoji);
	}

	public void hideEmoji(Emoji emoji) {
		emojiJLabelMap.get(emoji).setIcon(null);
		message.removeEmoji(emoji);
	}

	private void doDeleteMessage() {
//		https://docs.oracle.com/javase/6/docs/api/javax/swing/SwingUtilities.html#getAncestorOfClass%28java.lang.Class,%20java.awt.Component%29
// Need to find MessageBoard and indicate that MessageCard has been deleted
		final Container ancestorOfClass = SwingUtilities.getAncestorOfClass(MessagesPanel.class, this.getParent());
		if (Objects.nonNull(ancestorOfClass)
			&& ancestorOfClass instanceof MessagesPanel mp) {
			// Notify the MessagePanel about the need to remove the MessageCard
			mp.removeMessageFromChat(this);
		}
	}

	private void doEditMessage() {
		final PopupDialog popup = new PopupDialog();
		popup.setEditedInfo(message.getContent());
		Point p = this.getLocationOnScreen();
		popup.setLocation(p.x, p.y + this.getSize().height);
		popup.pack();
		popup.setVisible(true);
		message.setContent(popup.getEditedInfo());
		showContent();
	}

	public void createPopupMenu(JPanel pEmoji) {
//		JMenuItem menuItem;

		//Create the popup menu.
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem(DELETE_MESSAGE);
		mi.setActionCommand(DELETE_MESSAGE);
		// Assign the class itself as the handler for these menu items and specify conditions for their execution in the actionPerformed method.
		mi.addActionListener(this);
		popup.add(mi);

		itemEdit = new JMenuItem(EDIT_MESSAGE);
		itemEdit.setActionCommand(EDIT_MESSAGE);
		itemEdit.addActionListener(this);
		// Only own message may be edited
		itemEdit.setEnabled(editable);
		popup.add(itemEdit);

		popup.addSeparator();
		Arrays.stream(Emoji.values()).forEach(e -> {
			// These MenuItems have an Action associated with them, where the reaction method is overridden to handle the event.
			JMenuItem menuItem = new JMenuItem(new EmojiAction(e));
			popup.add(menuItem);
		});

		//Add listener to the text area so the popup menu can come up.
		MouseListener popupListener = new PopupListener(popup);
		this.addMouseListener(popupListener);
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case EDIT_MESSAGE -> doEditMessage();
			case DELETE_MESSAGE -> doDeleteMessage();
			default -> {
				highligter.stop();
				setBorder(border);
				this.revalidate();
				this.repaint();
			}
		}
	}

	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/*
	The auxiliary class for displaying the popup menu is taken from the Oracle tutorial.
	https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/PopupMenuDemoProject/src/components/PopupMenuDemo.java
	 */
	class PopupListener extends MouseAdapter {
		JPopupMenu popup;

		PopupListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// refresh status editable
				itemEdit.setEnabled(editable);
				popup.revalidate();
				popup.show(e.getComponent(),
					e.getX(), e.getY()
				);
			}
		}
	}

	/*
	Class for managing the popup menu and displaying/hiding emojis beneath the message.
	https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html
	https://docs.oracle.com/javase/tutorial/uiswing/examples/misc/ActionDemoProject/src/misc/ActionDemo.java
	Refactored the LeftAction class to suit my needs.
	 */
	public class EmojiAction extends AbstractAction {

		private final Emoji emoji;
		private boolean isShow;

		public EmojiAction(Emoji emoji) {
			super(emoji.getName(), emoji.getEmoji());
			this.emoji = emoji;
			isShow = false;

			putValue(SHORT_DESCRIPTION, "<html>" + this.emoji.getLicenceEmoji() + "</html>");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isShow) {
				hideEmoji(emoji);
			} else {
				showEmoji(emoji);
			}
			isShow = !isShow;
//			displayResult("Action for first button/menu item", e);
		}
	}

	/*
	 * Simple dialog containing the actual editing component
	 */
	class PopupDialog extends JDialog implements ActionListener {

		private final JTextField txtEditMessage;

		private String savedText;

		public PopupDialog() {
			super((Frame) null, EDIT_MESSAGE, true);

			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			txtEditMessage = new JTextField();
			panel.add(txtEditMessage, BorderLayout.CENTER);
			add(panel);
//			JPanel p = new JPanel(new BorderLayout());
//			p.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
//			p.add(panel, BorderLayout.CENTER);
			JButton cancel = new JFlatButton(CANCEL);
			cancel.addActionListener(this);
			JButton ok = new JFlatButton(OK);
			ok.setPreferredSize(cancel.getPreferredSize());
			ok.addActionListener(this);

			JPanel buttons = new JPanel();
			buttons.add(ok);
			buttons.add(cancel);
			getContentPane().add(buttons, BorderLayout.SOUTH);
			pack();

			getRootPane().setDefaultButton(ok);
		}

		public void setEditedInfo(String text) {
			savedText = text;
			txtEditMessage.setText(text);
		}

		public String getEditedInfo() {
			return txtEditMessage.getText().trim();
		}

		/*
		 * Save the changed text before hiding the popup
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case CANCEL -> setEditedInfo(savedText);
				case OK -> {
					String s = getEditedInfo();
					if (Objects.isNull(s) || s.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Doesn't accept empty message");
						txtEditMessage.requestFocus();
						return;
					}
				}
				default -> {
				}
			}
			setVisible(false);
		}
	}
}
