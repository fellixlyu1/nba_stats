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
        String teamId = team.getTeamId(teamName);
        String oppTeamId = team.getTeamId(opponentTeamName);
        String h2h = teamId + "-" + oppTeamId;

        HashMap<String, Object> gamesList = (HashMap<String, Object>) api.getGamesList(season, h2h);

        HashMap<String, Object> playerPointsList = new HashMap<>();

        for (String gamesMap : gamesList.keySet()) {
            if (gamesMap.contains("Home")) {
                String homeId = String.valueOf(gamesList.get(gamesMap));
                String[] parts = gamesMap.split(" ");
                String homeGameId = parts[0];
                String date = parts[parts.length - 1];
                HashMap<String, Object> teamStats = (HashMap<String, Object>) api.getPlayersStatistics(season, homeId, homeGameId);
                for (String teamData : teamStats.keySet()) {
                    if (teamData.contains(homeGameId)) {
                        int homePoints = (int) teamStats.get(teamData);
                        String[] teamParts = teamData.split(" ");
                        String homeFName = teamParts[1];
                        String homeLName = teamParts[2];
                        String homeTeamName = teamParts[teamParts.length - 1] + " " + teamParts[teamParts.length - 2];
                        playerPointsList.put("Home [" + homeGameId + "] (" + homeTeamName + ") " + homeFName + " " + homeLName + " [" + date + "]", homePoints);
                    }
                }
            } else if (gamesMap.contains("Visitor")) {
                String visitingId = String.valueOf(gamesList.get(gamesMap));
                String[] parts = gamesMap.split(" ");
                String visitingGameId = parts[0];
                String date = parts[parts.length - 1];
                HashMap<String, Object> teamStats = (HashMap<String, Object>) api.getPlayersStatistics(season, visitingId, visitingGameId);
                for (String teamData : teamStats.keySet()) {
                    if (teamData.contains(visitingGameId)) {
                        int visitingPoints = (int) teamStats.get(teamData);
                        String[] teamParts = teamData.split(" ");
                        String visitingFName = teamParts[1];
                        String visitingLName = teamParts[2];
                        String visitingTeamName = teamParts[teamParts.length - 2] + " " + teamParts[teamParts.length - 1];
                        playerPointsList.put("Visiting [" + visitingGameId + "] (" + visitingTeamName + ") " + visitingFName + " " + visitingLName + " [" + date + "]", visitingPoints);
                    }
                }
            }
        }
        return playerPointsList;
    }

    public HashMap<String, Object> getSameGamePlayers(String playerName, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        Players players = new Players();
        HashMap<String, Object> playersMap = getPlayersPoints(season, teamName, opponentTeamName);
        PointPercentage percentage = new PointPercentage();
        HashMap<String, Object> pointsBySeason = players.getPointsBySeason(playerName, season, teamName, opponentTeamName);
        List<Integer> gameIds = new ArrayList<>();
        HashMap<String, Object> playersPoints = new HashMap<>();
        for (String key : pointsBySeason.keySet()) {
            String[] parts = key.split(" ");
            int gameId = Integer.parseInt(parts[3].substring(1, parts[3].length() - 1));
            gameIds.add(gameId);
        }

        for (int i = 0; i < gameIds.size(); i++) {
            int intGameId = gameIds.get(i);
            String stringGameId = String.valueOf(intGameId);
            for (String player : playersMap.keySet()) {
                if (player.contains(stringGameId)) {
                    playersPoints.put(player, playersMap.get(player));
                }
            }
        }
        return playersPoints;
    }

    public HashMap<String, Object> getOpponentStats(String playerName, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        HashMap<String, Object> opponents = new HashMap<>();
        HashMap<String, Object> allPlayers = getSameGamePlayers(playerName, season, teamName, opponentTeamName);
        for (String key : allPlayers.keySet()) {
            if (key.contains(opponentTeamName)) {
                opponents.put(key, allPlayers.get(key));
            }
        }
        return opponents;
    }
}