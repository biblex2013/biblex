/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.biblex.validation;

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
}