package statModule.api;

import java.lang.String;
import java.lang.Integer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Games {
    Api api = new Api();
    Teams team = new Teams();
    public HashMap<String, Object> getPlayersPoints(int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        int teamId = team.getTeamId(teamName);
        int oppTeamId = team.getTeamId(opponentTeamName);
        String h2h = teamId + "-" + oppTeamId;

        List<Map<String, Object>> gamesList = api.getGamesList(season, h2h);

        HashMap<String, Object> playerPointsList = new HashMap<>();

        for (Map<String, Object> gamesMap : gamesList) {
            double doubleGameId = (Double) gamesMap.get("id");
            int gameId = (int) doubleGameId;
            Map<String, Object> teams = (Map<String, Object>) gamesMap.get("teams");
            Map<String, Object> visitors = (Map<String, Object>) teams.get("visitors");
            Map<String, Object> home = (Map<String, Object>) teams.get("home");
            Map<String, Object> date = (Map<String, Object>) gamesMap.get("date");
            double doubleVisitorId = (Double) visitors.get("id");
            double doubleHomeId = (Double) home.get("id");
            int visitorId = (int) doubleVisitorId;
            int homeId = (int) doubleHomeId;
            String startDate = (String) date.get("start");

            List<Map<String, Object>> teamStats = (List<Map<String, Object>>) api.getPlayersStatistics(season, teamId, gameId);
            for (Map<String, Object> teamData : teamStats) {
                Map<String, Object> teamMap = (Map<String, Object>) teamData.get("team");
                double doublePlayersTeamId = (Double) teamMap.get("id");
                int playersTeamId = (int) doublePlayersTeamId;
                Map<String, Object> teamPlayerMap = (Map<String, Object>) teamData.get("player");
                String fName = (String) teamPlayerMap.get("firstname");
                String lName = (String) teamPlayerMap.get("lastname");
                double doublePlayersPoints = (Double) teamData.get("points");
                int playerPoints = (int) doublePlayersPoints;
                if (playersTeamId == visitorId) {
                    playerPointsList.put("Visiting [" + gameId + "] " + startDate + " " + fName + " " + lName + " " + teamName, playerPoints);
                } else if (playersTeamId == homeId) {
                    playerPointsList.put("Home [" + gameId + "] " + startDate + " " + fName + " " + lName + " " + teamName, playerPoints);
                }
            }

            List<Map<String, Object>> oppTeamStats = (List<Map<String, Object>>) api.getPlayersStatistics(season, oppTeamId, gameId);
            for (Map<String, Object> oppTeamData : oppTeamStats) {
                Map<String, Object> oppTeamMap = (Map<String, Object>) oppTeamData.get("team");
                double doubleOppPlayersTeamId = (Double) oppTeamMap.get("id");
                int oppPlayersTeamId = (int) doubleOppPlayersTeamId;
                Map<String, Object> oppTeamPlayerMap = (Map<String, Object>) oppTeamData.get("player");
                String oppFName = (String) oppTeamPlayerMap.get("firstname");
                String oppLName = (String) oppTeamPlayerMap.get("lastname");
                double doublePlayersPoints = (Double) oppTeamData.get("points");
                int playerPoints = (int) doublePlayersPoints;
                if (oppPlayersTeamId == visitorId) {
                    playerPointsList.put("Visiting [" + gameId + "] " + startDate + " " + oppFName + " " + oppLName + " " + opponentTeamName, playerPoints);
                } else if (oppPlayersTeamId == homeId) {
                    playerPointsList.put("Home [" + gameId + "] " + startDate + " " + oppFName + " " + oppLName + " " + opponentTeamName, playerPoints);
                }
            }
        }
        return playerPointsList;
    }
    // Need this
    public HashMap<String, Object> getSameGamePlayers(String playerName, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        Games games = new Games();
        HashMap<String, Object> playersMap = games.getPlayersPoints(season, teamName, opponentTeamName);
        PointPercentage percentage = new PointPercentage();
        Map<String, Object> pointsBySeason = percentage.getPointPercentage(playerName, season, teamName, opponentTeamName);
        List<Integer> gameIds = new ArrayList<>();
        HashMap<String, Object> playersPoints = new HashMap<>();
        for (String key : pointsBySeason.keySet()) {
            String[] parts = key.split(" ");
            int gameId = Integer.parseInt(parts[3].substring(1, parts[3].length() - 1));
            gameIds.add(gameId);
        }

        for (int i = 0; i < gameIds.size(); i++) {
            int intGameId = gameIds.get(i);
            String stringGameId = "[" + String.valueOf(intGameId) + "]";
            for (String players : playersMap.keySet()) {
                if (players.contains(stringGameId)) {
                    playersPoints.put(players, playersMap.get(players));
                }
            }
        }
        return playersPoints;
    }
}