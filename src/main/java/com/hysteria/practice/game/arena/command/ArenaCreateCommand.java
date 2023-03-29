package com.hysteria.practice.game.arena.command;

import com.hysteria.practice.game.arena.Arena;
import com.hysteria.practice.game.arena.ArenaType;
import com.hysteria.practice.game.arena.impl.SharedArena;
import com.hysteria.practice.game.arena.impl.StandaloneArena;
import com.hysteria.practice.game.arena.selection.Selection;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ArenaCreateCommand extends BaseCommand {

	@Command(name = "arena.create", permission = "cpractice.arena.admin")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String[] args = commandArgs.getArgs();

		if (args.length < 2) {
			player.sendMessage(CC.translate("&cPlease usage: /arena create (name) (type)"));
			return;
		}

		String arenaName = args[0];
		ArenaType arenaType = Arrays.stream(ArenaType.values())
				.filter(val -> val.name().equalsIgnoreCase(args[1])).findFirst().orElse(null);

		if (arenaType == null) {
			player.sendMessage(CC.translate("&cPlease usage a valid ArenaType: SHARED, STANDALONE"));
			return;
		}

		if (Arena.getByName(arenaName) == null) {
			Selection selection = Selection.createOrGetSelection(player);

			if (selection.isFullObject()) {
				if (arenaType == ArenaType.SHARED) {
					Arena arena = new SharedArena(arenaName, selection.getPoint1(), selection.getPoint2());
					Arena.getArenas().add(arena);

					player.sendMessage(CC.translate("&cCreated new Shared arena &f\"" + arenaName + "\""));
				} else if (arenaType == ArenaType.STANDALONE) {
					Arena arena = new StandaloneArena(arenaName, selection.getPoint1(), selection.getPoint2());
					Arena.getArenas().add(arena);

					player.sendMessage(CC.translate("&cCreated new Standalone arena&f \"" + arenaName + "\""));
				}
			} else {
				player.sendMessage(CC.RED + "Your selection is incomplete.");
			}
		} else {
			player.sendMessage(CC.RED + "An arena with that name already exists.");
		}
	}
}