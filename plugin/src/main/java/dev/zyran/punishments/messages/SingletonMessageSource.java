package dev.zyran.punishments.messages;

import me.yushust.message.source.MessageSource;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SingletonMessageSource
		implements MessageSource {

	private final Configuration config;

	public SingletonMessageSource(final Configuration config) { this.config = config; }

	@Override
	public void load(final String language) {
		// Do nothing
	}

	@Override
	public Object get(@Nullable final String language, final String path) {
		Object o = config.get(path);
		if (o instanceof String) {
			return ChatColor.translateAlternateColorCodes('&', (String) o);
		}
		if (o instanceof List) {
			List<Object> list = new ArrayList<>();
			for (Object object : (List<?>) o) {
				if (object instanceof String) {
					object = ChatColor.translateAlternateColorCodes('&', (String) object);
				}
				list.add(object);
			}
			return list;
		}
		return o;
	}
}
