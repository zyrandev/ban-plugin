package dev.zyran.punishments.commands;

import dev.zyran.api.storage.Storage;
import dev.zyran.punishments.user.SimpleUser;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "unban")
public class UnbanCommand
		implements CommandClass {

	private @Inject Storage storage;
	private @Inject MessageHandler messageHandler;

	@Command(names = "")
	public void runRootCommand(
			@Sender Player player,
			OfflinePlayer target
	) {
		storage.saveUser(new SimpleUser(target.getUniqueId()))
				.whenComplete((unused, throwable) -> {
					if (throwable != null) {
						throwable.printStackTrace();
						messageHandler.send(player, "commands.unban.failure");
						return;
					}
					messageHandler.send(player, "commands.unban.success");
				});
	}
}
