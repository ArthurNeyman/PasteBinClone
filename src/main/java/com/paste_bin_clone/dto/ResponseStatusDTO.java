package com.paste_bin_clone.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonDeserialize
@JsonSerialize
public class ResponseStatusDTO<E> {

    private HttpStatus status;
    private E data;
    private List<String> messages;

    public ResponseStatusDTO() {
        this.status = HttpStatus.OK;
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
