package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.storage.Storage;
import fi.helsinki.biblex.storage.SQLiteStorage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jtmikkon
 */
public class ExportToFileTest {
    private Storage stor;
    private BibTexEntry entry1, entry2;
    private Exporter exporter;
    
    public ExportToFileTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            stor = new SQLiteStorage(":memory:");
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
        entry1 = new BibTexEntry("name1", BibTexStyle.ARTICLE);
        entry1.put("author", "author1");
        entry1.put("title", "title1");
        entry1.put("journal", "journal1");
        entry1.put("year", "1600");
        
        entry2 = new BibTexEntry("name2", BibTexStyle.INPROCEEDINGS);
        entry2.put("author", "author2");
        entry2.put("title", "title2");
        entry2.put("booktitle", "booktitle2");
        entry2.put("year", "1900");
        exporter = new ExportToFile();
        try {
            stor.add(entry1);
            stor.add(entry2);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
    }
    
    @After
    public void tearDown() {
        File file = new File("test1.txt");
        file.delete();
    }

    /**
     * Test of write method, of class ExportToFile.
     */
    @Test
    public void testWrite_String_Storage() throws Exception {
        String filename = "test1.txt";
        
        exporter.write(filename, stor);

        fail("Test not implemented yet.");
    }

    /**
     * Test of write method, of class ExportToFile.
     */
    @Test
    public void testWrite_File_Storage() {
        fail("Test not implemented yet.");
    }
}
