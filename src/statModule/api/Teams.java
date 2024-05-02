package statModule.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teams {
    public String getTeamId(String teamName) throws IOException, InterruptedException {
        // Might need to change these according to api updates, use the commented out method "getTeamId" in Api class
        if (teamName == "Atlanta Hawks") {
            return "1";
        }
        else if (teamName == "Boston Celtics") {
            return "2";
        }
        else if (teamName == "Brooklyn Nets") {
            return "4";
        }
        else if (teamName == "Charlotte Hornets") {
            return "5";
        }
        else if (teamName == "Chicago Bulls") {
            return "6";
        }
        else if (teamName == "Cleveland Cavaliers") {
            return "7";
        }
        else if (teamName == "Dallas Mavericks") {
            return "8";
        }
        else if (teamName == "Denver Nuggets") {
            return "9";
        }
        else if (teamName == "Detroit Pistons") {
            return "10";
        }
        else if (teamName == "Golden State Warriors") {
            return "11";
        }
        else if (teamName == "Houston Rockets") {
            return "14";
        }
        else if (teamName == "Indiana Pacers") {
            return "15";
        }
        else if (teamName == "Los Angeles Clippers") {
            return "16";
        }
        else if (teamName == "Los Angeles Lakers") {
            return "17";
        }
        else if (teamName == "Memphis Grizzlies") {
            return "19";
        }
        else if (teamName == "Miami Heat") {
            return "20";
        }
        else if (teamName == "Milwaukee Bucks") {
            return "21";
        }
        else if (teamName == "Minnesota Timberwolves") {
            return "22";
        }
        else if (teamName == "New Orleans Pelicans") {
            return "23";
        }
        else if (teamName == "New York Knicks") {
            return "24";
        }
        else if (teamName == "Oklahoma City Thunder") {
            return "25";
        }
        else if (teamName == "Orlando Magic") {
            return "26";
        }
        else if (teamName == "Philadelphia 76ers") {
            return "27";
        }
        else if (teamName == "Phoenix Suns") {
            return "28";
        }
        else if (teamName == "Portland Trail Blazers") {
            return "29";
        }
        else if (teamName == "Sacramento Kings") {
            return "30";
        }
        else if (teamName == "San Antonio Spurs") {
            return "31";
        }
        else if (teamName == "Toronto Raptors") {
            return "38";
        }
        else if (teamName == "Utah Jazz") {
            return "40";
        }
        else if (teamName == "Washington Wizards") {
            return "41";
        }
        return "00";
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
