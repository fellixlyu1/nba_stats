package statModule.api;

import java.util.TreeMap;

public class Rebounds {
    public TreeMap<String, Object> getReboundsPercentages(TreeMap<String, Object> seasonalOrPlayoff) {
        TreeMap<String, Object> percentages = new TreeMap<>();
        Integer previousScore = null;
        for (String key : seasonalOrPlayoff.keySet()) {
            if (key.contains("Rebounds")) {
                Integer currentScore = (int) seasonalOrPlayoff.get(key);
                if (previousScore != null) {
                    double doublePercentage = (((double) currentScore / (double) previousScore) - 1) * 100.0;
                    String percentage = String.valueOf(doublePercentage);
                    percentages.put(key + " = " + seasonalOrPlayoff.get(key), "(" + percentage + "%)");
                } else {
                    percentages.put(key + " = " + seasonalOrPlayoff.get(key), "(Start)");
                }
                previousScore = currentScore;
            }
        }
        return percentages;
    }
}
