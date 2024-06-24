import csv
from datetime import datetime

import matplotlib
import matplotlib.pyplot as plt
import pandas as pd


# Using matplotlib, we have to set up the backend before we can begin using the tools in matplotlib
matplotlib.use('TkAgg')


# The 'load_dates_from_csv' method uses the data from the csv files created from the 'league_game_finder.py' file
# and the dates from the player data extracted from the csv file.
def load_dates_from_csv(csv_data):
    dates = []
    with open(csv_data) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        next(csv_reader)
        for row in csv_reader:
            game_date = datetime.strptime(row[6], "%Y-%m-%d")
            dates.append(game_date)
    return dates


# The 'create_scatter_plot' method uses the player csv file and allows the user to find the statistic column from
# the csv data. This method uses pandas and plt from matplotlib. This method will create a scatter plot graph.
# In the future, this project will apply an AI/ML algorithm to show the projection of the player's next game.
def create_scatter_plot(csv_data, stat):

    dates = load_dates_from_csv(csv_data)

    player_data = load_data_from_csv(csv_data, stat)

    df = pd.DataFrame(player_data, columns=['value'], index=dates)

    area = 2.5

    x = df.index
    y = df['value']

    plt.plot(x, y)

    plt.scatter(x, y, c="blue", s=area, label='Predictive Range')

    plt.title(f'{stat} vs Date')
    plt.xlabel('Date')
    plt.ylabel(stat)
    plt.legend()
    plt.gcf().autofmt_xdate()

    plt.show()


# The 'load_data_from_csv' method is used to load the data from the csv file and extract the data for the player's
# 'Points', 'Assists', 'Rebounds', and 'Threes' in each of the games they've played throughout the player's career.
def load_data_from_csv(csv_data, stats):
    data = []
    with open(csv_data) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        next(csv_reader)
        for row in csv_reader:
            if stats == "Points":
                data.append(int(row[10]))
            elif stats == "Assists":
                data.append(int(row[23]))
            elif stats == "Rebounds":
                data.append(int(row[22]))
            elif stats == "Threes":
                data.append(int(row[14]))
    return data
