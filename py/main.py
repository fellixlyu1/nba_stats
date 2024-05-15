import os
import threading
from tkinter import *
from nba_statistical_visualizations import create_scatterplot
from league_game_finder import get_player_stats
from py.percentages import get_prediction_plots


def create_button():
    os.system("league_game_finder.py")
    create_window()


def analyze_button():
    analyze_window()


def report_button():
    report_window()


def run():
    threading.Thread(target=create_scatterplot).start()
    threading.Thread(target=get_player_stats).start()


def create_window():
    cw = Tk()
    cw.resizable(width=True, height=True)
    cw.title("NBA STATISTICAL ")
    cw.geometry("550x550")

    player_name_label = Label(cw, text="Player's Name")
    player_name_label.grid(row=0)
    player_name_entry = Entry(cw)
    player_name_entry.grid(row=0, column=1)

    season_label = Label(cw, text="Season/Year")
    season_label.grid(row=1)
    season_entry = Entry(cw)
    season_entry.grid(row=1, column=1)

    opp_code_label = Label(cw, text="Opponent Team Code")
    opp_code_label.grid(row=2)
    opp_code_entry = Entry(cw)
    opp_code_entry.grid(row=2, column=1)

    csv_button = Button(cw, text="Create CSV", width=10, height=2,
                        command=lambda: get_player_stats(player_name_entry.get(),
                                                         str(season_entry.get()),
                                                         opp_code_entry.get()))
    csv_button.grid(row=3, column=1)
    cw.mainloop()


def analyze_window():
    aw = Tk()
    aw.resizable(width=True, height=True)
    aw.title("NBA STATISTICAL ")
    aw.geometry("550x550")

    player_name_label = Label(aw, text="Player's Name")
    player_name_label.grid(row=0)
    player_name_entry = Entry(aw)
    player_name_entry.grid(row=0, column=1)

    season_label = Label(aw, text="Year")
    season_label.grid(row=1)
    season_entry = Entry(aw)
    season_entry.grid(row=1, column=1)

    opp_a_code_label = Label(aw, text="Opponent Team A Code")
    opp_a_code_label.grid(row=2)
    opp_a_code_entry = Entry(aw)
    opp_a_code_entry.grid(row=2, column=1)

    opp_b_code_label = Label(aw, text="Opponent Team B Code")
    opp_b_code_label.grid(row=3)
    opp_b_code_entry = Entry(aw)
    opp_b_code_entry.grid(row=3, column=1)

    stat_label = Label(aw, text="P/A/R/T")
    stat_label.grid(row=4)
    stat_label_entry = Entry(aw)
    stat_label_entry.grid(row=4, column=1)

    date_label = Label(aw, text="Date (YYYY-(M)M-(D)D)")
    date_label.grid(row=5)
    date_label_entry = Entry(aw)
    date_label_entry.grid(row=5, column=1)

    position_label = Label(aw, text="Home or Away")
    position_label.grid(row=6)
    position_label_entry = Entry(aw)
    position_label_entry.grid(row=6, column=1)

    stat_button = Button(aw, text="Analyze", width=10, height=2, command=lambda: create_scatterplot(
        str(str(player_name_entry.get()) + "_" + str(opp_a_code_entry.get()) + "_" + str(season_entry.get()) + ".csv"),
        str(str(player_name_entry.get()) + "_" + str(opp_b_code_entry.get()) + "_" + str(season_entry.get()) + ".csv"),
        str(stat_label_entry.get()), str(date_label_entry.get()), str(position_label_entry.get())))
    stat_button.grid(row=7, column=1)


def report_window():
    rw = Tk()
    rw.resizable(width=True, height=True)
    rw.title("NBA STATISTICAL ")
    rw.geometry("550x550")

    player_name_label = Label(rw, text="Player's Name")
    player_name_label.grid(row=0)
    player_name_entry = Entry(rw)
    player_name_entry.grid(row=0, column=1)

    season_label = Label(rw, text="Year")
    season_label.grid(row=1)
    season_entry = Entry(rw)
    season_entry.grid(row=1, column=1)

    players_code_label = Label(rw, text="Player's Team Code")
    players_code_label.grid(row=2)
    players_code_entry = Entry(rw)
    players_code_entry.grid(row=2, column=1)

    opp_a_code_label = Label(rw, text="Opponent Team A Code")
    opp_a_code_label.grid(row=3)
    opp_a_code_entry = Entry(rw)
    opp_a_code_entry.grid(row=3, column=1)

    opp_b_code_label = Label(rw, text="Opponent Team B Code")
    opp_b_code_label.grid(row=4)
    opp_b_code_entry = Entry(rw)
    opp_b_code_entry.grid(row=4, column=1)

    stat_label = Label(rw, text="P/A/R/T")
    stat_label.grid(row=5)
    stat_label_entry = Entry(rw)
    stat_label_entry.grid(row=5, column=1)

    position_label = Label(rw, text="Home or Away")
    position_label.grid(row=6)
    position_label_entry = Entry(rw)
    position_label_entry.grid(row=6, column=1)

    pred_button = Button(rw, text="Report", width=10, height=2,
                         command=lambda: get_prediction_plots(str(str(player_name_entry.get())
                                                                  + "_" + str(opp_a_code_entry.get()) + "_"
                                                                  + str(season_entry.get()) + ".csv"),
                                                              str(str(player_name_entry.get()) + "_"
                                                                  + str(opp_b_code_entry.get()) + "_"
                                                                  + str(season_entry.get()) + ".csv"),
                                                              str(str(player_name_entry.get()) + "_"
                                                                  + str(players_code_entry.get()) + "_"
                                                                  + str(season_entry.get()) + ".csv"),
                                                              str(stat_label_entry.get()),
                                                              str(position_label_entry.get())))
    pred_button.grid(row=7, column=1)

    rw.mainloop()


root = Tk()
root.resizable(width=True, height=True)
root.title("NBA STATISTICAL ")
root.geometry("550x550")

button1 = Button(root, text="Create CSVs for Player", width=20, height=5, command=create_button)
button1.grid(row=0, column=0)

button2 = Button(root, text="Analyze Scatter Data", width=20, height=5, command=analyze_button)
button2.grid(row=1, column=0)

button3 = Button(root, text="Report Predictions", width=20, height=5, command=report_button)
button3.grid(row=2, column=0)

root.mainloop()
