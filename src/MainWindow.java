import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * @Author _se.ho
 * @create 2024-03-14
 */
public class MainWindow extends JFrame
	implements ActionListener, PropertyChangeListener {

	private static final String SORTING_ALPHABETICAL = "Alphabetical";
	private static final String SORTING_OLDEST = "Oldest";
	private int panelWidth = 1000;
	private int headerHeight = 100;
	private int footerHeight = 40;
	private int mainHeight;
	private int totalHeight = 1000;

	private JPanel mainView;

	private HeaderMainPanel headerMainPanel;
	private BottomMenuPanel bottomMenuPanel;
	private ContactsPanel contactsPanel;

	private JPanel contactsSidePanel;
	private ContactPanel contactPanel;
	private JPanel decoratedContactPanel;

	private ChatsPanel chatsPanel;
	private UserInfoPanel userInfoPanel;

	// Temporary, for assembly app
	private MessageCard messageCard;
	private MessagesPanel messagesPanel;
	private List<Contact> members;
	private User currentUser;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

	private final ApiBackend service;

	private final Action saveAction = new SaveAction("Save data");
	private final Action loadAction = new LoadAction("Load data");
	private final Action exitAction = new ExitAction("Exit");

	class SaveAction extends AbstractAction {
		public SaveAction(final String nameAction) {
			super(nameAction);
		}

		public void actionPerformed(ActionEvent ev) {
			File file = Utils.getSaveFileName();
			if (Objects.nonNull(file)) {
				currentUser.saveData(file);
			}
		}
	}

	class LoadAction extends AbstractAction {
		public LoadAction(final String nameAction) {
			super(nameAction);
		}

		public void actionPerformed(ActionEvent ev) {
			String fileName = Utils.getOpenFileName();
			if (Objects.nonNull(fileName)) {
				currentUser = User.loadData(fileName);
				MainWindow.this.service.loadData(currentUser);
				hidePanels();
				doChatBottomMenu();
			}
		}
	}

	class ExitAction extends AbstractAction {
		public ExitAction(final String nameAction) {
			super(nameAction);
		}

		public void actionPerformed(ActionEvent ev) {
			System.exit(NORMAL);
		}
	}

	public MainWindow(final ApiBackend service, final String caption) throws HeadlessException {
		this.setTitle(caption);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.service = service;
		members = this.service.getContacts();
		currentUser = this.service.getUser(new Random().nextLong(1, 14));
		this.mainHeight = this.totalHeight - this.headerHeight - this.footerHeight;

		setSize(this.panelWidth, this.headerHeight + this.mainHeight + this.footerHeight);
		getContentPane().setLayout(new BorderLayout());
		headerMainPanel = prepareHeaderMainPanel();
		headerMainPanel.setUser(currentUser);
		getContentPane().add(headerMainPanel, BorderLayout.PAGE_START);
		contactsSidePanel = getContactSidePanel();
		getContentPane().add(contactsSidePanel, BorderLayout.WEST);
		mainView = new JPanel(new BorderLayout());
		getContentPane().add(mainView, BorderLayout.CENTER);

//		createBottomMenuPanel();

//		this.service.setRefresherContact(this::refreshContacts);
		createMenu();
		this.revalidate();
		this.repaint();
//		doShowContacts();
		doChatBottomMenu();
//		this.pack();
	}

	private JMenu createMenu() {
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

//Create the menu bar.
		menuBar = new JMenuBar();

//Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

//a group of JMenuItems
		menuItem = new JMenuItem("Load data");
		menuItem.setAction(loadAction);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save data");
		menuItem.setAction(saveAction);
		menu.add(menuItem);

		menu.addSeparator();
		menuItem = new JMenuItem("Exit");
		menuItem.setAction(exitAction);
		menu.add(menuItem);

		this.setJMenuBar(menuBar);
		return menu;
	}

	private JPanel getContactSidePanel() {
		contactsSidePanel = new JPanel(new BorderLayout());

		createMenuPanel(contactsSidePanel);

		contactsPanel = new ContactsPanel();
		contactsPanel.addPropertyChangeListener(this);
		contactsPanel.setBorder(BorderFactory.createCompoundBorder());
		JScrollPane
			sp =
			new JScrollPane(
				contactsPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
		sp.setPreferredSize(new Dimension(272, 250));
		sp.setViewportBorder(
			BorderFactory.createCompoundBorder());
		contactsSidePanel.add(sp, BorderLayout.CENTER);
		JButton b = new JFlatButton("Add Contact");
		b.setActionCommand("Add");
		b.addActionListener(this);
		contactsSidePanel.add(b, BorderLayout.PAGE_END);
		return contactsSidePanel;
	}

	private ChatsPanel getChatsPanel() {
//		private MessagesPanel getMessagesPanel() {
		List<Chat> list = this.service.getChats(currentUser);
		chatsPanel = new ChatsPanel();
//		https://stackoverflow.com/a/9346946
		chatsPanel.addPropertyChangeListener(this);
		registerActions(chatsPanel.getBtnNewChat(), (e) -> doMakeNewChat());
		list.forEach(c -> {
			c.setOwner(currentUser);
			chatsPanel.addChat(c);
		});
		mainView.add(chatsPanel, BorderLayout.WEST);
		return chatsPanel;
	}

	private void createMenuPanel(final JPanel pn) {
		// add menu panel with buttons for test GUI features
		JPanel menu = new JPanel();
		menu.setBorder(BorderFactory.createTitledBorder("Sorting:"));
		menu.setLayout(new BoxLayout(menu, BoxLayout.X_AXIS));
		JButton b1 = new JFlatButton(SORTING_ALPHABETICAL);
		b1.setActionCommand(SORTING_ALPHABETICAL);
		b1.addActionListener(this);
		menu.add(b1);
		b1 = new JFlatButton(SORTING_OLDEST);
		b1.setActionCommand(SORTING_OLDEST);
		b1.addActionListener(this);
		menu.add(b1);
//		b1 = new JFlatButton("View/Edit MessageCard");
//		b1.setActionCommand("ViewEditMessageCard");
//		b1.addActionListener(this);
//		menu.add(b1);
//		b1 = new JFlatButton("View MessagesPanel");
//		b1.setActionCommand("ViewMessagePanel");
//		b1.addActionListener(this);
//		menu.add(b1);
		pn.add(menu, BorderLayout.BEFORE_FIRST_LINE);
	}

	private void createBottomMenuPanel() {
		// add menu panel with buttons for test GUI features
		bottomMenuPanel = new BottomMenuPanel();

		registerActions(bottomMenuPanel.getBtnEdit(), e -> doEditBottomMenu());
		registerActions(bottomMenuPanel.getBtnContacts(), e -> doContactsBottomMenu());
		registerActions(bottomMenuPanel.getBtnChat(), e -> doChatBottomMenu());

		getContentPane().add(bottomMenuPanel, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
//			if (indexMember >= members.size()) {
//				indexMember = 0;
//			}
//			contactsPanel.addContact(members.get(indexMember++));
//			CreateNewContactDlg dlg = new CreateNewContactDlg(this);
			CreateNewContactDialog dialog = new CreateNewContactDialog(this);
			dialog.pack();
			// https://stackoverflow.com/a/2442610
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(
				dim.width / 2 - dialog.getSize().width / 2,
				dim.height / 2 - dialog.getSize().height / 2
			);
			dialog.setVisible(true);
			Contact contact = dialog.getContact();
			if (Objects.nonNull(contact)) {
				this.service.addContact(contact);
				currentUser.addContact(contact);
				contactsPanel.addContact(contact);
			}

			return;
		}
		if (e.getActionCommand().equals(SORTING_ALPHABETICAL)) {
			if (Objects.isNull(contactsPanel)) {
				getContactSidePanel();
			} else {
				contactsPanel.setVisible(true);
			}
			contactsPanel.clearContacts();
			contactsPanel.addContacts(this.service.getAlphabeticalContacts());

			this.revalidate();
			this.repaint();
			return;
		}
		if (e.getActionCommand().equals(SORTING_OLDEST)) {
			if (Objects.isNull(contactsPanel)) {
				getContactSidePanel();
			} else {
				contactsPanel.setVisible(true);
			}
			contactsPanel.clearContacts();
			contactsPanel.addContacts(this.service.getContacts());

			this.revalidate();
			this.repaint();
			return;
		}
		hidePanels();
		if (e.getActionCommand().equals("ViewEditMessageCard")) {
			if (Objects.isNull(messageCard)) {
				messageCard = getMessageCard();
			} else {
				messageCard.setVisible(true);
			}
//			userInfoPanel.setUser(this.service.getUser((long) contactsPanel.getActiveContact().getID()));

			this.revalidate();
			this.repaint();
			return;
		}
		if (e.getActionCommand().equals("ViewMessagePanel")) {
			if (Objects.isNull(messagesPanel)) {
				messagesPanel = getMessagesPanel();
			} else {
				messagesPanel.setVisible(true);
			}
//			userInfoPanel.setUser(this.service.getUser((long) contactsPanel.getActiveContact().getID()));

			this.revalidate();
			this.repaint();
			return;
		}
	}

	private MessageCard getMessageCard() {
		List<Message> list = this.service.getMessages(null);
		messageCard = new MessageCard();
		messageCard.setMessage(list.get(new Random(100).nextInt(0, 5)));
		mainView.add(messageCard, BorderLayout.CENTER);
		return messageCard;
	}

	private MessagesPanel getMessagesPanel() {
		List<Message> list = this.service.getMessages(null);
		messagesPanel = new MessagesPanel();
		registerActions(messagesPanel.getSendBtn(), (e) -> doSendNewMessage());
		registerActions(messagesPanel.getTxt(), (e) -> doSendNewMessage());
		messagesPanel.setChat(this.service.getChats(currentUser).get(0));
		mainView.add(messagesPanel, BorderLayout.CENTER);
		return messagesPanel;
	}

	private HeaderMainPanel prepareHeaderMainPanel() {
		HeaderMainPanel hp = new HeaderMainPanel();
		registerActions(hp.getBtnChat(), (e) -> doChatBottomMenu());
		registerActions(hp.getBtnContacts(), (e) -> doShowContacts());
		registerActions(hp.getBtnEdit(), (e) -> doShowUserInfo());
		return hp;
	}

	/**
	 * метод делает невидимыми все возможные панели, которые могут быть показаны
	 */
	private void hidePanels() {
//		if (Objects.nonNull(contactPanel)) {
//			contactPanel.setVisible(false);
//		}
		hideUserInfoPanel();
		if (Objects.nonNull(messageCard)) {
			messageCard.setVisible(false);
		}
		hideMessagesPanel();
		hideContactDecorSidePanel();
		hideChatsPanel();
	}

	private void hideChatsPanel() {
		if (Objects.nonNull(chatsPanel)) {
			chatsPanel.setVisible(false);
		}
	}

	private void hideContactDecorSidePanel() {
		if (Objects.nonNull(decoratedContactPanel)) {
			decoratedContactPanel.setVisible(false);
		}
		if (Objects.nonNull(contactsSidePanel)) {
			contactsSidePanel.setVisible(false);
		}
	}

	private void hideMessagesPanel() {
		if (Objects.nonNull(messagesPanel)) {
			messagesPanel.setVisible(false);
		}
	}

	private void hideUserInfoPanel() {
		if (Objects.nonNull(userInfoPanel)) {
			userInfoPanel.setVisible(false);
		}
	}

	private ContactPanel getContactPanel() {
		decoratedContactPanel = new JPanel(new BorderLayout());
		HeaderBackPanel backPanel = new HeaderBackPanel();
		registerActions(backPanel.getBtnBack(), e -> doBackFromContactPanel());
		decoratedContactPanel.add(backPanel, BorderLayout.PAGE_START);
		contactPanel = new ContactPanel();
		decoratedContactPanel.add(contactPanel, BorderLayout.CENTER);
		FooterSavePanel footerSavePanel = new FooterSavePanel();
		registerActions(footerSavePanel.getBtnSave(), e -> doSaveFromContactPanel());
		decoratedContactPanel.add(footerSavePanel, BorderLayout.PAGE_END);
		mainView.add(decoratedContactPanel, BorderLayout.CENTER);
		return contactPanel;
	}

	private UserInfoPanel getUserPanel() {
		userInfoPanel = new UserInfoPanel();
		registerActions(userInfoPanel.getBtnBack(), e -> doBackFromContactsPanel());
		registerActions(userInfoPanel.getBtnSave(), e -> doMakeNewChat());
		mainView.add(userInfoPanel, BorderLayout.CENTER);
		return userInfoPanel;
	}

	private void doBackFromContactsPanel() {
		userInfoPanel.setVisible(false);
	}

	private void clearMainView() {
		Component[] arr = mainView.getComponents();
		for (Component c : arr) {
			mainView.remove(c);
		}
		contactPanel = null;
	}

	private void showActiveChat(Chat chat) {
		if (Objects.isNull(messagesPanel)) {
			messagesPanel = getMessagesPanel();
		} else {
			messagesPanel.setVisible(true);
		}
		messagesPanel.setChat(chat);

		this.revalidate();
		this.repaint();
	}

	void refreshContacts(List<Contact> list) {
		members.addAll(list);
	}

	private void registerActions(JButton btn, ActionListener actionListener) {
		btn.addActionListener(actionListener);
	}

	private void registerActions(JTextField btn, ActionListener actionListener) {
		btn.addActionListener(actionListener);
	}

	private void doEditBottomMenu() {

	}

	private void doContactsBottomMenu() {

	}

	private void doChatBottomMenu() {
		this.hidePanels();
		if (Objects.isNull(chatsPanel)) {
			chatsPanel = getChatsPanel();
		} else {
			chatsPanel.setVisible(true);
		}

		this.revalidate();
		this.repaint();
	}

	private void doBackFromContactPanel() {
//		contactsPanel.setVisible(false);
		decoratedContactPanel.setVisible(false);
	}

	private void doSaveFromContactPanel() {
		Contact contact = contactPanel.getContact();
		User user = headerMainPanel.getUser();
		user.setName(contact.getName());
		user.setID(contact.getID());
		user.setPhoneNumber(contact.getPhoneNumber());
		user.setProfileImage(contact.getProfileImage());
		headerMainPanel.setUser(user);

		doBackFromContactPanel();
	}

	private void doMakeNewChat() {
		List<Contact> list;
		if (Objects.nonNull(userInfoPanel) && userInfoPanel.isVisible()) {
			// if seening contact, then may make new chat with him
			list = new ArrayList<>();
			list.add(userInfoPanel.getContact());
		} else {
			// else need show contacts list and choose contacs for new chat
			ChooseContactsDialog dialog = new ChooseContactsDialog(this);
			dialog.setContacts(this.service.getContacts());
			dialog.pack();
			// https://stackoverflow.com/a/2442610
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(
				dim.width / 2 - dialog.getSize().width / 2,
				dim.height / 2 - dialog.getSize().height / 2
			);
			dialog.setVisible(true);
			list = dialog.getSelectedContacts();
		}
		if (Objects.nonNull(list) && !list.isEmpty()) {
			list.add(currentUser);
			makeNewChat(list);
		}
	}

	private void makeNewChat(final List<Contact> list) {
		Chat chat = currentUser.createChat("New Chat", list);
		this.service.addChat(currentUser, chat);
		doChatBottomMenu();
		chatsPanel.addChat(chat);
		chatsPanel.setActiveChat(chat);
		showActiveChat(chat);
	}

	private void doRemoveChat(Chat chat) {
		if (this.service.removeChat(currentUser, chat)) {
			chatsPanel.removeChat(chat);
			currentUser.getChats().remove(chat);
			hideMessagesPanel();
		}
	}

	private void doShowContacts() {
		this.hidePanels();
		if (Objects.isNull(contactsSidePanel)) {
			contactsSidePanel = getContactSidePanel();
		} else {
			contactsSidePanel.setVisible(true);
		}
		if (contactsPanel.getContacts().size() != currentUser.getContactList().size()) {
			contactsPanel.addContacts(currentUser.getContactList());
//			currentUser.getContactList().forEach(contactsPanel::addContact);
		}
		this.revalidate();
		this.repaint();
	}

	private void doShowUserInfo() {
		this.hidePanels();
		if (Objects.isNull(contactPanel)) {
			contactPanel = getContactPanel();
		} else {
			decoratedContactPanel.setVisible(true);
		}
		contactPanel.setContact(headerMainPanel.getUser());

		this.revalidate();
		this.repaint();
	}

	private void doSendNewMessage() {
		Message mess = messagesPanel.getMessageForSend();
		if (Objects.nonNull(mess)) {
			messagesPanel.addMessageToChat(mess);
			this.service.sendMessageToChat(mess.getChatOwner(), mess);
			updateChatsPanel();
			Message mess1 = replyRandomParticipant(mess.getChatOwner(), mess.getAuthor());
			messagesPanel.addMessageToChat(mess1);
			this.service.sendMessageToChat(mess1.getChatOwner(), mess1);
		}
	}

	private Message replyRandomParticipant(final Chat chatOwner, final Contact author) {
		final Optional<Contact> any = chatOwner.getMembers().stream()
			.filter(contact -> !contact.equals(author))
			.findAny();
		if (any.isPresent()) {
			Contact newAutor = any.get();
			return newAutor.sendMessage("Random answer.\n" + LocalDateTime.now().format(formatter), chatOwner);
		}
		return null;
	}

	private void updateChatsPanel() {
		chatsPanel.updateView();
	}

	/**
	 * This method gets called when a bound property is changed.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source
	 * 	and the property that has changed.
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
//		https://stackoverflow.com/a/9346946
		if (evt.getPropertyName().equals(ChatsPanel.CHATPANEL_ACTIVECHAT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("MainWindowsTwo:: new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof ChatsPanel chatsPnl) {
				showActiveChat(chatsPnl.getActiveChat());
			}
		}
		if (evt.getPropertyName().equals(ChatsPanel.CHATPANEL_REMOVECHAT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("MainWindowsTwo:: new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof Chat chat) {
				doRemoveChat(chat);
			}
		}
		if (evt.getPropertyName().equals(ContactsPanel.CONTACTSPANEL_ACTIVECONTACT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("MainWindowsTwo:: new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof ContactsPanel contsPnl) {
				showContactInfo(contsPnl.getActiveContact());
			}
		}
		if (evt.getPropertyName().equals(ContactsPanel.CONTACTSPANEL_DELETECONTACT)) {
			String innerValue = evt.getNewValue().toString();
			System.out.println("MainWindowsTwo:: new value from inside of OuterView: "
				+ innerValue);
			if (evt.getNewValue() instanceof Contact contact) {
				doRemoveContact(contact);
			}
		}
	}

	private void doRemoveContact(final Contact contact) {
		Map<Chat, List<Message>> chatMessages = new HashMap<>();
		Map<Chat, Contact> chatAuthor = new HashMap<>();
		List<Message> list = null;
		// collect Messages for delete
		for (Chat chat : contact.getChats()) {
			for (Message mess : chat.getMessages()) {
				if (mess.getAuthor().equals(contact)) {
					if (Objects.isNull(list)) {
						list = new ArrayList<>();
					}
					list.add(mess);
//					chat.getMessages().remove(mess);
				}
			}
			if (Objects.nonNull(list)) {
				chatMessages.put(chat, list);
				list = null;
			}
			for (Contact cont : chat.getMembers()) {
				if (cont.equals(contact)) {
					chatAuthor.put(chat, contact);
				}
			}
		}
		for (Map.Entry<Chat, List<Message>> e : chatMessages.entrySet()) {
			for (Message m : e.getValue()) {
				e.getKey().getMessages().remove(m);
			}
		}
		for (Map.Entry<Chat, Contact> e : chatAuthor.entrySet()) {
			e.getKey().getMembers().remove(e.getValue());
			if (e.getKey().getMembers().size() < 2) {
				contact.getChats().remove(e.getKey());
				chatsPanel.removeChat(e.getKey());
			}
		}

		if (Objects.nonNull(messagesPanel)) {
			messagesPanel.updateView();
		}
		chatsPanel.updateView();
		this.service.removeContact(contact);
		currentUser.getContactList().remove(contact);
		hideUserInfoPanel();
		contactsPanel.removeContact(contact);
	}

	private void showContactInfo(final Contact activeContact) {
		if (Objects.isNull(userInfoPanel)) {
			userInfoPanel = getUserPanel();
			userInfoPanel.getBtnSave().setText("New Chat");
		} else {
			userInfoPanel.setVisible(true);
		}
//			this.service.getUser(contactsPanel.getActiveContact().getID())
		userInfoPanel.setContact(activeContact);
//		userInfoPanel.setUser(this.service.getUser((long) activeContact.getID()));

		this.revalidate();
		this.repaint();
	}
}
