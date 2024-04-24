package statModule.api;

import java.io.IOException;
import java.util.*;

public class Module {
    public String percentages(String player, int season, String teamName, String opponentTeamName, String position) throws IOException, InterruptedException {
        Games playersPoints = new Games();
        HashMap<String, Object> playerStats = playersPoints.getPlayersPoints(player, season, teamName, opponentTeamName);
        String playoffDate = String.valueOf(season + 1);
        String seasonalDate = String.valueOf(season);
        Map<String, Object> playoffAverages = new HashMap<>();
        Map<String, Object> seasonalAverages = new HashMap<>();
        for (int i = 0; i < playerStats.size(); i++){
            String order = (String) String.valueOf(i);
            for (String key : playerStats.keySet()) {
                if (key.contains(position)) {
                    if (key.contains(playoffDate + "-04")) { // playoff points
                        if (key.contains("Points")) {
                            int aprilPoints = (int) playerStats.get(key);
                            playoffAverages.put("04-" + playoffDate + " " + i, aprilPoints);
                        }
                    } else if (key.contains(playoffDate + "-05")) {
                        if (key.contains("Points")) {
                            int junePoints = (int) playerStats.get(key);
                            playoffAverages.put("05-" + playoffDate + " " + i, junePoints);
                        }
                    } else if (key.contains(playoffDate + "-06")) {
                        if (key.contains("Points")) {
                            int julyPoints = (int) playerStats.get(key);
                            playoffAverages.put("06-" + playoffDate + " " + i, julyPoints);
                        }
                    } else if (key.contains(seasonalDate + "-10")) {
                        if (key.contains("Points")) {
                            int octoberPoints = (int) playerStats.get(key);
                            seasonalAverages.put("10-" + seasonalDate + " " + i, octoberPoints);
                        }
                    } else if (key.contains(seasonalDate + "-11")) {
                        if (key.contains("Points")) {
                            int novemberPoints = (int) playerStats.get(key);
                            seasonalAverages.put("11-" + seasonalDate + " " + i, novemberPoints);
                        }
                    } else if (key.contains(seasonalDate + "-12")) {
                        if (key.contains("Points")) {
                            int decemberPoints = (int) playerStats.get(key);
                            seasonalAverages.put("12-" + seasonalDate + " " + i, decemberPoints);
                        }
                    } else if (key.contains(playoffDate + "-01")) {
                        if (key.contains("Points")) {
                            int januaryPoints = (int) playerStats.get(key);
                            seasonalAverages.put("01-" + playoffDate + " " + i, januaryPoints);
                        }
                    } else if (key.contains(playoffDate + "-02")) {
                        if (key.contains("Points")) {
                            int februaryPoints = (int) playerStats.get(key);
                            seasonalAverages.put("02-" + playoffDate + " " + i, februaryPoints);
                        }
                    } else if (key.contains(playoffDate + "-03")) {
                        if (key.contains("Points")) {
                            int marchPoints = (int) playerStats.get(key);
                            seasonalAverages.put("03-" + playoffDate + " " + i, marchPoints);
                        }
                    } else if (key.contains(playoffDate + "-04")) {
                        if (key.contains("Points")) {
                            int aprilPoints = (int) playerStats.get(key);
                            seasonalAverages.put("04-" + playoffDate + " " + i, aprilPoints);
                        }
                    }
                }
            }
        }

        for (String key : playerStats.keySet()) {
            if (key.contains(position)) {
                if (key.contains("Players")) {
                    List<String> players = (List<String>) playerStats.get(key);
                    for (int i = 0; i < players.size(); i++) {
                        String oppPlayerNames = (String) players.get(i);
                    }
                }
            }
        }

        String stringPlayoffAverage = playoffAverages.toString();
        String stringSeasonalAverage = seasonalAverages.toString();

        return "Playoff: " + stringPlayoffAverage + "\n"
                + "Seasonal: " + stringSeasonalAverage;
    }
}
