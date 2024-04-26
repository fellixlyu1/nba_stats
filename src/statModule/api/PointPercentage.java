package statModule.api;

import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class PointPercentage {
    //Need this
    public HashMap<String, Object> getPointPercentage(String playerName, int season, String teamName, String opponentTeamName) throws IOException, InterruptedException {
        Players players = new Players();
        HashMap<String, Object> percentageMap = players.getPointsBySeason(playerName, season, teamName, opponentTeamName);
        HashMap<String, Object> percentageFromSeason = new HashMap<>();
        HashMap<String, Object> homeSeasonPercentage = new HashMap<>();
        HashMap<String, Object> visitingSeasonPercentage = new HashMap<>();
        HashMap<String, Object> homePlayoffPercentage = new HashMap<>();
        HashMap<String, Object> visitingPlayoffPercentage = new HashMap<>();
        for (String allGames : percentageMap.keySet()) {
            if (allGames.contains("Seasonal Game: Home")) {
                int homeSeasonPoints = (int) percentageMap.get(allGames);
                homeSeasonPercentage.put(allGames, homeSeasonPoints);
            } else if (allGames.contains("Playoff Game: Home")) {
                int homePlayoffPoints = (int) percentageMap.get(allGames);
                homePlayoffPercentage.put(allGames, homePlayoffPoints);
            } else if (allGames.contains("Seasonal Game: Visiting")) {
                int visitingSeasonPoints = (int) percentageMap.get(allGames);
                visitingSeasonPercentage.put(allGames, visitingSeasonPoints);
            } else if (allGames.contains("Playoff Game: Visiting")) {
                int visitingPlayoffPoints = (int) percentageMap.get(allGames);
                visitingPlayoffPercentage.put(allGames, visitingPlayoffPoints);
            }
        }

        List<Integer> homeSeasonPerc = new ArrayList<>();
        List<Integer> visitingSeasonPerc = new ArrayList<>();

        for (String homeSeasonPoints : homeSeasonPercentage.keySet()) {
            int avg = (int) homeSeasonPercentage.get(homeSeasonPoints);
            homeSeasonPerc.add(avg);
        }

        for (String visitingSeasonPoints : visitingSeasonPercentage.keySet()) {
            int avg = (int) visitingSeasonPercentage.get(visitingSeasonPoints);
            visitingSeasonPerc.add(avg);
        }

        OptionalDouble homeSeasonalAvg = homeSeasonPerc
                .stream()
                .mapToDouble(a -> a)
                .average();

        OptionalDouble visitingSeasonalAvg = visitingSeasonPerc
                .stream()
                .mapToDouble(a -> a)
                .average();

        for (String homePlayoffKey : homePlayoffPercentage.keySet()) {
            if (homePlayoffKey.contains("Playoff Game: Home")) {
                String homeData = (String) homePlayoffKey;
                int homeElement = (int) homePlayoffPercentage.get(homePlayoffKey);
                double homePercentage = (homeSeasonalAvg.getAsDouble() / 100) * (homeElement - homeSeasonalAvg.getAsDouble());
                percentageFromSeason.put(homeData, homePercentage);
            }
        }

        for (String visitingPlayoffKey : visitingPlayoffPercentage.keySet()) {
            if (visitingPlayoffKey.contains("Playoff Game: Visiting")) {
                String visitingData = (String) visitingPlayoffKey;
                int visitingElement = (int) visitingPlayoffPercentage.get(visitingPlayoffKey);
                double visitingPercentage = (visitingSeasonalAvg.getAsDouble() / 100) * (visitingElement - visitingSeasonalAvg.getAsDouble());
                percentageFromSeason.put(visitingData, visitingPercentage);
            }
        }
        return percentageFromSeason;
    }
}
