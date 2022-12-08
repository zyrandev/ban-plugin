package dev.zyran.punishments.user;

import dev.zyran.api.user.User;

import java.util.UUID;

public class SimpleUser
		implements User {

	private final UUID id;
	private String currentBanId;

	public SimpleUser(final UUID id) { this(id, null); }

	public SimpleUser(final UUID id, final String currentBanId) {
		this.id = id;
		this.currentBanId = currentBanId;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public String getCurrentBanId() {
		return currentBanId;
	}

	private void setCurrentBanId(final String currentBanId) {
		this.currentBanId = currentBanId;
	}
}
