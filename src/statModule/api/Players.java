package statModule.api;

import java.io.IOException;
import java.util.HashMap;

public class Players {
    public HashMap<String, Object> getPointsBySeason(HashMap<String, Object> pointsByName, int season) throws IOException, InterruptedException {

        int playoffs = season + 1;

        HashMap<String, Object> allGames = new HashMap<>();

        for (String date : pointsByName.keySet()) {
            String[] data = date.split(" ");
            String gameId = data[1].substring(1, data[1].length() - 1);
            if (date.contains(playoffs + "-04")) {
                int points = (int) pointsByName.get(date);
                String playoffString = gameId + " Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(playoffs + "-05")) {
                int points = (int) pointsByName.get(date);
                String playoffString = gameId + " Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(playoffs + "-06")) {
                int points = (int) pointsByName.get(date);
                String playoffString = gameId + " Playoff Game: " + date;
                allGames.put(playoffString, points);
            } else if (date.contains(season + "-10")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(season + "-11")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(season + "-12")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-01")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-02")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            } else if (date.contains(playoffs + "-03")) {
                int points = (int) pointsByName.get(date);
                String seasonString = gameId + " Seasonal Game: " + date;
                allGames.put(seasonString, points);
            }
        }
        return allGames;
    }
}
