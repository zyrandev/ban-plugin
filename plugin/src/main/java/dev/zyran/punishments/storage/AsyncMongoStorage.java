package dev.zyran.punishments.storage;

import com.google.common.collect.Iterables;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.storage.Storage;
import dev.zyran.api.user.User;
import dev.zyran.punishments.user.SimpleUser;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AsyncMongoStorage
		implements Storage {

	private final Executor executor;
	private final MongoStorage mongoStorage;

	public AsyncMongoStorage(final Executor executor, final MongoStorage mongoStorage) {
		this.executor = executor;
		this.mongoStorage = mongoStorage;
	}

	@Override
	public void init() {
		mongoStorage.init();
	}

	@Override
	public CompletableFuture<Void> saveUser(User user) {
		return CompletableFuture.runAsync(() -> mongoStorage.saveUser(user), executor);
	}

	@Override
	public void close() {
		mongoStorage.close();
	}

	@Override
	public CompletableFuture<Punishment> savePunishment(final String type, final Punishment punishment) {
		return CompletableFuture.supplyAsync(() -> mongoStorage.savePunishment(type, punishment), executor);
	}

	@Override
	public CompletableFuture<User> retrieveUser(final UUID userId) {
		return CompletableFuture.supplyAsync(() -> mongoStorage.retrieveUser(userId), executor)
				       .thenCompose(user -> {
					       if (user == null) {
						       return CompletableFuture.completedFuture(new SimpleUser(userId));
					       }
					       return CompletableFuture.completedFuture(user);
				       });
	}

	@Override
	public CompletableFuture<Punishment> retrievePunishment(final String punishmentId) {
		return CompletableFuture.supplyAsync(() -> mongoStorage
				                                           .retrievePunishments(Filters.eq("_id", new ObjectId(punishmentId)))
				                                           .first(), executor);
	}

	@Override
	public CompletableFuture<Set<Punishment>> retrieveUserPunishments(final UUID userId) {
		return CompletableFuture.supplyAsync(() -> {
			Set<Punishment> punishments = new HashSet<>();
			MongoIterable<Punishment> retrievedPunishments = mongoStorage.retrievePunishments(
					Filters.eq("user", userId.toString())
			);
			Iterables.addAll(punishments, retrievedPunishments);
			return punishments;
		}, executor);
	}
}
