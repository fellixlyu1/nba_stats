package statModule.api;

import java.util.TreeMap;

public class Statistics {
    public TreeMap<String, Object> getHomePoints(TreeMap<String, Object> statistics, String gameId, String date) {
        TreeMap<String, Object> points = new TreeMap<>();
        for (String sKey : statistics.keySet()) {
            if (sKey.contains(gameId) && sKey.contains("Points")) {
                points.put(gameId + " Home " + date + " Points", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Three Pointers")) {
                points.put(gameId + " Home " + date + " Threes", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Rebounds")) {
                points.put(gameId + " Home " + date + " Rebounds ", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Assists")) {
                points.put(gameId + " Home " + date + " Assists ", statistics.get(sKey));
            }
        }
        return points;
    }

    public TreeMap<String, Object> getVisitorPoints(TreeMap<String, Object> statistics, String gameId, String date) {
        TreeMap<String, Object> points = new TreeMap<>();
        for (String sKey : statistics.keySet()) {
            if (sKey.contains(gameId) && sKey.contains("Points")) {
                points.put(gameId + " Visitor " + date + " Points", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Three Pointers")) {
                points.put(gameId + " Visitor " + date + " Threes", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Rebounds")) {
                points.put(gameId + " Visitor " + date + " Rebounds ", statistics.get(sKey));
            } else if (sKey.contains(gameId) && sKey.contains("Assists")) {
                points.put(gameId + " Visitor " + date + " Assists ", statistics.get(sKey));
            }
        }
        return points;
    }
}
