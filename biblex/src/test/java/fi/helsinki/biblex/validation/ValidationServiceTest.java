/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.biblex.validation;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.support.ArticleValidator;
import fi.helsinki.biblex.validation.support.BookValidator;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author rodionefremov
 */
public class ValidationServiceTest {

    private static ValidationService service;

    @BeforeClass
    public static void setUpClass() {
        service = new ValidationService();
        service.attach(new ArticleValidator());
        service.attach(new BookValidator());
    }

    @Test
    public void testIsAttached() {
        assertTrue(service.styleIsSupported(BibTexStyle.ARTICLE));
        assertTrue(service.styleIsSupported(BibTexStyle.BOOK));
        assertFalse(service.styleIsSupported(BibTexStyle.INPROCEEDINGS));
    }

    @Test(expected = ValidationException.class)
    public void testThrowsOnUnsupportedStyle() throws ValidationException {
        service.checkValidity(BibTexStyle.INPROCEEDINGS, "author", "Thor");
    }

    @Test(expected = ValidationException.class)
    public void testThrowsOnNonstandardField() throws ValidationException {
        service.checkValidity(BibTexStyle.ARTICLE, "galaxy", "sw");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullField() throws ValidationException {
        service.checkValidity(BibTexStyle.ARTICLE, null, "Thor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsOnNullValue() throws ValidationException {
        service.checkValidity(BibTexStyle.ARTICLE, "author", null);
    }

    @Test(expected = ValidationException.class)
    public void testThrowsOnMissingRequiredField() throws ValidationException {
        BibTexEntry e = new BibTexEntry("namu", BibTexStyle.ARTICLE);
        e.put("author", "Thor");
        e.put("title", "Title");
        e.put("year", "2013");
        // 'journal' is missing
        service.checkEntry(e);
    }

    @Test(expected = ValidationException.class)
    public void testRequiredFieldValueMayNotBeEmptyString()
            throws ValidationException {
        BibTexEntry e = new BibTexEntry("namu", BibTexStyle.ARTICLE);
        e.put("author", "Thor");
        e.put("title", "Title");
        e.put("year", "2013");
        e.put("journal", "");
        // 'journal' is empty str
        service.checkEntry(e);
    }

    @Test
    public void testDoesNotThrowOnAccept()
            throws ValidationException {
        service.checkValidity(BibTexStyle.ARTICLE, "author", "Thor");
        assertTrue(true);
    }
}
