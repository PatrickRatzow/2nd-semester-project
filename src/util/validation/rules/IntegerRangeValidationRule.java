package util.validation.rules;

import util.validation.ValidationRule;

public class IntegerRangeValidationRule extends ValidationRule<Integer> {
	private final int min;
	private final int max;
	
	public IntegerRangeValidationRule(Integer rule, int min, int max) {
		super(rule);
		
		this.min = min;
		this.max = max;
	}
	
	public IntegerRangeValidationRule(Integer rule, String errorReplacement, int min, int max) {
		super(rule, errorReplacement);
		
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void validate() throws Exception {
		int value = getRule();
		if (value > max) {
			throw createException("Integer is too high. (actual: " + value + ". expects max: " + max);
		}
		if (value < min) {
			throw createException("Integer is too low. (actual: " + value + ". expects min: " + min);
		}
	}
}
