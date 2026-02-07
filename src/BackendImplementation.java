import java.util.List;
import java.util.function.Consumer;

public class BackendImplementation implements ApiBackend {

	User user;

	public BackendImplementation(String userName, String userPhoneNumber) {
		this.user = new User(userName, userPhoneNumber);
	}

	@Override
	public List<Contact> getContacts() {
		return this.user.getContactList();
	}

	@Override
	public void addContact(final Contact contact) {
		user.addContact(contact);
	}

	/**
	 * @param contact
	 */
	@Override
	public void removeContact(final Contact contact) {
		for (Chat chat : contact.getChats()) {
			for (Message mess : chat.getMessages()) {
				if (mess.getAuthor().equals(contact)) {
					chat.getMessages().remove(mess);
				}
			}
			for (Contact cont : chat.getMembers()) {
				if (cont.equals(contact)) {
					chat.getMembers().remove(contact);
				}
			}
			if (chat.getMembers().size() < 2) {
				contact.getChats().remove(chat);
			}
		}
		user.getContactList().remove(contact);
	}

	/**
	 * @param chat
	 * @param message
	 */
	@Override
	public void sendMessageToChat(final Chat chat, final Message message) {
		chat.addMessage(message);
	}

	/**
	 * @param user
	 */
	@Override
	public void loadData(final User user) {
		this.user.getChats().addAll(user.getChats());
		this.user.addAll(user.getContactList());
	}

	@Override
	public void addContacts() {
		Contact contact1 = new Contact("Megan", "470275917");
		Contact contact2 = new Contact("Jonny", "402754381");
		Contact contact3 = new Contact("Liam Hall", "752748204");
		Contact contact4 = new Contact("Mary Smith", "28462847");
		Contact contact5 = new Contact("Jane Trigo", "34947281");
		Contact contact6 = new Contact("Jeremy Milne", "847284731");
		Contact contact7 = new Contact("Eric Hill", "492841321");
		Contact contact8 = new Contact("Alice Low", "374285932");
		Contact contact9 = new Contact("David Davis", "473748372");
		Contact contact10 = new Contact("Lee Miller", "347384111");
		user.addContact(contact1);
		user.addContact(contact2);
		user.addContact(contact3);
		user.addContact(contact4);
		user.addContact(contact5);
		user.addContact(contact6);
		user.addContact(contact7);
		user.addContact(contact8);
		user.addContact(contact9);
		user.addContact(contact10);
	}

	/**
	 * @param id
	 *
	 * @return
	 */
	@Override
	public User getUser(final Long id) {
		return getUser();
	}

	/**
	 * @param contact
	 *
	 * @return
	 */
	@Override
	public List<Message> getMessages(final Contact contact) {
		return null;
	}

	//    @Override
	public User getUser() {
		return this.user;
	}

	//	@Override
	public List<Message> getMessages(Chat chat) {
		return chat.getMessages();
	}

	@Override
	public List<Chat> getChats(Contact contact) {
		return contact.getChats();
	}

	/**
	 * @param consumer
	 */
	@Override
	public void setRefresherContact(final Consumer<List<Contact>> consumer) {

	}

	@Override
	public int getLikesNumber(Message message) {
		return message.countReaction(1);
	}

	@Override
	public int getDislikesNumber(Message message) {
		return message.countReaction(-1);
	}

	@Override
	public List<Contact> getAlphabeticalContacts() {
		return this.user.getContactAlphabetical();
	}

	/**
	 * @param contact
	 *
	 * @return
	 */
	@Override
	public List<Message> getLastMessages(final Contact contact) {
		return null;
	}

	/**
	 * @param contact
	 * @param chat
	 *
	 * @return
	 */
	@Override
	public boolean removeChat(final Contact contact, final Chat chat) {
		getChats(contact).remove(chat);
		return true;
	}

	/**
	 * @param contact
	 * @param chat
	 */
	@Override
	public void addChat(final Contact contact, final Chat chat) {
		List<Chat> list = getChats(contact);
		if (!list.contains(chat)) {
			list.add(chat);
		}
	}

	@Override
	public List<Contact> getMembers(Chat chat) {
		return chat.getMembers();
	}
}
