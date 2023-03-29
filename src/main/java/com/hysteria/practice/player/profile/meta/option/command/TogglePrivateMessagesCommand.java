package com.hysteria.practice.player.profile.meta.option.command;

import com.hysteria.practice.Locale;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.utilities.MessageFormat;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

public class TogglePrivateMessagesCommand extends BaseCommand {

    @Command(name = "togglepm", aliases = {"togglepms", "tpm", "tpms"})
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        Profile profile = Profile.get(player.getUniqueId());
        profile.getOptions().receivingNewConversations(!profile.getOptions().receivingNewConversations());
        profile.getConversations().expireAllConversations();

        if (profile.getOptions().receivingNewConversations()) {
            new MessageFormat(Locale.OPTIONS_PRIVATE_MESSAGES_ENABLED
                    .format(profile.getLocale()))
                    .send(player);
        } else {
            new MessageFormat(Locale.OPTIONS_PRIVATE_MESSAGES_DISABLED
                    .format(profile.getLocale()))
                    .send(player);
        }
    }
}
