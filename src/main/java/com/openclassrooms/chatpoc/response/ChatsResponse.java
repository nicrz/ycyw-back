package com.openclassrooms.chatpoc.response;

import java.util.List;

import com.openclassrooms.chatpoc.model.Chat;

public class ChatsResponse {
    private List<Chat> chats;

    public ChatsResponse(List<Chat> chats) {
        this.chats = chats;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
