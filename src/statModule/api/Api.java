package statModule.api;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class Api {

    public int getPlayersIds(String name, int teamId, int season) throws IOException, InterruptedException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest requestForId = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/players?name=" + name + "&season=" + season + "&team=" + teamId))
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

    public List<Map<String, Object>> getPlayersStatistics(int season, int teamId, int gameId) throws IOException, InterruptedException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/players/statistics?" + "season=" + season + "&team=" + teamId + "&game=" + gameId))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> playerInfo = gson.fromJson(responseBody, HashMap.class);

        List<Map<String, Object>> playersStatisticsList = (List<Map<String, Object>>) playerInfo.get("response");
        return playersStatisticsList;
    }

    public List<Map<String, Object>> getTeamsEndpoint(String teamName) throws IOException, InterruptedException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("x-rapidapi-key");
        String apiHost = properties.getProperty("x-rapidapi-host");


        String encodedPlayer = URLEncoder.encode(teamName, "UTF-8");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v2.nba.api-sports.io/teams?name=" + encodedPlayer))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Gson gson = new Gson();
        Map<String, Object> teamInfo = gson.fromJson(responseBody, HashMap.class);
        List<Map<String, Object>> teamList = (List<Map<String, Object>>) teamInfo.get("response");
        return teamList;
    }

    public List<Map<String, Object>>  getGamesList(int season, String h2h) throws IOException, InterruptedException {
        List<Integer> gamesIdList = new ArrayList<>();
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
        List<Map<String, Object>> gamesList = (List<Map<String, Object>> ) gameInfo.get("response");
        return gamesList;
    }
}
