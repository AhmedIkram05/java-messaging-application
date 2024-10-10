import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Scanner;

/**
 * @Author _se.ho
 * @create 2024-02-23
 **/

public class Utils {
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Prompts the user to enter an integer value and returns it.
	 *
	 * @param message the message to display to the user
	 *
	 * @return the integer entered by the user
	 */
	public static int getInt(String message) {
		System.out.print(message);
		while (!scanner.hasNextInt()) {
			System.out.println("Invalid input. Please enter an integer.");
			scanner.next();
			System.out.print(message);
		}
		int input = scanner.nextInt();
		scanner.nextLine();
		return input;
	}

	/**
	 * Prompts the user to enter a double value and returns it.
	 *
	 * @param message the message to display to the user
	 *
	 * @return the double entered by the user
	 */
	public static double getDouble(String message) {
		System.out.print(message);
		while (!scanner.hasNextDouble()) {
			System.out.println("Invalid input. Please enter a number.");
			scanner.next();
			System.out.print(message);
		}
		double input = scanner.nextDouble();
		scanner.nextLine();
		return input;
	}

	/**
	 * Prompts the user to enter a string value and returns it.
	 *
	 * @param message the message to display to the user
	 *
	 * @return the string entered by the user
	 */
	public static String getString(String message) {
		System.out.print(message);
		String input = scanner.nextLine();
		return input;
	}

	/**
	 * Prints a message to the console.
	 *
	 * @param message the message to print
	 */
	public static void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Closes the scanner associated with user input.
	 */
	public static void closeScanner() {
		scanner.close();
	}

	/**
	 * Adds indentation between two specified strings.
	 *
	 * @param firstString the first string
	 * @param secondString the second string
	 * @param indentSize the size of the indentation, number of spaces
	 *
	 * @return a new string consisting of the first string, indentation, and the second string
	 */
	public static String addIndentBetweenStrings(String firstString, String secondString, int indentSize) {
		StringBuilder result = new StringBuilder();

		result.append(firstString);

		for (int i = 0; i < indentSize; i++) {
			result.append(" ");
		}

		result.append(secondString);

		return result.toString();
	}

	/**
	 * Generates a string consisting of the specified number of spaces.
	 *
	 * @param numSpaces The number of spaces to generate.
	 *
	 * @return A string containing the specified number of spaces.
	 */
	public static String generateSpaces(int numSpaces) {
		StringBuilder spaces = new StringBuilder();
		for (int i = 0; i < numSpaces; i++) {
			spaces.append(" ");
		}
		return spaces.toString();
	}

	/**
	 * Returns a shortened version of the input string up to the specified length.
	 *
	 * @param string The input string to be shortened.
	 * @param gap The maximum length of the shortened string.
	 *
	 * @return A shortened version of the input string, up to the specified length.
	 */
	public static String getShortString(String string, int gap) {
		return string.substring(0, Math.min(string.length(), gap));
	}

	public static String getOpenFileName() {
		String filePath = null;
		String location = System.getProperty("user.home");
		JFileChooser fc = new JFileChooser(location);
		fc.setFileFilter(new SCDFileFilter());
		int res = fc.showOpenDialog(null);
		// We have an file!
		try {
			if (res == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (file.exists()) {
					filePath = file.getAbsolutePath();
				}
			} // Oops!
			else {
				JOptionPane.showMessageDialog(null,
					"You must select one file contained saved chats data to be the reference.", "Aborting...",
					JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (Exception ignored) {
		}
		JOptionPane.showMessageDialog(null, "Show dialog choose Load Chats Data file", "Choose Load Chats Data file",
			JOptionPane.INFORMATION_MESSAGE
		);
		return filePath;
	}

	public static File getSaveFileName() {
		String filePath = null;
		String location = System.getProperty("user.home");
		JFileChooser fc = new JFileChooser(location);
		fc.setFileFilter(new SCDFileFilter());
		int res = fc.showSaveDialog(null);
		// We have an file!
		try {
			if (res == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if(!file.getName().toLowerCase().endsWith(".scd")){
					String absolutePath = file.getAbsolutePath();
						absolutePath += ".scd";
						file = new File(absolutePath);
				}
				return file;
			} // Oops!
			else {
				JOptionPane.showMessageDialog(null,
					"You must select one file contained saved chats data to be the reference.", "Aborting...",
					JOptionPane.WARNING_MESSAGE
				);
			}
		} catch (Exception ignored) {
		}
		JOptionPane.showMessageDialog(null, "Show dialog choose Saved Chats Data file", "Choose Saved Chats Data file",
			JOptionPane.INFORMATION_MESSAGE
		);
		return null;
	}

	private static class SCDFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.getName().toLowerCase().endsWith(".scd")) {
				return true;
			}
			if (f.isDirectory()) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "Saved Chats Data(*.scd) files";
		}
	}
}
