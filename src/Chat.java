import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Chat class represents a chat in the program
 * CHat implements Serializable interface
 */
public class Chat implements Serializable {
	// Define fileds
	private String name;
	private List<Contact> members;
	private LinkedList<Message> messages;

	private Contact owner;

	public Chat(List<Contact> members, LinkedList<Message> messages) {
		this.members = members;
		this.messages = messages;
	}

	/**
	 * Default constructor for Chat class
	 *
	 * @param newName represent name
	 * @param newMembers represents members list
	 */
	public Chat(String newName, List<Contact> newMembers) {
		this.members = newMembers;
		this.messages = new LinkedList<>();
		this.name = newName;
	}

	/**
	 * Setters for members list
	 *
	 * @param newMembers represents new members list
	 */
	public void setMembers(List<Contact> newMembers) {
		this.members = newMembers;
	}

	/**
	 * Setters for messages list
	 *
	 * @param newMessages represents new messages list
	 */
	public void setMessages(LinkedList<Message> newMessages) {
		this.messages = newMessages;
	}

	/**
	 * Getter for messages list
	 *
	 * @return messages list
	 */
	public LinkedList<Message> getMessages() {
		return this.messages;
	}

	/**
	 * Getter for members list
	 *
	 * @return members list
	 */
	public List<Contact> getMembers() {
		return this.members;
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
	 * addMember function to add new member into a chat
	 *
	 * @param newMember represents new member to add
	 */
	public void addMember(Contact newMember) {
		if (!this.members.contains(newMember)) {
			this.members.add(newMember);
		}
	}

	/**
	 * removeMember function to remove a member from a chat
	 *
	 * @param memberToRemove represents member to remove from a chat
	 */
	public void removeMember(Contact memberToRemove) {
		this.members.remove(memberToRemove);
	}

	/**
	 * addMessage function to add new message to a chat
	 *
	 * @param newMessage represents new message to add
	 */
	public void addMessage(Message newMessage) {
		if (!this.messages.contains(newMessage)) {
			this.messages.add(newMessage);
		}
	}

	/**
	 * findMessages function finds based on message content
	 *
	 * @param inputToFind represents content of a message to find
	 *
	 * @return found messages
	 */
	public List<Message> findMessages(String inputToFind) {
		// Create a list to store found messages
		List<Message> foundMessages = new LinkedList<>();
		// If messages list is empty,return empty list
		if (this.messages.isEmpty()) {
			return foundMessages;
		}
		// Iterate through each message in the list
		for (int i = 0; i < this.messages.size(); i++) {
			String content = this.messages.get(i).getContent();
			// If content of a message contains content to find add this message to a found messages list
			if (content.contains(inputToFind)) {
				foundMessages.add(this.messages.get(i));
			}
		}
		return foundMessages;
	}

	/**
	 * Function is used to set status flags of previously unread messages to true
	 * Used when user enters a chat
	 * All new messages will be automatically set to read
	 */
	public void readMessages() {
		// Iterate through last messages in the chat
		for (int i = this.messages.size() - 1; i >= 0; i--) {
			// Set all messages which are not read yet to read
			if (!this.messages.get(i).isStatus()) {
				this.messages.get(i).setStatus(true);
			} else {
				// When already read message occurs, terminate
				return;
			}
		}
	}

	public Contact getOwner() {
		return owner;
	}

	public void setOwner(final Contact owner) {
		this.owner = owner;
	}

	public LocalDateTime getLastActivities() {
		return messages.isEmpty() ? LocalDateTime.now() : messages.getLast().getDateAdded();
	}
}
