package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
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

/**
 *
 * @author jtmikkon
 */
public class GuiTest extends TestCase {
    
    private GUI gui;
    private EntryPane entryPane;
    private MainWindow mainWindow;
    private ReferenceWindow refWindow;
    private App app;
    
    public GuiTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
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
    public void testTest() {
        assertTrue(true);
    }
}
