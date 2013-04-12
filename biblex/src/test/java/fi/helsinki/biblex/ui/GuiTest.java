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
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.fest.swing.timing.Timeout;

/**
 *
 * @author jtmikkon
 */
public class GuiTest {
    
    private GUI gui;
    private JFrame entryPane;
    private JFrame mainWindow;
    private JFrame refWindow;
    private App app;
    private Storage storage;
    private Exporter exporter;
    private FrameFixture testFrame;
    private FrameFixture mainWindowFrameFixture;
    
    
    public GuiTest() throws InstantiationException, IllegalAccessException {
        app.createInstance();
        app = App.getInstance();
        gui = app.getP_gui();
        refWindow = gui.getWindow();
        
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
    }
    
    @Test
    public void refWindowTest() {
        FrameFixture testFrame = new FrameFixture(refWindow);
        testFrame.show();
        
        testFrame.comboBox().selectItem("inproceedings");
        testFrame.comboBox().requireSelection("inproceedings");
        
        //testFrame.textBox().enterText("lols");
        testFrame.button("p_submitButton").click();
        
        
    }
}
