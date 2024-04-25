package statModule.api;

import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class PointPercentage {
    Players players = new Players();
    //Need this
    public Map<String, Object> getPointPercentage(String playerName, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        HashMap<String, Object> percentageMap = players.getPointsBySeason(playerName, season, teamName, opponentTeamName);
        List<String> pointPercentageMap = new ArrayList<>();
        Map<String, Object> percentageFromSeason = new HashMap<>();
        List<Integer> seasonPercentage = new ArrayList<>();
        List<Integer> playoffPercentage = new ArrayList<>();
        for (String allGames : percentageMap.keySet()) {
            if (allGames.contains("Seasonal Game: Home")) {
                int homeSeasonPoints = (int) percentageMap.get(allGames);
                pointPercentageMap.add(allGames);
                seasonPercentage.add(homeSeasonPoints);
            } else if (allGames.contains("Playoff Game: Home")) {
                int homePlayoffPoints = (int) percentageMap.get(allGames);
                pointPercentageMap.add(allGames);
                playoffPercentage.add(homePlayoffPoints);
            } else if (allGames.contains("Seasonal Game: Visiting")) {
                int visitingSeasonPoints = (int) percentageMap.get(allGames);
                pointPercentageMap.add(allGames);
                seasonPercentage.add(visitingSeasonPoints);
            } else if (allGames.contains("Playoff Game: Visiting")) {
                int visitingPlayoffPoints = (int) percentageMap.get(allGames);
                pointPercentageMap.add(allGames);
                seasonPercentage.add(visitingPlayoffPoints);
            }
        }

        OptionalDouble seasonalAvg = seasonPercentage
                .stream()
                .mapToDouble(a -> a)
                .average();

        List<Double> pointPercentage = new ArrayList<>();

        for (int i = 0; i < playoffPercentage.size(); i++) {
            int element = (int) playoffPercentage.get(i);
            double percentage = seasonalAvg.getAsDouble() / element;
            pointPercentage.add(percentage);
        }

        for (int i = 0; i < pointPercentage.size(); i++) {
            double doublePointPercentage = (Double) pointPercentage.get(i);
            String stringPointPercentageMap = pointPercentageMap.get(i);
            percentageFromSeason.put(stringPointPercentageMap, doublePointPercentage);
        }
        return percentageFromSeason;
    }
}
