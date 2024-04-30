package statModule.api;

import java.lang.String;
import java.lang.Integer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Games {
    public HashMap<String, Object> getPlayersPoints(HashMap<String, Object> playerStatistics, HashMap<String, Object> gamesList) throws IOException, InterruptedException {

        Teams team = new Teams();
        PointPercentage percentage = new PointPercentage();
        HashMap<String, Object> playerPointsList = new HashMap<>();

        for (String gamesMap : gamesList.keySet()) {
            int intGameId = (int) gamesList.get(gamesMap);
            String gameId = String.valueOf(intGameId);
            if (gamesMap.contains("Home")) {
                String[] teamParts = gamesMap.split(" ");
                String homeId = teamParts[0];
                String date = teamParts[2];
                for (String playerData : playerStatistics.keySet()) {
                    if (playerData.contains(gameId) && playerData.contains(homeId) && playerData.contains("Points")) {
                        int homePoints = (int) playerStatistics.get(playerData);
                        String[] homeData = playerData.split(" ");
                        String fullName = homeData[1] + " " + homeData[2];
                        String homeTeamName = homeData[3] + " " + homeData[4];
                        playerPointsList.put("Home [" + gameId + "] (" + homeTeamName + ") " + fullName + " [" + date + "] ", homePoints);
                    }
                }
            } else if (gamesMap.contains("Visitor")) {
                String[] teamParts = gamesMap.split(" ");
                String visitorId = teamParts[0];
                String date = teamParts[2];
                for (String playerData : playerStatistics.keySet()) {
                    if (playerData.contains(gameId) && playerData.contains(visitorId) && playerData.contains("Points")) {
                        int visitingPoints = (int) playerStatistics.get(playerData);
                        String[] visitingData = playerData.split(" ");
                        String fullName = visitingData[1] + " " + visitingData[2];
                        String visitingTeamName = visitingData[3] + " " + visitingData[4];
                        playerPointsList.put("Visiting [" + gameId + "] (" + visitingTeamName + ") " + fullName + " [" + date + "]", visitingPoints);
                    }
                }
            }
        }
        return playerPointsList;
    }
}