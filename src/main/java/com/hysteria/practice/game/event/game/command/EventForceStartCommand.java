package com.hysteria.practice.game.event.game.command;

import com.hysteria.practice.game.event.game.EventGame;
import com.hysteria.practice.game.event.game.EventGameState;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EventForceStartCommand extends BaseCommand {

	@Command(name = "event.forcestart", permission = "cpractice.event.admin")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();

		if (EventGame.getActiveGame() != null) {
			EventGame game = EventGame.getActiveGame();

			if(game.getParticipants().size() < 2) {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(ChatColor.RED + "Event must have 2 or more players.");
				player.sendMessage(CC.CHAT_BAR);
				return;
			}

			if (game.getGameState() == EventGameState.WAITING_FOR_PLAYERS ||
					game.getGameState() == EventGameState.STARTING_EVENT) {
				game.getGameLogic().startEvent();
				game.getGameLogic().preStartRound();
				game.setGameState(EventGameState.STARTING_ROUND);
				game.getGameLogic().getGameLogicTask().setNextAction(4);
			} else {
				player.sendMessage(CC.CHAT_BAR);
				player.sendMessage(ChatColor.RED + "The event has already started.");
				player.sendMessage(CC.CHAT_BAR);
			}
		} else {
			player.sendMessage(CC.CHAT_BAR);
			player.sendMessage(ChatColor.RED + "There is no active event.");
			player.sendMessage(CC.CHAT_BAR);
		}
	}
}
