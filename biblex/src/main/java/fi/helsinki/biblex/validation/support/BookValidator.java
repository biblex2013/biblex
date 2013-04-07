package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;

/**
 * The validator object for reference style 'book'.
 */
public class BookValidator extends AbstractValidator {
    private static final BibTexStyle STYLE = BibTexStyle.BOOK;
    private static final String[] REQUIRED_FIELDS = {
        "author",
        "editor",
        "title",
        "publisher",
        "year",
    };

    private static final String[] OPTIONAL_FIELDS = {
        "volume",
        "number",
        "series",
        "address",
        "edition",
        "month",
        "note"
    };

    public BookValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS);
    }

    public boolean isValid(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        // Simply check whether the field is standard for style 'book';
        // ignore the value.
        return super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s);
    }
}
