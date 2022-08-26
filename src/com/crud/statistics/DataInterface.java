package com.crud.statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInterface {

    public DataInterface() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statUser, statScore, statRank;
                statUser = jTextFieldUsername.getText();
                statScore = jTextFieldScore.getText();
                statRank = jTextFieldRank.getText();

                try {
                    preparedStatement = Connector.ConnectDB().prepareStatement("INSERT INTO statistic (stat_username, stat_score, stat_rank) values (?,?,?);");
                    preparedStatement.setString(1, statUser);
                    preparedStatement.setString(2, statScore);
                    preparedStatement.setString(3, statRank);
                    preparedStatement.executeUpdate();
                    showData();
                    JOptionPane.showMessageDialog(null, "Data Successfully Inserted");
                } catch (SQLException err){
                    Logger.getLogger(DataInterface.class.getName()).log(Level.SEVERE, null, err);
                }
                jTextFieldUsername.setText("");
                jTextFieldScore.setText("");
                jTextFieldRank.setText("");
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createUpdateGUI);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createDeleteGUI);
            }
        });
    }

    public JPanel getMainPanel(){
        showData();
        return mainPanel;
    }

    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showData(){
        try {
            Object[] columnTitle = {"Statistic ID", "Username", "Score", "Rank"};
            tableModel = new DefaultTableModel(null, columnTitle);
            jTable.setModel(tableModel);

            //retrieve connection from DB
            Connection connection = Connector.ConnectDB();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            //initiate result with SQL Query
            resultSet = statement.executeQuery("SELECT * FROM statistic");
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getString("stat_id"),
                        resultSet.getString("stat_username"),
                        resultSet.getString("stat_score"),
                        resultSet.getString("stat_rank")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);
        }
    }

    private static void createUpdateGUI(){
        UpdatePanel updateUI = new UpdatePanel();
        JPanel updateRoot = updateUI.getUpdatePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void createDeleteGUI(){
        DeletePanel deleteUI = new DeletePanel();
        JPanel deleteRoot = deleteUI.getDeletePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(deleteRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private JPanel mainPanel;
    private JLabel jTitlePanel;
    private JTextField jTextFieldUsername;
    private JTextField jTextFieldScore;
    private JTextField jTextFieldRank;
    private JTable jTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel jFirstPanel;
    private JPanel jSecondPanel;
    private JPanel jThirdPanel;
    private JLabel jLabelUsername;
    private JLabel jLabelScore;
    private JLabel jLabelRank;
    private JScrollPane jScrolls;
}
