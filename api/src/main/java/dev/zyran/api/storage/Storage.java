package dev.zyran.api.storage;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.user.User;

import java.io.Closeable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface Storage
		extends Closeable {

	void init();

	CompletableFuture<Void> saveUser(User user);

	CompletableFuture<Punishment> savePunishment(String type, Punishment punishment);

	CompletableFuture<User> retrieveUser(UUID userId);

	CompletableFuture<Punishment> retrievePunishment(String punishmentId);

	CompletableFuture<Set<Punishment>> retrieveUserPunishments(UUID userId);
}
