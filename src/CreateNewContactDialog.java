import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author _se.ho
 * @create 30.03.2024
 */
public class CreateNewContactDialog extends JDialog implements ActionListener {

	private static final String OK = "Ok";
	private static final String CANCEL = "Cancel";
	private ContactPanel contactPanel;

	private Contact contact;
	private boolean bCancel;

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
	public CreateNewContactDialog() {
		this(null);
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
	public CreateNewContactDialog(final Frame owner) {
		super(owner, "Create new contact", true);
		setLayout(new BorderLayout());
		contactPanel = new ContactPanel();

		contact = new Contact(null, 0, "", null, null);
		contactPanel.setContact(contact);
		contactPanel.createContactMode(true);
		add(contactPanel, BorderLayout.CENTER);
		JButton cancel = new JFlatButton(CANCEL);
		cancel.addActionListener(this);
		JButton ok = new JFlatButton(OK);
		ok.setPreferredSize(cancel.getPreferredSize());
		ok.addActionListener(this);

		JPanel buttons = new JPanel();
		buttons.add(ok);
		buttons.add(cancel);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(600, 600));
	}

	public Contact getContact() {
		if (bCancel) {
			return null;
		}
		return contactPanel.getContact();
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case CANCEL -> {
				bCancel = true;
				contactPanel.createContactMode(false);
				setVisible(false);
			}
			default -> {
				if(contactPanel.checkFullFilledFields()) {
					bCancel = false;
					contactPanel.createContactMode(false);
					setVisible(false);
				}
			}
		}
	}
}
