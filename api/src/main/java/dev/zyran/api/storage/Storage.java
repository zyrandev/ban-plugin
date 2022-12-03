package dev.zyran.api.storage;

import dev.zyran.api.user.UserHistoryRecord;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
public interface Storage {

	CompletableFuture<Set<UserHistoryRecord>> retrieveUserHistory(UUID userId);
}
