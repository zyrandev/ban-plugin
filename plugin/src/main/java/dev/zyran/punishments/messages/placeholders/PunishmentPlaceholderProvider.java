package dev.zyran.punishments.messages.placeholders;

import dev.zyran.api.punishment.Punishment;
import dev.zyran.punishments.util.DurationFormatter;
import me.yushust.message.format.PlaceholderProvider;
import me.yushust.message.track.ContextRepository;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;

public class PunishmentPlaceholderProvider
		implements PlaceholderProvider<Punishment> {

	@Override
	public @Nullable Object replace(ContextRepository handle, Punishment punishment, String path) {
		switch (path) {
			case "id":
				return punishment.getId();
			case "user_id":
				return punishment.getUserId();
			case "staff_name":
				return punishment.getActorName();
			case "staff_id":
				return punishment.getActorId();
			case "reason":
				return punishment.getReason();
			case "until":
				Duration duration = punishment.getDuration();
				if (duration == null) {
					return "Permanent";
				}
				Instant now = Instant.now();
				Instant expiration = Instant.ofEpochMilli(punishment.getCreatedAt())
						                     .plus(punishment.getDuration());
				Duration until = Duration.between(now, expiration);
				return DurationFormatter.LONG.format(until);
		}
		return path;
	}
}
