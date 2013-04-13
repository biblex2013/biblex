package fi.helsinki.biblex.ui;

import javax.swing.*;
import java.awt.*;

/**
 * This class handles the visual appearance of the main UI window
 */
public class EntryPane extends JPanel {
    public static enum UIAction {
        MENU_EXPORT,
        MENU_QUIT,
        MENU_NEW_ENTRY
    }

    private DefaultListModel p_entryList;
    private JScrollPane p_scrollPane;


    public EntryPane() {
        p_entryList = new DefaultListModel();

        populate();
    }


    /**
     * Add a new entry to the entry list
     *
     * @param name Name/ID of the entry to add
     */
    public void addEntry(String name) {
        p_entryList.addElement(name);
        p_scrollPane.revalidate();
    }

    public void clearEntryList() {
        p_entryList.clear();
        p_scrollPane.revalidate();
    }


    /**
     * Add UI elements to the window, and setup action handling
     */
    private void populate() {
        p_scrollPane = new JScrollPane(new JList(p_entryList));
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        this.add(p_scrollPane, BorderLayout.CENTER);
    }
}

