import javax.swing.*;
import java.awt.*;

/**
 * @Author _se.ho
 * @create 2024-03-16
 */
public class UserInfoPanel extends JPanel {

	private JLabel avatar;

	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPhone;

	private JButton btnEditAvatar;
	private JButton btnEditName;
	private JButton btnEditPhone;
	private HeaderBackPanel backPanel;
	private ContactPanel contactPanel;
	private LastChatViewPanel lastChatViewPanel;
	private	FooterSavePanel footerSavePanel;

	private Contact contact;

	/**
	 * Creates a new <code>JPanel</code> with a double buffer
	 * and a flow layout.
	 */
	public UserInfoPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BorderLayout());
	}

	private void initComponents() {
		backPanel = new HeaderBackPanel();
//		registerActions(backPanel.getBtnBack(), e -> doBackFromContactPanel());
		add(backPanel, BorderLayout.PAGE_START);
		JPanel pn = new JPanel();
		pn.setLayout(new BoxLayout(pn, BoxLayout.Y_AXIS));
		add(pn, BorderLayout.CENTER);
		contactPanel = new ContactPanel();
		pn.add(contactPanel);
		lastChatViewPanel = new LastChatViewPanel();
		pn.add(lastChatViewPanel);
		footerSavePanel = new FooterSavePanel();
//		registerActions(footerSavePanel.getBtnSave(), e -> doSaveFromContactPanel());
		add(footerSavePanel, BorderLayout.PAGE_END);
	}

	public JButton getBtnBack(){
		return backPanel.getBtnBack();
	}

	public JButton getBtnSave(){
		return footerSavePanel.getBtnSave();
	}

	public void setContact(Contact contact){
		this.contact = contact;
		contactPanel.setContact(contact);
		lastChatViewPanel.setChats(contact.getLastThreeMessages());
	}

	public Contact getContact(){
		Contact contact = contactPanel.getContact();
		contact.setName(contact.getName());
		contact.setID(contact.getID());
		contact.setPhoneNumber(contact.getPhoneNumber());
		contact.setProfileImage(contact.getProfileImage());
		return contact;
	}
}
