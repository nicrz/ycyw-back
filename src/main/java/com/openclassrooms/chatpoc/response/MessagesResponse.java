package com.openclassrooms.chatpoc.response;

import java.util.List;

import com.openclassrooms.chatpoc.model.Message;

public class MessagesResponse {
    private List<Message> messages;

    public MessagesResponse(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

