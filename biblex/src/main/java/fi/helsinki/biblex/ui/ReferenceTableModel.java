package fi.helsinki.biblex.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author danefl
 */
public class ReferenceTableModel extends AbstractTableModel {

    private String[] columnNames;
    private List<List<String>> data;

    public ReferenceTableModel() {
        columnNames = new String[]{"Name", "Title", "Author"};
        data = new ArrayList();
    }
    
    public void clear() {
        data.clear();
    }
    
    public void addData(String name, String title, String author) {
        ArrayList<String> row = new ArrayList();
        row.add(name);
        row.add(title);
        row.add(author);
        data.add(row);
    }
    
    public int getRowByName(String name) {
        int index = 0;
        for(List<String> list : data) {
            if(list.get(0).equals(name)) {
                return index;
            }
            index++;
        }
        return 0;
    }
    
    public void deleteData(int row) {
        data.remove(row);
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
    
}
