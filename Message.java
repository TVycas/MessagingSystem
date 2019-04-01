public class Message {

	private final String sender;
	private final String text;
	private final String groupName;

	Message(String errorMsg) {
		this.sender = errorMsg;
		text = "";
		groupName = "";
	}

	Message(String sender, String text) {
		this.sender = sender;
		this.text = text;
		groupName = "";
	}

	Message(String groupName, String sender, String text) {
		this.groupName = groupName;
		this.sender = sender;
		this.text = text;
	}

	public String getSender() {
		return sender;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		if (text.equals(""))
			return sender;
		
		 else if (groupName.equals(""))
			return "From " + sender + ": " + text;
		
		else
			return "From group '" + groupName + "' member " + sender + ": " + text;
	}
}
