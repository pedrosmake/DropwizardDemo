package org.kainos.ea.models;

import java.security.Principal;

import javax.security.auth.Subject;

public class JwtToken implements Principal {
	UserRole userRole;

	public JwtToken(UserRole userRole) {
		setUserRole(userRole);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean implies(Subject subject) {
		return Principal.super.implies(subject);
	}
}
