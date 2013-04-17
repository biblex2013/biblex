package fi.helsinki.biblex.ui;

import fi.helsinki.biblex.App;
import fi.helsinki.biblex.category.FestTest;
import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.exporter.Exporter;
import fi.helsinki.biblex.storage.Storage;
import java.io.File;
import javax.swing.JFrame;
import net.java.openjdk.cacio.ctc.junit.CacioFESTRunner;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 *
 * @author jtmikkon
 */
@RunWith(CacioFESTRunner.class)
@Category(FestTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GuiTest {

    private GUI gui;
    private JFrame refWindow;
    private App app;
    private Storage storage;
    private FrameFixture testFrame;

    /**
     * Some GUI testings.
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    //   public GuiTest() {
    //   }
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        File file = new File("database.dat");
        file.deleteOnExit();
        assertTrue(file.exists());
    }

    @Before
    public void setUp() {
        App.createInstance();
        app = App.getInstance();
        gui = app.getGUI();
        refWindow = gui.getWindow();
        testFrame = new FrameFixture(refWindow);
        storage = app.getStorage();
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
    public void BBrequireErrorMessageWhenTextBoxEmptyTest() {
        testFrame.comboBox().selectItem("inproceedings");
        testFrame.comboBox().requireSelection("inproceedings");
        testFrame.button("p_setEntryButton").click();
        testFrame.optionPane().requireErrorMessage();
        testFrame.optionPane().button().click();

    }

    @Test
    public void CCrequireErrorMessageWhenClickingCreateWithEmptyFields() {
        testFrame.comboBox().selectItem("article");
        testFrame.comboBox().requireSelection("article");
        testFrame.textBox("p_entryNameInput").enterText("omgtesti");
        testFrame.button("p_setEntryButton").click();
        testFrame.button("p_submitButton").click();

        testFrame.optionPane().requireErrorMessage();

    }

    //Jostain syystä:database is locked.
    //@Test
    public void DDallIsWellWhenFieldsAreFull() {
        testFrame.comboBox().selectItem("article");
        testFrame.textBox("p_entryNameInput").enterText("tester");
        testFrame.button("p_setEntryButton").click();
        testFrame.textBox("year").enterText("tester");
        testFrame.textBox("journal").enterText("tester");
        testFrame.textBox("author").enterText("tester");
        testFrame.textBox("title").enterText("tester");
        Pause.pause(1000);

        testFrame.button("p_submitButton").click();

        //Storage stor = app.getStorage();

        BibTexEntry entry = storage.get("tester");

        assertNotNull(entry);

        //Pause.pause(6000);
        if (entry != null) {
            try {
                storage.delete(entry.getId());
            } catch (Exception ex) {
                fail("DDallIsWell.. NOT: " + ex.toString());
            }
        }
    }

    @Test
    public void AAexportingReallyExportsTest() {

        addStuffToDatabase();
        testFrame.menuItem("p_menuExport").click();
        testFrame.fileChooser().focus();
        testFrame.fileChooser().setCurrentDirectory(new File(System.getProperty("user.dir")));
        testFrame.fileChooser().fileNameTextBox().enterText("exportFestTest.bib");
        testFrame.fileChooser().approveButton().click();

        String fileString = System.getProperty("user.dir") + System.getProperty("file.separator") + "exportFestTest.bib";
        File testExportFile = new File(fileString);

        System.out.println("FILE: " + testExportFile.getAbsolutePath());
        assertTrue(testExportFile.exists());

        testExportFile.delete();

        removeStuffFromDatabase();
    }

    private void addStuffToDatabase() {
        //Storage stor = app.getStorage();

        if (storage.get("article1") == null) {
            testFrame.focus();
            testFrame.comboBox().selectItem("article");
            testFrame.textBox("p_entryNameInput").enterText("article1");
            testFrame.button("p_setEntryButton").click();
            testFrame.textBox("year").enterText("year");
            testFrame.textBox("journal").enterText("journal");
            testFrame.textBox("author").enterText("author");
            testFrame.textBox("title").enterText("title");

            testFrame.button("p_submitButton").click();
        }

        if (storage.get("article2") == null) {
            testFrame.focus();
            testFrame.comboBox().selectItem("article");
            testFrame.textBox("p_entryNameInput").enterText("article2");
            testFrame.button("p_setEntryButton").click();
            testFrame.textBox("year").enterText("year22");
            testFrame.textBox("journal").enterText("journal22");
            testFrame.textBox("author").enterText("author22");
            testFrame.textBox("title").enterText("title22");

            testFrame.button("p_submitButton").click();
        }
    }

    private void removeStuffFromDatabase() {
        //Storage stor = app.getStorage();
        BibTexEntry article1 = storage.get("article1");
        BibTexEntry article2 = storage.get("article2");
        System.out.println(article1.getId());
        System.out.println(article2.getId());
        assertNotNull(article1);
        assertNotNull(article2);
        try {
            storage.delete(article1.getId());
            storage.delete(article2.getId());
        } catch (Exception ex) {

            fail("failed to delete entry in remoteStuffFromDatabase()" + ex.getMessage());
        }
    }
}
