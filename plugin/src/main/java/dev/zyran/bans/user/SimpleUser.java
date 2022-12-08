package dev.zyran.bans.user;

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

	private void setCurrentBanId(final String currentBanId) {
		this.currentBanId = currentBanId;
	}

	@Override
	public String getCurrentBanId() {
		return currentBanId;
	}
}
