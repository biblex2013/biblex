package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.domain.BibTexEntry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * This class handles the visual appearance of the main UI window
 */
public class EntryPane extends JPanel {
    public static enum UIAction {
        MENU_EXPORT,
        MENU_QUIT,
        MENU_NEW_ENTRY,
        POPUP_DELETE,
        SELECT_ENTRY
    }

    private DefaultListModel p_entryList;
    private JScrollPane p_scrollPane;
    public JList p_entryJList;
    public JPopupMenu p_popMenu;

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
        p_entryJList = new JList(p_entryList);
        p_popMenu = new JPopupMenu();
        p_popMenu.addPopupMenuListener(new PopupListener());
        p_entryJList.setComponentPopupMenu(p_popMenu);
        p_scrollPane = new JScrollPane(p_entryJList);
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));   
        p_scrollPane.setPreferredSize(new Dimension(140, 390));

        this.add(p_scrollPane, BorderLayout.CENTER);
    }
    
    public void addMListener(MouseListener ml) {
        p_entryJList.addMouseListener(ml);
    }
    
    public void registerAction(UIAction ui_ac, Action ac) {
        switch(ui_ac) {
            case MENU_EXPORT:
                break;
            case MENU_QUIT:
                break;
            case MENU_NEW_ENTRY:
                break;
            case SELECT_ENTRY:
                
                break;
            case POPUP_DELETE:
                p_popMenu.add(new JMenuItem(ac));
                
                break;
            default:
                throw new AssertionError(ui_ac.name());
            }
    }
    
    class PopupListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        }
        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    }
}
