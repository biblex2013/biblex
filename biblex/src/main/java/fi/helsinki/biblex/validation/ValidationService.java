package fi.helsinki.biblex.validation;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class defines an extensible validator.
 */
public class ValidationService {
    private Map<BibTexStyle, AbstractValidator> m;

    public ValidationService() {
        m = new HashMap<BibTexStyle, AbstractValidator>();
    }

    public AbstractValidator attach(AbstractValidator validator) {
        return m.put(validator.getTargetStyle(), validator);
    }

    public boolean styleIsSupported(BibTexStyle style) {
        return m.containsKey(style);
    }

    public AbstractValidator getValidator(BibTexStyle style) {
        return m.get(style);
    }

    public void checkValidity(BibTexStyle style,
                              String fieldName,
                              String value) throws ValidationException {
        if (!m.containsKey(style)) {
            throw new ValidationException("Reference style unknown: " + style);
        }

        String errorMsg = m.get(style).validateAndGetErrorMessage(fieldName, value);

        if (errorMsg != null) {
            throw new ValidationException(errorMsg);
        }

        // Once here, everything's fine.
    }

    public void checkEntry(BibTexEntry entry) throws ValidationException {
        for (Map.Entry<String, String> mapping : entry) {
            checkValidity(entry.getStyle(),
                          mapping.getKey(),
                          mapping.getValue());
        }

        // Check required fields
        Set<String> req = m.get(entry.getStyle()).getSetOfRequiredFields();
        for (String field : req) {
            if (!entry.containsField(field)) {
                throw new ValidationException(
                        "Field '" + field + "' is required but is missing.");
            } else if (entry.get(field).isEmpty()) {
                throw new ValidationException(
                        "Field '" + field +
                        "' is required but maps to an empty string.");
            }
        }

        // Check mutually exclusive fields
        Set<AbstractValidator.ExclusiveField> excl = m.get(entry.getStyle()).getSetOfExclusiveFields();
        for (AbstractValidator.ExclusiveField fieldData : excl) {
            boolean exists = false;

            for (String field : fieldData.getSetOfFields()) {
                if (entry.containsField(field)) {
                    if (!exists) {
                        exists = true;
                    } else {
                        throw new ValidationException(
                                "Entry must only have one of the following fields: " +
                                fieldData.getSetOfFields() + ".");
                    }
                }
            }

            if (!exists && fieldData.isRequired()) {
                throw new ValidationException(
                        "One of the fields " + fieldData.getSetOfFields() +
                        " is required, but none exist.");
            }
        }
    }
}
