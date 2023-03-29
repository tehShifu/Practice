package com.hysteria.practice.essentials.chat.impl;

import com.hysteria.practice.Locale;
import com.hysteria.practice.essentials.chat.impl.event.ChatAttemptEvent;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.utilities.TimeUtil;
import com.hysteria.practice.utilities.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		ChatAttempt chatAttempt = Chat.attemptChatMessage(event.getPlayer(), event.getMessage());
		ChatAttemptEvent chatAttemptEvent = new ChatAttemptEvent(event.getPlayer(), chatAttempt, event.getMessage());

		Bukkit.getServer().getPluginManager().callEvent(chatAttemptEvent);
		String message = event.getMessage();
		if (!chatAttemptEvent.isCancelled()) {
			switch (chatAttempt.getResponse()) {
				case ALLOWED: {
					event.setCancelled(true);
					for (Player receiver : Bukkit.getOnlinePlayers()) {
						final Profile profileReceiver = Profile.get(receiver.getUniqueId());

						if (profileReceiver.getOptions().publicChatEnabled()) {
								receiver.sendMessage(Chat.getChatFormat()
									.format(event.getPlayer(), receiver, message));
						}
					}
				}
				break;
				case MESSAGE_FILTERED: {
					event.setCancelled(true);
					event.getPlayer().sendMessage(CC.RED + "Please don't write this...");
				}
				break;
				case CHAT_MUTED: {
					event.setCancelled(true);
					event.getPlayer().sendMessage(CC.RED + "The public chat is currently muted.");
				}
				break;
				case CHAT_DELAYED: {
					event.setCancelled(true);
					Profile profile = Profile.get(event.getPlayer().getUniqueId());
					new MessageFormat(Locale.CHAT_DELAYED.format(profile.getLocale()))
						.add("{delay_time}", TimeUtil.millisToSeconds((long) chatAttempt.getValue()) + " seconds")
						.send(event.getPlayer());
				}
				break;
			}
		}
	}

}
