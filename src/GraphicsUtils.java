import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Objects;

/**
 * @Author _se.ho
 * @create 2024-03-16
 */
public class GraphicsUtils {
	public class Borders {

		private static final int WIDTH40 = 40;
		private static final int HEIGHT20 = 20;
		private static final int WIDTH20 = 20;
		private static final int HEIGHT10 = 10;

		private Borders() {
		}

		public static Border createEmptyBorder20x40() {
			return BorderFactory.createEmptyBorder(HEIGHT20, WIDTH40, HEIGHT20, WIDTH40);
		}

		public static Border createEmptyBorder() {
			return BorderFactory.createEmptyBorder(HEIGHT10, WIDTH20, HEIGHT10, WIDTH20);
		}

		public static Border createLoweredEtchedBorder() {
			return BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		}
	}

	public class Images {

		static final String ICON = "src/defaultImage.png";

		public static ImageIcon getProfileAvatar() {
			return resizeIcon(new ImageIcon(ICON), 40, 50);
		}

		public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
			if (Objects.isNull(icon)) {
				icon = new ImageIcon(ICON);
			}
			Image img = icon.getImage();
			Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(resizedImg);
		}
	}
}
