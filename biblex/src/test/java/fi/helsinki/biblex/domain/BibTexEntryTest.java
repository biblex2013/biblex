package fi.helsinki.biblex.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BibTexEntryTest {

    private BibTexEntry e;

    @Before
    public void setUp() {
        e = new BibTexEntry("king", BibTexStyle.ARTICLE);
        e.put("author", "Stephen King");
        e.put("title", "On Fear Distribution");
        e.put("journal", "Äöå!");
    }

    @Test
    public void testPutGetToArticle() {
        assertEquals(e.get("author"), "Stephen King");
        assertEquals(e.get("title"), "On Fear Distribution");
    }

    @Test
    public void testRemoveAndContains() {
        assertTrue(e.containsField("author"));

        String removedValue = e.remove("author");
        assertEquals(removedValue, "Stephen King");
        assertFalse(e.containsField("author"));

        removedValue = e.remove("author");
        assertNull(removedValue);
    }

    @Test
    public void testStyleGetAndNameGet() {
        System.out.println(e.getStyle());
        assertEquals(e.getStyle(), BibTexStyle.ARTICLE);
        assertEquals(e.getName(), "king");
    }

    @Test
    public void testToString() {
        System.out.println(e);
        String target = "@article{ king,\n"
                      + "          author = {Stephen King},\n"
                      + "          journal = {\\\"{A}\\\"{o}\\aa!},\n"
                      + "          title = {On Fear Distribution},\n"
                      + "}";
        assertEquals(e.toString(), target);
    }
    
    @Test
    public void testDataToString() {       
        assertTrue(e.dataToString().contains("king"));
        assertTrue(e.dataToString().contains("stephen king"));
        assertTrue(e.dataToString().contains("ear dist")); //On Fear Distribution
    }
    
    @Test
    public void testDataToStringÄäkköset() {
        assertTrue("äöå etc should print to string.", e.dataToString().contains("äöå!")); 
    }
    
    @Test
    public void testDataToStringIsLowCase() {
        assertFalse("String should be in lowercase.", e.dataToString().contains("Stephen King"));
 
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameThrowsException() {
        new BibTexEntry(null, BibTexStyle.BOOK);
    }
}
