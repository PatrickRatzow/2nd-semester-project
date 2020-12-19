package util.validation.rules;

import util.validation.ValidationRule;

public class StringNotEmptyValidationRule extends ValidationRule<String> {
	public StringNotEmptyValidationRule(String rule) {
		super(rule);
	}
	
	public StringNotEmptyValidationRule(String rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		String rule = getRule();
		if (rule == null) {
			throw createException("String is null!");
		}
		
		if (getRule().isEmpty()) {
			throw createException("String is empty");
		}
	}
}
