package statModule.api;

import java.io.IOException;
import java.util.HashMap;

public class Players {
    //this method is used by games.getPlayersPoints
    public HashMap<String, Object> getPointsByName(HashMap<String, Object> playersPoints, String playerName) throws IOException, InterruptedException {
        HashMap<String, Object> pointsByName = new HashMap<>();
        for (String player : playersPoints.keySet()) {
            if (player.contains(playerName)) {
                String key = player;
                int points = (int) playersPoints.get(player);
                pointsByName.put(key, points);
            }
        }
        return pointsByName;
    }
    //Need this
    public HashMap<String, Object> getPointsBySeason(HashMap<String, Object> pointsByName, int season) throws IOException, InterruptedException {

        int playoffs = season + 1;

        HashMap<String, Object> allGames = new HashMap<>();

        for (String date : pointsByName.keySet()) {
            if (date.contains(playoffs + "-04")) {
                int points = (int) pointsByName.get(date);
                String playoffString = "Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(playoffs + "-05")) {
                int points = (int) pointsByName.get(date);
                String playoffString = "Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(playoffs + "-06")) {
                int points = (int) pointsByName.get(date);
                String playoffString = "Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(season + "-10")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(season + "-11")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(season + "-12")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-01")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-02")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-03")) {
                int points = (int) pointsByName.get(date);
                String seasonString = "Seasonal Game: " + date;
                allGames.put(seasonString, points);
            }
        }
        return allGames;
    }
}
