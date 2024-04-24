package statModule.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teams {
    public int getTeamId(String teamName) throws IOException, InterruptedException {
        Api api = new Api();
        List<Map<String, Object>> teamsMap = (List<Map<String, Object>>) api.getTeamsEndpoint(teamName);
        for (Map<String, Object> teamData: teamsMap) {
            double doubleTeamId = (Double) teamData.get("id");
            int teamId = (int) doubleTeamId;
            return teamId;
        }
        return 0;
    }
}
