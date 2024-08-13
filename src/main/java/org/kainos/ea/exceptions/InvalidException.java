package org.kainos.ea.exceptions;

public class InvalidException extends Throwable{
    public InvalidException(Entity entity, String reason) {
        super(entity.getEntity() + " is not valid: " + reason);
    }
}
