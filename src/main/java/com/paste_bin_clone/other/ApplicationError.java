package com.paste_bin_clone.other;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ApplicationError extends RuntimeException {

    private Map<ERRORS, List<String>> errors;
    private HttpStatus httpStatus;

    public ApplicationError() {
        this.errors = new HashMap<>();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ApplicationError add(ERRORS error, String message) {
        this.errors.computeIfAbsent(error, (str) -> new ArrayList<>()).add(message);
        return this;
    }

    public ApplicationError add(ERRORS error, List<String> messages) {
        this.errors.put(error, messages);
        return this;
    }

    public ApplicationError withStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
