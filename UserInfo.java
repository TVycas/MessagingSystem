import java.util.ArrayList;

public class UserInfo {
	private ArrayList<Message> allMessages = new ArrayList<Message>();
	// Stores active logins in a String array that has the username with the number
	// of login add to the end of the names
	private ArrayList<String> activeLogins = new ArrayList<String>();
	private String username = "username";
	private int current = -1;
	private int numbOfActiveLogins = 0;

	public ArrayList<Message> getAllMessages() {
		return allMessages;
	}

	// Add messages to be stored in the the servers
	public void add(Message msg) {
		if (current == allMessages.size() - 1)
			current++;
		allMessages.add(msg);
	}

	public Message getMsg(String nickname, int index) {
		return allMessages.get(index);
	}

	public void deleteCurrentMsg() {
		if (allMessages.size() == 0) {
			current = -1;
		} else {
			allMessages.remove(current);
			if (allMessages.size() > current + 1)
				current++;
			else if (allMessages.size() == current)
				current--;
		}
	}

	public Message getCurrentMsg() {
		if (current > -1)
			return allMessages.get(current);
		else
			return null;
	}

	public boolean changeCurrent(String command) {
		if (command.equals(Strings.next) && allMessages.size() > current + 1) {
			current++;
			return true;
		}

		if (command.equals(Strings.previous) && current - 1 > -1) {
			current--;
			return true;
		}

		return false;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public int getNumbOfActiveLogins() {
		return numbOfActiveLogins;
	}

	// checks if there is an empty slot (which would be named "Not used") and if
	// finds one, adds a new login to that stops, otherwise just adds the ne login
	// to the and of the ArrayList
	public int incNumbOfActiveLogins() {
		int indexOfUser;
		if (activeLogins.contains("Not used")) {
			int itemIndex = activeLogins.indexOf("Not used");
			activeLogins.remove("Not used");
			activeLogins.add(itemIndex, (username + itemIndex));
			indexOfUser = itemIndex;
		} else {
			indexOfUser = activeLogins.size();
			activeLogins.add(username + activeLogins.size());
		}
		numbOfActiveLogins++;
		return indexOfUser;
	}

	// removes the login be replacing its name with "Not used" in the array
	public void decNumbOfActiveLogins(String usernameWithIndex) {
		int itemIndex = activeLogins.indexOf(usernameWithIndex);
		activeLogins.remove(usernameWithIndex);

		activeLogins.add(itemIndex, "Not used");

		numbOfActiveLogins--;
	}
}
