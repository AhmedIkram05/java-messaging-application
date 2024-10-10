import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Author _se.ho
 * @create 2024-03-13
 **/

public class MenuPanel extends JPanel {


    private List<JButton> buttons;

    public MenuPanel(List<JButton> buttons) {
        this.buttons = buttons;
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.addMenuButtons();
    }

    private void addMenuButtons() {
        for (JButton button : buttons) {
            this.add(button);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.repositionButtons();
    }

    private void repositionButtons() {
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();

        int totalButtonWidth = 0;
        for (JButton button : buttons) {
            totalButtonWidth += button.getPreferredSize().width;
        }

        int x = (panelWidth - totalButtonWidth) / 2;
        int y = panelHeight / 2;

        for (JButton button : buttons) {
            int buttonWidth = button.getPreferredSize().width;
            button.setBounds(x, y - button.getHeight() / 2, buttonWidth, button.getHeight());
            x += buttonWidth;
        }
    }

}
