package com.hysteria.practice.match.duel;

import com.hysteria.practice.Locale;
import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.MessageFormat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DuelRequest {

	@Getter private final UUID sender;
	@Getter private final UUID target;
	@Getter private final boolean party;
	@Getter @Setter private Kit kit;
	@Getter @Setter private Arena arena;
	private final long timeStamp = System.currentTimeMillis();
	@Getter @Setter private int rounds;

	DuelRequest(UUID sender, UUID target, boolean party) {
		this.sender = sender;
		this.target = target;
		this.party = party;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - this.timeStamp >= 30_000;
	}

	public void expire() {
		Player sender = Bukkit.getPlayer(this.sender);
		Player target = Bukkit.getPlayer(this.target);

		if (sender != null && target != null) {
			new MessageFormat(Locale.DUEL_SENDER_EXPIRED.format(Profile.get(sender.getUniqueId()).getLocale()))
					.add("{kit}", kit.getName())
					.add("{target}", target.getName())
					.send(sender);

			new MessageFormat(Locale.DUEL_TARGET_EXPIRED.format(Profile.get(target.getUniqueId()).getLocale()))
					.add("{kit}", kit.getName())
					.add("{sender}", sender.getName())
					.send(target);
		}
	}

}
