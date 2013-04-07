package fi.helsinki.biblex.validation;

import fi.helsinki.biblex.domain.BibTexStyle;
import java.util.HashSet;
import java.util.Set;

/**
 * This interface defines the API for the actual validating objects.
 */
public abstract class AbstractValidator {
    private final BibTexStyle targetStyle;
    // Protected as to be visible in implementation classes.
    protected final Set<String> requiredFieldsSet;
    protected final Set<String> optionalFieldsSet;

    public AbstractValidator(BibTexStyle targetStyle,
                             String[] requiredFields,
                             String[] optionalFields) {
        this.targetStyle = targetStyle;
        requiredFieldsSet = new HashSet<String>();
        optionalFieldsSet = new HashSet<String>();
        java.util.Collections.addAll(requiredFieldsSet, requiredFields);
        java.util.Collections.addAll(optionalFieldsSet, optionalFields);
    }

    public abstract boolean isValid(String fieldName, String value);

    public BibTexStyle getTargetStyle() {
        return targetStyle;
    }

    public Set<String> getSetOfRequiredFields() {
        return java.util.Collections.unmodifiableSet(requiredFieldsSet);
    }

    public Set<String> getSetOfOptionalFields() {
        return java.util.Collections.unmodifiableSet(optionalFieldsSet);
    }

    public static void checkArguments(String fieldName, String value) {
        if (fieldName == null) {
            throw new IllegalArgumentException("'fieldName' may not be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("'value' may not be null.");
        }
    }
}
