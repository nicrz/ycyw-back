package com.openclassrooms.chatpoc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequest {
    private String content;
    private Integer chat;

    @JsonCreator
    public MessageRequest(@JsonProperty("content") String content, @JsonProperty("chat") Integer chat) {
        this.content = content;
        this.chat = chat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getChat() {
        return chat;
    }

    public void setChat(Integer chat) {
        this.chat = chat;
    }
}



