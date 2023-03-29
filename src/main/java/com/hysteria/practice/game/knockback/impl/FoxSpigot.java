package com.hysteria.practice.game.knockback.impl;

import com.hysteria.practice.game.knockback.KnockbackProfiler;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pt.foxspigot.jar.knockback.KnockbackModule;
import pt.foxspigot.jar.knockback.KnockbackProfile;

import java.lang.reflect.Field;

public class FoxSpigot implements KnockbackProfiler {

    @Override
    public void setKnockback(Player player, String kb) {
        KnockbackProfile knockbackProfile = KnockbackModule.getByName(kb);
        EntityHuman entityPlayer =  ((CraftPlayer) player).getHandle();
        Class entityclass = entityPlayer.getClass().getSuperclass();

        try {
            Field filed = entityclass.getDeclaredField("knockback");
            filed.setAccessible(true);
            filed.set(entityPlayer, knockbackProfile);
            filed.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
