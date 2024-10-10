import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author _se.ho
 * @create 2024-03-29
 */
public class ChatsPanel extends JPanel implements MouseListener, PropertyChangeListener {

	public static final String CHATPANEL_ACTIVECHAT = "ChatPanel Active Chat";
	public static final String CHATPANEL_REMOVECHAT = "ChatPanel Remove Chat";

	private List<ChatCard> chatCards;
	private Chat activeChat;

	private JPanel chatsBoard;

	private JButton btnNewChat;

	public ChatsPanel() {
		super();
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
//		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BorderLayout(5, 5));
	}

	private void initComponents() {
		chatCards = new ArrayList<>();
		chatsBoard = new JPanel();
		chatsBoard.setLayout(new BoxLayout(chatsBoard, BoxLayout.PAGE_AXIS));
		chatsBoard.setBorder(BorderFactory.createCompoundBorder());
		JScrollPane
			sp =
			new JScrollPane(
				chatsBoard,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
		sp.setPreferredSize(new Dimension(272, 250));
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.setViewportBorder(
			BorderFactory.createCompoundBorder());
		add(sp, BorderLayout.CENTER);
		btnNewChat = new JFlatButton("Add Chat");
		btnNewChat.setActionCommand("Add");
//		btnNewChat.addActionListener(this);
		add(btnNewChat, BorderLayout.PAGE_END);
	}

	public void addChat(Chat chat) {
		for (var chatCard : chatCards){
			if(chatCard.getChat().equals(chat)){
				return;
			}
		}
		ChatCard c = new ChatCard(chat);
		c.addPropertyChangeListener(this);
//		c.addMouseListener(this);
//		c.getContact().setViewer(this);
		chatCards.add(c);
		reorganize();
	}

	public void removeChat(Chat chat) {
		chatCards.removeIf(o -> {
				if (o.getChat().equals(chat)) {
					o.removePropertyChangeListener(this);
				}
				return o.getChat().equals(chat);
			}
		);
		reorganize();
	}

	public void updateView(){
		chatCards.forEach(ChatCard::updateView);
		reorganize();
	}
	public void reorganize() {
		Component[] arr = chatsBoard.getComponents();
		for (Component c : arr) {
			chatsBoard.remove(c);
		}
		chatCards.sort(Comparator
			.comparing(
				ChatCard::getChat,
				Comparator.comparing(Chat::getLastActivities)
			)
			.reversed());
		chatCards.forEach(chatsBoard::add);

		this.revalidate();
		this.repaint();
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

	public void setActiveChat(final ChatCard chatCard) {
		setActiveChat(chatCard.getChat());
	}

	public void setActiveChat(final Chat chat) {
		this.activeChat = chat;
//		https://stackoverflow.com/a/9346946
		firePropertyChange(CHATPANEL_ACTIVECHAT, null, this);
	}

	/**
	 * This method gets called when a bound property is changed.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source
	 * 	and the property that has changed.
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ChatCard.CHATCARD_CHANGED)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("ChatPanel::" + ChatCard.CHATCARD_CHANGED + "::new value from inside of OuterView: "
				+ innerValue);
		}
		if (evt.getPropertyName().equals(ChatCard.CHATCARD_ACTIVECHAT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("ChatPanel::" + ChatCard.CHATCARD_ACTIVECHAT + "::new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof ChatCard chatCard) {
				setActiveChat(chatCard);
			}
		}
		if (evt.getPropertyName().equals(ChatCard.CHATCARD_REMOVECHAT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("ChatPanel::" + ChatCard.CHATCARD_REMOVECHAT + "::new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof ChatCard chatCard) {
				firePropertyChange(CHATPANEL_REMOVECHAT, null, chatCard.getChat());
			}
		}
	}

	public Chat getActiveChat() {
		return this.activeChat;
	}

	public JButton getBtnNewChat() {
		return btnNewChat;
	}
}
