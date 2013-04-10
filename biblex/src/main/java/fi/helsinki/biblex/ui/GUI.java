package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;
import fi.helsinki.biblex.validation.ValidationException;
import fi.helsinki.biblex.validation.support.ArticleValidator;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * This class handles the application GUI The appearance of the interface is
 * handled by the Window class.
 */
public class GUI {

    private Window p_window;
    private BibTexEntry p_entry;

    public GUI() {
        p_window = new Window();

        populateEntryStyles();
        createActions();
    }

    /**
     * Add all known entry styles to the style selection combo-box
     */
    private void populateEntryStyles() {
        for (BibTexStyle style : BibTexStyle.values()) {
            p_window.addEntryStyle(style);
        }
    }

    /**
     * Set the name and style of the current entry
     *
     * Also removes all fields from the current entry, and adds all required
     * fields for the selected entry style.
     */
    private void setEntry(BibTexStyle style, String name) {
        if (style == null || name.trim().isEmpty()) {
            p_window.displayError("Reference name or type is invalid", "Reference creation failed");
            return;
        }

        p_entry = new BibTexEntry(name, style);
        p_window.setEntry(style.name(), name);

        AbstractValidator validator = App.getValidationService().getValidator(style);
        for (String field : validator.getSetOfRequiredFields()) {
            p_window.addField(field, "");
        }
    }

    private void submitEntry() {
        if (p_entry == null) {
            return;
        }

        try {
            for (Map.Entry<String, String> field : p_window) {
                p_entry.put(field.getKey(), field.getValue());
            }

            App.getValidationService().checkEntry(p_entry);

        } catch (ValidationException e) {
            p_window.displayError(e.getMessage(), "Validation failed");
            return;
        }

        try {
            App.getStorage().add(p_entry);
        } catch (Exception ex) {
            p_window.displayError(ex.getMessage(), "Saving failed");
            return;
        }
        System.out.println(p_entry.toString());
    }

    /**
     * Set up the actions to be used in the UI
     */
    private void createActions() {
        // TODO: thread safety for action handlers

        p_window.registerAction(
                Window.UIAction.SUBMIT,
                new AbstractAction("Save Reference", UIManager.getIcon("FileView.hardDriveIcon")) {

                    public void actionPerformed(ActionEvent e) {
                        submitEntry();
                    }
                });

        p_window.registerAction(
                Window.UIAction.ADD_FIELD,
                new AbstractAction("Add Field") {

                    public void actionPerformed(ActionEvent e) {
                        String name = p_window.getFieldNameEntry();
                        if (name.trim().isEmpty()) {
                            p_window.displayError("Field name cannot be empty", "Failed to add field");
                            return;
                        }

                        p_window.addField(name, "");
                        p_entry.put(name, "");
                        p_window.clearFieldNameEntry();
                    }
                });


        p_window.registerAction(
                Window.UIAction.DELETE_FIELD,
                new AbstractAction("", UIManager.getIcon("InternalFrame.paletteCloseIcon")) {

                    public void actionPerformed(ActionEvent e) {
                        p_window.deleteField(e.getActionCommand());
                        p_entry.remove(e.getActionCommand());
                    }
                });


        p_window.registerAction(
                Window.UIAction.SET_ENTRY,
                new AbstractAction("Create") {

                    public void actionPerformed(ActionEvent e) {
                        setEntry(p_window.getEntryStyleInput(), p_window.getEntryNameInput());
                        p_window.clearEntryNameInput();
                    }
                });

        p_window.registerAction(
                Window.UIAction.MENU_EXPORT,
                new AbstractAction("Export") {
                    @Override
                    /* Maybesti needs some fileselector thingie to select the outputfile */
                    public void actionPerformed(ActionEvent e) {
                        try {
                            App.getExporter().write("exported.bib");
                        } catch (IOException ex) {
                            p_window.displayError(ex.getMessage(), "Export failed");
                        }
                    }
                });

        p_window.registerAction(
                Window.UIAction.MENU_QUIT,
                new AbstractAction("Quit") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }

        });
    }
}
