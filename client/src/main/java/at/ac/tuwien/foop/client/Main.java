package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.client.server.util.HerokuUtil;
import io.javalin.Javalin;
import io.javalin.staticfiles.Location;
import io.javalin.websocket.WsSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import static j2html.TagCreator.article;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;

public class Main {

    private static Map<WsSession, String> userUsernameMap = new ConcurrentHashMap<>();
    private static int nextUserNumber = 1; // Assign to username for next connecting user

    public static void main(String[] args) {
        Javalin.create()
                .port(HerokuUtil.getHerokuAssignedPort())
                .enableStaticFiles("src/main/java/client/", Location.EXTERNAL)
                .ws("/chat", ws -> {
                    ws.onConnect(session -> {
                        String username = "User" + nextUserNumber++;
                        userUsernameMap.put(session, username);
                        broadcastMessage("Server", (username + " joined the chat"));
                    });
                    ws.onClose((session, status, message) -> {
                        String username = userUsernameMap.get(session);
                        userUsernameMap.remove(session);
                        broadcastMessage("Server", (username + " left the chat"));
                    });
                    ws.onMessage((session, message) -> {
                        broadcastMessage(userUsernameMap.get(session), message);
                    });
                })
                .start();
    }

    // Sends a message from one user to all users, along with a list of current usernames
    private static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            session.send(
                    new JSONObject()
                            .put("userMessage", createHtmlMessageFromSender(sender, message))
                            .put("userlist", userUsernameMap.values()).toString()
            );
        });
    }

    // Builds a HTML element with a sender-name, a message, and a timestamp
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article(
                b(sender + " says:"),
                span(attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())),
                p(message)
        ).render();
    }

}
