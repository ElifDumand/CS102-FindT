package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Message {

    private int messageId;
    private String senderType;
    private int senderId;
    private String receiverType;
    private int receiverId;
    private String body;

    public Message(int messageId, String senderType, int senderId, String receiverType, int receiverId, String body) {
        this.messageId = messageId;
        this.senderType = senderType;
        this.senderId = senderId;
        this.receiverType = receiverType;
        this.receiverId = receiverId;
        this.body = body;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getSenderType() {
        return senderType;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSentByCurrentUser(int currentUserId) {
        return this.senderId == currentUserId;
    }

    public static Message createMessage(String senderType, int senderId, String receiverType, int receiverId, String body) throws SQLException {
        String query = "INSERT INTO messages (sender_type, senderid, receiver_type, receiverid, messagebody) VALUES (?, ?, ?, ?, ?)";
        Message message = new Message(receiverId, senderType, senderId, receiverType, receiverId, body);
        try (Connection connection = Main.connect();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, senderType);
            statement.setInt(2, senderId);
            statement.setString(3, receiverType);
            statement.setInt(4, receiverId);
            statement.setString(5, body);
            
            int count = statement.executeUpdate();

            statement.close();
            connection.close();
            return message;
            }

    }
    
    public static ArrayList<Message> getMessages(String receiverType, int receiverId) throws SQLException {
        ArrayList<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE receiver_type = ? AND receiverid = ?";
    
        try (Connection connection = Main.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setString(1, receiverType);
            statement.setInt(2, receiverId);
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt("messageid");
                    String senderType = rs.getString("sender_type");
                    int senderId = rs.getInt("senderid");
                    String body = rs.getString("messagebody");
                    messages.add(new Message(messageId, senderType, senderId, receiverType, receiverId, body));
                }
            }
        }
        return messages;
    }
    

    public static List<Student> getChatsForTutor(int tutorid) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = Main.connect();
        String query = "SELECT DISTINCT senderid FROM messages WHERE receiverid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, tutorid);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int studentid = resultSet.getInt("senderid");
            Student student = Student.getById(studentid);
            if (student != null) {
                students.add(student);
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
        return students;
    }

    public boolean isSentByCurrentUser() {
		if (User.getCurrentUser().getId() == senderId) {
			return true;
		}
		return false;
	}
}