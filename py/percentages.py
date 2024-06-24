import csv
from tkinter import Tk, Text


# The 'get_prediction' method uses the player's csv file, the opponent team's identifier, the statistic that the
# user wants to retrieve, the 'Home' or 'Away' position of the next game, and the player's team identifier. By
# analyzing the games based on the 'Home' or 'Away' position of the next game, the user will be able to find
# the projection.
def get_prediction(players_csv, opp_code, stats, position, team_code):
    game_ids = []
    percents = [0.0]
    statistic = []
    score_percentage = []
    prediction_range = []
    with open(players_csv) as player_data:
        reader = csv.reader(player_data, delimiter=",")
        next(reader)
        for row in reader:
            if stats == "Points":
                game_ids.append(str(str(row[7])))
                statistic.append(int(row[10]))
            elif stats == "Assists":
                game_ids.append(str(str(row[7])))
                statistic.append(int(row[23]))
            elif stats == "Rebounds":
                game_ids.append(str(str(row[7])))
                statistic.append(int(row[22]))
            elif stats == "Threes":
                game_ids.append(str(str(row[7])))
                statistic.append(int(row[14]))

    ordered_stats = statistic[::-1]
    game_id_list = game_ids[::-1]

    for score in range(len(ordered_stats) - 1):
        next_score = score + 1
        percentage = int(ordered_stats[score]) / int(ordered_stats[next_score]) if ordered_stats[next_score] != 0 else 0
        percents.append(percentage)

    for x in range(len(game_id_list)):
        if position == "Home":
            home = team_code + " vs. " + opp_code
            if game_id_list[x] == home:
                reg_percent = percents[x]
                score_percentage.append(reg_percent)
        elif position == "Away":
            visitor = team_code + " @ " + opp_code
            if game_id_list[x] == visitor:
                reg_percent = percents[x]
                score_percentage.append(reg_percent)

    for y in range(len(score_percentage)):
        percentages = score_percentage[y]
        predictions = statistic[0] * percentages
        prediction_range.append(predictions)

    return prediction_range


def get_prediction_plots(opp_code, players_csv, stats, position, team):
    pred_range = get_prediction(players_csv, opp_code, stats, position, team)

    root = Tk()
    root.geometry("720x250")
    root.title(opp_code + " " + position + " Prediction")

    txt_output = Text(root, height=200, width=100)
    txt_output.pack(pady=30)

    txt_output.insert("end", f"Percent Range: {pred_range}\n\n")

    root.mainloop()
