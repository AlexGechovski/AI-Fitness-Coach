package com.example.fittrainer.repositories;

import com.example.fittrainer.dtos.ChatDTO;
import com.example.fittrainer.dtos.MessageDTO;
import com.example.fittrainer.models.Chat;
import com.example.fittrainer.models.FullProfile;
import com.example.fittrainer.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Repository
public class ChatRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSourceTransactionManager transactionManager;

    public ChatRepository(JdbcTemplate jdbcTemplate, DataSourceTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    // Save a new chat and its associated messages
    public Long saveChat(Chat chat) {
        String insertChatSql = "INSERT INTO Chat (profileId, modelName) VALUES (?, ?)";
        jdbcTemplate.update(insertChatSql, chat.getProfileId(), chat.getModelName());

        Long chatId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        chat.setChatId(chatId);

        for (Message message : chat.getMessages()) {
            saveMessage(chat.getChatId(), message);
        }

        return chatId;
    }

    // Save a new message associated with a chat
    public void saveMessage(Long chatId, Message message) {
        String insertMessageSql = "INSERT INTO Message (chatId, role, content, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                insertMessageSql,
                chatId,
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

    //Get ChatDTO by chatId
    public ChatDTO getChatByChatId(Long chatId) {
        String selectChatSql = "SELECT * FROM Chat WHERE chatId = ?";
        return jdbcTemplate.queryForObject(
                selectChatSql,
                new Object[]{chatId},
                (rs, rowNum) -> {
                    ChatDTO chat = new ChatDTO();
                    chat.setChatId(rs.getLong("chatId"));
                    chat.setModel(rs.getString("modelName"));
                    chat.setMessages(getMessagesByChatIdDTO(chat.getChatId()));
                    return chat;
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

    public ChatDTO createChatWithInitialMessage(String message, int profileId, String modelName, FullProfile fullProfile, String username) {
        String insertChatSql = "INSERT INTO Chat (profileId, modelName) VALUES (?, ?)";
        jdbcTemplate.update(insertChatSql, profileId, modelName);

        Long chatId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        String insertMessageSql = "INSERT INTO Message (chatId, role, content) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                insertMessageSql,
                chatId,
                "system",
                "You are a helpful fitness assistant"
        );

        String insertInitialMessageSql = "INSERT INTO Message (chatId, role, content) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                insertInitialMessageSql,
                chatId,
                "assistant",
                message
        );

        String insertInfoMessageSql = "INSERT INTO Message (chatId, role, content) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                insertInfoMessageSql,
                chatId,
                "user",
                "This is my personal information Username=" + username + " " + fullProfile.toString()

        );

        ChatDTO chat = new ChatDTO();
        chat.setChatId(chatId);
        chat.setModel(modelName);
        chat.setMessages(getMessagesByChatIdDTO(chat.getChatId()));

        return chat;
    }

    public void deleteChatByChatId(Long chatId) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                String deleteMessagesSql = "DELETE FROM Message WHERE chatId = ?";
                jdbcTemplate.update(deleteMessagesSql, chatId);

                String deleteChatSql = "DELETE FROM Chat WHERE chatId = ?";
                jdbcTemplate.update(deleteChatSql, chatId);
            }
        });
    }


    // Other methods for update and delete operations can be implemented as needed
}
