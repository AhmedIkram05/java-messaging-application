import java.awt.*;

/**
 * @Author _se.ho
 * @create 2024-03-26
 */
public class MessageEvent extends AWTEvent {

	public static final int NEW_MESSAGE = RESERVED_ID_MAX + 1;
	public static final int CHANGE_MESSAGE = RESERVED_ID_MAX + 2;
	public static final int DELETE_MESSAGE = RESERVED_ID_MAX + 3;


	Object sourceChat;

	/**
	 * Constructs an AWTEvent object with the specified source object and type.
	 *
	 * @param source the object where the event originated
	 * @param id the event type
	 */
	public MessageEvent(final Object source, final int id) {
		super(source, id);
	}
}
