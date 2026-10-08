/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itnow
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class AdminLogsGUI extends JPanel{

    private JTextField searchField;
    private JComboBox<String> sortDropdown;
    private List<List<String>> allLogs = new ArrayList<>(); // Logs fetched from Google Sheets
    private List<List<String>> displayLogs = new ArrayList<>(); // Logs shown on JTable
    private JButton searchButton,downloadButton;
    private JTable logTable;
    private DefaultTableModel tableModel;

    public AdminLogsGUI(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        

        JLabel titleLabel = new JLabel("Administrator Logs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        searchField = new JTextField(20);
         searchButton = new JButton("Search");
        sortDropdown = new JComboBox<>(new String[]{"All", "LOGIN","LOGOUT", "RESERVE", "ACCOUNT",});
         downloadButton = new JButton("Download CSV");

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(sortDropdown);
        topPanel.add(downloadButton);

        String[] columnNames = {"Timestamp", "UserID", "ActionType", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        logTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(logTable);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
       
    }

    public JPanel getPanel() {
        return this;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JComboBox<String> getSortDropdown() {
        return sortDropdown;
    }

    public void setSortDropdown(JComboBox<String> sortDropdown) {
        this.sortDropdown = sortDropdown;
    }

    public JTable getLogTable() {
        return logTable;
    }

    public void setLogTable(JTable logTable) {
        this.logTable = logTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public List<List<String>> getAllLogs() {
        return allLogs;
    }

    public void setAllLogs(List<List<String>> allLogs) {
        this.allLogs = allLogs;
    }

    public List<List<String>> getDisplayLogs() {
        return displayLogs;
    }

    public void setDisplayLogs(List<List<String>> displayLogs) {
        this.displayLogs = displayLogs;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public JButton getDownloadButton() {
        return downloadButton;
    }

    public void setDownloadButton(JButton downloadButton) {
        this.downloadButton = downloadButton;
    }
}

