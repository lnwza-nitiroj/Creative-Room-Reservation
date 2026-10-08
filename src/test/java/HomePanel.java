/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author padol
 */
import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
            SettingFrame settingFrame = new SettingFrame(mainFrame);
            settingFrame.setVisible(true);
        });
        
        topPanel.add(settingsButton);

        add(topPanel, BorderLayout.NORTH);

        add(new JLabel("Home Panel"), BorderLayout.CENTER);
    }
}