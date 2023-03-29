package com.hysteria.practice.player.clan.commands.subcommands;

import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

public class ClanChatCommand extends BaseCommand {

    @Command(name = "clan.chat", aliases = {"clan.c"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        /*Player player = commandArgs.getPlayer();
        Profile profile = Profile.get(player.getUniqueId());

        if (profile.getClan() == null) {
            new MessageFormat(Locale.CLAN_DO_NOT_HAVE
                    .format(profile.getLocale()))
                    .send(player);
            return;
        }

        String[] args = commandArgs.getArgs();
        StringBuilder message = new StringBuilder();

        for (String msg : args) message.append(msg).append(" ");

        profile.getClan().chat(player, message.toString());
    }*/
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.RED + "Please insert a valid message.");
            return;
        }

        Profile profile = Profile.get(player.getUniqueId());
        StringBuilder builder = new StringBuilder();

        if (profile.getClan() == null) {
            player.sendMessage(CC.RED + "You are not in a clan.");
        }

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        if (profile.getClan() != null) {
            profile.getClan().sendChat(player, builder.toString());
        }
    }
}
