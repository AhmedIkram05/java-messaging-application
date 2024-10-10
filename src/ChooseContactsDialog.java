import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-30
 */
public class ChooseContactsDialog extends JDialog implements ActionListener {

	public static final String CHOOSE_CONTACTS = "Choose contacts";

	private static final String OK = "Ok";
	private static final String CANCEL = "Cancel";

	private List<Contact> contactList;

	private ContactsPanel contactsPanel;

	/**
	 * Creates a modeless dialog without a title and without a specified
	 * {@code Frame} owner.  A shared, hidden frame will be
	 * set as the owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value
	 * returned by {@code JComponent.getDefaultLocale}.
	 * <p>
	 * NOTE: This constructor does not allow you to create an unowned
	 * {@code JDialog}. To create an unowned {@code JDialog}
	 * you must use either the {@code JDialog(Window)} or
	 * {@code JDialog(Dialog)} constructor with an argument of
	 * {@code null}.
	 *
	 * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
	 * 	returns {@code true}.
	 * @see GraphicsEnvironment#isHeadless
	 * @see JComponent#getDefaultLocale
	 */
	public ChooseContactsDialog() {
		this((Frame) null);
	}

	/**
	 * Creates a modeless dialog with the specified {@code Frame}
	 * as its owner and an empty title. If {@code owner}
	 * is {@code null}, a shared, hidden frame will be set as the
	 * owner of the dialog.
	 * <p>
	 * This constructor sets the component's locale property to the value
	 * returned by {@code JComponent.getDefaultLocale}.
	 * <p>
	 * NOTE: This constructor does not allow you to create an unowned
	 * {@code JDialog}. To create an unowned {@code JDialog}
	 * you must use either the {@code JDialog(Window)} or
	 * {@code JDialog(Dialog)} constructor with an argument of
	 * {@code null}.
	 *
	 * @param owner the {@code Frame} from which the dialog is displayed
	 *
	 * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
	 * 	returns {@code true}.
	 * @see GraphicsEnvironment#isHeadless
	 * @see JComponent#getDefaultLocale
	 */
	public ChooseContactsDialog(final Frame owner) {
		super(owner, CHOOSE_CONTACTS, true);

//		JPanel panel = new JPanel(new BorderLayout());
//		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		contactsPanel = new ContactsPanel();
		contactsPanel.setChooseView(true);
//		panel.add(contactsPanel, BorderLayout.CENTER);
		JScrollPane
			sp =
			new JScrollPane(
				contactsPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
			);
//		sp.setPreferredSize(new Dimension(272, 250));
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.setViewportBorder(
			BorderFactory.createCompoundBorder());
		add(sp, BorderLayout.CENTER);
//		add(panel);
		JButton cancel = new JFlatButton(CANCEL);
		cancel.addActionListener(this);
		JButton ok = new JFlatButton(OK);
		ok.setPreferredSize(cancel.getPreferredSize());
		ok.addActionListener(this);

		JPanel buttons = new JPanel();
		buttons.add(ok);
		buttons.add(cancel);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		pack();

		getRootPane().setDefaultButton(ok);
	}

	public void setContacts(List<Contact> list) {
		contactList = list;
		if(Objects.nonNull(contactList) && !contactList.isEmpty()) {
			contactsPanel.addContacts(contactList);
//			contactList.forEach(contactsPanel::addContact);
		} else {
			JLabel l = new JLabel("Contact list is empty!");
			l.setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
			l.setForeground(Color.RED);
			contactsPanel.add(l);
		}
	}

	public List<Contact> getSelectedContacts() {
		return contactsPanel.getSelectedContacts();
	}

	/*
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
//		switch (e.getActionCommand()) {
//			case CANCEL -> setEditedInfo(savedText);
//			case OK -> {
//				String s = getEditedInfo();
//				if (Objects.isNull(s) || s.isEmpty()) {
//					JOptionPane.showMessageDialog(null, "Doesn't accept empty message");
//					txtEditMessage.requestFocus();
//					return;
//				}
//			}
//		}
		setVisible(false);
	}
}
