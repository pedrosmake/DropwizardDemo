package org.kainos.ea.exceptions;

public class FailedToCreateException extends Throwable {
	private static final long serialVersionUID = 1L;

	public FailedToCreateException(Entity entity) {
		super("Failed to create " + entity.getEntity());
	}
}
