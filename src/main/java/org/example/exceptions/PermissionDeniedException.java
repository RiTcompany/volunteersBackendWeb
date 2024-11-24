package org.example.exceptions;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super("У вас недостаточно прав");
    }
}
