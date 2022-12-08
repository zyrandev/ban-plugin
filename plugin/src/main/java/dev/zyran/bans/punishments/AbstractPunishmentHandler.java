package dev.zyran.bans.punishments;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.punishment.PunishmentHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
public abstract class AbstractPunishmentHandler
		implements PunishmentHandler {

	@Override
	public CompletableFuture<Punishment> execute(
			@NotNull final UUID userId,
			@Nullable final String reason,
			@NotNull final UUID actorId,
			@Nullable final Duration duration
	) {
		return save(new SimplePunishment(userId, reason, actorId, duration))
				       .thenApplyAsync(punishment -> {
					       execute(punishment);
					       return punishment;
				       });
	}

	@Override
	public CompletableFuture<Punishment> sendExecutedPunishment(
			@NotNull final Player sender,
			@NotNull final UUID userId,
			@Nullable final String reason,
			@NotNull final UUID actorId,
			@Nullable final Duration duration,
			boolean silent
	) {

		return execute(userId, reason, actorId, duration)
				       .whenComplete((punishment, throwable) -> {
					       if (throwable != null) {
						       sendFailureMessage(sender, throwable);
						       return;
					       }
					       if (!silent) {
						       broadcastPunishment(punishment);
					       }
					       sendSuccessMessage(sender, punishment);
				       });
	}

	protected abstract void broadcastPunishment(@NotNull Punishment punishment);

	protected abstract void execute(final @NotNull Punishment punishment);
	protected abstract CompletableFuture<Punishment> save(@NotNull Punishment punishment);
	protected abstract void sendSuccessMessage(@NotNull Player sender, @NotNull Punishment punishment);

	protected abstract void sendFailureMessage(@NotNull Player sender, @Nullable Throwable throwable);
}
