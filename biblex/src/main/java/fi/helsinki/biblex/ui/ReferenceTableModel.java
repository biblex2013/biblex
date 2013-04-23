package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.domain.BibTexEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author danefl
 */
public class ReferenceTableModel extends AbstractTableModel implements Iterable<BibTexEntry> {

    private String[] columnNames;
    private List<List<String>> data;
    private List<BibTexEntry> entries;

    public ReferenceTableModel() {
        columnNames = new String[]{"Name", "Title", "Author"};
        data = new ArrayList();
        entries = new ArrayList<BibTexEntry>();
    }
    
    public void clear() {
        data.clear();
        entries.clear();
    }
    

    public void addData(BibTexEntry entry) {
        ArrayList<String> row = new ArrayList();
        row.add(entry.getName());
        row.add(entry.containsField(columnNames[1].toLowerCase()) ? entry.get(columnNames[1].toLowerCase()) : "");
        row.add(entry.containsField(columnNames[2].toLowerCase()) ? entry.get(columnNames[2].toLowerCase()) : "");
        for(Entry<String, String> f : entry) {
            if(!f.getKey().equals(columnNames[1].toLowerCase()) || !f.getKey().equals(columnNames[2].toLowerCase())) {
                row.add(f.getValue());
            }
        }
        data.add(row);
        entries.add(entry);
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
    public Iterator<BibTexEntry> iterator() {
        return entries.iterator();
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
