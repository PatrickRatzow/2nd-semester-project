package util.validation.rules;

import util.validation.ValidationRule;

import java.util.regex.Pattern;

public class EmailValidationRule extends ValidationRule<String> {
	// RFC 5322 compliant Regex. Source: http://emailregex.com/
	private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	
	public EmailValidationRule(String rule) {
		super(rule);
	}
	
	public EmailValidationRule(String rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		String rule = getRule();
		if (rule.isEmpty()) {
			throw createException("Emailen er tom!");
		}
		if (!emailPattern.matcher(rule).find()) {
			throw createException("Emailen er ikke gyldig!");
		}
	}
}
