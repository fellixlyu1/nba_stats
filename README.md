The program uses api-sports.io to send requests to the api's endpoints and get the stats of inputted NBA players from a specific team.
By using JavaFX, I created a program to show percentage changes in between games using the opponent team and season as the parameters.
For example, if you want to see the games that Lebron James played between the Lakers and the Clippers, you'd input the name, the season,
the LA Lakers, and the LA Clippers. The result shows the points, assists, rebounds, and three pointers from the games between those two
teams.

![Screenshot 2024-05-06 5 55 55 AM](https://github.com/fellixlyu1/nba_stats/assets/116593040/1180894a-80c9-4edd-b966-dd9d113d293b)

I used a simple algorithm to show the percentage changes. Since the numbers between the two teams show a certain range of stats, I used
the patterns of changes depending on the days that those games were played. I plan to include other parameters to make it easier to
determine the stat predictions of the next games depending on whether the game is "Home" or "Away", or in this case, "Visiting".
