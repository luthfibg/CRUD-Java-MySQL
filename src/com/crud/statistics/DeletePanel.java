package com.crud.statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DeletePanel {

    public DeletePanel() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId;
                userId = jTextFieldId.getText();
                if (!Objects.equals(userId, "")){
                    try {
                        preparedStatement = Connector.ConnectDB().prepareStatement("DELETE FROM statistic WHERE stat_id=?;");
                        preparedStatement.setString(1, userId);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Successfully Deleted");
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException err){
                        err.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Id target shouldn't empty");
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

    public JPanel getDeletePanel(){
        return deletePanel;
    }
    public PreparedStatement preparedStatement;
    private JPanel deletePanel;
    private JLabel jTitleDeletePanel;
    private JPanel jPanelID;
    private JPanel jPanelButton;
    private JTextField jTextFieldId;
    private JButton cancelButton;
    private JButton deleteButton;
    private JLabel jIdDeleteLabel;
    private JLabel jIdLabel;
}
