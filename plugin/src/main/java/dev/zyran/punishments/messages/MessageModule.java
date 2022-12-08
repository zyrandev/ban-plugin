package dev.zyran.punishments.messages;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.punishments.inject.ConfigurationModule;
import dev.zyran.punishments.messages.placeholders.PlayerPlaceholderProvider;
import dev.zyran.punishments.messages.placeholders.PunishmentPlaceholderProvider;
import me.yushust.message.MessageHandler;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;

public class MessageModule
		extends AbstractModule {

	@Provides
	@Singleton
	public MessageHandler provideMessageHandler(
			Plugin plugin,
			Path folder
	) throws IOException {
		Configuration messages = ConfigurationModule.loadConfiguration(plugin, folder, "messages.yml");
		return MessageHandler.of(new SingletonMessageSource(messages),
		                         configuration -> configuration.delimiting("{", "}"),
		                         configuration -> configuration.specify(Player.class)
				                                          .addProvider("player", new PlayerPlaceholderProvider()),
		                         configuration -> configuration.specify(Punishment.class)
				                                          .addProvider("punishment", new PunishmentPlaceholderProvider()));
	}
}
