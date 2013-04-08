//// SHIT - FEEL FREE TO MODIFY ////

package fi.helsinki.biblex;

import fi.helsinki.biblex.ui.BiblexGUI;
import fi.helsinki.biblex.validation.*;
import fi.helsinki.biblex.validation.support.*;

/**
 * Singleton-style main class for the application
 */
public class App {
	private static App p_instance;

	private BiblexGUI p_gui;
	private ValidationService p_validation;


	private App() {
		p_gui = new BiblexGUI();
		p_validation = new ValidationService();

		// register the individual validators
		p_validation.attach(new ArticleValidator());
		p_validation.attach(new BookValidator());
		p_validation.attach(new InproceedingsValidator());
	}


    public static void main( String[] args ) {
		App.getInstance();
    }


	public static ValidationService getValidationService() {
		if (p_instance == null)
			throw new RuntimeException("No App instance available");

		return p_instance.p_validation;
	}


	private static App getInstance() {
		if (p_instance == null)
			p_instance = new App();

		return p_instance;
	}
}
