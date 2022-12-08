package dev.zyran.punishments.punishments;

import dev.zyran.api.punishment.PunishmentHandler;
import dev.zyran.punishments.punishments.ban.BanHandler;
import team.unnamed.inject.AbstractModule;

public class PunishmentModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PunishmentHandler.class)
				.named("ban")
				.to(BanHandler.class)
				.singleton();

	}

}
