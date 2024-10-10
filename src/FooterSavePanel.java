import javax.swing.*;
import java.awt.*;

/**
 * @Author _se.ho
 * @create 2024-03-23
 */
public class FooterSavePanel extends JPanel {

	private static final String SAVE = "Save";
	private JButton btnSave;

	public FooterSavePanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BorderLayout(5, 50));
//		setLayout(new GridLayout(1, 3, 5, 50));
	}

	private void initComponents() {
		btnSave = new JFlatButton(SAVE);
//		btnSave.setContentAreaFilled(false);
//		btnSave.setOpaque(true);
		add(btnSave, BorderLayout.LINE_END);
	}

	public void setCaption(final String caption){
		btnSave.setText(caption);
	}

	public JButton getBtnSave() {
		return btnSave;
	}
}

