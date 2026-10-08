
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;
//https://docs.google.com/spreadsheets/d/1PtJRdL57HVFkjTcl-lcG3YDXOjdOwN7sZzAn-9AF0Rw/edit?gid=0#gid=0
public class SheetsQuickstart implements DataBase{
    private static final String spreadsheetId = "1PtJRdL57HVFkjTcl-lcG3YDXOjdOwN7sZzAn-9AF0Rw";
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Sheets getSheetsService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> readData(String range) {
        try {
            Sheets service = getSheetsService();
            ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
                return new ArrayList<>();
            } else {
                return values;
            }
        } catch (Exception e) {
            System.err.println("Error reading data: " + e.getMessage());
            return null;
        }
    }

    public void createData(String range, List<List<Object>> data) {
        try {
            Sheets service = getSheetsService();
            ValueRange body = new ValueRange().setValues(data);
            service.spreadsheets().values().append(spreadsheetId, range, body)
                    .setValueInputOption("RAW")
                    .execute();
            System.out.println("Data added successfully.");
        } catch (Exception e) {
            System.err.println("Error creating data: " + e.getMessage());
        }
    }

    public boolean updateData(String range, List<List<Object>> data) {
        try {
            Sheets service = getSheetsService();
            ValueRange body = new ValueRange().setValues(data);
            service.spreadsheets().values().update(spreadsheetId, range, body)
                    .setValueInputOption("RAW")
                    .execute();
            System.out.println("Data updated successfully.");
            return true;
        } catch (Exception e) {
            System.err.println("Error updating data: " + e.getMessage());
            return false;
        }
    }
    
    public void deleteData(String range) {
        try {
            Sheets service = getSheetsService();
            service.spreadsheets().values().clear(spreadsheetId, range, new ClearValuesRequest()).execute();
            System.out.println("Data cleared successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting data: " + e.getMessage());
        }
    }
    
    public void deleteData(int sheetID, int rowIndex) {
         try {
            Sheets service = getSheetsService();
            DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                    .setRange(new DimensionRange()
                            .setSheetId(sheetID)  // 0 is typically the sheet ID for the first sheet
                            .setDimension("ROWS")  // Specify that we are working with rows
                            .setStartIndex(rowIndex)  // The 0-based index of the row to delete
                            .setEndIndex(rowIndex + 1));  // End index (one row after the start)

            // Create a batch update request
            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Arrays.asList(new Request().setDeleteDimension(deleteRequest)));

            // Execute the batch update request
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
            System.out.println("Row " + rowIndex + " deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting row: " + e.getMessage());
        }
    }

    public String searchWithQuery(String sheet, String columnLetter, String keyword) {
        try {
            Sheets service = getSheetsService();
            String range = sheet+"!" + columnLetter + ":" + columnLetter;

            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            boolean found = false;

            if (values != null && !values.isEmpty()) {
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).size() > 0 && values.get(i).get(0).toString().equals(keyword)) {
                        System.out.println("found \"" + keyword + "\" at: " + (i+1));
                        return i+"";
                    }
                }
            }
            return "NOT FOUND";
        } catch (Exception e) {
            System.err.println("Error searching data: " + e.getMessage());
        }
        return null;
    }
    
    public String getByIndexandColumn(String index, String column) {
        try {
            Sheets service = getSheetsService();
            String range = "Sheet2!"+column + (Integer.parseInt(index) + 1) + ":"+column + (Integer.parseInt(index) + 1);

            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            if (values != null && !values.isEmpty()) {
                return values.get(0).get(0).toString();
            }
            return "Unknown";
        } catch (Exception e) {
            System.err.println("Error getting username: " + e.getMessage());
            return "Error";
        }
    }

    //public static void main(String... args) {
        //SheetsQuickstart sqs = new SheetsQuickstart();
        
        //sqs.searchWithQuery(spreadsheetId, "Sheet2", "A", "BroCode");
        // Read data
        //sqs.readData(spreadsheetId, "Sheet1!A2:10");

        // Create new data
        //List<List<Object>> newData = Arrays.asList(
        //    Arrays.asList("Alice", "Math", "So", "Female", "GPA: 3.7")
        //);
        //sqs.createData(spreadsheetId, "Sheet1!A2", newData);

        // Update data
        //List<List<Object>> updateData = Arrays.asList(
        //    Arrays.asList("Alice", "Mathematics", "Sophomore", "Female", "GPA: 3.8")
        //);
        //sqs.updateData(spreadsheetId, "Sheet1!A2", updateData);

        // Delete data
        //sqs.deleteData(spreadsheetId, "Sheet1!A2:5");
    //}
}
