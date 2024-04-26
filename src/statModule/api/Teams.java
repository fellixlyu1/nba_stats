package statModule.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teams {
    Api api = new Api();
    public String getTeamId(String teamName) throws IOException, InterruptedException {
        List<Map<String, Object>> teamsMap = (List<Map<String, Object>>) api.getTeamsEndpoint(teamName);
        for (Map<String, Object> teamData: teamsMap) {
            double doubleTeamId = (Double) teamData.get("id");
            int teamId = (int) doubleTeamId;
            return String.valueOf(teamId);
        }
        return "0";
    }
}
