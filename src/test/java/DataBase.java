
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author user
 */
public interface DataBase {
    
    public List<List<Object>> readData(String range);
    public void createData(String range, List<List<Object>> data);
    public boolean updateData(String range, List<List<Object>> data);
    public void deleteData(String range);
    public String getByIndexandColumn(String index, String column);
    public String searchWithQuery(String sheet, String columnLetter, String keyword);
}
