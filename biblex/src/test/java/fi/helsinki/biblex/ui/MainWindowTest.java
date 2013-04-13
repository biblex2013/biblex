package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import javax.swing.JFrame;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.fest.util.*;
import org.fest.assertions.*;
import org.fest.swing.annotation.GUITest;
import org.fest.swing.*;
import org.fest.swing.fixture.FrameFixture;

/**
 *
 * @author jtmikkon
 */
public class MainWindowTest extends TestCase {
    
    private GUI gui;
    private EntryPane entryPane;
    private JFrame mainWindow;
    private ReferenceWindow refWindow;
    private App app;
    
    private FrameFixture main;
    
    
        
//    @Before
//    public void setUp() {
//        app = App.getInstance();
//        gui = app.getP_gui();
//        mainWindow = gui.getWindow();
//        
//        main = new FrameFixture(mainWindow);
//    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void testTest() {
        assertTrue(true);
    }
}
