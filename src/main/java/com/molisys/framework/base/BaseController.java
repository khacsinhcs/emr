package com.molisys.framework.base;

import java.util.Collection;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
	private String loggedUser = null;

	void add() {

	}

	public String index() {
		return "home";
	}

	void remove() {

	}

	void update() {

	}

	/**
	 * @return the loggedUser
	 */
	public String getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			// get logged in username
			this.loggedUser = auth.getName();
		}
		return loggedUser;
	}

	/**
	 * @param loggedUser
	 *            the loggedUser to set
	 */
	public void setLoggedUser(String loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Collection<SimpleGrantedAuthority> getUserRoles() {
		@SuppressWarnings("unchecked")
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		return authorities;
	}

	protected boolean hasRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}
		return false;
	}
}
