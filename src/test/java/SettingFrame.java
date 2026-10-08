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
import java.awt.event.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SettingFrame extends JFrame {
    private MainFrame mainFrame;
    
    
    public SettingFrame(MainFrame frame) {
        super("Settings");
        this.mainFrame = frame;
        
        
        setSize(300, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton closeButton = createStyledButton("Close");
        JButton userManagementButton = createStyledButton("User Management");
        JButton loggingButton = createStyledButton("Logging");
        JButton logoutButton = createStyledButton("Logout");
        

        closeButton.addActionListener(e -> {
            
            this.dispose();
        });
        userManagementButton.addActionListener(e -> {
            if (Login.isAdmin) {
                mainFrame.changePanelContent(new UserManagementPanel(mainFrame));
                
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Admin privileges required", "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        loggingButton.addActionListener(e -> {
            if (Login.isAdmin) {
                try {
                    AdminLogsGUI adminLogsGUI = new AdminLogsGUI(mainFrame);
        
                    LogControl deez = new LogControl(adminLogsGUI);
        
                    mainFrame.changePanelContent(adminLogsGUI);
                    
                    mainFrame.setVisible(true);
                }
                catch (GeneralSecurityException | IOException ex) {
                    ex.printStackTrace();
                }
           }
            else{
                JOptionPane.showMessageDialog(this, "Admin privileges required", "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
        });

        logoutButton.addActionListener(e -> {
            try {
                Logger.getInstance().log(Login.currentUser, "LOGOUT",Login.currentUser + " has logged out.");
                }
                catch (GeneralSecurityException | IOException ex) {
                    ex.printStackTrace();
                }
            Login.currentUser = null;
            Login.isAdmin = false;
            
            for (Window window : Window.getWindows()) {
                if (window instanceof JFrame) {
                    window.dispose();
                }
            }

            new Login().setVisible(true);
        });
        
        add(closeButton);
        add(userManagementButton);
        add(loggingButton);
        add(logoutButton);
        
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.ORANGE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.ORANGE);
                button.setForeground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.ORANGE);
            }
        });

        return button;
    }
    
    private void logout() {
        this.dispose();
        mainFrame.changePanelContent(new LogoutPanel(mainFrame));
    }
}