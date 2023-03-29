package com.hysteria.practice.match.duel.command;

import com.hysteria.practice.Locale;
import com.hysteria.practice.match.duel.DuelProcedure;
import com.hysteria.practice.match.duel.DuelRequest;
import com.hysteria.practice.match.duel.menu.DuelSelectKitMenu;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.ProfileState;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DuelCommand extends BaseCommand {

	@Command(name = "duel")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String[] args = commandArgs.getArgs();

		if (args.length == 0) {
			player.sendMessage(CC.RED + "Please usage: /duel (player)");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			new MessageFormat(Locale.DUEL_PLAYER_NOT_FOUND.format(Profile.get(player.getUniqueId()).getLocale()))
					.send(player);
			return;
		}

		if (player.getUniqueId().equals(target.getUniqueId())) {
			new MessageFormat(Locale.DUEL_CANNOT_YOURSELF.format(Profile.get(player.getUniqueId()).getLocale()))
					.send(player);
			return;
		}

		Profile playerProfile = Profile.get(player.getUniqueId());
		Profile targetProfile = Profile.get(target.getUniqueId());

		if (playerProfile.isBusy()) {
			new MessageFormat(Locale.DUEL_CANNOT_DUEL_RIGHT_NOW.format(playerProfile.getLocale()))
					.send(player);
			return;
		}

		if (targetProfile.isBusy()) {
			new MessageFormat(Locale.DUEL_IS_BUSY.format(playerProfile.getLocale()))
					.add("{player}", target.getName())
					.send(player);
			return;
		}

		if (!targetProfile.getOptions().receiveDuelRequests()) {
			new MessageFormat(Locale.DUEL_DONT_RECEIVE_DUELS.format(playerProfile.getLocale()))
					.send(player);
			return;
		}

		DuelRequest duelRequest = targetProfile.getDuelRequest(player);

		if (duelRequest != null) {
			if (!playerProfile.isDuelRequestExpired(duelRequest)) {
				new MessageFormat(Locale.DUEL_ALREADY_SENT.format(playerProfile.getLocale()))
						.send(player);
				return;
			}
		}

		if (playerProfile.getParty() != null && targetProfile.getParty() == null) {
			new MessageFormat(Locale.DUEL_CANNOT_SEND_PARTY_DUEL.format(playerProfile.getLocale()))
					.send(player);
			return;
		}

		if (playerProfile.getParty() == null && targetProfile.getParty() != null) {
			new MessageFormat(Locale.DUEL_REQUEST_NO_PARTY.format(playerProfile.getLocale()))
					.send(player);
			return;
		}

		if (playerProfile.getParty() != null && targetProfile.getParty() != null) {
			if (playerProfile.getParty().equals(targetProfile.getParty())) {
				new MessageFormat(Locale.DUEL_REQUEST_EQUALS_PARTY.format(playerProfile.getLocale()))
						.send(player);
				return;
			}
			for (Player member : playerProfile.getParty().getListOfPlayers()) {
				Profile profileMember = Profile.get(member.getUniqueId());
				if (profileMember.getState() != ProfileState.LOBBY) {
					new MessageFormat(Locale.DUEL_NO_PLAYERS_ON_LOBBY_PARTY.format(playerProfile.getLocale()))
							.send(player);
					return;
				}
			}
		}

		DuelProcedure procedure = new DuelProcedure(player, target, playerProfile.getParty() != null);
		playerProfile.setDuelProcedure(procedure);

		new DuelSelectKitMenu().openMenu(player);
	}
}
