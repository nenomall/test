package org.server.untitled;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class Prefixes {
    public final Map<String, String> prefixMap = new HashMap<>();
    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private Team team;

    private static final String TEAM_NAME = "prefix";
    private static final String PREFIX = "Player";

    public void setTab(Player player) {
        prefixMap.put("myserver.prefix.admin", "§с[ADMIN]");
        prefixMap.put("myserver.prefix.moder", "§3[MODER]");
        prefixMap.put("myserver.prefix.default", "§7");
        ScoreboardManager managerTab = Bukkit.getScoreboardManager();
        Scoreboard boardTab = managerTab.getNewScoreboard();
        String prefix = "Player";
        String PREFIX = "Player";
        Team team = boardTab.registerNewTeam("Player");
        for (Map.Entry<String, String> stringStringEntry : prefixMap.entrySet()) {
            if (player.hasPermission(stringStringEntry.getKey())) {
                prefix = stringStringEntry.getValue();
                PREFIX = stringStringEntry.getValue() + " ";

                break;
            }
        }

        player.setDisplayName(prefix + " " + player.getName());
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        team = scoreboard.registerNewTeam(TEAM_NAME);
        team.setPrefix(PREFIX);

        if (!team.getEntries().contains(player.getName())) {
            team.addEntry(player.getName());
        }

        setScoreboardForPlayers();
    }

    private void setScoreboardForPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }

}
