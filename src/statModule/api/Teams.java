package statModule.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teams {
    public String getTeamId(String teamName) throws IOException, InterruptedException {
        Api api = new Api();
        List<Map<String, Object>> teamsMap = (List<Map<String, Object>>) api.getTeamsEndpoint(teamName);
        for (Map<String, Object> teamData : teamsMap) {
            double doubleTeamId = (Double) teamData.get("id");
            int teamId = (int) doubleTeamId;
            return String.valueOf(teamId);
        }
        return "0";
    }

    public String[] getTeams() {
        String[] teams = {
                "Atlanta Hawks",
                "Boston Celtics",
                "Brooklyn Nets",
                "Charlotte Hornets",
                "Chicago Bulls",
                "Cleveland Cavaliers",
                "Dallas Mavericks",
                "Denver Nuggets",
                "Detroit Pistons",
                "Golden State Warriors",
                "Houston Rockets",
                "Indiana Pacers",
                "Los Angeles Clippers",
                "Los Angeles Lakers",
                "Memphis Grizzlies",
                "Miami Heat",
                "Milwaukee Bucks",
                "Minnesota Timberwolves",
                "New Orleans Pelicans",
                "New York Knicks",
                "Oklahoma City Thunder",
                "Orlando Magic",
                "Philadelphia 76ers",
                "Phoenix Suns",
                "Portland Trail Blazers",
                "Sacramento Kings",
                "San Antonio Spurs",
                "Toronto Raptors",
                "Utah Jazz",
                "Washington Wizards"
        };
        return teams;
    }
}
