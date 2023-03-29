package com.hysteria.practice.player.clan;

import com.hysteria.practice.cPractice;
import com.hysteria.practice.match.participant.MatchGamePlayer;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.game.tournament.events.TournamentEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClanListener implements Listener {

    @EventHandler
    public void onClanWinTournament(TournamentEndEvent event){
        if(!event.isClan()) return;
        MatchGamePlayer leader = event.getWinner().getLeader();
        Player player = leader.getPlayer();
        Profile profile = Profile.get(player.getUniqueId());
        Clan clan = profile.getClan();
        clan.addPoints(cPractice.get().getMainConfig().getInteger("WINNING-POINTS-CLAN-TOURNAMENT"));
    }
}