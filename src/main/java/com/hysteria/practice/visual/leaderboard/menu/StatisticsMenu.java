package com.hysteria.practice.visual.leaderboard.menu;

import com.google.common.collect.Maps;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.meta.ProfileKitData;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.hysteria.practice.visual.scoreboard.BoardAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsMenu extends Menu {

    private final OfflinePlayer target;

    public StatisticsMenu(OfflinePlayer target) {
        this.target = target;
    }

    @Override
    public String getTitle(Player player) {
        return cPractice.get().getMainConfig().getString("STATS_MENU.TITLE")
            .replace("{player}", this.target.getName());
    }

    @Override
    public int getSize() {
        if (Kit.getKits().size() < 10) return 9;
        else if (Kit.getKits().size() < 19) return 18;
        else if (Kit.getKits().size() < 28) return 27;
        return 36;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int pos = 0;
        for (Kit kit : Kit.getKits()) {
            if (kit.getGameRules().isRanked()) buttons.put(pos++, new KitsItems(kit));
        }
        return buttons;
    }

    @AllArgsConstructor
    private class KitsItems extends Button {

        Kit kit;

        @Override
        public ItemStack getButtonItem(Player player) {
            Profile data = Profile.get(target.getUniqueId());
            ProfileKitData kitData = data.getKitData().get(kit);
            List<String> lore = new ArrayList<>();
            for (String s : cPractice.get().getMainConfig().getStringList("STATS_MENU.DESCRIPTION")) {
                lore.add(s.replace("{bars}", CC.MENU_BAR)
                        .replace("{elo}", String.valueOf(kitData.getElo()))
                        .replace("{wins}", String.valueOf(kitData.getWon()))
                        .replace("{division}", String.valueOf(BoardAdapter.getDivision(player)))
                        .replace("{losses}", String.valueOf(kitData.getLost())));
            }
            return new ItemBuilder(kit.getDisplayIcon())
                    .name(cPractice.get().getMainConfig().getString("STATS_MENU.ITEM_NAME").replace("{kit}", kit.getName()))
                    .lore(lore)
                    .build();
        }
    }
}
