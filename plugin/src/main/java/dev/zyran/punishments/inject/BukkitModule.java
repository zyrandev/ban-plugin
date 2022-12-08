package dev.zyran.punishments.inject;

import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.concurrent.Executor;

public class BukkitModule
		extends AbstractModule {

	private final Plugin plugin;

	public BukkitModule(final Plugin plugin) { this.plugin = plugin; }

	@Override
	protected void configure() {
		bind(Plugin.class).toInstance(plugin);
		bind(Path.class).toInstance(plugin.getDataFolder().toPath());
	}

	@Provides
	@Singleton
	@Named("sync")
	public Executor provideSyncExecutor(Plugin plugin) {
		return command -> plugin.getServer().getScheduler().runTask(plugin, command);
	}
}
