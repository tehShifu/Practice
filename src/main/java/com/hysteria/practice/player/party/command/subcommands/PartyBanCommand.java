package com.hysteria.practice.player.party.command.subcommands;

import com.hysteria.practice.Locale;
import com.hysteria.practice.player.party.Party;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PartyBanCommand extends BaseCommand {

    @Command(name = "party.ban", aliases = {"p.ban"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.RED + "Please usage: /party ban (player)");
            return;
        }

        Profile profile = Profile.get(player.getUniqueId());
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            new MessageFormat(Locale.PLAYER_NOT_FOUND
                    .format(profile.getLocale()))
                    .send(player);
            return;
        }

        if (profile.getParty() == null) {
            player.sendMessage(CC.RED + "You do not have a party.");
            return;
        }

        if (!profile.getParty().getLeader().equals(player)) {
            player.sendMessage(CC.RED + "You are not the leader of your party.");
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(CC.RED + "You cannot ban yourself from your party.");
            return;
        }

        if (profile.getParty().containsPlayer(target.getUniqueId())) {
            profile.getParty().leave(target, true);
            return;
        }

        Party party = profile.getParty();
        Profile targetProfile = Profile.get(target.getUniqueId());
        party.getBannedPlayers().add(target.getUniqueId());

        party.getListOfPlayers().forEach(other ->
                other.sendMessage(targetProfile.getColor() + target.getName() + ChatColor.RED + " has been banned from your party."));
    }
}