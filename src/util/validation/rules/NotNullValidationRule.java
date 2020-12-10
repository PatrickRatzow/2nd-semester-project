package util.validation.rules;

import util.validation.ValidationRule;

public class NotNullValidationRule<T> extends ValidationRule<T> {
	public NotNullValidationRule(T rule) {
		super(rule);
	}
	
	public NotNullValidationRule(T rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		if (getRule() == null) {
			throw createException("Object is null!");
		}
	}
}
