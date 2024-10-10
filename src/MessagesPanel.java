import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * @Author _se.ho
 * @create 2024-03-24
 */
public class MessagesPanel extends JPanel implements ActionListener {
	private static final String SEARCH = "Search";
	private static final String SEND = "Send";
	final static String CANCEL_ACTION = "cancel-search";

	private HeaderMessagePanel headerMessagePanel;

	private JPanel messageBoard;

	private final Map<Message, MessageCard> messageToCardMap = new HashMap<>();
	private List<Map.Entry<Message, MessageCard>> foundedResult;
	private final AtomicInteger findedCounter = new AtomicInteger(0);
	private String forSearch;
	private InputMessagePanel inputMessagePanel;

	private Chat chat;

	public MessagesPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BorderLayout(5, 5));
	}

	private void initComponents() {
		headerMessagePanel = new HeaderMessagePanel();
		add(headerMessagePanel, BorderLayout.PAGE_START);

		messageBoard = new JPanel();
		messageBoard.setLayout(new BoxLayout(messageBoard, BoxLayout.Y_AXIS));
		JScrollPane
			sp =
			new JScrollPane(
				messageBoard,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
//		sp.setPreferredSize(new Dimension(272, 250));
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.setViewportBorder(
			BorderFactory.createCompoundBorder());
		add(sp, BorderLayout.CENTER);
//		add(messageBoard, BorderLayout.CENTER);
		addFillingBox();

		inputMessagePanel = new InputMessagePanel();
		add(inputMessagePanel, BorderLayout.PAGE_END);
	}

	private void addFillingBox() {
		Box b = Box.createHorizontalBox();
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.add(Box.createRigidArea(
			new Dimension(5, 5)
		));
		messageBoard.add(b);
		b = Box.createHorizontalBox();
		b.setAlignmentX(Component.RIGHT_ALIGNMENT);
		b.add(Box.createRigidArea(
			new Dimension(5, 5)
		));
		messageBoard.add(b);
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(final Chat chat) {
		this.prepareToNewChat();
		this.chat = chat;
		headerMessagePanel.setChat(chat);
		this.chat.getMessages().forEach(this::addMessageToBoard);
	}

	public JButton getSendBtn() {
		return inputMessagePanel.btnSend;
	}

	public JTextField getTxt() {
		return inputMessagePanel.txtInput;
	}

	private void prepareToNewChat() {
		Component[] arr = messageBoard.getComponents();
		for (Component c : arr) {
			messageBoard.remove(c);
		}
		messageToCardMap.clear();
	}

	private MessageCard addMessageToBoard(final Message message) {
		MessageCard mc = new MessageCard();
		mc.setMessage(message);
		mc.setAlignmentX(message.getAuthor().equals(chat.getOwner())
			? Component.LEFT_ALIGNMENT
			: Component.RIGHT_ALIGNMENT);
		mc.setEditable(message.getAuthor().equals(chat.getOwner()));
		messageToCardMap.put(message, mc);
		messageBoard.add(mc);
		messageBoard.revalidate();
		messageBoard.repaint();
		scrollCardToVisible(mc);
		return mc;
	}

	public void addMessageToChat(final Message message) {
		forSearch = "";
		this.chat.addMessage(message);
		scrollCardToVisible(addMessageToBoard(message));
	}

	public void removeMessageFromChat(final MessageCard messageCard) {
		SwingUtilities.invokeLater(() -> {
			messageToCardMap.remove(messageCard.getMessage());
			this.chat.getMessages().remove(messageCard.getMessage());
			messageBoard.remove(messageCard);
			// When deleting messages, reset the search to ensure that deleted messages are not retained in the search results.
			forSearch = "";
			messageBoard.revalidate();
			messageBoard.repaint();
		});
	}

	public Message getMessageForSend() {
		String mess = inputMessagePanel.getMessageText();
		if (Objects.nonNull(mess) && !mess.isEmpty()) {
			return chat.getOwner().sendMessage(mess, this.chat);
		}
		return null;
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case SEARCH -> doSearchInChat();
//			case SEND -> doSendNewMessage();
		}
	}

	private void doSendNewMessage() {
		String mess = inputMessagePanel.getMessageText();
		if (Objects.nonNull(mess) && !mess.isEmpty()) {
			Message message = chat.getOwner().sendMessage(mess, this.chat);
//			Message message = new Message(
//				this.chat,
//				mess,
//				chat.getOwner(),
//				false,
//				LocalDateTime.now()
//			);
			addMessageToChat(message);
			// Reset the search when new messages are received to include them in the search results as well.
			forSearch = "";
		}
	}

	private void doSearchInChat() {
		String searched = headerMessagePanel.getSearchText();

		if (Objects.nonNull(searched)
			&& searched.length() > 2
			&& !forSearch.equalsIgnoreCase(searched)) {
			// Remember what was searched for to display multiple results.
			forSearch = searched;
			// Form a list of results to display.
			foundedResult =
				messageToCardMap.entrySet()
					.stream()
					.filter(e -> e.getKey().getContent().toUpperCase().contains(searched.toUpperCase()))
					.sorted(Comparator
						.comparing(e -> ((Map.Entry<Message, MessageCard>) e).getKey().getDateAdded())
						.reversed())
					.collect(Collectors.toList());
			// Reset the counter of displayed items.
			findedCounter.set(0);

			if (foundedResult.isEmpty()) {
				headerMessagePanel.setSearchError();
			}
//		} else {
//			foundedResult = null;
		}
		// show results search
		// Check that we found something and that the display counter hasn't exceeded the limits.
		if (Objects.nonNull(foundedResult) && !foundedResult.isEmpty() && findedCounter.get() < foundedResult.size()) {
			// Get the card to be displayed.
			final MessageCard card = foundedResult.get(findedCounter.getAndIncrement()).getValue();
			scrollCardToVisible(card);
			// Highlight with a red border to draw attention to the message card.
			// The card has a timer set to 600 milliseconds to turn off the highlight.
			card.Highlighted();

			// circling process showing results
			// Loop the display of messages by adjusting the display counter values.
			if (findedCounter.get() >= foundedResult.size()) {
				findedCounter.set(0);
			}
		}
	}

	private static void scrollCardToVisible(final MessageCard card) {
		// Message card! Show yourself! :)
		SwingUtilities.invokeLater(() -> card.scrollRectToVisible(card.getBounds()));
//		card.scrollRectToVisible(card.getBounds());
	}

	private Component leftJustify(JPanel panel) {
		Box b = Box.createHorizontalBox();
		b.add(panel);
		b.add(Box.createHorizontalGlue());
		// (Note that you could throw a lot more components
		// and struts and glue in here.)
		return b;
	}

	private Component rightJustify(JPanel panel) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(panel);
		// (Note that you could throw a lot more components
		// and struts and glue in here.)
		return b;
	}

	public void updateView() {
		Chat chat = this.getChat();
		this.setChat(chat);
	}

	private class HeaderMessagePanel extends JPanel {

		private JLabel avatar;
		private JLabel lblNameChat;

		private Color entryBg;
		final static Color ERROR_COLOR = Color.PINK;
		private JTextField txtSearch;

		private JButton btnSearch;

		public HeaderMessagePanel() {
			setPanelUp();
			initComponents();
		}

		private void setPanelUp() {
			setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		}

		private void initComponents() {
			add(Box.createRigidArea(new Dimension(5, 0)));
			add(Box.createHorizontalGlue());
			avatar = new JLabel();
			add(avatar);
			add(Box.createRigidArea(new Dimension(5, 0)));
			lblNameChat = new JLabel();
			add(lblNameChat);
			add(Box.createRigidArea(new Dimension(5, 0)));
			txtSearch = new JTextField();

			txtSearch.setBorder(GraphicsUtils.Borders.createEmptyBorder());
			txtSearch.setActionCommand(SEARCH);
			txtSearch.addActionListener(MessagesPanel.this);

			entryBg = txtSearch.getBackground();
//			txtSearch.getDocument().addDocumentListener(MessagesPanel.this);

			InputMap im = txtSearch.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			ActionMap am = txtSearch.getActionMap();
			im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
			am.put(CANCEL_ACTION, new CancelAction());
//			JPanel p = new JPanel(new BorderLayout());
			add(txtSearch);
//			p.add(txtSearch, BorderLayout.CENTER);
//			add(rightJustify(p));

			add(Box.createRigidArea(new Dimension(5, 0)));
			btnSearch = new JFlatButton(SEARCH);
			btnSearch.addActionListener(MessagesPanel.this);
			add(btnSearch);
		}

		public void setChat(Chat chat) {
			avatar.setIcon(GraphicsUtils.Images.resizeIcon(chat.getOwner().getProfileImage(), 40, 40));
			String chatName = chat.getMembers().stream().map(Contact::getName).collect(joining(", "));
			if (chatName.length() > 20) {
				// The last comma found was replaced with "..."
				chatName = chatName.substring(0, 17) + " ...";
			}
			lblNameChat.setText(chatName);
			txtSearch.setText("");
			txtSearch.setBackground(entryBg);
			foundedResult = null;
			findedCounter.set(0);
			forSearch = "";
		}

		public String getSearchText() {
			txtSearch.setBackground(entryBg);
			return txtSearch.getText().trim();
		}

		public void setSearchError() {
			txtSearch.setBackground(ERROR_COLOR);
		}

		class CancelAction extends AbstractAction {
			public void actionPerformed(ActionEvent ev) {
				txtSearch.setText("");
				txtSearch.setBackground(entryBg);
				foundedResult = null;
				findedCounter.set(0);
				forSearch = "";
			}
		}
	}

	private class InputMessagePanel extends JPanel {

		private JTextField txtInput;

		private JButton btnSend;

		public InputMessagePanel() {
			setPanelUp();
			initComponents();
		}

		private void setPanelUp() {
			setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
			setLayout(new BorderLayout(5, 5));
		}

		private void initComponents() {
//			add(Box.createRigidArea(new Dimension(5, 0)));
			txtInput = new JTextField();
			txtInput.setActionCommand(SEND);
			txtInput.addActionListener(MessagesPanel.this);
			add(txtInput, BorderLayout.CENTER);
//			add(Box.createRigidArea(new Dimension(5, 0)));
			btnSend = new JFlatButton(SEND);
			btnSend.addActionListener(MessagesPanel.this);
			add(btnSend, BorderLayout.LINE_END);
		}

		public String getMessageText() {
			String mess = txtInput.getText().trim();
			txtInput.setText("");
			return mess;
		}
	}
}