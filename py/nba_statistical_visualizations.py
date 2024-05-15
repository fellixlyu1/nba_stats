import csv
from datetime import datetime
from percentages import get_prediction

import matplotlib
import matplotlib.pyplot as plt
import pandas as pd


matplotlib.use('TkAgg')


def load_dates_from_csv(csv_data):
    dates = []
    with open(csv_data) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        next(csv_reader)
        for row in csv_reader:
            game_date = datetime.strptime(row[6], "%Y-%m-%d")
            dates.append(game_date)
    return dates


def create_scatterplot(csv_data, opp_csv, stat, date, position):
    opp1 = csv_data[15:] + "_" + stat
    opp2 = opp_csv[15:] + "_" + stat

    dates1 = load_dates_from_csv(csv_data)
    dates2 = load_dates_from_csv(opp_csv)

    player_data = load_data_from_csv(csv_data, stat)
    opp_data = load_data_from_csv(opp_csv, stat)

    predictive_range = get_prediction(csv_data, opp_csv, stat, position)

    avg = predictive_range.keys()
    other = list(predictive_range.values())

    df = pd.DataFrame(player_data, columns=['value'], index=dates1)
    d2f = pd.DataFrame(opp_data, columns=['value'], index=dates2)

    area = 2.5

    x = df.index
    x2 = d2f.index

    y = df['value']
    y2 = d2f['value']

    plt.plot(x, y)
    plt.plot(x2, y2)

    plt.scatter(x=x, y=y, c="red", s=area, label=opp1)
    plt.scatter(x=x2, y=y2, c="green", s=area, label=opp2)

    for i, (avg_val, other_vals) in enumerate(zip(avg, other)):
        plt.axhline(y=avg_val, color='gray', linestyle='--', label='Predictive Average' if i == 0 else None)
        plt.scatter([date] * len(other_vals), other_vals, c="blue", s=area, label='Predictive Range' if i == 0 else None)

    plt.legend()
    plt.gcf().autofmt_xdate()
    plt.show()


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
