package dev.zyran.punishments.storage;

import dev.zyran.api.storage.Storage;
import org.bukkit.configuration.Configuration;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Singleton;
import java.util.concurrent.Executors;

public class StorageModule
		extends AbstractModule {

	@Provides
	@Singleton
	public Storage provideStorage(Configuration config) {
		return new AsyncMongoStorage(
				Executors.newFixedThreadPool(config.getInt("storage.thread-pool-size")),
				MongoStorage.of(
						config.getString("storage.mongo-uri"),
						config.getString("storage.database")
				)
		);
	}
}
