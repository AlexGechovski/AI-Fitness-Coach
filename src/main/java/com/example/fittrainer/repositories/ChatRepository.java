package com.example.fittrainer.repositories;

import com.example.fittrainer.dtos.ChatDTO;
import com.example.fittrainer.dtos.MessageDTO;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save a new chat and its associated messages
    public Long saveChat(Chat chat) {
        String insertChatSql = "INSERT INTO Chat (profileId, modelName) VALUES (?, ?)";
        jdbcTemplate.update(insertChatSql, chat.getProfileId(), chat.getModelName());

        Long chatId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        chat.setChatId(chatId);

        for (Message message : chat.getMessages()) {
            saveMessage(chat, message);
        }

        return chatId;
    }

    // Save a new message associated with a chat
    private void saveMessage(Chat chat, Message message) {
        String insertMessageSql = "INSERT INTO Message (chatId, role, content, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                insertMessageSql,
                chat.getChatId(),
                message.getRole(),
                message.getContent(),
                message.getTimestamp()
        );
    }


    // Retrieve messages by chatId
    private List<Message> getMessagesByChatId(Long chatId) {
        String selectMessagesSql = "SELECT * FROM Message WHERE chatId = ?";
        return jdbcTemplate.query(
                selectMessagesSql,
                new Object[]{chatId},
                (rs, rowNum) -> {
                    Message message = new Message();
                    message.setMessageId(rs.getLong("messageId"));
                    message.setChatId(rs.getLong("chatId"));
                    message.setRole(rs.getString("role"));
                    message.setContent(rs.getString("content"));
                    message.setTimestamp(rs.getTimestamp("timestamp"));
                    return message;
                }
        );
    }

    private List<MessageDTO> getMessagesByChatIdDTO(Long chatId) {
        String selectMessagesSql = "SELECT * FROM Message WHERE chatId = ?";
        return jdbcTemplate.query(
                selectMessagesSql,
                new Object[]{chatId},
                (rs, rowNum) -> {
                    MessageDTO message = new MessageDTO();
                    message.setRole(rs.getString("role"));
                    message.setContent(rs.getString("content"));
                    return message;
                }
        );
    }

    // Retrieve all chats and their associated messages

    public List<Chat> getAllChats() {
        String selectChatsSql = "SELECT * FROM Chat";
        return jdbcTemplate.query(selectChatsSql, (rs, rowNum) -> {
            Chat chat = new Chat();
            chat.setChatId(rs.getLong("chatId"));
            chat.setProfileId(rs.getLong("profileId"));
            chat.setModelName(rs.getString("modelName"));
            chat.setMessages(getMessagesByChatId(chat.getChatId()));
            return chat;
        });
    }

    public List<ChatDTO> getChatsByProfileId(int profileId) {
        String selectChatsSql = "SELECT * FROM Chat WHERE profileId = ?";
        return jdbcTemplate.query(selectChatsSql, (rs, rowNum) -> {
            ChatDTO chat = new ChatDTO();
            chat.setChatId(rs.getLong("chatId"));
            chat.setModel(rs.getString("modelName"));
            chat.setMessages(getMessagesByChatIdDTO(chat.getChatId()));
            return chat;
        }, profileId);
    }

    // Other methods for update and delete operations can be implemented as needed
}
