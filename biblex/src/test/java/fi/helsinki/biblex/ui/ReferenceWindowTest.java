package fi.helsinki.biblex.ui;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import fi.helsinki.biblex.domain.BibTexStyle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;

public class ReferenceWindowTest {
    private ReferenceWindow win;

    @Before
    public void setUp() {
        // skipataan GUI testaus, jos ympäristö ei ole graafinen (esim. Jenkins)
        assumeTrue(!GraphicsEnvironment.isHeadless());

        win = new ReferenceWindow();
        win.setEntry("Test", "Test");
    }


    @After
    public void tearDown() {
        if (win != null)
            win.dispose();
    }


    @Test
    public void testSetEntryClearsFields() {
        win.addField("test", "");
        assertTrue(win.iterator().hasNext());

        win.setEntry("Test", "Test");
        assertFalse(win.iterator().hasNext());
    }


    @Test
    public void testAddAndGetEntryStyle() {
        assertNull(win.getEntryStyleInput());

        win.addEntryStyle(BibTexStyle.ARTICLE);
        assertEquals(BibTexStyle.ARTICLE, win.getEntryStyleInput());

        win.addEntryStyle(BibTexStyle.BOOK);
        assertEquals(BibTexStyle.ARTICLE, win.getEntryStyleInput());
    }


    @Test
    public void testAddField() {
        // window should contain no fields
        assumeTrue(!win.iterator().hasNext());

        win.addField("\tTest ", "Test ");
        assertTrue(win.iterator().hasNext());

        // field name should be converted to lower-case, with excess whitespace removed
        assertEquals("test", win.iterator().next().getKey());

        // field contents should be inserted as is
        assertEquals("Test ", win.iterator().next().getValue());

        // test that adding 2nd field succeeds
        win.addField("Test 2", "");
        Iterator<Map.Entry<String, String>> it = win.iterator();
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());

        // clear fields
        win.setEntry("Test", "Test");
    }


    @Test
    public void testDeleteField() {
        // window should contain no fields
        assumeTrue(!win.iterator().hasNext());

        win.addField("test1", "");
        win.addField("test2", "");
        win.deleteField("test1");
        assertEquals("test2", win.iterator().next().getKey());

        win.addField("\tOddName ", "test");

        // test deleting non-existent field
        try {
            win.deleteField("\tOddName ");
            fail("No exception on deleteField() with invalid name");
        } catch (RuntimeException e) {}

        // test deleting field with sanitized name
        win.deleteField("oddname");
        assertEquals("test2", win.iterator().next().getKey());

        // test content field gets cleared
        win.addField("\tOddName", "");
        win.deleteField("test2");
        assertEquals("", win.iterator().next().getValue());

        win.deleteField("oddname");
        assertFalse(win.iterator().hasNext());
    }


    @Test
    public void testIterable() {
        // window should contain no fields
        assumeTrue(!win.iterator().hasNext());

        win.addField("test", "content");
        for (Map.Entry<String, String> e : win) {
            assertEquals("test", e.getKey());
            assertEquals("content", e.getValue());
        }
    }
}
