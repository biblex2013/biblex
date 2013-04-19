package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;

/**
 * The validator object for reference style 'inproceedings'.
 */
public class InproceedingsValidator extends AbstractValidator {
    private static final BibTexStyle STYLE = BibTexStyle.INPROCEEDINGS;
    private static final String[] REQUIRED_FIELDS = {
        "author",
        "title",
        "booktitle",
        "year",
    };

    private static final String[] OPTIONAL_FIELDS = {
        "editor",
        "series",
        "pages",
        "address",
        "month",
        "organization",
        "publisher",
        "note",
    };

    private static final ExclusiveField[] EXCLUSIVE_FIELDS = {
        // optional exclusive:
        new ExclusiveField(false, "volume", "number"),
    };

    public InproceedingsValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS, EXCLUSIVE_FIELDS);
    }

    public String validateAndGetErrorMessage(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        if (super.getSetOfRequiredFields().contains(s) && value.isEmpty()) {
            return "Field '" + s
                    + "' is a required field, yet its value is empty.";
        }

        // Simply check whether the field is standard for style 'article';
        // ignore the value.
        return (super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s)) ?
                null :
                "Reference style 'inproceedings' does not support field '"
                + s + "'";
    }
}
