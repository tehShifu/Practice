package com.hysteria.practice.game.ffa.command.subcommands;
/* 
   Made by cpractice Development Team
   Created on 27.11.2021
*/

import net.audidevelopment.cspigot.knockback.KnockbackModule;
import com.hysteria.practice.cPractice;
import com.hysteria.practice.game.knockback.Knockback;
import com.hysteria.practice.player.profile.Profile;
import com.hysteria.practice.player.profile.ProfileState;
import com.hysteria.practice.player.profile.hotbar.Hotbar;
import com.hysteria.practice.utilities.PlayerUtil;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.api.command.BaseCommand;
import com.hysteria.practice.api.command.Command;
import com.hysteria.practice.api.command.CommandArgs;
import me.scalebound.pspigot.KnockbackProfile;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotConfig;

public class FFALeaveCommand extends BaseCommand {

    @Command(name="ffa.leave")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        Profile profile = Profile.get(player.getUniqueId());

        if (profile.getState() != ProfileState.FFA) {
            player.sendMessage(CC.translate("&cYou can only use this command in FFA Arena."));
            return;
        }

        Knockback.getKnockbackProfiler().setKnockback(player.getPlayer(), "default");

        PlayerUtil.reset(player);
        profile.setState(ProfileState.LOBBY);
        Hotbar.giveHotbarItems(player);
        cPractice.get().getEssentials().teleportToSpawn(player);
    }

    private void broadcastMessage(String message) {
        for (Profile ffaPlayer : cPractice.get().getFfaManager().getFFAPlayers()) {
            ffaPlayer.msg(message);
        }
    }
}
