package dev.zyran.punishments.messages.placeholders;

import me.yushust.message.format.PlaceholderProvider;
import me.yushust.message.track.ContextRepository;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderProvider
		implements PlaceholderProvider<Player> {
	@Override
	public @Nullable Object replace(ContextRepository contextRepository, Player player, String path) {
		if (path.equals("name")) {
			return player.getName();
		}
		return player;
	}
}
