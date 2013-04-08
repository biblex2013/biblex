package fi.helsinki.biblex.storage;

import fi.helsinki.biblex.domain.BibTexEntry;
import java.util.Iterator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SQLiteStorageTest {
    private Storage s;
    private BibTexEntry king, martin;

    @Before
    public void setUp() {
        try {
            s = new SQLiteStorage(":memory:");
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Before
    public void makeEntries() {
        king = new BibTexEntry("king", "article");
        king.put("author", "Stephen King");
        king.put("title", "On Fear Distribution");

        martin = new BibTexEntry("martin", "book");
        martin.put("author", "Robert Martin");
        martin.put("title", "Clean Code: A Handbook of Agile Software Craftsmanship");
        martin.put("year", "2008");
        martin.put("publisher", "Prentice Hall");
    }

    @Test
    public void testAddAndGet() {
        try {
            s.add(king);
        } catch (Exception king) {
            fail(king.toString());
        }

        BibTexEntry e = s.get(king.getName());
        assertEquals(king.toString(), e.toString());
    }

    @Test
    public void testDoubleAdd() {
        try {
            s.add(king);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            s.add(king);
            fail("double add: should fail");
        } catch (Exception e) {
        }
    }

    @Test
    public void testIterator() {
        try {
            s.add(king);
            s.add(martin);
        } catch (Exception e) {
            fail(e.toString());
        }

        Iterator<BibTexEntry> it = s.iterator();
        assertNotNull(it);
        assertTrue(it.hasNext());

        assertEquals(king.toString(), it.next().toString());
        assertTrue(it.hasNext());

        assertEquals(martin.toString(), it.next().toString());
        assertFalse(it.hasNext());
    }
}
