import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @Author _se.ho
 * @create 2024-03-23
 */
public class LastChatViewPanel extends JPanel {

	private List<ChatButton> btns = new ArrayList<>(3);

	public LastChatViewPanel() {
		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(GraphicsUtils.Borders.createLoweredEtchedBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void initComponents() {
		IntStream.range(0, 3)
			.mapToObj(i -> new ChatButton())
			.forEach(b -> {
				btns.add(b);
				this.add(b);
			});
	}

	private void clearInfo(){
		Component[] arr = this.getComponents();
		for (Component c : arr) {
			this.remove(c);
		}
		btns.clear();
	}
	public void setChats(List<Message> messageList) {
		clearInfo();
		if (Objects.nonNull(messageList) && !messageList.isEmpty()) {
			for (int i = 0; i < Math.min(3, messageList.size()); i++) {
				ChatButton cb = new ChatButton();
				cb.setChat(messageList.get(i));
				cb.setAlignmentX(Component.CENTER_ALIGNMENT);
				btns.add(cb);
				add(cb);
			}
		}
		this.revalidate();
		this.repaint();
	}
}
