package statModule.api;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private final Games games = new Games();
    private final PointPercentage percentage = new PointPercentage();
    private final Players players = new Players();
    public Main() {

        JFrame frame = new JFrame("NBA STATS");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel[] labels = {
                new JLabel("NBA Player: "),
                new JLabel("Season: "),
                new JLabel("Team: "),
                new JLabel("Opponent Team: ")
        };
        JTextField[] textFields = {
                new JTextField(),
                new JTextField(),
                new JTextField(),
                new JTextField()
        };

        for (int i = 0; i < labels.length; i++) {
            JPanel rowPanel = new JPanel();
            labels[i].setPreferredSize(new Dimension(150, 30));
            textFields[i].setPreferredSize(new Dimension(550, 30));
            rowPanel.add(labels[i]);
            rowPanel.add(textFields[i]);
            panel.add(rowPanel);
        }

        JTextArea responseTextArea = new JTextArea(10, 50);
        responseTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseTextArea);

        JButton button = new JButton("Submit");

        button.setPreferredSize(new Dimension(150, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        button.addActionListener(e -> {
            String playerName = textFields[0].getText();
            int season = Integer.parseInt(textFields[1].getText());
            String teamName = textFields[2].getText();
            String opponentTeamName = textFields[3].getText();

            try {
                HashMap<String, Object> sameGamePlayers = games.getOpponentStats(playerName, season, teamName, opponentTeamName);
                Map<String, Object> pointPercentage = percentage.getPointPercentage(playerName, season, teamName, opponentTeamName);
                HashMap<String, Object> playerPointsBySeason = players.getPointsBySeason(playerName, season, teamName, opponentTeamName);

                StringBuilder response = new StringBuilder();
                response.append("Opponents: \n");
                for (Map.Entry<String, Object> entry : sameGamePlayers.entrySet()) {
                    response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                response.append("\nPoint Percentage: \n");
                for (Map.Entry<String, Object> entry : pointPercentage.entrySet()) {
                    response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
                response.append("\nPoints By Season: \n");
                for (Map.Entry<String, Object> entry : playerPointsBySeason.entrySet()) {
                    response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
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