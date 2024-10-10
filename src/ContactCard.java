import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-14
 */
public class ContactCard extends JPanel
	implements MouseListener, ActionListener {

	public static final String CONTACTCARD_ACTIVECONTACT = "ContactCard Active Contact";
	public static final String CONTACTCARD_SELECTCONTACT = "ContactCard Select Contact";
	public static final String CONTACTCARD_DELETECONTACT = "ContactCard Delete Contact";
	final String ICON_REMOVE = "src/man-remove.256.png";
	private final Contact contact;
	private final JLabel avatar;
	private final JLabel nickName;
	private Integer countUnreadMessages;
	private final JLabel lblCountUnreadMessages;
	private final JLabel lblLastActivities;
	private final JButton btnRemove;

	// if this flag is true - change behavior for selected card.
	private boolean chooseView = false;

	// Flag showing other view when card is selected
	private boolean selected = false;

	private Border selectBorder;
	private Border entryBorder;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

	private Dimension preferredSize;

	public ContactCard(Contact contact) {
		super(new BorderLayout());
		this.entryBorder = BorderFactory.createEtchedBorder();
		this.selectBorder = new LineBorder(Color.PINK, 2);
		setBorder(this.entryBorder);
		this.contact = contact;
		if (Objects.nonNull(this.contact.getProfileImage())) {
			avatar = new JLabel(GraphicsUtils.Images.resizeIcon(this.contact.getProfileImage(), 40, 50));
		} else {
			avatar = new JLabel(GraphicsUtils.Images.resizeIcon(GraphicsUtils.Images.getProfileAvatar(), 40, 50));
		}
		add(avatar, BorderLayout.WEST);
		JPanel p = new JPanel(new BorderLayout());
		nickName = new JLabel(contact.getName());
		p.add(nickName, BorderLayout.CENTER);
		lblCountUnreadMessages = new JLabel(" ");
		countUnreadMessages = 0;
		btnRemove = new JFlatButton(GraphicsUtils.Images.resizeIcon(new ImageIcon(ICON_REMOVE), 40, 50));
		btnRemove.setBackground(p.getBackground());
		btnRemove.addActionListener(this);
//		btnRemove.setForeground(p.getBackground());
//		btnRemove.setBorderPainted(false);
		// these next two lines do the magic..
//		btnRemove.setContentAreaFilled(false);
//		btnRemove.setOpaque(true);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(btnRemove, BorderLayout.CENTER);
		p1.add(lblCountUnreadMessages, BorderLayout.EAST);
		add(p1, BorderLayout.EAST);
		lblLastActivities = new JLabel(contact.getLastActivities().format(formatter));
		p.add(lblLastActivities, BorderLayout.PAGE_END);
		add(p, BorderLayout.CENTER);
		preferredSize = new Dimension(250, 50);
		addMouseListener(this);
	}

	public Contact getContact() {
		return contact;
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	public Dimension getMinimumSize() {
		return preferredSize;
	}

	public Dimension getMaximumSize() {
		return preferredSize;
	}

	private void setCountUnreadMessages(final Integer countUnreadMessages) {
		this.countUnreadMessages = countUnreadMessages;
		lblCountUnreadMessages.setText(String.valueOf(this.countUnreadMessages));
	}

	public void setLastActivities(LocalDateTime lastActivities) {
		contact.setLastActivities(lastActivities);
		lblLastActivities.setText(contact.getLastActivities().format(formatter));
	}


	//The MouseListener interface requires that we define
	//mousePressed, mouseReleased, mouseEntered, mouseExited,
	//and mouseClicked.
	public void mousePressed(MouseEvent e) {
		System.out.println("ContactCard::" + e.toString());
//		setCountUnreadMessages(++countUnreadMessages);
		setLastActivities(LocalDateTime.now());
		if (isChooseView()) {
			setSelected(!isSelected());
		} else {
			firePropertyChange(CONTACTCARD_ACTIVECONTACT, null, this);
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public boolean isChooseView() {
		return chooseView;
	}

	public void setChooseView(final boolean chooseView) {
		this.chooseView = chooseView;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(final boolean selected) {
		if (this.selected != selected) {
			final boolean oldValue = this.selected;
			this.selected = selected;
			this.setBorder(this.selected ? this.selectBorder : this.entryBorder);
			revalidate();
			repaint();
			firePropertyChange(CONTACTCARD_SELECTCONTACT, oldValue, selected);
		}
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		firePropertyChange(CONTACTCARD_DELETECONTACT, null, this.contact);
	}
}
