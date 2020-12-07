package util.validation.rules;

import util.validation.ValidationRule;

public class StringToIntegerValidationRule extends ValidationRule<String> {
	public StringToIntegerValidationRule(String rule) {
		super(rule);
	}
	
	public StringToIntegerValidationRule(String rule, String replacement) {
		super(rule, replacement);
	}
	
	@Override
	public void validate() throws Exception {
		String rule = getRule();
		if (rule.isEmpty()) {
			throw createException("Integer is not set");
		}
		try {
			Integer.parseInt(rule);
		} catch (NumberFormatException e) {
			throw createException("Integer is not a number!");
		}
	}
}
