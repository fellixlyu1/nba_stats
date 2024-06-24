from nba_api.stats.static import players
from nba_api.stats.endpoints import leaguegamefinder


# The 'get_player_stats' method uses three arguments for its parameters: 'player_name', 'year', and 'team_code'.
# By using the 'find_players_by_full_name' method from the 'players' library, I created the 'nba_player' variable
# to return a map containing the data of the specified player. The 'leaguegamefinder' api uses the player's id and
# provides a data frame of the player's game log and the statistics of each game. The dataframe is then converted
# into a csv file.
def get_player_stats(player_name, year, team_code):
    prev_year = str(int(year) - 1)
    nba_player = players.find_players_by_full_name(str(player_name))
    nba_player_id_data = nba_player[0]
    nba_player_id = nba_player_id_data['id']

    finder = leaguegamefinder.LeagueGameFinder(player_id_nullable=nba_player_id)
    games = finder.get_data_frames()[0]
    all_games_map = games[games.MATCHUP.str.contains(team_code)]
    games_map = all_games_map[all_games_map.SEASON_ID.str[-4:] == prev_year]
    games_map.to_csv(player_name + "_" + team_code + "_" + year + ".csv")
