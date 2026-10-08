/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itnow
 */
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import java.util.regex.*;
/**
 *
 * @author user
 */
public class LogControl {
    
    private AdminLogsGUI view;
    private SheetsQuickstart sqs = new SheetsQuickstart();
    public AdminLogsGUI getView() {
        return view;
    }

    public void setView(AdminLogsGUI view) {
        this.view = view;
    }
    
    public LogControl(AdminLogsGUI view) throws GeneralSecurityException, IOException{
    this.view = view;
    System.out.println("AdminLogsGUI instance in LogControl: " + view);
    
    this.setup(sqs.readData("Logs!A2:D"));
    view.getSearchButton().addActionListener(e -> sorted(view.getAllLogs()));
    view.getDownloadButton().addActionListener(e -> downloadCSV());
    view.getSortDropdown().addItemListener(e -> applyFilters(view.getAllLogs()));
    this.refreshTable();
    }
    
    
    private void setup(List<List<Object>> thislist){
        List<List<String>> stringList = new ArrayList<>();
        
        for (List<Object> i : thislist) {
            List<String> s  = i.stream()
                        .map(Object :: toString)
                        .collect(Collectors.toList());
            stringList.add(s);
        }
        view.setAllLogs(stringList);
        view.setDisplayLogs(stringList.reversed());
        System.out.println("Fetched Data: " + stringList);
    }
    
    private void sorted(List<List<String>> allLogs){
        
        String searchText = view.getSearchField().getText().toLowerCase();
        Pattern pattern = Pattern.compile(searchText,Pattern.CASE_INSENSITIVE);
        List<List<String>> temp = new ArrayList<>();
        
        for (List<String> log : allLogs){
            Matcher matcher = pattern.matcher(log.toString());
            boolean matchesSearch = matcher.find(); 
            if (matchesSearch){
                temp.add(log);
            }
            applyFilters(temp);
            
        }
    }
    
    private void applyFilters(List<List<String>> displaylog) {
        
        String selectedActionType = (String) view.getSortDropdown().getSelectedItem();
        List<List<String>> temp = new ArrayList<>();
        
        for (List<String> log : displaylog) {
            
            boolean matchesType = selectedActionType.equals("All") || log.get(2).trim().equalsIgnoreCase(selectedActionType);
            if (matchesType) {
                temp.add(log);
            }
        }
        view.setDisplayLogs(temp.reversed());
       refreshTable();
    }
    
    private void refreshTable() {
        view.getTableModel().setRowCount(0);
        for (List<String> log : view.getDisplayLogs()) {
            view.getTableModel().addRow(log.toArray());
        }
    }

    private void downloadCSV() {
        final String PATH = System.getProperty("user.home")+"/Downloads/logs.csv";
        try (FileWriter writer = new FileWriter(PATH)) {
            writer.append("Timestamp,UserID,ActionType,Action\n");
            for (List<String> log : view.getDisplayLogs()) {
                writer.append(String.join(",", log)).append("\n");
            }
            JOptionPane.showMessageDialog(view.getPanel(), "CSV Downloaded Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view.getPanel(), "Error Saving CSV", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
}