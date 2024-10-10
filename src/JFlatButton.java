import javax.swing.*;

/**
 * @Author _se.ho
 * @create 2024-03-29
 */
public class JFlatButton extends JButton {

	/**
	 * Creates a button with no set text or icon.
	 */
	public JFlatButton() {
		setFlat();
	}

	/**
	 * Creates a button with an icon.
	 *
	 * @param icon the Icon image to display on the button
	 */
	public JFlatButton(final Icon icon) {
		super(icon);
		setFlat();
	}

	/**
	 * Creates a button with text.
	 *
	 * @param text the text of the button
	 */
	public JFlatButton(final String text) {
		super(text);
		setFlat();
	}

	/**
	 * Creates a button where properties are taken from the
	 * <code>Action</code> supplied.
	 *
	 * @param a the <code>Action</code> used to specify the new button
	 *
	 * @since 1.3
	 */
	public JFlatButton(final Action a) {
		super(a);
		setFlat();
	}

	/**
	 * Creates a button with initial text and an icon.
	 *
	 * @param text the text of the button
	 * @param icon the Icon image to display on the button
	 */
	public JFlatButton(final String text, final Icon icon) {
		super(text, icon);
		setFlat();
	}

	private void setFlat(){
		// these next two lines do the magic..
		setContentAreaFilled(false);
		setOpaque(true);}
}
