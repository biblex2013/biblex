package fi.helsinki.biblex.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.table.TableColumn;


/**
 * This class handles the visual appearance of the main UI window
 */
public class EntryPane extends JPanel {
    private DefaultListModel p_entryListModel;
    private JList p_entryList;
    private JScrollPane p_scrollPane;
    
    public JTable p_entryTable;
    public ReferenceTableModel refTableModel;
    
    private JMenuItem p_menuDeleteEntry;
    private JMenuItem p_menuCopyEntryToClipboard;


    public EntryPane() {
        p_entryListModel = new DefaultListModel();
        p_entryList = new JList(p_entryListModel);
        p_entryList.setName("entryList");
        refTableModel = new ReferenceTableModel();
        p_entryTable = new JTable(refTableModel);

        populate();
    }


    /**
     * Add a new entry to the entry list
     *
     * @param name Name/ID of the entry to add
     */
    public void addEntry(String name, String title, String author) {
        refTableModel.addData(name, title, author);
        //p_entryListModel.addElement(name);
        //p_entryList.revalidate();
        //p_entryList.repaint();
        p_entryTable.revalidate();
        p_entryTable.repaint();
    }


    public void clearEntryList() {
        p_entryTable.revalidate();
    }


    public String getSelectedEntry() {
        return (String) refTableModel.getValueAt(p_entryTable.getSelectedRow(), 0);
    }


    public void addMListener(MouseListener ml) {
        //p_entryList.addMouseListener(ml);
        p_entryTable.addMouseListener(ml);
    }


    public void registerDeleteAction(Action ac) {
        p_menuDeleteEntry.setAction(ac);
    }


    public void registerCopyToClipboardAction(Action ac) {
        p_menuCopyEntryToClipboard.setAction(ac);
    }


    /**
     * Add UI elements to the window, and setup action handling
     */
    private void populate() {
        this.setLayout(new BorderLayout());

        JPopupMenu popMenu = new JPopupMenu();
        p_menuDeleteEntry = new JMenuItem();
        p_menuCopyEntryToClipboard = new JMenuItem();
        p_menuCopyEntryToClipboard.setName("menuCopyEntryToClipboard");

        popMenu.add(p_menuDeleteEntry);
        popMenu.add(p_menuCopyEntryToClipboard);
        p_entryTable.setComponentPopupMenu(popMenu);
        p_entryTable.setAutoCreateRowSorter(true);
        
        
        p_scrollPane = new JScrollPane(p_entryTable);
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder());

        p_scrollPane.setMinimumSize(new Dimension(175, 390));
        p_scrollPane.setPreferredSize(new Dimension(175, 390));
        p_scrollPane.setMaximumSize(new Dimension(175, Integer.MAX_VALUE));

        this.add(p_scrollPane, BorderLayout.CENTER);
    }
}
