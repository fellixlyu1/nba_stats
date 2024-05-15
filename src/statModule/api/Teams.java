package statModule.api;

public class Teams {
    public String getTeamId(String teamName) {
        if (teamName == "Atlanta Hawks") {
            return "ATL";
        }
        else if (teamName == "Boston Celtics") {
            return "BOS";
        }
        else if (teamName == "Brooklyn Nets") {
            return "BKN";
        }
        else if (teamName == "Charlotte Hornets") {
            return "CHO";
        }
        else if (teamName == "Chicago Bulls") {
            return "CHI";
        }
        else if (teamName == "Cleveland Cavaliers") {
            return "CLE";
        }
        else if (teamName == "Dallas Mavericks") {
            return "DAL";
        }
        else if (teamName == "Denver Nuggets") {
            return "DEN";
        }
        else if (teamName == "Detroit Pistons") {
            return "DET";
        }
        else if (teamName == "Golden State Warriors") {
            return "GSW";
        }
        else if (teamName == "Houston Rockets") {
            return "HOU";
        }
        else if (teamName == "Indiana Pacers") {
            return "IND";
        }
        else if (teamName == "Los Angeles Clippers") {
            return "LAC";
        }
        else if (teamName == "Los Angeles Lakers") {
            return "LAL";
        }
        else if (teamName == "Memphis Grizzlies") {
            return "MEM";
        }
        else if (teamName == "Miami Heat") {
            return "MIA";
        }
        else if (teamName == "Milwaukee Bucks") {
            return "MIL";
        }
        else if (teamName == "Minnesota Timberwolves") {
            return "MIN";
        }
        else if (teamName == "New Orleans Pelicans") {
            return "NOP";
        }
        else if (teamName == "New York Knicks") {
            return "NYK";
        }
        else if (teamName == "Oklahoma City Thunder") {
            return "OKC";
        }
        else if (teamName == "Orlando Magic") {
            return "ORL";
        }
        else if (teamName == "Philadelphia 76ers") {
            return "PHI";
        }
        else if (teamName == "Phoenix Suns") {
            return "PHX";
        }
        else if (teamName == "Portland Trail Blazers") {
            return "POR";
        }
        else if (teamName == "Sacramento Kings") {
            return "SAC";
        }
        else if (teamName == "San Antonio Spurs") {
            return "SAS";
        }
        else if (teamName == "Toronto Raptors") {
            return "TOR";
        }
        else if (teamName == "Utah Jazz") {
            return "UTA";
        }
        else if (teamName == "Washington Wizards") {
            return "WAS";
        }
        return "No team with that name";
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
