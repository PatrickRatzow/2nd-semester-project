package util.validation.rules;

import util.validation.ValidationRule;

public class PhoneValidationRule extends ValidationRule<String> {
	public PhoneValidationRule(String rule) {
		super(rule);
	}
	
	public PhoneValidationRule(String rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		String rule = getRule();
		int length = rule.length();
		if (length < 3) {
			throw createException("Telefonnummeret er for kort! Mindst 3 tal");
		}
		if (length > 8) {
			throw createException("Telefonnummeret er for langt! Maks 8 tal");
		}
	}
}
