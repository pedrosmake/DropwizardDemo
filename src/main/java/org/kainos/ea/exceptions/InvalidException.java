package org.kainos.ea.exceptions;

public class InvalidException extends Throwable {
	private static final long serialVersionUID = 1L;

	public InvalidException(Entity entity, String reason) {
		super(entity.getEntity() + " is not valid: " + reason);
	}
}
