package fi.helsinki.biblex.validation;

import fi.helsinki.biblex.domain.BibTexStyle;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * This interface defines the API for the actual validating objects.
 */
public abstract class AbstractValidator {
    private final BibTexStyle targetStyle;
    // Protected as to be visible in implementation classes.
    protected final Set<String> requiredFieldsSet;
    protected final Set<String> optionalFieldsSet;
    protected final Set<ExclusiveField> exclusiveFieldsSet;

    /**
     * Container class for information on mutually exclusive fields
     */
    public static class ExclusiveField {
        private final Set<String> fields;
        private final boolean required;

        public ExclusiveField(boolean required, String... fields) {
            this.required = required;

            this.fields = new TreeSet<String>();
            java.util.Collections.addAll(this.fields, fields);
        }

        public Set<String> getSetOfFields() {
            return java.util.Collections.unmodifiableSet(this.fields);
        }

        public boolean isRequired() {
            return required;
        }
    }


    public AbstractValidator(BibTexStyle targetStyle,
                             String[] requiredFields,
                             String[] optionalFields,
                             ExclusiveField[] exclusiveFields) {
        this.targetStyle = targetStyle;
        requiredFieldsSet = new HashSet<String>();
        optionalFieldsSet = new HashSet<String>();
        exclusiveFieldsSet = new HashSet<ExclusiveField>();
        java.util.Collections.addAll(requiredFieldsSet, requiredFields);
        java.util.Collections.addAll(optionalFieldsSet, optionalFields);
        java.util.Collections.addAll(exclusiveFieldsSet, exclusiveFields);
    }

    /**
     * Validates the <code>fieldName</code> and <code>value</code> against
     * this target style.
     *
     * @param fieldName the name of a field (the key).
     * @param value the value of a field.
     * @return <code>null</code> if validation was successful, or an error
     * message for <code>ValidationException</code>-object.
     */
    public abstract String validateAndGetErrorMessage(String fieldName,
                                                      String value);

    public BibTexStyle getTargetStyle() {
        return targetStyle;
    }

    public Set<String> getSetOfRequiredFields() {
        return java.util.Collections.unmodifiableSet(requiredFieldsSet);
    }

    public Set<String> getSetOfOptionalFields() {
        return java.util.Collections.unmodifiableSet(optionalFieldsSet);
    }

    public Set<ExclusiveField> getSetOfExclusiveFields() {
        return java.util.Collections.unmodifiableSet(exclusiveFieldsSet);
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
