import javax.swing.*;
import java.io.Serializable;

/**
 * @Author _se.ho
 * @create 2024-03-24
 */
public enum Emoji implements Serializable {
	AMAZED(
		"Amazed",
		new ImageIcon("src/amazed.png"),
		"<a href=\"https://iconscout.com/icons/amazed\" class=\"text-underline font-size-sm\" target=\"_blank\">Amazed</a> by <a href=\"https://iconscout.com/contributors/vincent-le-moign\" class=\"text-underline font-size-sm\" target=\"_blank\">Vincent Le moign</a>"
	),
	ANGRY(
		"Angry",
		new ImageIcon("src/angry.png"),
		"<a href=\"https://iconscout.com/icons/angry\" class=\"text-underline font-size-sm\" target=\"_blank\">Angry</a> by <a href=\"https://iconscout.com/contributors/vincent-le-moign\" class=\"text-underline font-size-sm\" target=\"_blank\">Vincent Le moign</a>"
	),
	HEARD(
		"Heard",
		new ImageIcon("src/heart.png"),
		"<a href=\"https://iconscout.com/icons/heart\" class=\"text-underline font-size-sm\" target=\"_blank\">Heart</a> by <a href=\"https://iconscout.com/contributors/vincent-le-moign\" class=\"text-underline font-size-sm\">Vincent Le moign</a> on <a href=\"https://iconscout.com\" class=\"text-underline font-size-sm\">IconScout</a>"
	);
	private final ImageIcon pic;
	private final String name;

//<a href="https://iconscout.com/icons/amazed" class="text-underline font-size-sm" target="_blank">Amazed</a> by <a href="https://iconscout.com/contributors/vincent-le-moign" class="text-underline font-size-sm" target="_blank">Vincent Le moign</a>
//<a href="https://iconscout.com/icons/angry" class="text-underline font-size-sm" target="_blank">Angry</a> by <a href="https://iconscout.com/contributors/vincent-le-moign" class="text-underline font-size-sm" target="_blank">Vincent Le moign</a>
//<a href="https://iconscout.com/icons/heart" class="text-underline font-size-sm" target="_blank">Heart</a> by <a href="https://iconscout.com/contributors/vincent-le-moign" class="text-underline font-size-sm">Vincent Le moign</a> on <a href="https://iconscout.com" class="text-underline font-size-sm">IconScout</a>

	private final String licenceEmoji;

	Emoji(String name, ImageIcon pic, String licenceEmoji) {
		this.name = name;
		this.pic = pic;
		this.licenceEmoji = licenceEmoji;
	}

	public String getName() {
		return name;
	}

	public ImageIcon getEmoji() {
		return pic;
	}

	public String getLicenceEmoji() {
		return licenceEmoji;
	}
}
