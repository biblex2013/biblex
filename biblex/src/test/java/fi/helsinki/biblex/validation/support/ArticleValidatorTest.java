/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rodionefremov
 */
public class ArticleValidatorTest {

    private AbstractValidator v;

    @Before
    public void setUp() {
        v = new ArticleValidator();
    }

    @Test
    public void testGetStyle() {
        assertTrue(v.getTargetStyle() == BibTexStyle.ARTICLE);
    }

    @Test
    public void testRequiredFields() {
        assertEquals(4, v.getSetOfRequiredFields().size());
        assertTrue(v.getSetOfRequiredFields().contains("author"));
        assertFalse(v.getSetOfRequiredFields().contains("fdsafds"));
    }

    @Test
    public void testOptionalFields() {
        assertEquals(5, v.getSetOfOptionalFields().size());
        assertTrue(v.getSetOfOptionalFields().contains("volume"));
        assertFalse(v.getSetOfOptionalFields().contains("fdsafds"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRequiredFieldsAdd() {
        v.getSetOfRequiredFields().add("yo");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRequiredFieldsRemove() {
        v.getSetOfRequiredFields().remove("author");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOptionalFieldsAdd() {
        v.getSetOfRequiredFields().add("yo");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOptionalFieldsRemove() {
        v.getSetOfRequiredFields().remove("author");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullField() {
        v.isValid(null, "value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValue() {
        v.isValid("author", null);
    }

    @Test
    public void testValidation() {
        assertTrue(v.isValid("author", "Thor"));
        assertTrue(v.isValid("volume", "I"));
        assertFalse(v.isValid("epic", "fail"));
    }
}
