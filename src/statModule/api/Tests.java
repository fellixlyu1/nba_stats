package statModule.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {
    public static void main(String[] args) throws IOException, InterruptedException {
        String playerName = "Jayson Tatum";
        int season = 2023;
        String teamName = "Boston Celtics";
        String opponentTeamName = "Miami Heat";
        Games games = new Games();
        HashMap<String, Object> playersMap = games.getSameGamePlayers(playerName, season, teamName, opponentTeamName);
        System.out.println(playersMap);
    }
}
