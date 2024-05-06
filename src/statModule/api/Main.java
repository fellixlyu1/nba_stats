package statModule.api;

import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
    private final Api api = new Api();
    private final Teams teams = new Teams();
    private final Statistics statistic = new Statistics();
    private final Playoff playoff = new Playoff();
    private final Points points = new Points();
    private final Assists assists = new Assists();
    private final Rebounds rebounds = new Rebounds();
    private final Threes threes = new Threes();
    //private static final WriteToCsv csv = new WriteToCsv();
    private final JComboBox<String> teamComboBox = new JComboBox<>();
    private final JComboBox<String> opponentTeamComboBox = new JComboBox<>();

    public Main() {
        JFrame frame = new JFrame("NBA STATS");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel[] labels = {
                new JLabel("NBA Player: "),
                new JLabel("Season: "),
                new JLabel("Team: "),
                new JLabel("Opponent Team: "),
        };

        JTextField[] textFields = {
                new JTextField(),
                new JTextField()
        };

        JComboBox<String> teamComboBox = new JComboBox<>(teams.getTeams());
        JComboBox<String> opponentTeamComboBox = new JComboBox<>(teams.getTeams());

        teamComboBox.setPreferredSize(new Dimension(550, 30));
        opponentTeamComboBox.setPreferredSize(new Dimension(550, 30));

        JPanel[] rowPanels = new JPanel[labels.length];
        for (int i = 0; i < labels.length; i++) {
            rowPanels[i] = new JPanel();
            labels[i].setPreferredSize(new Dimension(150, 30));
            rowPanels[i].add(labels[i]);
            if (i == 2) {
                rowPanels[i].add(teamComboBox);
            } else if (i == 3) {
                rowPanels[i].add(opponentTeamComboBox);
            } else {
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(550, 30));
                rowPanels[i].add(textField);
            }
            panel.add(rowPanels[i]);
        }

        JTextArea responseTextArea = new JTextArea(10, 50);
        responseTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseTextArea);

        JButton button = new JButton("Submit");

        button.setPreferredSize(new Dimension(150, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        JButton switchButton = new JButton("Switch Teams");
        switchButton.setPreferredSize(new Dimension(150, 50));

        switchButton.addActionListener(e -> {
            String selectedTeam = (String) teamComboBox.getSelectedItem();
            String selectedOpponentTeam = (String) opponentTeamComboBox.getSelectedItem();

            teamComboBox.setSelectedItem(selectedOpponentTeam);
            opponentTeamComboBox.setSelectedItem(selectedTeam);
        });

        buttonPanel.add(switchButton);

        button.addActionListener(e -> {
            String playerName = ((JTextField) rowPanels[0].getComponent(1)).getText();
            int season = Integer.parseInt(((JTextField) rowPanels[1].getComponent(1)).getText());
            String teamName = (String) teamComboBox.getSelectedItem();
            String opponentTeamName = (String) opponentTeamComboBox.getSelectedItem();

            try {
                String[] fullName = playerName.split(" ");
                String lName = fullName[1];
                String[] teamFullName = teamName.split(" ");
                String team = teamFullName[teamFullName.length - 1];
                String csvFileName = lName + "_" + team + ".csv";
                String[] teamsList = teams.getTeams();
                String teamId = teams.getTeamId(teamName);
                String oppTeamId = teams.getTeamId(opponentTeamName);
                int playerId = api.getPlayersIds(playerName, teamId, season);

                String h2h = teamId + "-" + oppTeamId;

                TreeMap<String, Object> gamesList = (TreeMap<String, Object>) api.getGamesList(season, h2h, teamId);

                TreeMap<String, Object> seasonalOrPlayoffList = new TreeMap<>();

                TreeMap<String, Object> all = new TreeMap<>();
                //TreeMap<String, Object> teamLineup = new TreeMap();

                for (String key : gamesList.keySet()) {
                    String[] gameData = key.split(" ");
                    String gameId = gameData[0];
                    String date = gameData[2].substring(0, 10);
                    TreeMap<String, Object> statistics = (TreeMap<String, Object>) api.getPlayersStatistics(gameId, season, playerId);
                    //teamLineup.putAll(api.getTeamLineup(gameId, season, oppTeamId));
                    if (key.contains("Home")) {
                        TreeMap<String, Object> pointsList = (TreeMap<String, Object>) statistic.getHomePoints(statistics, gameId, date);
                        TreeMap<String, Object> eachSeasonOrPlayoff = (TreeMap<String, Object>) playoff.getSeasonOrPlayoff(pointsList, season);
                        seasonalOrPlayoffList.putAll(eachSeasonOrPlayoff);
                    } else if (key.contains("Visitor")) {
                        TreeMap<String, Object> pointsList = (TreeMap<String, Object>) statistic.getVisitorPoints(statistics, gameId, date);
                        TreeMap<String, Object> eachSeasonOrPlayoff = (TreeMap<String, Object>) playoff.getSeasonOrPlayoff(pointsList, season);
                        seasonalOrPlayoffList.putAll(eachSeasonOrPlayoff);
                    }
                }

                all.putAll(points.getPointPercentages(seasonalOrPlayoffList));
                all.putAll(assists.getAssistsPercentages(seasonalOrPlayoffList));
                all.putAll(rebounds.getReboundsPercentages(seasonalOrPlayoffList));
                all.putAll(threes.getThreesPercentages(seasonalOrPlayoffList));

                StringBuilder response = new StringBuilder();
                response.append("\n" + season + "-" + (season + 1) + " Points Percentage: \n");
                for (Map.Entry<String, Object> entry : all.entrySet()) {
                    if (entry.getKey().contains("Points")) {
                        response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                    }
                }
                response.append("\n" + season + "-" + (season + 1) + " Assists Percentage: \n");
                for (Map.Entry<String, Object> entry : all.entrySet()) {
                    if (entry.getKey().contains("Assists")) {
                        response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                    }
                }
                response.append("\n" + season + "-" + (season + 1) + " Rebounds Percentage: \n");
                for (Map.Entry<String, Object> entry : all.entrySet()) {
                    if (entry.getKey().contains("Rebounds")) {
                        response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                    }
                }
                response.append("\n" + season + "-" + (season + 1) + " Threes Percentage: \n");
                for (Map.Entry<String, Object> entry : all.entrySet()) {
                    if (entry.getKey().contains("Threes")) {
                        response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                    }
                }
                /*response.append("\n" + season + "-" + (season + 1) + " Opponent Team Lineup: \n");
                for (Map.Entry<String, Object> entry : teamLineup.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }*/
                //csv.writeToCSV(all, csvFileName);
                responseTextArea.setText(response.toString());

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        SwingUtilities.invokeLater(Main::new);
    }
}