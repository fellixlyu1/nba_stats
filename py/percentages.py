import csv
from tkinter import Tk, Text

from py.cross_algorithm import get_cross


def get_percent(player_csv, stats):
    gid_mapping = {}
    percentage = set()
    game_id = []
    statistic = []
    percentage.add(0)
    with open(player_csv) as player_data:
        reader = csv.reader(player_data, delimiter=",")
        next(reader)
        for row in reader:
            if stats == "Points":
                game_id.append(row[5])
                statistic.append(row[10])
            elif stats == "Points":
                game_id.append(row[5])
                statistic.append(row[10])
            elif stats == "Assists":
                game_id.append(row[5])
                statistic.append(row[23])
            elif stats == "Assists":
                game_id.append(row[5])
                statistic.append(row[23])
            elif stats == "Rebounds":
                game_id.append(row[5])
                statistic.append(row[22])
            elif stats == "Rebounds":
                game_id.append(row[5])
                statistic.append(row[22])
            elif stats == "Threes":
                game_id.append(row[5])
                statistic.append(row[14])
            elif stats == "Threes":
                game_id.append(row[5])
                statistic.append(row[14])

    box_score = statistic[::-1]
    order = game_id[::-1]

    for player_score in range(len(box_score) - 1):
        if int(box_score[player_score]) != 0:
            percent = int(box_score[player_score + 1]) / int(box_score[player_score])
            percentage.add(percent)

    for game, percent in zip(order, percentage):
        gid_mapping[game] = percent

    return gid_mapping


def get_prediction(opp_csv1, opp_csv2, stats, position):
    percent_map = get_percent(opp_csv2, stats)
    predictive_range = []
    data = []
    predictive_map = {}
    with open(opp_csv1) as player_data:
        table = csv.reader(player_data, delimiter=",")
        next(table)
        for cell in table:
            match_up = cell[7]
            if stats == "Points" and position == "Home" and "vs." in match_up:
                data.append(int(cell[10]))
            elif stats == "Points" and position == "Away" and "@" in match_up:
                data.append(int(cell[10]))
            elif stats == "Assists" and position == "Home" and "vs." in match_up:
                data.append(int(cell[23]))
            elif stats == "Assists" and position == "Away" and "@" in match_up:
                data.append(int(cell[23]))
            elif stats == "Rebounds" and position == "Home" and "vs." in match_up:
                data.append(int(cell[22]))
            elif stats == "Rebounds" and position == "Away" and "@" in match_up:
                data.append(int(cell[22]))
            elif stats == "Threes" and position == "Home" and "vs." in match_up:
                data.append(int(cell[14]))
            elif stats == "Threes" and position == "Away" and "@" in match_up:
                data.append(int(cell[14]))

    prediction = data[::-1]

    percent_list = list(percent_map.values())

    for j in range(len(percent_list)):
        new2 = int(prediction[len(prediction) - 1]) * float(percent_list[j])
        predictive_range.append(new2)

    p_range = []

    sorted_range2 = sorted(predictive_range)

    for p in sorted_range2:
        p_range.append(p)

    sorted_range = sorted(p_range)

    total_sum = 0.0
    amount = 0

    for s in range(len(sorted_range)):
        total_sum += sorted_range[s] + sorted_range[s+1]
        amount = s

    avg = total_sum / amount if amount != 0 else 0

    predictive_map[avg] = sorted_range

    return predictive_map


def get_prediction_plots(opp_csv1, opp_csv2, players_csv, stats, position):
    percent_map = get_percent(opp_csv2, stats)
    predictive_range = []
    data = []
    score = []
    predictive_map = {}
    score_map = {}
    with open(opp_csv1) as player_data:
        table = csv.reader(player_data, delimiter=",")
        next(table)
        for cell in table:
            match_up = cell[7]
            if stats == "Points" and position == "Home" and "vs." in match_up:
                data.append(int(cell[10]))
                score.append(int(cell[9]))
            elif stats == "Points" and position == "Away" and "@" in match_up:
                data.append(int(cell[10]))
                score.append(int(cell[9]))
            elif stats == "Assists" and position == "Home" and "vs." in match_up:
                data.append(int(cell[23]))
                score.append(int(cell[9]))
            elif stats == "Assists" and position == "Away" and "@" in match_up:
                data.append(int(cell[23]))
                score.append(int(cell[9]))
            elif stats == "Rebounds" and position == "Home" and "vs." in match_up:
                data.append(int(cell[22]))
                score.append(int(cell[9]))
            elif stats == "Rebounds" and position == "Away" and "@" in match_up:
                data.append(int(cell[22]))
                score.append(int(cell[9]))
            elif stats == "Threes" and position == "Home" and "vs." in match_up:
                data.append(int(cell[14]))
                score.append(int(cell[9]))
            elif stats == "Threes" and position == "Away" and "@" in match_up:
                data.append(int(cell[14]))
                score.append(int(cell[9]))

    prediction = data[::-1]

    percent_list = list(percent_map.values())

    for i in range(len(percent_list)):
        new1 = int(prediction[len(prediction) - 1]) * float(percent_list[i])
        predictive_range.append(new1)

    size = 0
    total_min = 0

    for g in range(len(score)):
        total_min += score[g]
        size = g

    avg_min_per_game = total_min / (size + 1) if size != 0 else 0

    pts_amount = 0
    total_pts = 0

    for h in range(len(data)):
        total_pts += data[h]
        pts_amount = h

    avg_pts_per_game = total_pts / (pts_amount + 1) if pts_amount != 0 else 0

    score_map[avg_min_per_game] = avg_pts_per_game

    sorted_range1 = sorted(predictive_range)

    total_sum1 = 0.0
    amount1 = 0

    for s in range(len(sorted_range1)):
        total_sum1 += sorted_range1[s]
        amount1 = s

    avg1 = total_sum1 / amount1 if amount1 != 0 else 0  # Handling division by zero

    predictive_map[avg1] = sorted_range1

    cross_report = get_cross(players_csv, stats)

    root = Tk()
    root.geometry("720x250")
    root.title(opp_csv1 + " " + position + " Prediction")

    txt_output = Text(root, height=200, width=100)
    txt_output.pack(pady=30)

    for key, item in predictive_map.items():
        txt_output.insert("end", f"Percent Range: {opp_csv1}\n\n")
        txt_output.insert("end", f"Avg: {key}\nPredictive Range: {item}\n\n")

    for s_key, s_item in score_map.items():
        txt_output.insert("end", f"Average Score of games between: {opp_csv1}\n\n")
        txt_output.insert("end", f"Min: {s_key}\nScore: {s_item}\n\n")

    for c_key, c_item in cross_report.items():
        txt_output.insert("end", f"Seasonal Average: {players_csv}\n\n")
        txt_output.insert("end", f"Min: {c_key}\nAverage: {c_item}\n\n")

    root.mainloop()
