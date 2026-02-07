import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contact class represents contact in a chat
 * It implements Serializable interface
 */
public class Contact implements Serializable {
	// Define Contact class fields
	private String name;
	private int ID;
	private String phoneNumber;
	private final LocalDateTime dateAdded;
	private LocalDateTime date;
	private ImageIcon profileImage;

	private LocalDateTime lastActivities;

	// Container for most recent messages sent, has most recent message at the end of the list and the least recent at the start
	private List<Message> lastThreeMessages;
	private List<Chat> chats;

	/**
	 * for Serializable
	 */
	public Contact() {
		dateAdded = LocalDateTime.now();
	}

	/**
	 * Default constructor for Contact object
	 * Initializes the contact with the current date as dateAdded and defaultImage as profileImage
	 * Default image path is src/defaultImage.png
	 *
	 * @param name
	 * @param phoneNumber
	 */
	public Contact(String name, String phoneNumber) {
		this(name, 0, phoneNumber, LocalDateTime.now(), new ImageIcon("src/defaultImage.png"));
	}

	public Contact(String name, int ID, String phoneNumber, LocalDateTime date, ImageIcon profileImage) {
		this.name = name;
		this.ID = ID;
		this.phoneNumber = phoneNumber;
		this.date = date;
		// Set dateAdded to current time
		this.dateAdded = LocalDateTime.now();
		// Set profile image to default image
		if (Objects.isNull(profileImage)) {
			profileImage = new ImageIcon("src/defaultImage.png");
		}
		this.profileImage = profileImage;
		lastActivities = LocalDateTime.now();

		this.lastThreeMessages = new ArrayList<>();
		this.chats = new ArrayList<>();
	}

	/**
	 * createChat function creates new chat object and adds passed members along with the contact creating the chat
	 *
	 * @param chatName represents chat name
	 * @param members represent members chosen by this contact (does NOT include this contact)
	 *
	 * @return new chat
	 */
	public Chat createChat(String chatName, List<Contact> members) {
		// Create a chat object with passed name and members
		Chat newChat = new Chat(chatName, members);

		// Add this contact to the new chat
		newChat.addMember(this);
		// Add new chat to each members' chats list
		members.forEach(contact -> contact.addChat(newChat));

//		Iterator<Contact> iterator = newChat.getMembers().iterator();
//		while(iterator.hasNext()){
//			Contact current = iterator.next();
//			current.addChat(newChat);
//		}

		newChat.setOwner(this);
		addChatInList(newChat);
		// Return created chat
		return newChat;
	}

	/**
	 * Getter for chats list
	 *
	 * @return chats list
	 */
	public List<Chat> getChats() {
		return this.chats;
	}

	/**
	 * addChat method adds new chat to chats list
	 *
	 * @param newChat represents chat to add
	 */
	public void addChat(Chat newChat) {
		newChat.setOwner(this);
		addChatInList(newChat);
	}

	private void addChatInList(Chat chat) {
		if (!this.chats.contains(chat)) {
			this.chats.add(chat);
		}
	}

	/**
	 * Getter for last three messages list
	 *
	 * @return last three messages list
	 */
	public List<Message> getLastThreeMessages() {
		List<Message> messageList = new ArrayList<>();
		List<Chat> lc = this.chats.stream()
			.sorted(Comparator.comparing(Chat::getLastActivities))
			.limit(3)
			.collect(Collectors.toList());
		for (Chat c : lc) {
			if(Objects.nonNull(c.getMessages()) && !c.getMessages().isEmpty()){
				messageList.add(c.getMessages().getLast());
			}
		}
		return messageList;
	}

	/**
	 * Getter for date added
	 *
	 * @return date added
	 */
	public LocalDateTime getDateAdded() {
		return this.dateAdded;
	}

	/**
	 * Getter for name
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for name
	 *
	 * @param name represents new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for phone number
	 *
	 * @return phone number
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * Setter for phone number
	 *
	 * @param phoneNumber represents phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Getter for profile image
	 *
	 * @return profile image
	 */
	public ImageIcon getProfileImage() {
		return this.profileImage;
	}

	/**
	 * Setter for profile image
	 *
	 * @param profileImage represents new profile image
	 */
	public void setProfileImage(ImageIcon profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * sendMessage function sends message to passed chat with passed content as parameters
	 *
	 * @param content represents content of a message to send
	 * @param destination represents a chat to send the message to
	 */
	public Message sendMessage(String content, Chat destination) {
		// Initialize new message
		Message newMessage = new Message(destination, this);
		// Set content
		newMessage.setContent(content);
		// Set read flag to false
		newMessage.setStatus(false);
		// Check if contact is a member of destination chat
		if (chats.contains(destination)) {
			// If destination chat is valid add a message
			destination.addMessage(newMessage);
		} else {
			//If not a valid chat, terminate
			return null;
		}

		// Manage last three message list
		if (this.lastThreeMessages.size() < 3) { // If list has less than 3 messages
			this.lastThreeMessages.add(newMessage); //Adds new element to the end of the list
		} else { // If list has three messages
			this.lastThreeMessages.remove(0); //Removes the first element from the list
			this.lastThreeMessages.add(newMessage); //Adds new element to the end of the list
		}
		return newMessage;
	}

	/**
	 * getChatByName returns a chat with passed name if it exists
	 *
	 * @param chatName represents name of the chat to find
	 *
	 * @return chat if it exists, null otherwise
	 */
	public Chat getChatByName(String chatName) {

		for (int i = 0; i < this.chats.size(); i++) {
			if (this.chats.get(i).getName().equals(chatName)) {
				return this.chats.get(i);
			}
		}
		return null;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public LocalDateTime getLastActivities() {
		return lastActivities;
	}

	public void setLastActivities(final LocalDateTime lastActivities) {
		this.lastActivities = lastActivities;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Contact contact)) {
			return false;
		}

		if (getID() != contact.getID()) {
			return false;
		}
		if (!Objects.equals(getPhoneNumber(), contact.getPhoneNumber())) {
			return false;
		}
		if (getName() != null ? !getName().equals(contact.getName()) : contact.getName() != null) {
			return false;
		}
		if (getDateAdded() != null ? !getDateAdded().equals(contact.getDateAdded()) : contact.getDateAdded() != null) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = getName() != null ? getName().hashCode() : 0;
		result = 31 * result + getID();
		result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
		result = 31 * result + (getDateAdded() != null ? getDateAdded().hashCode() : 0);
		return result;
	}
}
