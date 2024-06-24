import os
import threading
from tkinter import *
from nba_statistical_visualizations import create_scatterplot
from league_game_finder import get_player_stats
from py.percentages import get_prediction_plots
from py.team_link import whole_team_analysis


def create_button():
    os.system("league_game_finder.py")
    create_window()


def analyze_button():
    analyze_window()


def report_button():
    report_window()


def team_button():
    team_window()


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

    opp_code_label = Label(cw, text="Team Code")
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
    aw.title("NBA ANALYSIS ")
    aw.geometry("550x550")

    player_name_label = Label(aw, text="Player's Name")
    player_name_label.grid(row=0)
    player_name_entry = Entry(aw)
    player_name_entry.grid(row=0, column=1)

    season_label = Label(aw, text="Year")
    season_label.grid(row=1)
    season_entry = Entry(aw)
    season_entry.grid(row=1, column=1)

    team_code_label = Label(aw, text="Team Code")
    team_code_label.grid(row=2)
    team_code_entry = Entry(aw)
    team_code_entry.grid(row=2, column=1)

    stat_label = Label(aw, text="P/A/R/T")
    stat_label.grid(row=3)
    stat_label_entry = Entry(aw)
    stat_label_entry.grid(row=3, column=1)

    stat_button = Button(aw, text="Analyze", width=10, height=2, command=lambda: create_scatter_plot(
        str(str(player_name_entry.get()) + "_" + str(team_code_entry.get()) + "_" + str(season_entry.get()) + ".csv"),
        str(stat_label_entry.get())))
    stat_button.grid(row=4, column=1)


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

    opp_code_label = Label(rw, text="Opponent Team Code")
    opp_code_label.grid(row=3)
    opp_code_entry = Entry(rw)
    opp_code_entry.grid(row=3, column=1)

    stat_label = Label(rw, text="P/A/R/T")
    stat_label.grid(row=4)
    stat_label_entry = Entry(rw)
    stat_label_entry.grid(row=4, column=1)

    position_label = Label(rw, text="Home or Away")
    position_label.grid(row=5)
    position_label_entry = Entry(rw)
    position_label_entry.grid(row=5, column=1)

    pred_button = Button(rw, text="Report", width=10, height=2,
                         command=lambda: get_prediction_plots(str(opp_code_entry.get()),
                                                              str(str(player_name_entry.get()) + "_"
                                                                  + str(players_code_entry.get()) + "_"
                                                                  + str(season_entry.get()) + ".csv"),
                                                              str(stat_label_entry.get()),
                                                              str(position_label_entry.get()),
                                                              str(players_code_entry.get())))
    pred_button.grid(row=6, column=1)

    rw.mainloop()


def team_window():
    tw = Tk()
    tw.resizable(width=True, height=True)
    tw.title("NBA STATISTICAL ")
    tw.geometry("850x550")

    star_player_name_label = Label(tw, text="Star Player's Name")
    star_player_name_label.grid(row=0)
    star_player_name_entry = Entry(tw)
    star_player_name_entry.grid(row=0, column=1)

    player_name2_label = Label(tw, text="Player 2's Name")
    player_name2_label.grid(row=1)
    player_name2_entry = Entry(tw)
    player_name2_entry.grid(row=1, column=1)

    player_name3_label = Label(tw, text="Player 3's Name")
    player_name3_label.grid(row=2)
    player_name3_entry = Entry(tw)
    player_name3_entry.grid(row=2, column=1)

    player_name4_label = Label(tw, text="Player 4's Name")
    player_name4_label.grid(row=3)
    player_name4_entry = Entry(tw)
    player_name4_entry.grid(row=3, column=1)

    player_name5_label = Label(tw, text="Player 5's Name")
    player_name5_label.grid(row=4)
    player_name5_entry = Entry(tw)
    player_name5_entry.grid(row=4, column=1)

    players_code_label = Label(tw, text="Player's Team Code")
    players_code_label.grid(row=6)
    players_code_entry = Entry(tw)
    players_code_entry.grid(row=6, column=1)

    season_label = Label(tw, text="Year")
    season_label.grid(row=7)
    season_entry = Entry(tw)
    season_entry.grid(row=7, column=1)

    pred_button = Button(tw, text="Report", width=10, height=2,
                         command=lambda: whole_team_analysis(str(star_player_name_entry.get()),
                                                             str(player_name2_entry.get()),
                                                             str(player_name3_entry.get()),
                                                             str(player_name4_entry.get()),
                                                             str(player_name5_entry.get()),
                                                             str(players_code_entry.get()),
                                                             str(season_entry.get())))
    pred_button.grid(row=8, column=1)

    tw.mainloop()


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

button4 = Button(root, text="Days Analysis", width=20, height=5, command=team_button)
button4.grid(row=3, column=0)

root.mainloop()
