package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.exporter.Exporter;
import fi.helsinki.biblex.storage.Storage;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assume.*;
import static org.junit.Assert.*;
import org.fest.util.*;
import org.fest.assertions.*;
import org.fest.swing.annotation.GUITest;
import org.fest.swing.*;
import org.fest.swing.core.matcher.DialogMatcher;
import org.fest.swing.fixture.ContainerFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author jtmikkon
 */
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
    
    public GuiTest() throws InstantiationException, IllegalAccessException {
        app.createInstance();
        app = App.getInstance();
        gui = app.getP_gui();
        refWindow = gui.getWindow();
        testFrame = new FrameFixture(refWindow);
        testFrame.show();
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        assumeTrue(!GraphicsEnvironment.isHeadless());
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        testFrame.cleanUp();
        testFrame = new FrameFixture(app.getP_gui().getWindow());
        
    }
  
    @Test
    public void requireErrorMessageWhenTextBoxEmptyTest() {
       
        Pause.pause(500);
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

    /*
    @Test
    public void requireErrorMessageWhenClickingCreateWithEmptyFields() {
        Pause.pause(500);
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
    */
}

