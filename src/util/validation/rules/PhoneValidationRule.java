package util.validation.rules;

import util.validation.ValidationRule;

import java.util.regex.Pattern;

public class PhoneValidationRule extends ValidationRule<String> {
	private static final Pattern matchPattern = Pattern.compile("[0-9]{3,8}");
	
	public PhoneValidationRule(String rule) {
		super(rule);
	}
	
	public PhoneValidationRule(String rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		String rule = getRule();
		// Trim all spaces
		rule = rule.replaceAll("\\s", "");
		
		if (!matchPattern.matcher(rule).matches()) {
			throw createException("Ikke gyldig telefonnummer! Telefonnummer skal være tal, og 3-8 tal lange!");
		}
	}
}
