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
            System.out.println("Failed to init/load the database.");
            System.exit(-1);
        }
        p_exporter = new ExportToFile(p_storage);
        p_gui = new GUI();
        p_validation = new ValidationService();
        

        // register the individual validators
        p_validation.attach(new ArticleValidator());
        p_validation.attach(new BookValidator());
        p_validation.attach(new InproceedingsValidator());
    }

    public static void main(String[] args) {
        App.getInstance();
    }

    public static Storage getStorage() {
        if(p_instance == null) {
            System.out.println("No App instance!?");
            System.exit(-1);
        }

        return p_instance.p_storage;
    }
    
    public static Exporter getExporter() {
        if(p_instance == null) {
            System.out.println("No App instance!?");
            System.exit(-1);
        }
        
        return p_instance.p_exporter;
    }
    
    public static ValidationService getValidationService() {
        if (p_instance == null) {
            throw new RuntimeException("No App instance available");
        }

        return p_instance.p_validation;
    }

    private static App getInstance() {
        if (p_instance == null) {
            p_instance = new App();
        }

        return p_instance;
    }
}
