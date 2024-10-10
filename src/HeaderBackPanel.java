import javax.swing.*;
import java.awt.*;

/**
 * @Author _se.ho
 * @create 2024-03-23
 */
public class HeaderBackPanel extends JPanel {

	private static final String BACK = "Back";
	private JButton btnBack;

	public HeaderBackPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BorderLayout(5, 50));
//		setLayout(new GridLayout(1, 3, 5, 50));
	}

	private void initComponents() {
		btnBack = new JFlatButton(BACK);
//		btnBack.setContentAreaFilled(false);
//		btnBack.setOpaque(true);
		Box b = Box.createHorizontalBox();
		b.add(Box.createRigidArea(new Dimension(5,5)));
		b.add(Box.createHorizontalGlue());
		b.add(btnBack);
		add(b, BorderLayout.LINE_END);
	}

	public JButton getBtnBack() {
		return btnBack;
	}
}
