package com.hysteria.practice.essentials.command.management;

import com.hysteria.practice.cPractice;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BaseCommand {

	@Command(name = "setspawn", permission = "cpractice.command.setspawn")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();

		cPractice.get().getEssentials().setSpawnAndSave(player.getLocation());
		player.sendMessage(CC.GREEN + "You updated this world's spawn.");
	}
}
