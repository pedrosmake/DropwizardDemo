package org.kainos.ea.exceptions;

public class DoesNotExistException extends Throwable {
    private static final long serialVersionUID = 1L;

    public DoesNotExistException(Entity entity) {
        super(entity.getEntity() + " does not exist");
    }
}
