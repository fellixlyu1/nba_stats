package statModule.api;

import java.io.IOException;
import java.sql.Array;
import java.util.*;

public class PointPercentage {
    //Need players.getPointsBySeason method
    public HashMap<String, Object> getPointPercentage(HashMap<String, Object> percentageMap) throws IOException, InterruptedException {
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

        for (Object homeSeasonPoints : homeSeasonPercentage.values()) {
            int avg = (int) homeSeasonPoints;
            homeSeasonPerc.add(avg);
        }

        for (Object visitingSeasonPoints : visitingSeasonPercentage.values()) {
            int avg = (int) visitingSeasonPoints;
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

        double homePercentage = 0.0;
        if (homeSeasonalAvg.isPresent()) {
            for (String homePlayoffKey : homePlayoffPercentage.keySet()) {
                if (homePlayoffKey.contains("Playoff Game: Home")) {
                    int homeElement = (int) homePlayoffPercentage.get(homePlayoffKey);
                    homePercentage = (homeSeasonalAvg.getAsDouble() / 100) * (homeElement - homeSeasonalAvg.getAsDouble());
                    percentageFromSeason.put(homePlayoffKey, homePercentage);
                }
            }
        }

        double visitingPercentage = 0.0;
        if (visitingSeasonalAvg.isPresent()) {
            for (String visitingPlayoffKey : visitingPlayoffPercentage.keySet()) {
                if (visitingPlayoffKey.contains("Playoff Game: Visiting")) {
                    int visitingElement = (int) visitingPlayoffPercentage.get(visitingPlayoffKey);
                    visitingPercentage = (visitingSeasonalAvg.getAsDouble() / 100) * (visitingElement - visitingSeasonalAvg.getAsDouble());
                    percentageFromSeason.put(visitingPlayoffKey, visitingPercentage);
                }
            }
        }

        return percentageFromSeason;
    }
}