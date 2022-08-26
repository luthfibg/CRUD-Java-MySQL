package com.crud.statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class UpdatePanel {

    public UpdatePanel() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId, userName, userScore, userRank;
                userId = jStatID.getText();
                userName = jUsername.getText();
                userScore = jScore.getText();
                userRank = jRank.getText();
                if (!Objects.equals(userId, "") && !Objects.equals(userName, "") && !Objects.equals(userScore, "") && !Objects.equals(userRank, "")){
                    try {
                        preparedStatement = Connector.ConnectDB().prepareStatement("UPDATE statistic SET stat_username=?, stat_score=?, stat_rank=? WHERE stat_id=?;");
                        preparedStatement.setString(1, userName);
                        preparedStatement.setString(2, userScore);
                        preparedStatement.setString(3, userRank);
                        preparedStatement.setString(4, userId);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Update Data Success");
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException exception){
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input shouldn't empty");
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
            }
        });
    }

    public JPanel getUpdatePanel(){
        return mainUpdatePanel;
    }

    private PreparedStatement preparedStatement;
    private JPanel mainUpdatePanel;
    private JLabel jTitleUpdatePanel;
    private JTextField jUsername;
    private JTextField jScore;
    private JTextField jRank;
    private JTextField jStatID;
    private JButton cancelButton;
    private JButton updateButton;
    private JLabel jPanelIdLabel;
    private JLabel jIdLabel;
    private JLabel jUserLabel;
    private JLabel jScoreLabel;
    private JLabel jRankLabel;
}
