package util.validation;

import java.util.Collection;
import java.util.Stack;
import java.util.stream.Collectors;

public class Validator {
	private final Stack<Exception> errors = new Stack<>();
	
	public void addRule(ValidationRule<?> rule) {
		try {
			rule.validate();
		} catch (Exception e) {
			errors.add(e);
		}
	}
	
	public void addException(Exception exception) {
		errors.add(exception);
	}
	
	public void addValidatable(Validatable validatable) {
		try {
			validatable.validate();
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
