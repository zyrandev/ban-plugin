package dev.zyran.api.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
public interface User {

	UUID getId();

	default Player toBukkitPLayer() {
		return Bukkit.getPlayer(getId());
	}

	boolean isMuted();

	String getCurrentBanId();
}
