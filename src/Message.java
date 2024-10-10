import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Message class represents message in a chat program
 * It implements Serializable interface
 */
public class Message implements Serializable {
	// Define class fields
	private String content;
	private Map<Contact, Integer> reaction; //1 is used to represent like; -1 is used for dislike
	private Contact author;
	//Status flag is used to determine whether message was read or not; false - not read, true - read
	private boolean status;
	private final LocalDateTime dateAdded;

	private final Set<Emoji> emojiSet = new HashSet<>();

	private final Chat chatOwner;

	public Message(Chat chat, String content, Contact author, boolean status, LocalDateTime dateAdded) {
		this.chatOwner = chat;
		this.content = content;
		this.reaction = new HashMap<>();
		this.author = author;
		this.status = status;
		this.dateAdded = dateAdded;
	}

	/**
	 * Default constructor for Message class
	 *
	 * @param author represents author of a message
	 */
	public Message(Chat chat, Contact author) {
		this(chat, null, author, false, LocalDateTime.now());
	}

	/**
	 * Getter for reactions
	 *
	 * @return reactions
	 */
	public Map<Contact, Integer> getReaction() {
		return this.reaction;
	}

	/**
	 * setReaction function sets the reaction of a given author for the message
	 *
	 * @param author the contact representing the author of the reaction
	 * @param reactionID an integer representing reaction ID: 1 represents like, -1 represents dislike
	 */
	public void setReaction(Contact author, Integer reactionID) {
		if (author != null && reactionID != null) {
			this.reaction.put(author, reactionID);
		} else {
			System.out.println("Parameters cannot be null");
		}
	}

	/**
	 * countReaction function counts reaction with passed ID in reactions map
	 *
	 * @param reactionID represents reaction ID
	 *
	 * @return number of reactions of passed type
	 */
	public int countReaction(Integer reactionID) {
		// Initialize count at zero
		int count = 0;
		// Iterate through each element in a map
		for (HashMap.Entry<Contact, Integer> entry : reaction.entrySet()) {
			// If current ID equals passed ID, increment count
			if (entry.getValue().equals(reactionID)) {
				count++;
			}
		}
		// Return number of reactions with passed ID
		return count;
	}

	/**
	 * Getter for content
	 *
	 * @return content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * Setter for content
	 *
	 * @param content represents content of a message
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Getter for author of a message
	 *
	 * @return author
	 */
	public Contact getAuthor() {
		return this.author;
	}

	/**
	 * Setter for author of a message
	 *
	 * @param author represents author of a message
	 */
	public void setAuthor(Contact author) {
		this.author = author;
	}

	/**
	 * Getter for status of a message
	 *
	 * @return status
	 */
	public boolean isStatus() {
		return this.status;
	}

	/**
	 * Setter for a status of a message
	 *
	 * @param status represents read/unread status of a message
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getDateAdded() {
		return dateAdded;
	}

	public Set<Emoji> getEmojies() {
		return emojiSet;
	}

	public void addEmoji(Emoji emoji) {
		emojiSet.add(emoji);
	}

	public void removeEmoji(Emoji emoji) {
		emojiSet.remove(emoji);
	}

	public Chat getChatOwner() {
		return chatOwner;
	}
}
