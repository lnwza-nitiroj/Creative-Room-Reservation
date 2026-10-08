
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.security.GeneralSecurityException;
import java.io.IOException;

public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private JTextField usernameField;
    private int SHEET_ID = 528164424;
    
    public UserManagementPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        
        // ดึงข้อมูลผู้ใช้ทั้งหมด
        List<List<Object>> users = Login.sqs.readData("Sheet2!A2:A");
        
        // สร้างตารางแสดงผู้ใช้
        String[] columnNames = {"Username"};
        Object[][] data = new Object[users.size()][1];
        
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i).get(0);
        }
        
        userTable = new JTable(data, columnNames);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        
        // ส่วนลบผู้ใช้
        JPanel deletePanel = new JPanel();
        usernameField = new JTextField(20);
        JButton deleteButton = new JButton("Delete User");
        
        deleteButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Confirm delete user: " + username, 
                    "Confirm", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // ลบผู้ใช้จากฐานข้อมูล
                    try {
                    int row = Integer.parseInt(Login.sqs.searchWithQuery("Sheet2", "A", username));
                    Login.sqs.deleteData(SHEET_ID, row);
                    Logger.getInstance().log(Login.currentUser, "ACCOUNT", "Account " + username + " has been deleted.");
                    JOptionPane.showMessageDialog(this, "User deleted successfully");
                    mainFrame.changePanelContent(new UserManagementPanel(mainFrame));
                    }
                    catch (IOException | GeneralSecurityException exp) {
                        exp.printStackTrace();
                    }
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        
        deletePanel.add(new JLabel("Username to delete:"));
        deletePanel.add(usernameField);
        deletePanel.add(deleteButton);
        add(deletePanel, BorderLayout.SOUTH);
    }
}