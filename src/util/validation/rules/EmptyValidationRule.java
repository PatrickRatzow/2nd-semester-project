package util.validation.rules;


import util.validation.ValidationRule;

public class EmptyValidationRule extends ValidationRule<String> {
	public EmptyValidationRule(String rule) {
		super(rule);
	}
	
	public EmptyValidationRule(String rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		if (getRule().isEmpty()) {
			throw createException("String is empty");
		}
	}
}
