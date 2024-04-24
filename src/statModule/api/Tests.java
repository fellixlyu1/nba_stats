package statModule.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {
    public static void main(String[] args) throws IOException, InterruptedException {
        Games game = new Games();
        HashMap<String, Object> percentages = game.getPlayersPoints("Siakam", 2023, "Indiana Pacers", "Milwaukee Bucks");
        for (String key : percentages.keySet()) {
            System.out.println(key + ": " + percentages.get(key));
        }
    }
}
