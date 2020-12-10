package util.validation.rules;

import util.validation.ValidationRule;

public class IntegerMinimumValidationRule extends ValidationRule<Integer> {
	private final int min;
	
	public IntegerMinimumValidationRule(Integer rule, int min) {
		super(rule);
		
		this.min = min;
	}
	
	public IntegerMinimumValidationRule(Integer rule, String errorReplacement, int min) {
		super(rule, errorReplacement);
		
		this.min = min;
	}
	
	@Override
	public void validate() throws Exception {
		int value = getRule();
		if (value < min) {
			throw createException("Integer is too low. (actual: " + value + ". expects min: " + min);
		}
	}
}
