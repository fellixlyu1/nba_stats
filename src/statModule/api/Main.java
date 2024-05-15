package statModule.api;

import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import static javafx.application.Application.launch;

public class Main {
    private final Api api = new Api();
    private final Percentages percentages = new Percentages();
    private static final WriteToCsv csv = new WriteToCsv();
    private final Teams teams = new Teams();
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
            int intSeason = Integer.parseInt(((JTextField) rowPanels[1].getComponent(1)).getText());
            String teamName = (String) teamComboBox.getSelectedItem();
            String opponentTeamName = (String) opponentTeamComboBox.getSelectedItem();

            try {
                String[] fullName = playerName.split(" ");
                String firstname = fullName[0];
                String lastname = fullName[fullName.length - 1];
                String playerId = api.getPlayersIds(firstname, lastname);
                String team = teams.getTeamId(teamName);
                String opponent = teams.getTeamId(opponentTeamName);
                String season = String.valueOf(intSeason);
                int intPrev = intSeason - 1;
                String previousYear = String.valueOf(intPrev);
                TreeMap<String, Object> game = api.getGames(playerId, season);
                TreeMap<String, Object> points = new TreeMap<>();
                TreeMap<String, Object> assists = new TreeMap<>();
                TreeMap<String, Object> rebounds = new TreeMap<>();
                TreeMap<String, Object> threes = new TreeMap<>();

                TreeMap<String, Object> all = new TreeMap<>();

                for (Map.Entry<String, Object> objects : game.entrySet()) {
                    if (((String) objects.getKey()).contains(opponent) && ((String) objects.getKey()).contains(team) && ((String) objects.getKey()).contains("Points")) {
                        points.put((String) objects.getKey(), objects.getValue());
                    } else if (((String) objects.getKey()).contains(opponent) && ((String) objects.getKey()).contains(team) && ((String) objects.getKey()).contains("Assists")) {
                        assists.put((String) objects.getKey(), objects.getValue());
                    } else if (((String) objects.getKey()).contains(opponent) && ((String) objects.getKey()).contains(team) && ((String) objects.getKey()).contains("Rebounds")) {
                        rebounds.put((String) objects.getKey(), objects.getValue());
                    } else if (((String) objects.getKey()).contains(opponent) && ((String) objects.getKey()).contains(team) && ((String) objects.getKey()).contains("Three Pointers")) {
                        threes.put((String) objects.getKey(), objects.getValue());
                    }
                }

                TreeMap<String, Object> pointPercentage = percentages.getPercentages(points);
                TreeMap<String, Object> assistPercentage = percentages.getPercentages(assists);
                TreeMap<String, Object> reboundPercentage = percentages.getPercentages(rebounds);
                TreeMap<String, Object> threePercentage = percentages.getPercentages(threes);

                StringBuilder response = new StringBuilder();
                response.append("\n" + previousYear + "-" + season + " Stats & Percentages: \n");
                for (Map.Entry<String, Object> entry : pointPercentage.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                for (Map.Entry<String, Object> entry : assistPercentage.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                for (Map.Entry<String, Object> entry : reboundPercentage.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                for (Map.Entry<String, Object> entry : threePercentage.entrySet()) {
                    response.append("    " + entry.getKey()).append(": ").append(entry.getValue()).append("    \n");
                }
                csv.writeToCSV(pointPercentage, playerName + "_" + opponent + "_" + season + "_seasonal_points.csv");
                csv.writeToCSV(pointPercentage, playerName + "_" + opponent + "_" + season + "_seasonal_assists.csv");
                csv.writeToCSV(pointPercentage, playerName + "_" + opponent + "_" + season + "_seasonal_rebounds.csv");
                csv.writeToCSV(pointPercentage, playerName + "_" + opponent + "_" + season + "_seasonal_threes.csv");

                responseTextArea.setText(response.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error fetching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
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