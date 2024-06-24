import csv
from tkinter import Tk, Text


def create_csv_link(player_name, team, season):
    csv_name = str(player_name) + "_" + str(team) + "_" + str(season) + ".csv"
    return csv_name


def team_link(player_csv, stats):
    stat_list = []
    items_appended = 0
    with open(player_csv) as player_data:
        reader = csv.reader(player_data)
        next(reader)
        for row in reader:
            if stats == "Points":
                stat_list.append(row[10])
                items_appended += 1
                if items_appended >= 9:
                    break
            elif stats == "Assists":
                stat_list.append(row[23])
                items_appended += 1
                if items_appended >= 9:
                    break
            elif stats == "Rebounds":
                stat_list.append(row[22])
                items_appended += 1
                if items_appended >= 9:
                    break
            elif stats == "Threes":
                stat_list.append(row[14])
                items_appended += 1
                if items_appended >= 9:
                    break

    return stat_list


def whole_team_analysis(player1, player2, player3, player4, player5, team, season):
    star_p = create_csv_link(player1, team, season)
    p2 = create_csv_link(player2, team, season)
    p3 = create_csv_link(player3, team, season)
    p4 = create_csv_link(player4, team, season)
    p5 = create_csv_link(player5, team, season)

    star = team_link(star_p, "Points")
    l2_assists = team_link(p2, "Assists")
    l2_rebounds = team_link(p2, "Rebounds")
    l3_assists = team_link(p3, "Assists")
    l3_rebounds = team_link(p3, "Rebounds")
    l4_assists = team_link(p3, "Assists")
    l4_rebounds = team_link(p4, "Rebounds")
    l5_assists = team_link(p5, "Assists")
    l5_rebounds = team_link(p5, "Rebounds")

    best_score = max(star)
    worst_score = min(star)

    best_day = 0
    worst_day = 0

    for i in range(len(star) - 1):
        if best_score in star:
            best_day = i
        elif worst_score in star:
            worst_day = i

    root = Tk()
    root.geometry("720x550")
    root.title("NBA STATISTICS")

    txt_output = Text(root, height=200, width=500)
    txt_output.pack(pady=30)

    txt_output.insert("end", "Star player (" + player1 + "): " + str(star) + "\n")
    txt_output.insert("end", "Player 2 Assists: (" + player2 + "): " + str(l2_assists) + " Worst and Best: "
                      + str(l2_assists[worst_day]) + "-" + str(l2_assists[best_day]) + "\n")
    txt_output.insert("end", "Player 3 Assists: (" + player3 + "): " + str(l3_assists) + " Worst and Best: "
                      + str(l3_assists[worst_day]) + "-" + str(l3_assists[best_day]) + "\n")
    txt_output.insert("end", "Player 4 Assists: (" + player4 + "): " + str(l4_assists) + " Worst and Best: "
                      + str(l4_assists[worst_day]) + "-" + str(l4_assists[best_day]) + "\n")
    txt_output.insert("end", "Player 5 Assists: (" + player5 + "): " + str(l5_assists) + " Worst and Best: "
                      + str(l5_assists[worst_day]) + "-" + str(l5_assists[best_day]) + "\n\n")
    txt_output.insert("end", "Star player (" + player1 + "): " + str(star) + "\n")
    txt_output.insert("end", "Player 2 Rebounds: (" + player2 + "): " + str(l2_rebounds) + " Worst and Best: "
                      + str(l2_rebounds[worst_day]) + "-" + str(l2_rebounds[best_day]) + "\n")
    txt_output.insert("end", "Player 3 Rebounds: (" + player3 + "): " + str(l3_rebounds) + " Worst and Best: "
                      + str(l3_rebounds[worst_day]) + "-" + str(l3_rebounds[best_day]) + "\n")
    txt_output.insert("end", "Player 4 Rebounds: (" + player4 + "): " + str(l4_rebounds) + " Worst and Best: "
                      + str(l4_rebounds[worst_day]) + "-" + str(l4_rebounds[best_day]) + "\n")
    txt_output.insert("end", "Player 5 Rebounds: (" + player5 + "): " + str(l5_rebounds) + " Worst and Best: "
                      + str(l5_rebounds[worst_day]) + "-" + str(l5_rebounds[best_day]) + "\n")

    root.mainloop()
