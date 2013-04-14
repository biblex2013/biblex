package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.category.FestTest;
import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.exporter.Exporter;
import fi.helsinki.biblex.storage.Storage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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
        testFrame.comboBox().requireSelection("inproceedings");
        testFrame.button("p_setEntryButton").click();
        testFrame.optionPane().requireErrorMessage();
        testFrame.optionPane().button().click();
        
    }
    
    @Test
    public void requireErrorMessageWhenClickingCreateWithEmptyFields() {
        testFrame.comboBox().selectItem("article");
        testFrame.comboBox().requireSelection("article");
        testFrame.textBox("p_entryNameInput").enterText("omgtesti");
        testFrame.button("p_setEntryButton").click();
        testFrame.button("p_submitButton").click();
        
        testFrame.optionPane().requireErrorMessage();
        
    }
    
    @Test
    public void allIsWellWhenFieldsAreFull() {
        testFrame.comboBox().selectItem("article");
        testFrame.textBox("p_entryNameInput").enterText("tester");
        testFrame.button("p_setEntryButton").click();
        testFrame.textBox("year").enterText("year");
        testFrame.textBox("journal").enterText("journal");
        testFrame.textBox("author").enterText("author");
        testFrame.textBox("title").enterText("title");
        
        testFrame.button("p_submitButton").click();
        
        Storage stor = app.getStorage();
        BibTexEntry entry = stor.get("tester");
        
        assertNotNull(entry);
        try {
            stor.delete(entry.getId());
        } catch (Exception ex) {
            fail("failed to delete tester entry from DB");
        }
    }
   
}

