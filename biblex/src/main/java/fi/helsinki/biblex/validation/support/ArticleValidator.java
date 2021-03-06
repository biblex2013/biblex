package fi.helsinki.biblex.validation.support;

import fi.helsinki.biblex.domain.BibTexStyle;
import fi.helsinki.biblex.validation.AbstractValidator;

/**
 * The validator object for reference style 'article'.
 */
public class ArticleValidator extends AbstractValidator {
    private static final BibTexStyle STYLE = BibTexStyle.ARTICLE;
    private static final String[] REQUIRED_FIELDS = {
        "author",
        "title",
        "journal",
        "year",
    };

    private static final String[] OPTIONAL_FIELDS = {
        "volume",
        "number",
        "pages",
        "month",
        "note",
    };

    private static final ExclusiveField[] EXCLUSIVE_FIELDS = {
    };

    public ArticleValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS, EXCLUSIVE_FIELDS);
    }

    public String validateAndGetErrorMessage(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        if (super.getSetOfRequiredFields().contains(s) && value.isEmpty()) {
            return "Field '" + s
                    + "' is a required field, yet its value is empty.";
        }

        // Simply check whether the field is standard for style 'article'.
        return (super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s)) ?
                null :
                "Reference style 'article' does not support field '" + s + "'";
    }
}
