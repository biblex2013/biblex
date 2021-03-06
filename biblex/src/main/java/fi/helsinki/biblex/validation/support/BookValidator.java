package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;

/**
 * The validator object for reference style 'book'.
 */
public class BookValidator extends AbstractValidator {
    private static final BibTexStyle STYLE = BibTexStyle.BOOK;
    private static final String[] REQUIRED_FIELDS = {
        "title",
        "publisher",
        "year",
    };

    private static final String[] OPTIONAL_FIELDS = {
        "series",
        "address",
        "edition",
        "month",
        "note",
    };

    private static final ExclusiveField[] EXCLUSIVE_FIELDS = {
        // required exclusive:
        new ExclusiveField(true, "author", "editor"),
        // optional exclusive:
        new ExclusiveField(false, "volume", "number"),
    };

    public BookValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS, EXCLUSIVE_FIELDS);
    }

    public String validateAndGetErrorMessage(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        if (super.getSetOfRequiredFields().contains(s) && value.isEmpty()) {
            return "Field '" + s
                    + "' is a required field, yet its value is empty.";
        }

        // Simply check whether the field is standard for style 'book'.
        return (super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s)) ?
                null :
                "Reference style 'book' does not support field '" + s + "'";
    }
}
