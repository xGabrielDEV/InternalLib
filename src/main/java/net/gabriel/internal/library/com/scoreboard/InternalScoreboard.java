package net.gabriel.internal.library.com.scoreboard;

import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class InternalScoreboard {
    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;
    private List<String> lines;

    // Constructor with only two parameter, it will generate only the essentials things
    public InternalScoreboard(String title, List<String> lines, Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.lines = lines;
        this.player = player;
        this.objective = scoreboard.registerNewObjective("test", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        buildScoreboard();
    }

    // This method builds the scoreboard, and returns it.
    public Scoreboard buildScoreboard() {
        int i = lines.size();
        int i2 = 0;
        for (String line : lines) {
            if (!Strings.isNullOrEmpty(line)) {
                objective.getScore(line).setScore(i);
                --i;
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append(ChatColor.RESET.toString());
                for (int ex = 0; ex < i2; ex++) {
                    builder.append(ChatColor.RESET.toString());
                }
                objective.getScore(line).setScore(i);
                --i;
            }
        }
        return scoreboard;
    }

    // To set scoreboard to not update, just set it to null or don't call the method
    public void setUpdatingDelay(Plugin plugin, long delayInTicks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                showTo();
            }
        }.runTaskTimer(plugin, 20L, delayInTicks);
    }

    // On this method you can show the scoreboard to the player selected in the constructor.
    public void showTo() {
        player.setScoreboard(this.scoreboard);
    }

    public Scoreboard getScoreboard() {
        buildScoreboard(); return scoreboard;
    }

}
