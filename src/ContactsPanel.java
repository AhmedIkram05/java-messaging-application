import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author _se.ho
 * @create 2024-03-14
 */
public class ContactsPanel extends JPanel implements MouseListener, PropertyChangeListener {

	public static final String CONTACTSPANEL_ACTIVECONTACT = "ContactsPanel Active Contact";
	public static final String CONTACTSPANEL_DELETECONTACT = "ContactsPanel Delete Contact";
	private final List<ContactCard> contacts;
	private Contact activeContact;

	// if this flag is true - change behavior for choose cards.
	private boolean chooseView = false;

	private final Comparator<ContactCard> comparatorLastActivities = Comparator
		.comparing(
			ContactCard::getContact,
			Comparator.comparing(Contact::getLastActivities)
		)
		.reversed();

	private final Comparator<ContactCard> compareNameContact = Comparator
		.comparing(
			ContactCard::getContact,
			Comparator.comparing(Contact::getName)
		);

	private final Comparator<ContactCard> compareAddedOrder = Comparator
		.comparing(
			ContactCard::getContact,
			Comparator.comparing(Contact::getID)
		);

	private final Comparator<ContactCard>
		comparatorSelectedCard =
		Comparator.comparing(ContactCard::isSelected).reversed();

	public ContactsPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		contacts = new ArrayList<>();
	}

	public void addContact(Contact contact) {
		ContactCard c = new ContactCard(contact);
//		c.addMouseListener(this);
		c.setChooseView(this.isChooseView());
//		c.getContact().setViewer(this);
		c.addPropertyChangeListener(this);
		contacts.add(c);
		reorganize();
	}

	public void addContacts(List<Contact> list) {
		list.stream()
			.forEach(c -> {
				ContactCard cc = new ContactCard(c);
				cc.setChooseView(this.isChooseView());
//				cc.getContact().setViewer(this);
				cc.addPropertyChangeListener(this);
				contacts.add(cc);
			});
		reorganize();
	}

	public void clearContacts() {
		Component[] arr = this.getComponents();
		for (Component c : arr) {
			this.remove(c);
		}
		contacts.clear();
		reorganize();
	}

	public void removeContact(Contact contact) {
		contacts.removeIf(o -> o.getContact().equals(contact));
		reorganize();
	}

	public List<ContactCard> getContacts() {
		return contacts;
	}

	public List<Contact> getSelectedContacts() {
		return contacts
			.stream()
			.filter(ContactCard::isSelected)
			.map(ContactCard::getContact)
			.collect(Collectors.toList());
	}

	public void reorganize() {
		Component[] arr = this.getComponents();
		for (Component c : arr) {
			this.remove(c);
		}
		if (isChooseView()) {
			contacts.sort(this.comparatorSelectedCard);
		}
		contacts.forEach(this::add);

		this.revalidate();
		this.repaint();
	}

	//The MouseListener interface requires that we define
	//mousePressed, mouseReleased, mouseEntered, mouseExited,
	//and mouseClicked.
	public void mousePressed(MouseEvent e) {
		System.out.println("ContactPanel::" + e.toString());
		reorganize();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public Contact getActiveContact() {
		if (Objects.nonNull(activeContact)) {
			return activeContact;
		}
		return null;
	}

	/**
	 * This method gets called when a bound property is changed.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source
	 * 	and the property that has changed.
	 */
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (isChooseView()) {
			if (evt.getPropertyName().equals(ContactCard.CONTACTCARD_SELECTCONTACT)) {
				String innerValue = evt.getNewValue().toString();
				System.out.println("ContactsPanel::" +
					ContactCard.CONTACTCARD_SELECTCONTACT +
					"::new value from inside of OuterView: "
					+
					innerValue);
//				передавать конткт кард и перестраивать список
				reorganize();
			}
		} else {
			if (evt.getPropertyName().equals(ContactCard.CONTACTCARD_ACTIVECONTACT)) {
				String innerValue = evt.getNewValue().toString();
				System.out.println("ContactsPanel::" +
					ContactCard.CONTACTCARD_ACTIVECONTACT +
					"::new value from inside of OuterView: "
					+
					innerValue);
				if (evt.getNewValue() instanceof ContactCard contactCard) {
					setActiveContact(contactCard.getContact());
				}
			}
			if (evt.getPropertyName().equals(ContactCard.CONTACTCARD_DELETECONTACT)) {
				String innerValue = evt.getNewValue().toString();
				System.out.println("ContactsPanel::" +
					ContactCard.CONTACTCARD_DELETECONTACT +
					"::new value from inside of OuterView: "
					+
					innerValue);
				if (evt.getNewValue() instanceof Contact contact) {
					firePropertyChange(CONTACTSPANEL_DELETECONTACT, null, contact);
				}
			}
		}
	}

	private void setActiveContact(final Contact contact) {
		this.activeContact = contact;
		firePropertyChange(CONTACTSPANEL_ACTIVECONTACT, null, this);
	}

	public boolean isChooseView() {
		return chooseView;
	}

	public void setChooseView(final boolean chooseView) {
		this.chooseView = chooseView;
	}
}
