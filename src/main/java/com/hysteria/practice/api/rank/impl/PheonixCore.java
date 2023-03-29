package com.hysteria.practice.api.rank.impl;

import dev.phoenix.phoenix.PhoenixAPI;
import dev.phoenix.phoenix.profile.Profile;
import com.hysteria.practice.api.rank.Rank;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PheonixCore implements Rank {

    @Override
    public String getName(UUID uuid) {
        Profile data = PhoenixAPI.INSTANCE.getProfile(uuid);
        return data == null ? "No Data" : data.getBestRank().getName();
    }

    @Override
    public String getPrefix(UUID uuid) {
        Profile data = PhoenixAPI.INSTANCE.getProfile(uuid);
        return data == null ? "No Data" : data.getBestRank().getPrefix();
    }

    @Override
    public String getSuffix(UUID uuid) {
        Profile data = PhoenixAPI.INSTANCE.getProfile(uuid);
        return data == null ? "No Data" : data.getBestRank().getSuffix();
    }

    @Override
    public String getColor(UUID uuid) {
        Profile data = PhoenixAPI.INSTANCE.getProfile(uuid);
        return data == null ? "No Data" : data.getBestRank().getColor() + data.getBestRank().getName();
    }

    @Override
    public String getRealName(Player player) {
        return null;
    }

    @Override
    public String getTag(Player player) {
        return PhoenixAPI.INSTANCE.getTag(player.getUniqueId()).getFormat();
    }
    
    @Override
    public int getWeight(UUID uuid) {
        Profile globalPlayer = PhoenixAPI.INSTANCE.getProfile(uuid);
        return globalPlayer == null ? 0 : globalPlayer.getPriority();
    }

}
