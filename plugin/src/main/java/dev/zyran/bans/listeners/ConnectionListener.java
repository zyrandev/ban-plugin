package dev.zyran.bans.listeners;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.storage.Storage;
import dev.zyran.bans.punishments.ban.BanHandler;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
public class ConnectionListener
		implements Listener {

	private @Inject Storage storage;
	private @Inject MessageHandler messageHandler;

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
		// this can lock the thread because it's a synchronous call
		final Punishment punishment = storage.retrieveUser(event.getUniqueId())
				                              .thenCompose(user -> {
					                              if (user.getCurrentBanId() != null) {
						                              return storage.retrievePunishment(user.getCurrentBanId());
					                              }
					                              return CompletableFuture.completedFuture(null);
				                              }).join();
		if (punishment != null) {
			StringList message = messageHandler.getMany(null, BanHandler.MESSAGE_TEMPLATE_PATH, punishment);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message.join("\n"));
		}
	}
}
