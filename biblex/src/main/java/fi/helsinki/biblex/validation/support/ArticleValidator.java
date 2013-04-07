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

    public ArticleValidator() {
        super(STYLE, REQUIRED_FIELDS, OPTIONAL_FIELDS);
    }

    public boolean isValid(String fieldName, String value) {
        String s = fieldName; // Shortcut.
        checkArguments(s, value);

        // Simply check whether the field is standard for style 'article';
        // ignore the value.
        return super.getSetOfRequiredFields().contains(s)
                || super.getSetOfOptionalFields().contains(s);
    }
}
