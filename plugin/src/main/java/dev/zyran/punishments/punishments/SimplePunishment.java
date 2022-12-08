package dev.zyran.punishments.punishments;

import dev.zyran.api.punishment.Punishment;

import java.time.Duration;
import java.util.UUID;

public class SimplePunishment
		implements Punishment {

	private String id;
	private final UUID userId;
	private final String reason;
	private final UUID actorId;
	private final String actorName;
	private final Duration duration;
	private final long createdAt;

	public SimplePunishment(
			final String id,
			final UUID userId,
			final String reason,
			final UUID actorId,
			final String actorName,
			final Duration duration,
			long createdAt
	) {
		this.id = id;
		this.userId = userId;
		this.reason = reason;
		this.actorId = actorId;
		this.actorName = actorName;
		this.duration = duration;
		this.createdAt = createdAt;
	}

	public SimplePunishment(
			final UUID userId, final String reason, final UUID actorId,
			String actorName, final Duration duration
	) {
		this(null, userId, reason, actorId, actorName, duration, System.currentTimeMillis());
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		if (this.id != null) {
			throw new IllegalStateException("Cannot set id twice");
		}
		this.id = id;
	}

	@Override
	public UUID getUserId() {
		return userId;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public UUID getActorId() {
		return actorId;
	}

	@Override
	public String getActorName() {
		return actorName;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public Long getCreatedAt() {
		return createdAt;
	}
}
