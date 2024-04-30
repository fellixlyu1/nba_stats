package statModule.api;

import java.util.HashMap;

public class ThreePointers {
    public HashMap<String, Object> getThreePointAttempts(HashMap<String, Object> playersStatistics, HashMap<String, Object> gamesList, String playerName) {
        HashMap<String, Object> threePointers = new HashMap<>();
        for (String game : gamesList.keySet()) {
            int intGameId = (int) gamesList.get(game);
            String gameId = String.valueOf(intGameId);
            String[] gameData = game.split(" ");
            String date = gameData[2];
            if (game.contains("Home")) {
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String homeId = keyParts[0];
                    String homeTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Threes Attempted") && key.contains(gameId) && key.contains(homeId) && key.contains(playerName)) {
                        int homePointsAttempts = (int) playersStatistics.get(key);
                        threePointers.put("Home [" + gameId + "] " + homeTeamName + " " + fullName + " [" + date + "] ", homePointsAttempts);
                    }
                }
            } else if (game.contains("Visitor")) {
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String visitingId = keyParts[0];
                    String visitingTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Threes Attempted") && key.contains(gameId) && key.contains(visitingId) && key.contains(playerName)) {
                        int visitingPointsAttempts = (int) playersStatistics.get(key);
                        threePointers.put("Visiting [" + gameId + "] " + visitingTeamName + " " + fullName + " [" + date + "]", visitingPointsAttempts);
                    }
                }
            }
        }
        return threePointers;
    }

    public HashMap<String, Object> getThreePointers(HashMap<String, Object> playersStatistics, HashMap<String, Object> gamesList, String playerName) {
        HashMap<String, Object> threePointers = new HashMap<>();
        for (String game : gamesList.keySet()) {
            int intGameId = (int) gamesList.get(game);
            String gameId = String.valueOf(intGameId);
            String[] gameData = game.split(" ");
            String date = gameData[2];
            if (game.contains("Home")) {
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String homeId = keyParts[0];
                    String homeTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Three Pointers") && key.contains(gameId) && key.contains(homeId) && key.contains(playerName)) {
                        int homePoints = (int) playersStatistics.get(key);
                        threePointers.put("Home [" + gameId + "] " + homeTeamName + " " + fullName + " [" + date + "] ", homePoints);
                    }
                }
            } else if (game.contains("Visitor")) {
                for (String key : playersStatistics.keySet()) {
                    String[] keyParts = key.split(" ");
                    String visitingId = keyParts[0];
                    String visitingTeamName = keyParts[3] + " " + keyParts[4];
                    String fullName = keyParts[1] + " " + keyParts[2];
                    if (key.contains("Three Pointer") && key.contains(gameId) && key.contains(visitingId) && key.contains(playerName)) {
                        int visitingPoints = (int) playersStatistics.get(key);
                        threePointers.put("Visiting [" + gameId + "] " + visitingTeamName + " " + fullName + " [" + date + "]", visitingPoints);
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
            String gameId = data[1].substring(1, data.length - 1);
            for (String point : points.keySet()) {
                int gameThrees = (int) points.get(point);
                String[] playerData = point.split(" ");
                String teamName = playerData[2] + " " + playerData[3];
                String fullName = playerData[4] + " " + playerData[5];
                String date = playerData[6];
                if (point.contains(gameId) && point.contains("Home")) {
                    threePointers.put("Home [" + gameId + "] (" + teamName + ") " + fullName + " " + date + " " + gameThrees + " out of " + threeAttempts + " ", gameThrees);
                } else if (point.contains(gameId) && point.contains("Visiting")) {
                    threePointers.put("Visiting [" + gameId + "] (" + teamName + ") " + fullName + " " + date + " " + gameThrees + " out of " + threeAttempts + " ", gameThrees);
                }
            }
        }
        return threePointers;
    }
}
