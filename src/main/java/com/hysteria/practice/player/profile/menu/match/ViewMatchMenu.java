package com.hysteria.practice.player.profile.menu.match;

import com.google.common.collect.Maps;
import com.hysteria.practice.match.mongo.MatchInfo;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.ItemBuilder;
import com.hysteria.practice.utilities.menu.Button;
import com.hysteria.practice.utilities.menu.pagination.PaginatedMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class ViewMatchMenu extends PaginatedMenu {

    private final Profile profile;

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&c&lRecent Matches &7: &f" + profile.getName();
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        profile.getMatches().forEach(matchInfo -> buttons.put(buttons.size(), new MatchButton(matchInfo)));

        return buttons;
    }

    @RequiredArgsConstructor
    private static class MatchButton extends Button{

        private final MatchInfo matchInfo;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.PAPER)
                .name(matchInfo.getWinningParticipant() + " &cvs&f " + matchInfo.getLosingParticipant())
                .lore("&7" + matchInfo.getDate())
                .lore("")
                //.lore("&cDuration&7:&f " + matchInfo.getDuration())
                .lore("&fKit&7:&c " + matchInfo.getKit().getName())
                .lore(" &7✦ &fWinner&7:&c " + matchInfo.getWinningParticipant() + " &7(&a+" + matchInfo.getNewWinnerElo() + "&7)")
                .lore(" &7✦ &fLoser&7:&c " + matchInfo.getLosingParticipant() + " &7(&c-" + matchInfo.getNewLoserElo() + "&7)")
                .build();
        }
    }

}