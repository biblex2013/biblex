package fi.helsinki.biblex;

import fi.helsinki.biblex.exporter.ExportToFile;
import fi.helsinki.biblex.exporter.Exporter;
import fi.helsinki.biblex.storage.SQLiteStorage;
import fi.helsinki.biblex.storage.Storage;
import fi.helsinki.biblex.ui.GUI;
import fi.helsinki.biblex.validation.*;
import fi.helsinki.biblex.validation.support.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton-style main class for the application
 */
public class App {

    private static App p_instance;

    private GUI p_gui;
    private ValidationService p_validation;
    private Storage p_storage;
    private Exporter p_exporter;

    private App() {
        try {
            p_storage = new SQLiteStorage("database.dat");  
        } catch (Exception ex) {
            System.out.println("Failed to init/load the database: " + ex.toString());
            System.exit(1);
        }

        p_exporter = new ExportToFile(p_storage);
        p_validation = new ValidationService();

        // register the individual validators
        p_validation.attach(new ArticleValidator());
        p_validation.attach(new BookValidator());
        p_validation.attach(new InproceedingsValidator());

        p_gui = new GUI();
    }

    private void run() {
        p_gui.init();
    }

    public static void main(String[] args) {
        p_instance = new App();
        p_instance.run();
    }

    public static Storage getStorage() {
        return getInstance().p_storage;
    }
    
    public static Exporter getExporter() {
        return getInstance().p_exporter;
    }
    
    public static ValidationService getValidationService() {
        return getInstance().p_validation;
    }

    private static App getInstance() {
        if (p_instance == null) {
            throw new RuntimeException("No App instance available");
        }

        return p_instance;
    }
}
