package server;

import java.util.ArrayList;

public class Group {
	private ArrayList<String> recipientsNames = new ArrayList<String>();
	private String groupName;

	public Group(String groupName) {
		this.groupName = groupName;
	}

	public void addGroupMember(String name) {
		if (!checkForName(name))
			recipientsNames.add(name);
	}

	public void removeGroupMember(String name) {
		if (checkForName(name))
			recipientsNames.remove(name);
	}

	// Get the names of all the members of the group
	public String[] getRecipientsNames() {
		String[] names = new String[recipientsNames.size()];
		names = recipientsNames.toArray(names);
		return names;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getNumbOfMem() {
		return recipientsNames.size();
	}

	// Checks if there is a member with that username in the group
	public boolean checkForName(String name) {
		for (String nickname : recipientsNames) {
			if (nickname.equals(name))
				return true;
		}
		return false;
	}

}
