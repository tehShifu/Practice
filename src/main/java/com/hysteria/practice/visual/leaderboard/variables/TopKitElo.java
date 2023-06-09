package com.hysteria.practice.visual.leaderboard.variables;

import com.gmail.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;
import com.hysteria.practice.utilities.chat.CC;
import com.hysteria.practice.visual.leaderboard.Leaderboard;
import lombok.AllArgsConstructor;
import com.hysteria.practice.visual.leaderboard.entry.LeaderboardKitsEntry;

import java.util.List;

@AllArgsConstructor
public class TopKitElo implements PlaceholderReplacer {

    public String kit;
    public int pos;

    @Override
    public String update() {
        /*if (Leaderboard.getKitLeaderboards().get(kit) == null) return " ";
        else if (!Leaderboard.getKitLeaderboards().get(kit).stream().findFirst().isPresent()) return " ";

        return String.valueOf(Leaderboard.getKitLeaderboards().get(kit).stream().findFirst().get().getElo());*/

        try {
            List<LeaderboardKitsEntry> kitsEntryList = Leaderboard.getKitLeaderboards().get(kit);
            if(kit == null) {
                return " ";
            }

            if(kitsEntryList == null) {
                return " ";
            }

            if (kitsEntryList.get(pos) == null) {
                return " ";
            }
            int profile = kitsEntryList.get(pos).getElo();
            return CC.translate(String.valueOf(profile));
        } catch (IndexOutOfBoundsException e) {
            return " ";
        }
    }
}
