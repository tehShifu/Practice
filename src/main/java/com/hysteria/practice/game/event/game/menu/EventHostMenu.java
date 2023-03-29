package com.hysteria.practice.game.event.game.menu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hysteria.practice.Locale;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.game.event.Event;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class EventHostMenu extends Menu {

	public EventHostMenu() {
		setPlaceholder(true);
	}

	@Override
	public String getTitle(Player player) {
		return cPractice.get().getEventsConfig().getString("EVENTS.TITLE");
	}

	@Override
	public int getSize() {
		return 5*9;
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = Maps.newHashMap();

		int pos = 10;

		for (int b = 0; b < Event.events.size(); b++) {
			Event event = Event.events.get(b);
			if (b <= 2) {
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			} else if (b == 3) {
				pos = pos + 9;
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			} else if (b <= 5) {
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			} else if (b == 6) {
				pos = pos + 9;
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			} else if (b <= 8) {
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			} else if (b <= 10) {
				pos = pos + 9;
				buttons.put(pos++, new SelectEventButton(event));
				pos++;
				pos++;
			}
		}

		return buttons;
	}

	@AllArgsConstructor
	private static class SelectEventButton extends Button {

		private final Event event;

		@Override
		public ItemStack getButtonItem(Player player) {
			List<String> lore = Lists.newArrayList();

			for (String s : cPractice.get().getEventsConfig().getStringList("EVENTS." + event.getName().toUpperCase() + ".DESCRIPTION")) {
				if (s.contains("{context}")) {
					if (event.canHost(player)) {
						for (String m : cPractice.get().getEventsConfig().getStringList("EVENTS.HOST_DESCRIPTION.WITH_PERMISSION")) {
							lore.add(m.replace("{slots}", String.valueOf(Profile.getHostSlots(player.getUniqueId()))));
						}
					} else {
						lore.addAll(cPractice.get().getEventsConfig().getStringList("EVENTS.HOST_DESCRIPTION.NO_PERMISSION"));
					}
					continue;
				}
				lore.add(s.replace("{bars}", CC.MENU_BAR));
			}

			return new ItemBuilder(event.getIcon().clone())
					.name(event.getDisplayName())
					.lore(lore)
					.build();
		}

		@Override
		public void clicked(Player player, ClickType clickType) {
			if (event.canHost(player)) {
				player.chat("/host " + event.getName());
			} else {
				new MessageFormat(Locale.EVENT_CANT_HOST.format(Profile.get(player.getUniqueId()).getLocale()))
						.send(player);
			}
		}

	}

}
