import javax.swing.*;
import java.time.LocalDateTime;

/**
 * @Author _se.ho
 * @create 2024-03-13
 **/

public class ChatButton extends JFlatButton {

	private ImageIcon chatIcon;
	private String chatName;
	private Message lastMessage;
	private boolean messageStatus;
	private LocalDateTime messageTime;

	private String buttonText;

	/**
	 * Creates a button with no set text or icon.
	 */
	public ChatButton() {
	}

	public ChatButton(Chat chat) {
		super();
//        setChat(chat);

		this.setIconTextGap(10);

		this.setHorizontalAlignment(SwingConstants.LEFT);

//        // Setting indents for the button
//        int top = 15;
//        int left = 20;
//        int bottom = 15;
//        int right = 20;
//        this.setBorder(new EmptyBorder(top, left, bottom, right));

//        //Setting the width to 80% for the button
//        gbc = new GridBagConstraints();
//        gbc.weightx = 0.8;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        this.setPreferredSize(new Dimension(gbc.gridx, gbc.gridy));
//        this.setPreferredSize(new Dimension(800, 70));

	}

	//    public void setChat(Chat chat){
	public void setChat(Message message) {
		this.chatIcon = GraphicsUtils.Images.resizeIcon(message.getAuthor().getProfileImage(), 40, 40);
		this.chatName = Utils.getShortString(this.createChatName(message.getChatOwner()), 20);
		this.lastMessage = message;
		this.messageStatus = this.lastMessage.isStatus();
		this.messageTime = this.lastMessage.getDateAdded();

		this.buttonText = "<html>" +
			"<body>" +
			"<font size='6'>" + this.chatName + "..." + "</font>" +
			"<font size='7'>|</font>" +
			"<font size='5'>" + Utils.getShortString(this.lastMessage.getContent(), 30) + "..." + "</fornt>" +
			"<font size='7'>|</font>" +
			"<font size='4'>" + getStatus() + "</font>" +
			"<font size='7'>|</font>" +
			"<font size='5'>" + getTime() + "</font>" +
			"</body>" +
			"</html>";

		this.setIcon(this.chatIcon);
		this.setText(this.buttonText);
		this.revalidate();
		this.repaint();
	}

	private String getStatus() {
		return getMessageStatus() ? "read" : "unread";
	}

	public String getTime() {
		int hour = messageTime.getHour();
		int minute = messageTime.getMinute();
		int second = messageTime.getSecond();
		return hour + ":" + minute + ":" + second;
	}

	private String createChatName(Chat chat) {
		StringBuilder chatName = new StringBuilder();
		for (Contact member : chat.getMembers()) {
			chatName.append(member.getName()).append(", ");
		}
		return chatName.delete(chatName.length() - 2, chatName.length()).toString();
	}

	public void clear() {
		this.setIcon(null);
		this.setText(null);
	}

	public ImageIcon getChatIcon() {
		return chatIcon;
	}

	public void setChatIcon(ImageIcon chatIcon) {
		this.chatIcon = chatIcon;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}

	public boolean getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(boolean messageStatus) {
		this.messageStatus = messageStatus;
	}

	public LocalDateTime getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(LocalDateTime messageTime) {
		this.messageTime = messageTime;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
}
