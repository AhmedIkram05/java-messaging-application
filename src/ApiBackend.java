import java.util.List;
import java.util.function.Consumer;

/**
 * @Author _se.ho
 * @create 2024-03-16
 */
public interface ApiBackend {
	List<Contact> getContacts();

	void addContacts();

	User getUser(Long id);

	List<Message> getMessages(Contact contact);

	List<Chat> getChats(Contact contact);

	void setRefresherContact(Consumer<List<Contact>> consumer);

	int getLikesNumber(Message message);
	int getDislikesNumber(Message message);

	List<Contact> getAlphabeticalContacts();
	List<Contact> getMembers(Chat chat);

	List<Message> getLastMessages(Contact contact);

	boolean removeChat(Contact contact, Chat chat);

	void addChat(Contact contact, Chat chat);

	void addContact(Contact contact);

	void removeContact(Contact contact);

	void sendMessageToChat(Chat chat, Message message);

	void loadData(User user);
}
