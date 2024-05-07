package statModule.api;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Api {

    public int getPlayersIds(String name, String teamId, int season) throws IOException, InterruptedException {

        String[] parts = name.split(" ");
        String lastName;
        if (parts[parts.length - 1].contains("-")) {
            lastName = parts[0];
        } else {
            lastName = parts[parts.length - 1];
        }

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        String encodedLastName = URLEncoder.encode(lastName, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest requestForId = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/players?name=" + encodedLastName + "&season=" + season + "&team=" + teamId))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();

        HttpResponse<String> responseForId = client.send(requestForId, HttpResponse.BodyHandlers.ofString());

        String responseForIdBody = responseForId.body();

        Gson gson = new Gson();
        Map<String, Object> playerInfo = gson.fromJson(responseForIdBody, HashMap.class);

        List<Map<String, Object>> playerResponse = (List<Map<String, Object>>) playerInfo.get("response");
        for (Map<String, Object> playersData : playerResponse) {
            double doublePlayerId = (Double) playersData.get("id");
            int playerId = (int) doublePlayerId;
            return playerId;
        }
        return 0;
    }

    public TreeMap<String, Object> getTeamLineup(String gameId, int season, String teamId) throws IOException, InterruptedException {

        TreeMap<String, Object> teamLineup = new TreeMap<String, Object>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/players/statistics?game=" + gameId + "&season=" + season + "&team=" + teamId))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> teamInfo = gson.fromJson(responseBody, HashMap.class);

        List<Map<String, Object>> teamLineupList = (List<Map<String, Object>>) teamInfo.get("response");

        for (Map<String, Object> teamLineupData : teamLineupList) {
            Map<String, Object> players = (Map<String, Object>) teamLineupData.get("player");
            String fName = (String) players.get("firstname");
            String lName = (String) players.get("lastname");
            String pos = (String) teamLineupData.get("pos");
            teamLineup.put("[" + gameId + "]" + fName + " " + lName, pos);
        }
        return teamLineup;
    }

    public TreeMap<String, Object> getPlayersStatistics(String gameId, int season, int playerId) throws IOException, InterruptedException {

        TreeMap<String, Object> playerStats = new TreeMap<String, Object>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/players/statistics?game=" + gameId + "&season=" + season + "&id=" + playerId))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> playerInfo = gson.fromJson(responseBody, HashMap.class);

        List<Map<String, Object>> playersStatisticsList = (List<Map<String, Object>>) playerInfo.get("response");

        for (Map<String, Object> teamData : playersStatisticsList) {
            Map<String, Object> teamMap = (Map<String, Object>) teamData.get("team");
            Map<String, Object> gameIdMap = (Map<String, Object>) teamData.get("game");
            double doublePlayersTeamId = (Double) teamMap.get("id");
            int playersTeamId = (int) doublePlayersTeamId;
            double doublePlayersPoints = (Double) teamData.get("points");
            int playerPoints = (int) doublePlayersPoints;
            double doublePlayersThreePointFieldGoals = (Double) teamData.get("tpm");
            int playersThreePointFieldGoals = (int) doublePlayersThreePointFieldGoals;
            double doubleAssists = (Double) teamData.get("assists");
            int assists = (int) doubleAssists;
            double doubleTotalReb = (Double) teamData.get("totReb");
            int rebounds = (int) doubleTotalReb;
            playerStats.put(gameId + " " + playersTeamId + " [Points]", playerPoints);
            playerStats.put(gameId + " " + playersTeamId + " [Three Pointers]", playersThreePointFieldGoals);
            playerStats.put(gameId + " " + playersTeamId + " [Assists]", assists);
            playerStats.put(gameId + " " + playersTeamId + " [Rebounds]", rebounds);
        }
        return playerStats;
    }

    public TreeMap<String, Object> getGamesList(int season, String h2h, String stringTeamId) throws IOException, InterruptedException {
        TreeMap<String, Object> gamesDataMap = new TreeMap<>();

        int teamId = Integer.valueOf(stringTeamId);

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/games?season=" + season + "&h2h=" + h2h))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> gameInfo = gson.fromJson(responseBody, HashMap.class);
        List<Map<String, Object>> gamesList = (List<Map<String, Object>>) gameInfo.get("response");

        for (Map<String, Object> gamesMap : gamesList) {
            double doubleGameId = (Double) gamesMap.get("id");
            int gameId = (int) doubleGameId;
            Map<String, Object> teams = (Map<String, Object>) gamesMap.get("teams");
            Map<String, Object> visitors = (Map<String, Object>) teams.get("visitors");
            Map<String, Object> home = (Map<String, Object>) teams.get("home");
            Map<String, Object> date = (Map<String, Object>) gamesMap.get("date");
            double doubleVisitorId = (Double) visitors.get("id");
            double doubleHomeId = (Double) home.get("id");
            int visitorId = (int) doubleVisitorId;
            int homeId = (int) doubleHomeId;
            String startDate = (String) date.get("start");
            String homeName = (String) home.get("name");
            String visitorName = (String) visitors.get("name");
            if (homeId == teamId) {
                gamesDataMap.put(gameId + " Home " + startDate, homeId);
            } else if (visitorId == teamId) {
                gamesDataMap.put(gameId + " Visitor " + startDate, visitorId);
            }
        }
        return gamesDataMap;
    }

    /*public int getTeamId(String teamName) throws IOException, InterruptedException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        String encodedTeamName = URLEncoder.encode(teamName, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/teams?name=" + encodedTeamName))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> gameInfo = gson.fromJson(responseBody, HashMap.class);
        List<Map<String, Object>> teamsList = (List<Map<String, Object>>) gameInfo.get("response");
        for (Map<String, Object> teamsData : teamsList) {
            double doubleTeamId = (Double) teamsData.get("id");
            int teamId = (int) doubleTeamId;
            return teamId;
        }
        return 0;
    }*/
}
