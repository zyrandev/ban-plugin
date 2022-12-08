package dev.zyran.api.punishment;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

public interface Punishment {

	String getId();

	UUID getUserId();

	String getReason();

	UUID getActorId();

	String getActorName();

	Duration getDuration();

	Long getCreatedAt();
}
