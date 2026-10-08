/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author itnow
 */
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Logger {
    private static Logger instance;
    private static final String SHEET_RANGE = "Logs!A:D"; // Logs go to columns A-D
    private SheetsQuickstart sheetManager;

    private Logger() throws GeneralSecurityException, IOException {
        sheetManager = new SheetsQuickstart();
    }

    // Singleton instance of Logger
    public static Logger getInstance() throws GeneralSecurityException, IOException {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Log user actions to Google Sheets
    public void log(String userId, String actionType, String action) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (userId == null) {
            userId = "Tester";
        }
        // Format log entry
        List<List<Object>> logEntry = Arrays.asList(Arrays.asList(timestamp, userId, actionType, action));

        // Add log to Google Sheets using GoogleSheetManager
        sheetManager.createData(SHEET_RANGE, logEntry);

        System.out.println("Log added: " + logEntry);
    }
}
