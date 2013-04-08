package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;
import fi.helsinki.biblex.validation.ValidationException;
import fi.helsinki.biblex.validation.support.ArticleValidator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
* This class handles the application GUI
*/
public class BiblexGUI {
    private JFrame p_window;

    private JPanel p_pane;
    private JScrollPane p_scrollPane;
    private JMenuBar p_menu;

    private JComboBox p_entryStyleInput;
    private JTextField p_entryNameInput;
    private JTextField p_fieldNameInput;

    private AbstractAction p_submitAction;
    private AbstractAction p_addFieldAction;
    private AbstractAction p_deleteFieldAction;
    private AbstractAction p_setEntryAction;

    private BibTexEntry p_entry;

    // Keep track of field name -> UI element pairs
    private List<Map.Entry<String, JPanel>> p_fieldMap;


    public BiblexGUI() {
        p_window = new JFrame();

        p_fieldMap = new ArrayList<Map.Entry<String, JPanel>>();

        p_window.setSize(550, 350);
        p_window.setMinimumSize(new Dimension(450, 300));
        p_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createActions();
        populate();
        setTitle();

        p_window.setVisible(true);
    }


    /**
     * Add UI elements to the window, and setup action handling
     */
    private void populate() {
        p_menu = new JMenuBar();
        p_window.setJMenuBar(p_menu);

        JPanel topPane = new JPanel();
        p_entryStyleInput = new JComboBox();
        p_entryNameInput = new JTextField();

        topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
        topPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        topPane.add(new JLabel("Viitteen tyyppi:"));
        topPane.add(Box.createRigidArea(new Dimension(5, 0)));
        topPane.add(p_entryStyleInput);
        topPane.add(Box.createRigidArea(new Dimension(10, 0)));
        topPane.add(new JLabel("Viitteen nimi:"));
        topPane.add(Box.createRigidArea(new Dimension(5, 0)));
        topPane.add(p_entryNameInput);
        topPane.add(Box.createRigidArea(new Dimension(5, 0)));
        topPane.add(new JButton(p_setEntryAction));

        JPanel bottomSubPane = new JPanel();
        p_fieldNameInput = new JTextField();
        bottomSubPane.setLayout(new BoxLayout(bottomSubPane, BoxLayout.X_AXIS));
        bottomSubPane.add(Box.createHorizontalGlue());
        bottomSubPane.add(p_fieldNameInput);
        bottomSubPane.add(Box.createRigidArea(new Dimension(3, 0)));
        bottomSubPane.add(new JButton(p_addFieldAction));

        JPanel bottomPane = new JPanel();
        GridLayout bottomLayout = new GridLayout(0, 1);
        bottomLayout.setVgap(3);
        bottomPane.setLayout(bottomLayout);
        bottomPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        bottomPane.add(bottomSubPane);
        bottomPane.add(new JButton(p_submitAction));

        p_pane = new JPanel();
        p_pane.setLayout(new BoxLayout(p_pane, BoxLayout.Y_AXIS));

        p_scrollPane = new JScrollPane(p_pane);
        p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p_scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));

        p_window.getContentPane().add(topPane, BorderLayout.NORTH);
        p_window.getContentPane().add(p_scrollPane, BorderLayout.CENTER);
        p_window.getContentPane().add(bottomPane, BorderLayout.SOUTH);

        populateEntryStyles();
    }


    /**
     * Add a new field to the current entry
     *
     * @param name Name/ID of the field to add
     */
    private void addField(String name) {
        if (p_entry == null || name.trim().isEmpty())
            return;

        // ei lisätä samaa kenttää kahdesti
        if (p_entry.containsField(name))
            return;

        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

        JLabel nameLabel = new JLabel(name.trim().toLowerCase());
        JTextField nameField = new JTextField();

        nameLabel.setMinimumSize(new Dimension(150, 20));
        nameLabel.setPreferredSize(new Dimension(150, 20));
        nameLabel.setMaximumSize(new Dimension(225, 20));
        nameField.setMinimumSize(new Dimension(225, 20));
        nameField.setPreferredSize(new Dimension(225, 20));
        nameField.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));

        pane.add(nameLabel);
        pane.add(Box.createRigidArea(new Dimension(10, 0)));
        pane.add(nameField);
        pane.add(Box.createRigidArea(new Dimension(3, 0)));

        JButton deleteButton = new JButton(p_deleteFieldAction);
        deleteButton.setActionCommand(name);
        pane.add(deleteButton);

        p_fieldMap.add(new AbstractMap.SimpleEntry<String, JPanel>(name, pane));
        p_entry.put(name, "");

        p_pane.add(pane);
        p_pane.validate();
        p_scrollPane.validate();
    }


    /**
     * Remove a field from the current entry
     *
     * @param name Name/ID of the field to be removed
     */
    private void deleteField(String name) {
        for (int i = 0; i < p_fieldMap.size(); i++) {
            if (p_fieldMap.get(i).getKey().equals(name)) {
                p_pane.remove(p_fieldMap.get(i).getValue());
				p_entry.remove(name);
                p_fieldMap.remove(i);

                p_pane.revalidate();
                p_pane.repaint();

                return;
            }
        }

        // Tänne ei pitäisi ikinä päästä...
        throw new RuntimeException("Delete field failed, field not found (" + name + ")");
    }


    /**
     * Add all known entry styles to the style selection combo-box
     */
    private void populateEntryStyles() {
        for (BibTexStyle style : BibTexStyle.values()) {
            p_entryStyleInput.addItem(style);
        }
    }


    /**
     * Set the name and style of the current entry
     *
     * Also removes all fields from the current entry,
     * and adds all required fields for the selected entry style.
     */
    private void setEntry(BibTexStyle style, String name) {
        if (style == null || name.isEmpty())
            return;

        p_entry = new BibTexEntry(name, style);

        p_pane.removeAll();

        p_scrollPane.setBorder(BorderFactory.createTitledBorder(style.name() + " - " + name));

		// TODO: ValidatorService --> Get correct validator
		AbstractValidator validator = new ArticleValidator();
		for (String field : validator.getSetOfRequiredFields()) {
			addField(field);
		}

        p_pane.repaint();
    }


    private void submitEntry() {
        if (p_entry == null)
            return;

		try {
			for (Map.Entry<String, JPanel> field : p_fieldMap) {
				// melko ruma tapa etsiä oikea tekstikenttä...
				String value = ((JTextField) field.getValue().getComponent(2)).getText();

				p_entry.put(field.getKey(), value);
				App.getValidationService().checkValidity(p_entry.getStyle(), field.getKey(), value);
			}

		} catch (ValidationException e) {
			displayException(e, "Validation failed");
			return;
		}

        // TODO: Actually do something with the entry
        System.out.println(p_entry.toString());
    }


    private void setTitle() {
        if (p_entry == null || p_entry.getName().isEmpty()) {
            p_window.setTitle("Biblex");
        } else {
            p_window.setTitle("Biblex - '" + p_entry.getName() + "'");
        }
    }


	private void displayException(Throwable exp, String title) {
		JOptionPane.showMessageDialog(p_window, exp.getMessage(), title, JOptionPane.ERROR_MESSAGE);
	}


    /**
     * Set up the actions to be used in the UI
     */
    private void createActions() {
        // TODO: tread safety for action handlers

        p_submitAction = new AbstractAction("Tallenna Viite", UIManager.getIcon("FileView.hardDriveIcon")) {
            public void actionPerformed(ActionEvent e) {
                submitEntry();
            }
        };

        p_addFieldAction = new AbstractAction("Lisää Kenttä") {
            public void actionPerformed(ActionEvent e) {
                addField(p_fieldNameInput.getText());
                p_fieldNameInput.setText("");
            }
        };

        p_deleteFieldAction = new AbstractAction("", UIManager.getIcon("InternalFrame.paletteCloseIcon")) {
            public void actionPerformed(ActionEvent e) {
                deleteField(e.getActionCommand());
            }
        };

        p_setEntryAction = new AbstractAction("Luo") {
            public void actionPerformed(ActionEvent e) {
                setEntry(
                        (BibTexStyle) p_entryStyleInput.getSelectedItem(),
                        p_entryNameInput.getText()
                );
            }
        };
    }
}
