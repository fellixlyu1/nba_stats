package statModule.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Games {
    public HashMap<String, Object> getPlayersPoints(String player, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {

        Api api = new Api();

        Teams team = new Teams();

        int teamId = team.getTeamId(teamName);
        int oppTeamId = team.getTeamId(opponentTeamName);
        String h2h = teamId + "-" + oppTeamId;
        int playerId = api.getPlayersIds(player, teamId, season);

        List<Map<String, Object>> gamesList = api.getGamesList(season, h2h);

        HashMap<String, Object> playerPointsList = new HashMap<>();

        for (Map<String, Object> gamesMap : gamesList) {
            Map<String, Object> dateTimeMap = (Map<String, Object>) gamesMap.get("date");
            String dateTimeStart = (String) dateTimeMap.get("start");
            double doubleGameId = (Double) gamesMap.get("id");
            int gameId = (int) doubleGameId;
            String stringGameId = String.valueOf(gameId);
            Map<String, Object> teamMap = (Map<String, Object>) gamesMap.get("teams");
            Map<String, Object> visitors = (Map<String, Object>) teamMap.get("visitors");
            Map<String, Object> home = (Map<String, Object>) teamMap.get("home");
            double doubleVisitorsPositionId = (Double) visitors.get("id");
            double doubleHomePositionId = (Double) home.get("id");
            int visitorsPositionId = (int) doubleVisitorsPositionId;
            int homePositionId = (int) doubleHomePositionId;

            List<Map<String, Object>> oppPlayersStatistics = api.getPlayersStatistics(season, oppTeamId, gameId);
            List<String> lastNameList = new ArrayList<>();

            List<Map<String, Object>> playersStatistics = api.getPlayersStatistics(season, teamId, gameId);
            for (Map<String, Object> oppPlayerData : oppPlayersStatistics) {
                Map<String, Object> oppPlayerDataMap = (Map<String, Object>) oppPlayerData.get("player");
                String oppPlayerLastName = (String) oppPlayerDataMap.get("lastname");
                lastNameList.add(oppPlayerLastName);
                for (Map<String, Object> playerData : playersStatistics) {
                    Map<String, Object> matchedPlayerData = (Map<String, Object>) playerData.get("player");
                    double doubleMatchedPlayerId = (Double) matchedPlayerData.get("id");
                    int matchedPlayerId = (int) doubleMatchedPlayerId;
                    if (playerId == matchedPlayerId) {
                        double doubleGamePoints = (Double) playerData.get("points");
                        int gamePoints = (int) doubleGamePoints;
                        if (teamId == visitorsPositionId) {
                            playerPointsList.put("Visiting (" + dateTimeStart + " " + gameId + ") [Points]", gamePoints);
                            playerPointsList.put("Visiting (" + dateTimeStart + " " + gameId + ") [Players]", lastNameList);
                        } else if (teamId == homePositionId) {
                            playerPointsList.put("Home (" + dateTimeStart + " " + gameId + ") [Points]", gamePoints);
                            playerPointsList.put("Home (" + dateTimeStart + " " + gameId + ") [Players]", lastNameList);
                        }
                    }
                }
            }
        }
        return playerPointsList;
    }
}