from nba_api.stats.static import players
from nba_api.stats.endpoints import leaguegamefinder


def get_player_stats(player_name, year, opponent_code):
    prev_year = str(int(year) - 1)
    nba_player = players.find_players_by_full_name(str(player_name))
    nba_player_id_data = nba_player[0]
    nba_player_id = nba_player_id_data['id']

    finder = leaguegamefinder.LeagueGameFinder(player_id_nullable=nba_player_id)
    games = finder.get_data_frames()[0]
    all_games_map = games[games.MATCHUP.str.contains(opponent_code)]
    games_map = all_games_map[all_games_map.SEASON_ID.str[-4:] == prev_year]
    games_map.to_csv(player_name + "_" + opponent_code + "_" + year + ".csv")
