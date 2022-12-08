package dev.zyran.api.punishment;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PunishmentHandler {

	CompletableFuture<Punishment> execute(
			@NotNull UUID userId,
			@Nullable String reason,
			@NotNull UUID actorId,
			@NotNull String actorName,
			@Nullable Duration duration
	);

	CompletableFuture<Punishment> sendExecutedPunishment(
			@NotNull Player sender,
			@NotNull UUID userId,
			@Nullable String reason,
			@NotNull UUID actorId,
			@NotNull String actorName,
			@Nullable Duration duration,
			boolean silent
	);
}
