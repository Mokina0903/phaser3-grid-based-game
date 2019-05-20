package at.ac.tuwien.foop.server.endpoint;


import at.ac.tuwien.foop.server.dto.Request;
import at.ac.tuwien.foop.server.dto.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/greeting")
    @SendTo("/topic/greetings")
    public Response greeting(Request request) {
        return Response.builder()
                .message(String.format("Hello %s!", request.getName()))
                .build();
    }
}
