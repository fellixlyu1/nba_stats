package statModule.api;

import java.util.TreeMap;

public class TravelPercentage {
    public TreeMap<String, Double> getTravelPercentage(TreeMap<String, Integer> percentageTravel) {
        TreeMap<String, Double> travelPercentage = new TreeMap<>();
        Integer previousPoints = null;
        for (String entry : percentageTravel.keySet()) {
            Integer currentPoints = (int) percentageTravel.get(entry);
            if (previousPoints != null) {
                double percentChange = (((double) currentPoints - previousPoints) / 100.0) * currentPoints;
                travelPercentage.put(entry, percentChange);
            } else {
                travelPercentage.put(entry, 0.0);
            }
            previousPoints = currentPoints;
        }
        return travelPercentage;
    }
}
