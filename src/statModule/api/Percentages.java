package statModule.api;

import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

public class Percentages {
    public TreeMap<String, Object> getPercentages(TreeMap<String, Object> gameMap) throws ParseException {
        TreeMap<String, Object> gamesMap = new TreeMap<>();

        double previousGame = 0.0;
        for (Map.Entry<String, Object> entry : gameMap.entrySet()) {
            Object value = entry.getValue();
            if (value == null || "null".equals(value)) { // Check for null or "null" string
                if (previousGame != 0.0) {
                    gamesMap.put(entry.getKey(), "0, Start");
                } else {
                    gamesMap.put(entry.getKey(), "0, Start");
                    previousGame = 0.0;
                }
            } else {
                int points = Integer.valueOf((String) value);
                double pointPercentage = Double.valueOf(points);
                double percent = pointPercentage / previousGame;
                previousGame = pointPercentage;
                gamesMap.put(entry.getKey(), value + ", " + percent);
            }
        }
        return gamesMap;
    }
}
