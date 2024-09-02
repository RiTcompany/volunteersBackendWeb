package org.example.exceptions;

public class HeadquartersNotFoundException extends RuntimeException {
    public HeadquartersNotFoundException(String message) {
        super("Not found headquarters ID = ".concat(message));
    }
}
