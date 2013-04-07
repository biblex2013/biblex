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
        "volume",
        "number",
        "series",
        "pages",
        "address",
        "month",
        "organization",
        "publisher",
        "note",
    };

    public InproceedingsValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS);
    }

    public boolean isValid(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        // Simply check whether the field is standard for style 'inproceedings';
        // ignore the value.
        return super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s);
    }
}
