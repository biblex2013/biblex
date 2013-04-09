package fi.helsinki.biblex.exporter;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.storage.SQLiteStorage;
import fi.helsinki.biblex.storage.Storage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.*;


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
    public static void tearDownClass() throws FileNotFoundException, IOException {
        File file1 = new File("test1.txt");
        File file2 = new File("test2.txt");
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        String read1 = "", read2 = "";
        while(read1 != null && read2 != null) {
            read1 = reader1.readLine();
            read2 = reader2.readLine();
            assertEquals(read1,read2);
        }
        
        assertNull(read1);
        assertNull(read2);
        file1.delete();
        file2.delete();
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
        exporter = new ExportToFile(stor);
        try {
            stor.add(entry1);
            stor.add(entry2);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
    }
    
    @After
    public void tearDown() {
 
    }

    /**
     * Test of write method, of class ExportToFile.
     */
    @Test
    public void testWrite_String() {
        String filename = "test1.txt";
        try {
            exporter.write(filename);
        } catch (IOException ex) {
            fail("Caught unexpected exception: " + ex.getMessage());
        }
        
        String filename2 = null;
        boolean thrown = false;
        try {
            exporter.write(filename2);
            fail("Should have thrown exception!");
        } catch (Exception ex) {
            thrown = true;
        }
        
        assertTrue(thrown);
        
        File file = new File("test1.txt");
        
        assertTrue(file.exists());
        assertTrue(file.length() > 10);
    }

    /**
     * Test of write method, of class ExportToFile.
     */
    @Test
    public void testWrite_File() {
        File file = new File("test2.txt");
        try {
            exporter.write(file);
        } catch (IOException ex) {
            fail("Caught undesired exception: " + ex.getMessage());
        }
        
        File file2 = null;
        boolean thrown = false;
        try {
            exporter.write(file2);
            fail("Should have thrown exception!");
        } catch (Exception ex) {
            thrown = true;
        }
        
        assertTrue(thrown);
        assertTrue(file.exists());
        assertTrue(file.length() > 10);
        
    }
    
    @Test
    public void testEndOfLine() {
        ExportToFile exp = (ExportToFile) exporter;
        exp.setNewLineFormat(ExportToFile.OS.WIN);
        assertTrue(exp.getNewLineFormat().equals("\r\n"));
        exp.setNewLineFormat(ExportToFile.OS.UNIX);
        assertTrue(exp.getNewLineFormat().equals("\n"));
        exp.setNewLineFormat(ExportToFile.OS.MAC);
        assertTrue(exp.getNewLineFormat().equals(("\n")));
        
        exp.setNewLineFormat(null);
        
    }
}
