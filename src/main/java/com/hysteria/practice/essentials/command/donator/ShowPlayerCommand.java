package com.hysteria.practice.essentials.command.donator;

import com.hysteria.practice.Locale;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShowPlayerCommand extends BaseCommand {

	@Command(name = "showplayer", permission = "cpractice.command.showplayer")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String[] args = commandArgs.getArgs();

		if (args.length == 0) {
			player.sendMessage(CC.RED + "Please usage: /showplayer (player) or /showplayer (player) (target)");
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (args.length == 1) {
			if (target == null) {
				new MessageFormat(Locale.PLAYER_NOT_FOUND
						.format(Profile.get(player.getUniqueId()).getLocale()))
						.send(player);
				return;
			}
			player.showPlayer(target);
			player.sendMessage(ChatColor.WHITE + "Showing " + ChatColor.RED + target.getName());
		}
		else {
			Player target2 = Bukkit.getPlayer(args[1]);
			if (target2 == null) {
				new MessageFormat(Locale.PLAYER_NOT_FOUND
						.format(Profile.get(player.getUniqueId()).getLocale()))
						.send(player);
				return;
			}
			target.showPlayer(target2);
			player.sendMessage(CC.translate("&fShowing &c" + target2.getName() + " &fto &c" + target.getName()));
		}
	}
}
