package com.paste_bin_clone.other;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ApplicationError extends RuntimeException {

    private final Map<ERRORS, ArrayList<String>> errors = new HashMap<>();

    public ApplicationError add(ERRORS error, String text) {
        this.getErrors().computeIfAbsent(error, (val) -> new ArrayList<>()).add(text);
        return this;
    }

}
