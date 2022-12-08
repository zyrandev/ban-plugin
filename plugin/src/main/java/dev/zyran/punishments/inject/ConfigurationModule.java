package dev.zyran.punishments.inject;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigurationModule
		extends AbstractModule {

	public static Configuration loadConfiguration(Plugin plugin, Path folder, String resource) throws IOException {
		Path file = folder.resolve(resource);
		if (!Files.exists(file)) {
			plugin.saveResource(resource, false);
		}
		return YamlConfiguration.loadConfiguration(Files.newBufferedReader(file));
	}

	@Provides
	@Singleton
	public Configuration provideMainConfiguration(
			Plugin plugin,
			Path folder
	) throws IOException {
		return loadConfiguration(plugin, folder, "config.yml");
	}
}
