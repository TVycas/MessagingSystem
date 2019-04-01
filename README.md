# MessagingSystem

Implementation:

* Register and login - when a client connects to the server, a new ClientInit thread is opened (I used threads to make sure that there could be many registrations and login checks at the same time). This thread is communicating with a Client class function membInit. In the ClientInit class there are checks what user wants to do - register or login. Also, this class checks if the name of the user already exists in the server and accordingly either lets the user to login ar register or sends message to the Client class to print an error messsage. When the user finally logins in, the thread stops. Multiple logins are allowed. All logins receive the same information from the server except the error for the exit if there are multiple logins and information when the user logged out or quited for the server.

* To use this messaging system, the user must always first write the command and all other information in the following lines. This information is then sent over to ClientReceiver which handles all commands.

* Logout - When the user sends a command called logout, the message travels the usual threads - ClientSender - ServerReceiver - ServerSender - ClientReceiver and breaks all the while loops of them. But client information is not removed from the server.

* Quit - When the user enters "quit" as a command, ClientReceiver checks if there is only one login at the time. If it's only one, then the ServerReceiver removes user info from the server send a message to quit to the ServerSender and stops, then ServerSender send that message to ClientReceiver and stops and then  ClientReceiver changes the value of breakTheLoop in ClientSender to true, which breaks the while loop in ClientSender and then stops itself.

* Keeping all the messages - when a user registers a new item in the msgTable in the ClientTable class is created. This ConcurrentHashMap saves all the user data in an object of a class UserInfo. In this class all the messages are stored in an ArrayList. Also, in this class, current message indes is stored and how many logins of the same user there are at the moment in the server.

* previous, next, delete - When the user types either previous or next as a command, ServerReceiver calls changeCurrent method in UserInfo object to change the current index and then sends, as a message, message of that index to the client. When the user enters delete as a command, ServerReceiver calls deleteCurrentMsg() method in UserInfo object to change delete the message that has an index of current. Then the new current message becomes the next message (as in current++) if there are any messages in front of the current in the ArrayList and if not then the next current becomes the previous message.

* Group chats. A user can create a group by typing "create group" then stating the name of the group and users to add all in 3 different lines. The user that creates the group doesn't need to type his name, as he or she will already be added the group. A user can exit the group by entering "exit group" and the name of the group in the next line. A user that is already in a group can add a new member to the group by typing "add group member", then the group name and the user nickname to add all in new lines. Finally, to send a message to all the group members, a user has to type "group" then the group name and the message all in different lines.

