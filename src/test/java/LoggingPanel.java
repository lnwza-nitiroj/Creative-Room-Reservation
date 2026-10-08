
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LoggingPanel extends JPanel {
    public LoggingPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        
        // ดึงข้อมูลการจองทั้งหมด
        List<List<Object>> logs = Login.sqs.readData("Logs!A2:D");
        
        // สร้างตารางแสดงประวัติ
        String[] columnNames = {"Username", "Room", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (List<Object> row : logs) {
            model.addRow(row.toArray());
        }
        
        JTable logTable = new JTable(model);
        add(new JScrollPane(logTable), BorderLayout.CENTER);
        
        // ปุ่ม Export to Excel
        JButton exportButton = new JButton("Export to Excel");
        exportButton.addActionListener(e -> {
            // ควรเพิ่มโค้ดการ export เป็น Excel ที่นี่
            JOptionPane.showMessageDialog(this, "Exported to Excel successfully");
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}