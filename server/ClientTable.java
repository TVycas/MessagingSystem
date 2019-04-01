package server;
// Each nickname has a different incomming-message queue.

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTable {

    // ConcurrentHashMap to store the user data
    private ConcurrentMap<String, UserInfo> msgTable = new ConcurrentHashMap<String, UserInfo>();
    // ConcurrentHashMap to store the group data
    private ConcurrentMap<String, Group> groupsTable = new ConcurrentHashMap<String, Group>();
    // ConcurrentHashMap to store new messages
    private ConcurrentMap<String, BlockingQueue<Message>> queueTable = new ConcurrentHashMap<String, BlockingQueue<Message>>();

    // Adds newly registered member
    public void addMem(String nickname) {
        msgTable.put(nickname, new UserInfo());
    }

    // logs users in
    public void login(String nickname) {
        queueTable.put(nickname, new LinkedBlockingQueue<Message>());
    }

    // logs users out
    public void logout(String nickname, String realNickname) {
        queueTable.remove(nickname);
        msgTable.get(nickname).decNumbOfActiveLogins(realNickname);
    }

    public void addGroup(String groupName) {
        groupsTable.put(groupName, new Group(groupName));
    }

    // Removes users form the server (when quit is called)
    public void remove(String nickname) {
        queueTable.remove(nickname);
        msgTable.remove(nickname);
    }

    public void removeGroup(String groupName) {
        groupsTable.remove(groupName);
    }

    // Checks if the users exists in the servers
    public boolean exists(String nickname) {
        if (msgTable.containsKey(nickname)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existsGroup(String nickname) {
        if (groupsTable.containsKey(nickname)) {
            return true;
        } else {
            return false;
        }
    }

    // Sends message to every login of a specified nickname (the main way to send
    // messages in the server)
    public void sendMsg(String nickname, Message msg) {
        if (exists(nickname) && msg != null) {
            for (int i = 0; i < getUserInfo(nickname).getNumbOfActiveLogins(); i++) {
                getQueue(nickname + i).offer(msg);
            }
        }
    }

    // Returns null if the nickname is not in the table:
    public BlockingQueue<Message> getQueue(String nickname) {
        return queueTable.get(nickname);
    }

    public UserInfo getUserInfo(String nickname) {
        return msgTable.get(nickname);
    }

    public Group getGroup(String groupName) {
        return groupsTable.get(groupName);
    }
}
