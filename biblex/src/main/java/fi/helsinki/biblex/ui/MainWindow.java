package fi.helsinki.biblex.ui;

import javax.swing.*;
import java.awt.*;

/**
 * This class handles the visual appearance of the main UI window
 */
public class MainWindow {
    public static enum UIAction {
        MENU_EXPORT,
        MENU_QUIT,
        MENU_NEW_ENTRY
    }

    private JFrame p_window;

    private DefaultListModel p_entryList;
    private JScrollPane p_scrollPane;
    private JMenuBar p_menu;

    private JMenuItem p_menuNewEntry;
    private JMenuItem p_menuExport;
    private JMenuItem p_menuQuit;

    // Need to keep track of this, as each field has it's own button
    private Action p_deleteAction;


    public MainWindow() {
        p_window = new JFrame(GUI.APP_NAME);

        p_window.setSize(550, 450);
        p_window.setMinimumSize(new Dimension(475, 350));
        p_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        p_entryList = new DefaultListModel();

        populate();

        p_window.setVisible(true);
    }


    /**
     * Register action handler for a specific UI action
     *
     * @param uiAction Action ID, for which the action handler is set
     * @param action Action, to be set as the action handler
     */
    public void registerAction(UIAction uiAction, Action action) {
        switch (uiAction) {
            case MENU_QUIT:
                p_menuQuit.setAction(action);
                return;
                
            case MENU_EXPORT:
                p_menuExport.setAction(action);
                return;

            case MENU_NEW_ENTRY:
                p_menuNewEntry.setAction(action);
                return;
               
            default:
                // action not supported
                assert(false);
        }
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
     * Remove a field from the current entry
     *
     * @param name Name/ID of the field to be removed
     */
    public void deleteField(String name) {
        // Tänne ei pitäisi ikinä päästä...
        throw new RuntimeException("Delete field failed, field not found (" + name + ")");
    }


    /**
     * Display an error dialog with a given message
     *
     * @param err Error message to display
     * @param title Title of the error dialog window
     */
    public void displayError(String err, String title) {
        JOptionPane.showMessageDialog(p_window, err, title, JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Dispose of the window
     */
    public void dispose() {
        p_window.dispose();
    }


    /**
     * Add UI elements to the window, and setup action handling
     */
    private void populate() {
        p_menu = new JMenuBar();
        p_window.setJMenuBar(p_menu);

        JMenu fileMenu = new JMenu("File");
        p_menuNewEntry = new JMenuItem();
        p_menuExport = new JMenuItem();
        p_menuQuit = new JMenuItem();

        fileMenu.add(p_menuNewEntry);
        fileMenu.add(new JSeparator());
        fileMenu.add(p_menuExport);
        fileMenu.add(new JSeparator());
        fileMenu.add(p_menuQuit);

        p_menu.add(fileMenu);

        p_scrollPane = new JScrollPane(new JList(p_entryList));
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        p_window.getContentPane().add(p_scrollPane, BorderLayout.CENTER);
    }
}
