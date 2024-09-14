package com.aziz.lonelyapp.messanger;



import com.aziz.lonelyapp.model.MessageEntity;
import com.aziz.lonelyapp.repository.DirectMessagesRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.HashMap;
import java.util.Map;

public class MessageController implements WebSocketHandler {
    @Autowired
    UserRepository rep;

    @Autowired
    DirectMessagesRepository messagesRepository;

    public static Map<String, WebSocketSession> activeUserSessions = new HashMap<>();
    private static Boolean authenticate(WebSocketSession session){
        String userId = (String) session.getAttributes().get("USER_ID");
        if (userId != null) {
            activeUserSessions.put(userId, session);
            System.out.println("WebSocket authenticated user: " + userId);
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
        System.out.println(message);
        try {
            // Convert JSON string to Map
            Map<String, Object> map = objectMapper.readValue(message.getPayload().toString(), new TypeReference<Map<String, Object>>() {});
            if(map.containsKey("receiver")){
                String rec = (String) map.get("receiver");
                if(activeUserSessions.containsKey(rec)){
                    System.out.println("sent");
                    WebSocketSession receiverSession =  activeUserSessions.get(rec);
                    String fromId = (String) session.getAttributes().get("USER_ID");
                    String text = map.get("text").toString();
                    if(text.length()<=250){
                    MessageEntity mess = new MessageEntity();
                    mess.setMessage(text);
                    mess.setFrom(fromId);
                    mess.setTo(rec);
                    mess.setMessage(text);
                    mess.setSentdate(System.currentTimeMillis());
                    MessageEntity e = messagesRepository.save(mess);
                    ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
                    String prettyJsonString = writer.writeValueAsString(e);
                    receiverSession.sendMessage(new TextMessage(prettyJsonString));
                    session.sendMessage(new TextMessage(prettyJsonString));
                    }
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
        String userId = (String) session.getAttributes().get("USER_ID");
        if (userId != null) {
            activeUserSessions.remove(userId);
            System.out.println("WebSocket authenticated user: " + userId);
        } else {
            System.out.println("WebSocket user is not authenticated");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}