import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-19
 */
public class ContactPanel extends JPanel implements ActionListener {

	private static final String EDIT_AVATAR_ACTION = "EditAvatar";
	private static final String EDIT_NAME_ACTION = "EditName";
	private static final String EDIT_ID_ACTION = "EditID";
	private static final String EDIT_PHONE_ACTION = "EditPhone";
	private JLabel avatar;
	private JTextField txtName;
	private JTextField txtId;
	private JTextField txtPhone;

	private JButton btnEditAvatar;
	private JButton btnEditName;
	private JButton btnEditId;
	private JButton btnEditPhone;
	private Contact contact;

	private JButton btnSave;

	/**
	 * Creates a new <code>JPanel</code>
	 */
	public ContactPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void initComponents() {
		JPanel pAvatar = new JPanel(new BorderLayout());
		pAvatar.setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		avatar = new JLabel(GraphicsUtils.Images.getProfileAvatar());
		btnEditAvatar = new JFlatButton("Edit");
		btnEditAvatar.addActionListener(this);
		btnEditAvatar.setActionCommand(EDIT_AVATAR_ACTION);
		pAvatar.add(avatar, BorderLayout.CENTER);
		pAvatar.add(btnEditAvatar, BorderLayout.EAST);

		add(pAvatar);
		txtName = new JTextField();
		btnEditName = new JFlatButton("Edit");
		add(addEditBlock("Name", EDIT_NAME_ACTION, txtName, btnEditName));
		txtId = new JTextField();
		btnEditId = new JFlatButton("Edit");
		add(addEditBlock("ID", EDIT_ID_ACTION, txtId, btnEditId));
		txtPhone = new JTextField();
		btnEditPhone = new JFlatButton("Edit");
		add(addEditBlock("Phone", EDIT_PHONE_ACTION, txtPhone, btnEditPhone));
	}

	private JPanel addEditBlock(String caption, String actionCommand, JTextField txt, JButton btn) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(GraphicsUtils.Borders.createEmptyBorder());
		final JLabel lbl = new JLabel(caption);
		lbl.setBorder(GraphicsUtils.Borders.createEmptyBorder());
		p.add(lbl, BorderLayout.WEST);
		txt.setEditable(false);
		txt.setHorizontalAlignment(JLabel.CENTER);
		p.add(txt, BorderLayout.CENTER);
		btn.addActionListener(this);
		btn.setActionCommand(actionCommand);
		p.add(btn, BorderLayout.EAST);
		return p;
	}

	public void createContactMode(boolean mode) {
		txtName.setEditable(mode);
		txtId.setEditable(mode);
		txtPhone.setEditable(mode);
		btnEditName.setVisible(!mode);
		btnEditId.setVisible(!mode);
		btnEditPhone.setVisible(!mode);
	}

	public void setContact(final Contact contact) {
		this.contact = contact;
		showContact(contact);
	}

	public Contact getContact() {
		if (checkFullFilledFields()) {
			return fillContact();
		}
		return null;
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case EDIT_AVATAR_ACTION -> doChooseNewAvatar();
			case EDIT_NAME_ACTION -> changeStateControls(txtName, btnEditName);
			case EDIT_ID_ACTION -> changeStateControls(txtId, btnEditId);
			case EDIT_PHONE_ACTION -> changeStateControls(txtPhone, btnEditPhone);
		}
	}

	private void changeStateControls(JTextField txt, JButton btn) {
		txt.setEditable(!txt.isEditable());
		if (txt.isEditable()) {
			btn.setText("Save");
		} else {
			btn.setText("Edit");
		}
	}

	private void doChooseNewAvatar() {
		String location = System.getProperty("user.home");
		JFileChooser fc = new JFileChooser(location);
		fc.setFileFilter(new ImageFileFilter());
		int res = fc.showOpenDialog(null);
		// We have an image!
		try {
			if (res == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				avatar.setIcon(null);
				setTarget(file);
			} // Oops!
			else {
				JOptionPane.showMessageDialog(null,
					"You must select one image to be the reference.", "Aborting...",
					JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (Exception ignored) {
		}
		JOptionPane.showMessageDialog(null, "Show dialog choose new avatar", "Choose new avatar",
			JOptionPane.INFORMATION_MESSAGE
		);
	}

	private void showContact(Contact contact) {
		if (Objects.isNull(contact)) {
			return;
		}
		txtName.setText(contact.getName());
		txtId.setText(String.valueOf(contact.getID()));
		txtPhone.setText(Objects.isNull(contact.getPhoneNumber()) ? "" : contact.getPhoneNumber());
		avatar.setIcon(contact.getProfileImage());
		revalidate();
		repaint();
	}

	private Contact fillContact() {
		contact.setName(txtName.getText());
		contact.setID(Integer.parseInt(txtId.getText()));
		contact.setPhoneNumber(txtPhone.getText());
		contact.setProfileImage((ImageIcon) avatar.getIcon());
		return contact;
	}

	public boolean checkFullFilledFields() {
		String name = txtName.getText();
		if (!(Objects.nonNull(name) && !name.isEmpty())) {
			txtName.requestFocus();
			return false;
		}
		String idStr = txtId.getText();
		if (!(Objects.nonNull(idStr) && !idStr.isEmpty())) {
			txtId.requestFocus();
			return false;
		} else {
			boolean isHappy = false;
			try {
				Integer test = Integer.parseInt(idStr);
			} catch (Exception ignored) {
				isHappy = true;
			}
			if (isHappy) {
				txtId.requestFocus();
				return false;
			}
		}
		String phonStr = txtPhone.getText();
		if (!(Objects.nonNull(phonStr) && !phonStr.isEmpty())) {
			txtPhone.requestFocus();
			return false;
		} else {
			String normalized = phonStr.replaceAll("\\s+", "");
			if (normalized.isEmpty() || !normalized.matches("\\d+")) {
				txtPhone.requestFocus();
				return false;
			}
		}
		return true;
	}

	public BufferedImage rescale(BufferedImage originalImage) {
		final int baseSize = 128;
		BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
		g.dispose();
		return resizedImage;
	}

	public void setTarget(File reference) {
		BufferedImage targetImg;
		try {
			targetImg = rescale(ImageIO.read(reference));
			avatar.setIcon(new ImageIcon(targetImg));
			setVisible(true);
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static class ImageFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.getName().toLowerCase().endsWith(".jpeg")) {
				return true;
			}
			if (f.getName().toLowerCase().endsWith(".jpg")) {
				return true;
			}
			if (f.getName().toLowerCase().endsWith(".png")) {
				return true;
			}
			if (f.getName().toLowerCase().endsWith(".gif")) {
				return true;
			}
			if (f.isDirectory()) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "JPEG, PNG, GIF files";
		}
	}
}
