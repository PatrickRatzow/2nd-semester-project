package util.validation;

public abstract class ValidationRule<T> implements Validatable {
	private final String errorReplacement;
	private final T rule;
	
	public ValidationRule(T rule) {
		this(rule, "");
	}
	
	public ValidationRule(T rule, String errorReplacement) {
		this.rule = rule;
		this.errorReplacement = errorReplacement;
	}
	
	protected Exception createException(String error) throws Exception {
		throw new Exception(getErrorMessage(error));
	}
	
	private String getErrorMessage(String error) {
		if (errorReplacement.isEmpty()) {
			return error;
		}
		
		return errorReplacement;
	}
	
	public T getRule() {
		return rule;
	}
}
