package statModule.api;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Api {

    public String getPlayersIds(String firstname, String lastname) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("X-RapidAPI-key");
        String apiHost = properties.getProperty("X-RapidAPI-host");

        String encodedFirstName = URLEncoder.encode(firstname, StandardCharsets.UTF_8);
        String encodedLastName = URLEncoder.encode(lastname, StandardCharsets.UTF_8);

        OkHttpClient client = new OkHttpClient();

        String requestBody = "{ \"pageSize\": 100, \"firstname\": \"" + encodedFirstName + "\", \"lastname\": \"" + encodedLastName + "\" }";

        Request requestForId = new Request.Builder()
                .url("https://basketball-head.p.rapidapi.com/players/search")
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();

        try (Response responseForId = client.newCall(requestForId).execute()) {
            if (!responseForId.isSuccessful()) throw new IOException("Unexpected code " + responseForId);

            String responseForIdBody = Objects.requireNonNull(responseForId.body()).string();

            Gson gson = new Gson();
            Map<String, Object> playerInfo = gson.fromJson(responseForIdBody, HashMap.class);

            List<Map<String, Object>> body = (List<Map<String, Object>>) playerInfo.get("body");
            for (Map<String, Object> values : body) {
                String playerId = (String) values.get("playerId");
                return playerId;
            }
        }
        return "Couldn't find Player Id";
    }

    public TreeMap<String, Object> getGames(String playerId, String season) throws IOException {
        TreeMap<String, Object> games = new TreeMap<>();

        Properties properties = new Properties();
        properties.load(new FileInputStream("api_keys.properties"));
        String apiKey = properties.getProperty("X-RapidAPI-key");
        String apiHost = properties.getProperty("X-RapidAPI-host");

        String encodedId = URLEncoder.encode(playerId, StandardCharsets.UTF_8);
        String encodedSeason = URLEncoder.encode(season, StandardCharsets.UTF_8);

        OkHttpClient client = new OkHttpClient();

        String requestUrl = "{ \"pageSize\": 100 }";

        Request request = new Request.Builder()
                .url("https://basketball-head.p.rapidapi.com/players/" + encodedId + "/games/" + encodedSeason)
                .post(RequestBody.create(MediaType.parse("application/json"), requestUrl))
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = Objects.requireNonNull(response.body()).string();

            Gson gson = new Gson();
            Map<String, Object> playerInfo = gson.fromJson(responseBody, HashMap.class);

            List<Map<String, Object>> body = (List<Map<String, Object>>) playerInfo.get("body");
            for (Map<String, Object> values : body) {
                String gameId = (String) values.get("gameId");
                String points = (String) values.get("points");
                String threePointers = (String) values.get("threePointerMade");
                String assists = (String) values.get("assists");
                String totalRebounds = (String) values.get("totalRebounds");
                String team = (String) values.get("teamPlayed");
                String opponent = (String) values.get("opponent");
                games.put("[Points] " + gameId + " " + team + " " + opponent, points);
                games.put("[Three Pointers] " + gameId + " " + team + " " + opponent, threePointers);
                games.put("[Assists] " + gameId + " " + team + " " + opponent, assists);
                games.put("[Rebounds] " + gameId + " " + team + " " + opponent, totalRebounds);
            }
        }
        return games;
    }
}
