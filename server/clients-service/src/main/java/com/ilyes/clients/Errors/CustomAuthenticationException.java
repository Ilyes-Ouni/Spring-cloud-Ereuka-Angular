package com.ilyes.clients.Errors;

import javax.naming.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String message, Throwable cause) {
        super(message);
    }

    public CustomAuthenticationException(String message) {
        super(message);
    }
}
