package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.category.FestTest;
import fi.helsinki.biblex.exporter.Exporter;
import fi.helsinki.biblex.storage.Storage;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.junit.FixMethodOrder;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

/**
 *
 * @author jtmikkon
 */
@Category(FestTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GuiTest {
    
    private GUI gui;
    private JFrame entryPane;
    private JFrame mainWindow;
    private JFrame refWindow;
    private App app;
    private Storage storage;
    private Exporter exporter;
    private FrameFixture testFrame;
    
    /**
     * Diipa.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    
//    public GuiTest() throws InstantiationException, IllegalAccessException {
//        app.createInstance();
//        app = App.getInstance();
//        gui = app.getP_gui();
//        refWindow = gui.getWindow();
//        testFrame = new FrameFixture(refWindow);
//        testFrame.show();
//        
//    }
    
    
    
    @Before
    public void setUp() {
        App.createInstance();
        app = App.getInstance();
        gui = app.getGUI();
        refWindow = gui.getWindow();
        testFrame = new FrameFixture(refWindow);
        testFrame.show();
        
    }
    
    @After
    public void tearDown() {
        testFrame.cleanUp();
        App.deleteInstance();
        app = null;
        gui = null;
        refWindow = null;
        testFrame = null;
    }
  
    @Test
    public void requireErrorMessageWhenTextBoxEmptyTest() {
        testFrame.comboBox().selectItem("inproceedings");
        Pause.pause(500);
        testFrame.comboBox().requireSelection("inproceedings");
        Pause.pause(500);
        //testFrame.textBox().enterText("lols");
        testFrame.button("p_setEntryButton").click();
        Pause.pause(500);
        //testFrame.button("p_submitButton").click();
        testFrame.optionPane().requireErrorMessage();
        testFrame.optionPane().button().click();
        
    }

    
    @Test
    public void requireErrorMessageWhenClickingCreateWithEmptyFields() {
        testFrame.comboBox().selectItem("article");
        Pause.pause(500);
        testFrame.comboBox().requireSelection("article");
        Pause.pause(500);
        testFrame.textBox("p_entryNameInput").enterText("omgtesti");
        Pause.pause(500);
        testFrame.button("p_setEntryButton").click();
        Pause.pause(500);
        testFrame.button("p_submitButton").click();
        
        testFrame.optionPane().requireErrorMessage();
        
    }
   
}

