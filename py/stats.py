class Stats:
    def __init__(self, points, dates):
        self.points = points
        self.dates = dates

    def __str__(self):
        return "%s, %s" % (self.points, self.dates)
