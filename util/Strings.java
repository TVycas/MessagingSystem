package util;

public class Strings {
    public static final String quit = "quit";
    public static final String quitMsg = "From quit: quit";
    public static final String logout = "logout";
    public static final String logoutMsg = "From logout: logout";
    public static final String send = "send";
    public static final String next = "next";
    public static final String previous = "previous";
    public static final String delete = "delete";
    public static final String login = "login";
    public static final String register = "register";
    public static final String noName = "There is no member called";
    public static final String noGroup = "You are not in a group called ";
    public static final String personAddedToGroup = " has been added to a group ";
    public static final String personRemovedFromGroup = "User has been removed from the group";
    public static final String personAlreadyInGroup = "is already in the group";
    public static final String addGroupMem = "add group member";
    public static final String group = "group";
    public static final String newGroup = "You've been added to a group called \"%s\" (type \"exit group\" to exit this group)";
    public static final String createGroup = "create group";
    public static final String exitGroup = "exit group";
    public static final String groupExited = "You have been removed from this group.";
    public static final String pleaseLogout = "To quit form your account, please first logout in any other computers";
    public static final String help = "help";
    public static final String toggleHelp = "(To toggle help messages write \"help\" as a new command)";
    public static final String sendHelp =
            "Usage:\n" +
            "Recipient\n" +
            "Message\n" +
            Strings.toggleHelp;
    public static final String createGroupHelp=                             "Usage:\n" +
            "Group name\n" +
            "Member names in one line\n" +
            Strings.toggleHelp;

    public static final String groupHelp =                                 "Usage:\n" +
            "Group name\n" +
            "Message" +
            Strings.toggleHelp;
    public static final String exitGroupHelp =                              "Usage:\n" +
            "Group name" +
            Strings.toggleHelp;
    public static final String addGroupMembHelp =                           "Usage:\n" +
            "Group name\n" +
            "Member to add" +
            Strings.toggleHelp;

}
