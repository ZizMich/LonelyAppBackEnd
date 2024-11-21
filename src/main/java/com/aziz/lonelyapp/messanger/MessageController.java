package com.aziz.lonelyapp.messanger;



import com.aziz.lonelyapp.model.ChatMemberEntity;
import com.aziz.lonelyapp.model.MessageEntity;
import com.aziz.lonelyapp.repository.ChatMemberRepository;
import com.aziz.lonelyapp.repository.DirectMessagesRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/**
 * This class is a WebSocket handler for managing messages in a chat application.
 * It handles user authentication, message sending, and notification for offline users.
 *
 * @author Aziz
 * @version 1.0
 */
@Component
public class MessageController implements WebSocketHandler {
    /**
     * User repository for retrieving user information.
     */
    @Autowired
    UserRepository rep;

    /**
     * Chat member repository for retrieving chat group members.
     */
    @Autowired
    ChatMemberRepository chatRepository;

    /**
     * Direct messages repository for saving and retrieving messages.
     */
    @Autowired
    DirectMessagesRepository messagesRepository;

    /**
     * Map to store active WebSocket sessions for authenticated users.
     */
    public static Map<String, WebSocketSession> activeUserSessions = new HashMap<>();

    /**
     * Authenticates a WebSocket session by checking if the user is authenticated.
     *
     * @param session The WebSocket session to authenticate.
     * @return True if the user is authenticated, false otherwise.
     */
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

    /**
     * Handles the event after a WebSocket connection is established.
     *
     * @param session The WebSocket session that was established.
     * @throws Exception If an error occurs during the handling.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        authenticate(session);

    }

    /**
     * Handles incoming WebSocket messages.
     *
     * @param session The WebSocket session that sent the message.
     * @param message The WebSocket message to handle.
     * @throws Exception If an error occurs during the handling.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(message);
        try {
            // Convert JSON string to Map
            Map<String, Object> map = objectMapper.readValue(message.getPayload().toString(), new TypeReference<Map<String, Object>>() {
            });
            if (map.containsKey("receiver")) {
                String rec = (String) map.get("receiver");
                System.out.println("sent");
                String fromId = (String) session.getAttributes().get("USER_ID");
                String text = map.get("text").toString();
                MessageEntity mess = new MessageEntity();
                mess.setMessage(text);
                mess.setFrom(fromId);
                mess.setTo(rec);
                if (text.length() <= 250) {
                    mess.setMessage(text);
                } else {
                    mess.setMessage(text.substring(0, 250));

                }
                mess.setSentdate(System.currentTimeMillis());
                ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

                // Handle message for a user
                if (rec.contains("USER")) {
                    if(rep.findById(rec).isPresent()){
                        MessageEntity e = messagesRepository.save(mess);
                        String prettyJsonString = writer.writeValueAsString(e);
                        session.sendMessage(new TextMessage(prettyJsonString));
                        if (activeUserSessions.containsKey(rec)) {
                            WebSocketSession receiverSession = activeUserSessions.get(rec);
                            receiverSession.sendMessage(new TextMessage(prettyJsonString));
                        }
                    }

                // Handle message for a chat group
                } else if (rec.contains("CHAT")) {
                    MessageEntity e = messagesRepository.save(mess);
                    String prettyJsonString = writer.writeValueAsString(e);

                    session.sendMessage(new TextMessage(prettyJsonString));

                    for (ChatMemberEntity member : chatRepository.findAllByGroupid(rec)) {
                        String receiverId = member.getMemberid();
                        if(!Objects.equals(fromId, receiverId)){
                            if (activeUserSessions.containsKey(receiverId)) {
                                WebSocketSession receiverSession = activeUserSessions.get(receiverId);
                                receiverSession.sendMessage(new TextMessage(prettyJsonString));
                            }
                            else{
                                Notifications.getInstance().sendNotification(receiverId,mess.getMessage(),"You got new message!", true);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles transport errors during WebSocket communication.
     *
     * @param session The WebSocket session where the error occurred.
     * @param exception The exception that occurred.
     * @throws Exception If an error occurs during the handling.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * Handles the event after a WebSocket connection is closed.
     *
     * @param session The WebSocket session that was closed.
     * @param closeStatus The status of the close.
     * @throws Exception If an error occurs during the handling.
     */
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

    /**
     * Checks if the WebSocket handler supports partial messages.
     *
     * @return True if partial messages are supported, false otherwise.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}