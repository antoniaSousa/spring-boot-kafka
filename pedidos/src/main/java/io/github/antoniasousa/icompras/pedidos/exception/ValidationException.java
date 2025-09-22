package io.github.antoniasousa.icompras.pedidos.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private String field;
    private String message;

    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }
}

