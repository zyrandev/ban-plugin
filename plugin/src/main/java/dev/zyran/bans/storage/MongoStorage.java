package dev.zyran.bans.storage;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import dev.zyran.api.punishment.Punishment;
import dev.zyran.api.user.User;
import dev.zyran.bans.punishments.SimplePunishment;
import dev.zyran.bans.user.SimpleUser;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;
public class MongoStorage {

	public static String PUNISHMENT_COLLECTION = "user_punishments";
	public static String USER_COLLECTION = "user_data";

	public static @NotNull MongoStorage of(String uri, String databaseName) {
		final MongoClient client = MongoClients.create(uri);
		return new MongoStorage(client.getDatabase(databaseName));
	}

	private final MongoDatabase database;

	public MongoStorage(final MongoDatabase database) { this.database = database; }

	public Punishment savePunishment(String type, Punishment punishment) {
		if (!( punishment instanceof SimplePunishment )) {
			throw new IllegalArgumentException("Punishment must be an instance of SimplePunishment");
		}

		ObjectId id = new ObjectId();

		Document document = new Document()
				                    .append("_id", id)
				                    .append("user", punishment.getUserId())
				                    .append("reason", punishment.getReason())
				                    .append("actor", punishment.getActorId())
				                    .append("actorName", punishment.getActorName())
				                    .append("duration", punishment.getDuration().toMillis())
				                    .append("_type", type);

		database.getCollection(PUNISHMENT_COLLECTION).insertOne(document);
		( (SimplePunishment) punishment ).setId(id.toHexString());
		return punishment;
	}

	public User saveUser(@NotNull User user) {
		Document document = new Document()
				                    .append("_id", user.getId().toString())
				                    .append("currentBanId", user.getCurrentBanId());

		database.getCollection(USER_COLLECTION)
				.insertOne(document);
		return user;
	}

	public User retrieveUser(@NotNull UUID userId) {
		Document document = database.getCollection(USER_COLLECTION)
				                    .find(new Document("_id", userId.toString()))
				                    .first();
		if (document == null) {
			return null;
		}

		String currentBanId = document.getString("currentBanId");
		return new SimpleUser(userId, currentBanId);
	}

	public MongoIterable<Punishment> retrievePunishments(Bson filter) {
		return database.getCollection(PUNISHMENT_COLLECTION)
				       .find(filter)
				       .map(document -> new SimplePunishment(
						       document.getObjectId("_id").toHexString(),
						       UUID.fromString(document.getString("user")),
						       document.getString("reason"),
						       UUID.fromString(document.getString("actor")),
						       document.getString("actorName"),
						       Duration.ofMillis(document.getLong("duration"))
				       ));
	}
}
