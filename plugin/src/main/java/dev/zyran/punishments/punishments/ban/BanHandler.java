package dev.zyran.punishments.punishments.ban;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.storage.Storage;
import dev.zyran.punishments.punishments.AbstractPunishmentHandler;
import dev.zyran.punishments.user.SimpleUser;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BanHandler
		extends AbstractPunishmentHandler {

	public static final String MESSAGE_PATH = "ban";
	public static final String MESSAGE_TEMPLATE_PATH = MESSAGE_PATH + ".template";

	private @Inject MessageHandler messageHandler;
	private @Inject Storage storage;
	@Named("sync")
	private @Inject Executor syncExecutor;

	@Override
	protected void broadcastPunishment(@NotNull final Punishment punishment) {
		StringList message = messageHandler.getMany(null, MESSAGE_PATH + ".broadcast", punishment);
		for (final Player player : Bukkit.getOnlinePlayers()) {
			for (final String messageLine : message) {
				player.sendMessage(messageLine);
			}
		}
	}

	@Override
	protected void execute(final @NotNull Punishment punishment) {
		final Player player = Bukkit.getPlayer(punishment.getUserId());
		if (player == null) {
			return;
		}
		final StringList message = messageHandler.getMany(null, MESSAGE_TEMPLATE_PATH, punishment);
		syncExecutor.execute(() -> player.kickPlayer(message.join("\n")));
		storage.saveUser(new SimpleUser(punishment.getUserId(), punishment.getId()));
	}

	@Override
	protected CompletableFuture<Punishment> save(final @NotNull Punishment punishment) {
		return storage.savePunishment("ban", punishment);
	}

	@Override
	protected void sendSuccessMessage(final @NotNull Player sender, final @NotNull Punishment punishment) {
		messageHandler.send(sender, MESSAGE_PATH + ".success", punishment);
	}

	@Override
	protected void sendFailureMessage(final @NotNull Player sender, final Throwable throwable) {
		if (throwable != null) {
			throwable.printStackTrace();
		}
		messageHandler.send(sender, MESSAGE_PATH + ".failure");
	}
}
