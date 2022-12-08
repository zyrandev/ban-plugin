package dev.zyran.punishments.commands;

import dev.zyran.api.punishment.PunishmentHandler;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = "ban")
public class BanCommand
		implements CommandClass {

	@Named("ban")
	private @Inject PunishmentHandler punishmentHandler;

	@Command(names = "")
	public void runRootCommand(
			@Sender Player sender,
			Player target,
			@Text String reason
	) {
		punishmentHandler.sendExecutedPunishment(
				sender, target.getUniqueId(),
				reason, sender.getUniqueId(),
				sender.getName(), null, false
		);
	}
}
