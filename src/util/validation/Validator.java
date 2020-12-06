package util.validation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Validator {
    Collection<Exception> errors = new LinkedList<>();

    public void addRule(ValidationRule<?> rule) {
        try {
            rule.validate();
        } catch (Exception e) {
            errors.add(e);
        }
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public Collection<Exception> getErrors() {
        return errors;
    }

    public Exception getCompositeException() {
        return new Exception(errors.stream()
                .map(Exception::getMessage)
                .collect(Collectors.joining("\n")));
    }

    public void addError(String error) {
        errors.add(new Exception(error));
    }
}
