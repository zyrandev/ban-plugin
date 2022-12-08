package dev.zyran.punishments;

import dev.zyran.api.storage.Storage;
import dev.zyran.punishments.commands.BanCommand;
import dev.zyran.punishments.commands.TempBanCommand;
import dev.zyran.punishments.commands.UnbanCommand;
import dev.zyran.punishments.commands.internal.CommandModule;
import dev.zyran.punishments.inject.BukkitModule;
import dev.zyran.punishments.inject.ConfigurationModule;
import dev.zyran.punishments.listeners.ConnectionListener;
import dev.zyran.punishments.messages.MessageModule;
import dev.zyran.punishments.punishments.PunishmentModule;
import dev.zyran.punishments.storage.StorageModule;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public class PunishmentPlugin
		extends JavaPlugin {

	private @Inject Storage storage;
	private @Inject ConnectionListener connectionListener;

	private @Inject AnnotatedCommandTreeBuilder commandBuilder;
	private @Inject CommandManager commandManager;
	private @Inject BanCommand banCommand;
	private @Inject TempBanCommand tempBanCommand;
	private @Inject UnbanCommand unbanCommand;

	@Override
	public void onLoad() {
		Injector.create(
				new BukkitModule(this),
				new ConfigurationModule(),
				new StorageModule(),
				new CommandModule(),
				new MessageModule(),
				new PunishmentModule()
		).injectMembers(this);
	}

	@Override
	public void onEnable() {
		storage.init();

		getServer().getPluginManager().registerEvents(connectionListener, this);

		registerCommands(banCommand, tempBanCommand, unbanCommand);
	}

	private void registerCommands(CommandClass... commandClasses) {
		for (CommandClass commandClass : commandClasses) {
			final List<Command> commands = commandBuilder.fromClass(commandClass);
			commandManager.registerCommands(commands);
		}
	}

	@Override
	public void onDisable() {
		try {
			storage.close();
		} catch (IOException e) {
			getLogger().warning("Failed to close storage\n'" + e.getMessage() + "'");
		}
	}
}
