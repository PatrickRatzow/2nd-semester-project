package util.validation.rules;

import util.validation.ValidationRule;

public class ZipCodeValidationRule extends ValidationRule<Integer> {
	public ZipCodeValidationRule(Integer rule) {
		super(rule);
	}
	
	public ZipCodeValidationRule(Integer rule, String errorReplacement) {
		super(rule, errorReplacement);
	}
	
	@Override
	public void validate() throws Exception {
		int rule = getRule();
		if (rule < 0) {
			throw createException("Ugyldig postnummer! Skal være et positiv tal mellem 0-9999");
		}
		if (rule > 9999) {
			throw createException("Ugyldig postnummer! Må ikke være højere end 9999");
		}
	}
}
