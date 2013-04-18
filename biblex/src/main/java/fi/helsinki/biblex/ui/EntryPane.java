package fi.helsinki.biblex.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;


/**
 * This class handles the visual appearance of the main UI window
 */
public class EntryPane extends JPanel {
    private DefaultListModel p_entryListModel;
    private JList p_entryList;
    private JScrollPane p_scrollPane;

    private JMenuItem p_menuDeleteEntry;
    private JMenuItem p_menuCopyEntryToClipboard;


    public EntryPane() {
        p_entryListModel = new DefaultListModel();
        p_entryList = new JList(p_entryListModel);
        p_entryList.setName("entryList");

        populate();
    }


    /**
     * Add a new entry to the entry list
     *
     * @param name Name/ID of the entry to add
     */
    public void addEntry(String name) {
        p_entryListModel.addElement(name);
        p_entryList.revalidate();
        p_entryList.repaint();
    }


    public void clearEntryList() {
        p_entryListModel.clear();
        p_scrollPane.revalidate();
    }


    public String getSelectedEntry() {
        return (String) p_entryList.getSelectedValue();
    }


    public void addMListener(MouseListener ml) {
        p_entryList.addMouseListener(ml);
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
        JPopupMenu popMenu = new JPopupMenu();
        p_menuDeleteEntry = new JMenuItem();
        p_menuCopyEntryToClipboard = new JMenuItem();
        p_menuCopyEntryToClipboard.setName("menuCopyEntryToClipboard");

        popMenu.add(p_menuDeleteEntry);
        popMenu.add(p_menuCopyEntryToClipboard);
        p_entryList.setComponentPopupMenu(popMenu);

        p_scrollPane = new JScrollPane(p_entryList);
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder());

        p_scrollPane.setMinimumSize(new Dimension(140, 390));
        p_scrollPane.setPreferredSize(new Dimension(140, 390));
        p_scrollPane.setMaximumSize(new Dimension(140, Integer.MAX_VALUE));

        this.add(p_scrollPane, BorderLayout.CENTER);
    }
}
