package com.aziz.lonelyapp.messanger;


import com.aziz.lonelyapp.dto.ReceiveMessageDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.socket.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;

public class MessageController implements WebSocketHandler {
    public static Map<String, WebSocketSession> activeUserSessions = new HashMap<>();
    private static Boolean authenticate(WebSocketSession session){

        SecurityContext context = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            activeUserSessions.put(context.getAuthentication().getName(),session);
            System.out.println("WebSocket authenticated user: " + context.getAuthentication().getName());
            return true;

        } else {
            System.out.println("WebSocket user is not authenticated");
            return false;
        }

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        authenticate(session);

    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert JSON string to Map
            Map<String, Object> map = objectMapper.readValue(message.getPayload().toString(), new TypeReference<Map<String, Object>>() {});
            if(map.containsKey("receiver")){
                if(activeUserSessions.containsKey(map.get("receiver"))){

                    WebSocketSession receiverSession =  activeUserSessions.get(map.get("receiver"));
                    SecurityContext context = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
                    String sender = context.getAuthentication().getName();
                    String text = map.get("text").toString();
                    ReceiveMessageDTO mess = new ReceiveMessageDTO(sender,text);
                    ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
                    String prettyJsonString = writer.writeValueAsString(mess);
                    receiverSession.sendMessage(new TextMessage(prettyJsonString));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        SecurityContext context = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            activeUserSessions.remove(context.getAuthentication().getName());
            System.out.println("WebSocket authenticated user: " + context.getAuthentication().getName());
        } else {
            System.out.println("WebSocket user is not authenticated");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}