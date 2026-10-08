/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author padol
 */
import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private SettingFrame settingFrame;
    private JDesktopPane desktopPane;
    
    
    public MainFrame() {
        setTitle("MDI Application");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mainPanel = new LogoutPanel(this);
        setContentPane(mainPanel);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> openSettings());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(settingsButton);

        add(topPanel, BorderLayout.NORTH);
        
        setLocationRelativeTo(null);
    }
    
    private void openSettings() {
        if (settingFrame == null || !settingFrame.isVisible()) {
            settingFrame = new SettingFrame(this);
            settingFrame.setSize(300, 300);
            settingFrame.setLocation(getX() - settingFrame.getWidth(), getY());
            settingFrame.setVisible(true);
        } else {
            settingFrame.toFront();
        }
    }

    public void changePanelContent(JPanel newPanel) {
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}