package com.good_three.dto;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ResponseStatusDTO <E> {

    private HttpStatus status;
    private E data;
    private List<String> messages;

    public ResponseStatusDTO() {
        this.status = HttpStatus.OK;
        this.messages=new ArrayList<>();
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
