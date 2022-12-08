package dev.zyran.punishments.commands.internal;

import dev.zyran.punishments.commands.internal.parts.DurationPartFactory;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilder;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Injector;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;
import java.time.Duration;

public class CommandModule
		extends AbstractModule {

	@Provides
	@Singleton
	public CommandManager provideCommandManager() {
		return new BukkitCommandManager("punishments");
	}

	@Provides
	@Singleton
	public AnnotatedCommandTreeBuilder provideAnnotatedCommandTreeBuilder(Injector injector) {
		PartInjector partInjector = PartInjector.create();

		partInjector.install(new BukkitModule());
		partInjector.install(new DefaultsModule());
		partInjector.bindFactory(Duration.class, new DurationPartFactory());

		return new AnnotatedCommandTreeBuilderImpl(
				AnnotatedCommandBuilder.create(partInjector),
				(clazz, parent) -> injector.getInstance(clazz)
		);
	}
}
