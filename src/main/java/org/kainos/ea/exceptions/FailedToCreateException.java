package org.kainos.ea.exceptions;

public class FailedToCreateException extends Throwable {
    public FailedToCreateException(Entity entity) {
        super("Failed to create " + entity.getEntity());
    }
}
