package fi.helsinki.biblex.validation;

import fi.helsinki.biblex.domain.BibTexEntry;
import fi.helsinki.biblex.domain.BibTexStyle;
import java.util.HashMap;
import java.util.Map;

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

    public void checkValidity(BibTexStyle style,
                              String fieldName,
                              String value) throws ValidationException {
        if (!m.containsKey(style)) {
            throw new ValidationException("Reference type unknown: " + style);
        }

        if (!m.get(style).isValid(fieldName, value)) {
            throw new ValidationException(
                    "Reference type '" + style + "' did not accept '"
                    + value + "' on field '" + fieldName + "'.");
        }
    }
}
