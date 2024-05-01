package statModule.api;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
    private final Api api = new Api();
    private final PointPercentage percentage = new PointPercentage();
    private final Players players = new Players();
    private final Games games = new Games();

    private final JComboBox<String> teamComboBox = new JComboBox<>();
    private final JComboBox<String> opponentTeamComboBox = new JComboBox<>();
    public Main() {
        Teams teams = new Teams();
        ThreePointers threePointers = new ThreePointers();
        TravelPercentage travelPercentage = new TravelPercentage();
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
                String teamId = (String) teams.getTeamId(teamName);
                String oppTeamId = (String) teams.getTeamId(opponentTeamName);
                String h2h = teamId + "-" + oppTeamId;
                HashMap<String, Object> gamesList = (HashMap<String, Object>) api.getGamesList(season, h2h);
                int playerId = api.getPlayersIds(playerName, teamId, season);
                HashMap<String, Object> teamStatistics = new HashMap<>();
                for (String object : gamesList.keySet()) {
                    String[] gameData = object.split(" ");
                    String intGameId = gameData[0];
                    int gameId = Integer.valueOf(intGameId);
                    HashMap<String, Object> teamsStat = (HashMap<String, Object>) api.getPlayersStatistics(gameId, season, teamId);
                    String stringGameId = String.valueOf(gameId);
                    for (String teamKey : teamsStat.keySet()) {
                        if (teamKey.contains(stringGameId)) {
                            int points = (int) teamsStat.get(teamKey);
                            teamStatistics.put(teamKey, points);
                        }
                    }
                }

                HashMap<String, Object> playerThreesBySeason = new HashMap<>();
                HashMap<String, Object> playersPoints = games.getPlayersPoints(teamStatistics, gamesList, playerName);

                HashMap<String, Object> playerPointsBySeason = players.getPointsBySeason(playersPoints, season);

                HashMap<String, Object> playersThreeAttempts = threePointers.getThreePointAttempts(teamStatistics, gamesList, playerName);
                HashMap<String, Object> playersThrees = threePointers.getThreePointers(teamStatistics, gamesList, playerName);
                HashMap<String, Object> threesStats = threePointers.threePointsStats(playersThreeAttempts, playersThrees);
                HashMap<String, Object> threesBySeason = players.getPointsBySeason(threesStats, season);

                TreeMap<String, Integer> percentageTravel = new TreeMap<>();

                for (String key : playerPointsBySeason.keySet()) {
                    int playerPoints = (int) playerPointsBySeason.get(key);
                    percentageTravel.put(key, playerPoints);
                }

                TreeMap<String, Double> percentFromTravel = travelPercentage.getTravelPercentage(percentageTravel);

                TreeMap<String, Object> homeSeason = new TreeMap<>();
                TreeMap<String, Object> visitingSeason = new TreeMap<>();
                TreeMap<String, Object> homePlayoff = new TreeMap<>();
                TreeMap<String, Object> visitingPlayoff = new TreeMap<>();
                TreeMap<String, Object> pointPercentage = new TreeMap<>();

                for (String entry : percentFromTravel.keySet()) {
                    double doublePercent = percentFromTravel.get(entry);
                    String percent = String.valueOf(doublePercent);
                    String[] data = entry.split(" ");
                    String gameId = data[3].substring(1, data[3].length() - 1);
                    if (entry.contains("Seasonal Game: Home") && entry.contains(gameId)) {
                        homeSeason.put(entry + " = " + playerPointsBySeason.get(entry), percent + "%");
                    } else if (entry.contains("Seasonal Game: Visiting") && entry.contains(gameId)) {
                        visitingSeason.put(entry + " = " + playerPointsBySeason.get(entry), percent + "%");
                    } else if (entry.contains("Playoff Game: Home") && entry.contains(gameId)) {
                        homePlayoff.put(entry + " = " + playerPointsBySeason.get(entry), percent + "%");
                    } else if (entry.contains("Playoff Game: Visiting") && entry.contains(gameId)) {
                        visitingPlayoff.put(entry + " = " + playerPointsBySeason.get(entry), percent + "%");
                    }
                }

                for (String key : playerPointsBySeason.keySet()) {
                    HashMap<String, Object> pointPercentages = percentage.getPointPercentage(playerPointsBySeason);
                    for (String pointKey : pointPercentages.keySet()) {
                        pointPercentage.put(pointKey, pointPercentages.get(pointKey));
                    }
                }

                TreeMap<String, Object> homeSeasonThrees = new TreeMap<>();
                TreeMap<String, Object> visitingSeasonThrees = new TreeMap<>();
                TreeMap<String, Object> homePlayoffThrees = new TreeMap<>();
                TreeMap<String, Object> visitingPlayoffThrees = new TreeMap<>();


                for (String key : threesBySeason.keySet()) {
                    if (key.contains("Seasonal Game: Home")) {
                        homeSeasonThrees.put(key, threesBySeason.get(key));
                    } else if (key.contains("Seasonal Game: Visiting")) {
                        visitingSeasonThrees.put(key, threesBySeason.get(key));
                    } else if (key.contains("Playoff Game: Home")) {
                        homePlayoffThrees.put(key, threesBySeason.get(key));
                    } else if (key.contains("Playoff Game: Visiting")) {
                        visitingPlayoffThrees.put(key, threesBySeason.get(key));
                    }
                }

                StringBuilder response = new StringBuilder();
                response.append("\n" + season + "-" + (season + 1) + " Points By Season (Home): \n");
                for (Map.Entry<String, Object> entry : homeSeason.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Points By Season (Visitor): \n");
                for (Map.Entry<String, Object> entry : visitingSeason.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Points By Playoff (Home): \n");
                for (Map.Entry<String, Object> entry : homePlayoff.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Points By Playoff (Visitor): \n");
                for (Map.Entry<String, Object> entry : visitingPlayoff.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Point Percentages: \n");
                for (Map.Entry<String, Object> entry : pointPercentage.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Three Pointers By Season (Home): \n");
                for (Map.Entry<String, Object> entry : homeSeasonThrees.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Three Pointers By Season (Visitor): \n");
                for (Map.Entry<String, Object> entry : visitingSeasonThrees.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Three Pointers By Playoff (Home): \n");
                for (Map.Entry<String, Object> entry : homePlayoffThrees.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                response.append("\n" + season + "-" + (season + 1) + " Three Pointers By Playoff (Visitor): \n");
                for (Map.Entry<String, Object> entry : visitingPlayoffThrees.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}