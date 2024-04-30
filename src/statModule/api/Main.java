package statModule.api;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    int gameId = (int) gamesList.get(object);
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
                HashMap<String, Object> playersPoints = games.getPlayersPoints(teamStatistics, gamesList);


                HashMap<String, Object> pointsByName = players.getPointsByName(playersPoints, playerName);
                HashMap<String, Object> playerPointsBySeason = players.getPointsBySeason(pointsByName, season);

                HashMap<String, Object> playersThreeAttempts = threePointers.getThreePointAttempts(teamStatistics, gamesList, playerName);
                HashMap<String, Object> playersThrees = threePointers.getThreePointers(teamStatistics, gamesList, playerName);
                HashMap<String, Object> threesStats = threePointers.threePointsStats(playersThreeAttempts, playersThrees);
                HashMap<String, Object> threesBySeason = players.getPointsBySeason(threesStats, season);

                HashMap<String, Object> homeSeason = new HashMap<>();
                HashMap<String, Object> visitingSeason = new HashMap<>();
                HashMap<String, Object> homePlayoff = new HashMap<>();
                HashMap<String, Object> visitingPlayoff = new HashMap<>();
                HashMap<String, Object> pointPercentage = new HashMap<>();

                for (String key : playerPointsBySeason.keySet()) {
                    if (key.contains("Seasonal Game: Home")) {
                        homeSeason.put(key, playerPointsBySeason.get(key));
                    } else if (key.contains("Seasonal Game: Visiting")) {
                        visitingSeason.put(key, playerPointsBySeason.get(key));
                    } else if (key.contains("Playoff Game: Home")) {
                        homePlayoff.put(key, playerPointsBySeason.get(key));
                    } else if (key.contains("Playoff Game: Visiting")) {
                        visitingPlayoff.put(key, playerPointsBySeason.get(key));
                    }
                }

                for (String key : playerPointsBySeason.keySet()) {
                    HashMap<String, Object> pointPercentages = percentage.getPointPercentage(playerPointsBySeason);
                    for (String pointKey : pointPercentages.keySet()) {
                        pointPercentage.put(pointKey, pointPercentages.get(pointKey));
                    }
                }

                HashMap<String, Object> homeSeasonThrees = new HashMap<>();
                HashMap<String, Object> visitingSeasonThrees = new HashMap<>();
                HashMap<String, Object> homePlayoffThrees = new HashMap<>();
                HashMap<String, Object> visitingPlayoffThrees = new HashMap<>();
                HashMap<String, Object> threePointPercentage = new HashMap<>();

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