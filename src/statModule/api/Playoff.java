package statModule.api;

import java.util.TreeMap;

public class Playoff {
    public TreeMap<String, Object> getSeasonOrPlayoff(TreeMap<String, Object> selection, int season) {
        TreeMap<String, Object> seasonOrPlayoff = new TreeMap<>();
        int intPlayoffYear = season + 1;
        String playoffYear = String.valueOf(intPlayoffYear);
        for (String key : selection.keySet()) {
            String[] selectionData = key.split(" ");
            String gameId = selectionData[0];
            String position = selectionData[1];
            String date = selectionData[2];
            String stat = selectionData[3];
            String stringMonth = selectionData[2].substring(5, 6);
            int month = Integer.valueOf(stringMonth);
            if (key.contains(playoffYear + "-04") || key.contains(playoffYear + "-05") || key.contains(playoffYear + "-06")) {
                seasonOrPlayoff.put("[" + stat + "] " + gameId + " Playoff Game (" + position + ") " + date, selection.get(key));
            } else {
                seasonOrPlayoff.put("[" + stat + "] " + gameId + " Seasonal Game (" + position + ") " + date, selection.get(key));
            }
        }
        return seasonOrPlayoff;
    }
}
