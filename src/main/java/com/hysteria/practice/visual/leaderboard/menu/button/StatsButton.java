package com.hysteria.practice.visual.leaderboard.menu.button;

import com.google.common.collect.Lists;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.utilities.elo.EloUtil;
import com.hysteria.practice.utilities.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

@AllArgsConstructor
public class StatsButton extends Button {

    public Player target;

    @Override
    public ItemStack getButtonItem(Player player){
        List<String> lore = Lists.newArrayList();
        Profile profile = Profile.get(target.getUniqueId());

        for (String s : cPractice.get().getLeaderboardConfig().getStringList("INVENTORY.PERSONAL_STATS.DESCRIPTION")) {
            if (s.contains("{kits}")) {
                Kit.getKits().stream().filter(kit -> kit.getGameRules().isRanked()).forEach(kit ->
                        lore.add(cPractice.get().getLeaderboardConfig().getString("INVENTORY.PERSONAL_STATS.KITS_FORMAT")
                                .replace("{kit}", kit.getName())
                                .replace("{color}", profile.getColor())
                                .replace("{data}", String.valueOf(profile.getKitData().get(kit).getElo()))));
                continue;
            }
            lore.add(s
                    .replace("{bars}", CC.MENU_BAR)
                    .replace("{elo}", String.valueOf(EloUtil.getGlobalElo(profile))));
        }

        ItemStack item = new ItemBuilder(Material.SKULL_ITEM)
                .durability(3)
                .name(cPractice.get().getLeaderboardConfig().getString("INVENTORY.PERSONAL_STATS.TITLE")
                        .replace("{color}", profile.getColor())
                        .replace("{name}", target.getName()))
                .lore(lore)
                .build();

        SkullMeta itemMeta = (SkullMeta)item.getItemMeta();
        itemMeta.setOwner(target.getName());
        item.setItemMeta(itemMeta);
        return item;
    }
}
