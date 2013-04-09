package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * The test for InproceedingsValidator.
 */
public class InproceedingsValidatorTest {

    private AbstractValidator v;

    @Before
    public void setUp() {
        v = new InproceedingsValidator();
    }

    @Test
    public void testGetStyle() {
        assertTrue(v.getTargetStyle() == BibTexStyle.INPROCEEDINGS);
    }

    @Test
    public void testRequiredFields() {
        assertEquals(4, v.getSetOfRequiredFields().size());
        assertTrue(v.getSetOfRequiredFields().contains("author"));
        assertFalse(v.getSetOfRequiredFields().contains("fdsafds"));
    }

    @Test
    public void testOptionalFields() {
        assertEquals(10, v.getSetOfOptionalFields().size());
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
        v.validateAndGetErrorMessage(null, "value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValue() {
        v.validateAndGetErrorMessage("author", null);
    }

    @Test
    public void testValidation() {
        assertNull(v.validateAndGetErrorMessage("author", "Thor"));
        assertNull(v.validateAndGetErrorMessage("volume", "I"));
        assertNotNull(v.validateAndGetErrorMessage("epic", "fail"));
        assertNotNull(v.validateAndGetErrorMessage("author", ""));
    }
}
