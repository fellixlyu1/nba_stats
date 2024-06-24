import csv


# The 'get_cross' method uses the reader method from the csv library. The data from the csv file is created from the
# main.py file and will be used as a parameter. The 'stat' parameter inside the 'get_cross' method is the parameter
# that determines the statistic that the user wishes to analyze. The user will offer the choices: 'Points', 'Assists',
# 'Rebounds', and 'Threes'.
def get_cross(csv_file, stat):
    minute = 0
    amount = 0
    total = 0
    min_map = {}
    with open(csv_file) as player_data:
        reader = csv.reader(player_data)
        next(reader)
        for row in reader:
            mins = row[9]
            pts = row[10]
            asts = row[23]
            rbds = row[22]
            thr = row[14]
            if stat == "Points":
                total += int(pts)
                minute += int(mins)
                amount += 1
            elif stat == "Assists":
                total += int(asts)
                minute += int(mins)
                amount += 1
            elif stat == "Rebounds":
                total += int(rbds)
                minute += int(mins)
                amount += 1
            elif stat == "Threes":
                total += int(thr)
                minute += int(mins)
                amount += 1

    avg = total / amount
    time = minute / amount

    min_map[time] = avg

    return min_map
