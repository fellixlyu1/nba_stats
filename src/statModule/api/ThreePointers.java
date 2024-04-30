package statModule.api;

import java.util.HashMap;

public class ThreePointers {
    public HashMap<String, Object> getThreePointAttempts(HashMap<String, Object> playersStatistics, HashMap<String, Object> gamesList, String playerName) {
        HashMap<String, Object> threePointers = new HashMap<>();
        for (String game : gamesList.keySet()) {
            if (game.contains("Home")) {
                int intHomeId = (int) gamesList.get(game);
                String homeId = String.valueOf(intHomeId);
                String[] gameData = game.split(" ");
                String gameId = gameData[0];
                String date = gameData[2];
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String homeTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Threes Attempted") && key.contains(gameId) && key.contains(homeId) && key.contains(playerName)) {
                        int homePointsAttempts = (int) playersStatistics.get(key);
                        threePointers.put("Home [" + gameId + "] " + homeTeamName + " " + fullName + " [" + date + "] " + homeId, homePointsAttempts);
                    }
                }
            } else if (game.contains("Visitor")) {
                int intVisitorId = (int) gamesList.get(game);
                String visitorId = String.valueOf(intVisitorId);
                String[] gameData = game.split(" ");
                String gameId = gameData[0];
                String date = gameData[2];
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String visitingTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Threes Attempted") && key.contains(gameId) && key.contains(visitorId) && key.contains(playerName)) {
                        int visitingPointsAttempts = (int) playersStatistics.get(key);
                        threePointers.put("Visiting [" + gameId + "] " + visitingTeamName + " " + fullName + " [" + date + "] " + visitorId, visitingPointsAttempts);
                    }
                }
            }
        }
        return threePointers;
    }

    public HashMap<String, Object> getThreePointers(HashMap<String, Object> playersStatistics, HashMap<String, Object> gamesList, String playerName) {
        HashMap<String, Object> threePointers = new HashMap<>();
        for (String game : gamesList.keySet()) {
            if (game.contains("Home")) {
                int intHomeId = (int) gamesList.get(game);
                String homeId = String.valueOf(intHomeId);
                String[] gameData = game.split(" ");
                String gameId = gameData[0];
                String date = gameData[2];
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String homeTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Three Pointers") && key.contains(gameId) && key.contains(homeId) && key.contains(playerName)) {
                        int homePoints = (int) playersStatistics.get(key);
                        threePointers.put("Home [" + gameId + "] " + homeTeamName + " " + fullName + " [" + date + "] " + homeId, homePoints);
                    }
                }
            } else if (game.contains("Visitor")) {
                int intVisitingId = (int) gamesList.get(game);
                String visitingId = String.valueOf(intVisitingId);
                String[] gameData = game.split(" ");
                String gameId = gameData[0];
                String date = gameData[2];
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String visitingTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Three Pointer") && key.contains(gameId) && key.contains(visitingId) && key.contains(playerName)) {
                        int visitingPoints = (int) playersStatistics.get(key);
                        threePointers.put("Visiting [" + gameId + "] " + visitingTeamName + " " + fullName + " [" + date + "] " + visitingId, visitingPoints);
                    }
                }
            }
        }
        return threePointers;
    }

    public HashMap<String, Object> threePointsStats(HashMap<String, Object> attempts, HashMap<String, Object> points) {
        HashMap<String, Object> threePointers = new HashMap<>();
        for (String attempt : attempts.keySet()) {
            int threeAttempts = (int) attempts.get(attempt);
            String[] data = attempt.split(" ");
            String gameId = data[1].substring(1, data[1].length() - 1);
            for (String point : points.keySet()) {
                int gameThrees = (int) points.get(point);
                String[] playerData = point.split(" ");
                String teamName = playerData[2] + " " + playerData[3];
                String fullName = playerData[4] + " " + playerData[5];
                String position = playerData[5];
                String date = playerData[6];
                if (point.contains(gameId) && point.contains("Home") && point.contains(position)) {
                    threePointers.put("Home [" + gameId + "] (" + teamName + ") " + fullName + " " + date + " " + gameThrees + " out of " + threeAttempts + " ", gameThrees);
                } else if (point.contains(gameId) && point.contains("Visiting") && point.contains(position)) {
                    threePointers.put("Visiting [" + gameId + "] (" + teamName + ") " + fullName + " " + date + " " + gameThrees + " out of " + threeAttempts + " ", gameThrees);
                }
            }
        }
        return threePointers;
    }
}
