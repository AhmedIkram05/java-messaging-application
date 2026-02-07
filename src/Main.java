import java.awt.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		MainWindow mw = new MainWindow(new BackendImplementation("Serhii", "123456"), "Chat for AC12001 Win2");
//            MainWindow mw = new MainWindow(new ApiBackendImpl(), "Chat for AC12001 Win2");
		// https://stackoverflow.com/a/2442610
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mw.setLocation(dim.width / 2 - mw.getSize().width / 2, mw.getLocation().y);
		mw.setVisible(true);
//        });
	}
}
