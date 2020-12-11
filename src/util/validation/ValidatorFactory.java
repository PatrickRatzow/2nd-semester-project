package util.validation;

public class ValidatorFactory {
	private static volatile ValidatorFactory factory;
	
	public static ValidatorFactory getInstance() {
		if (factory == null) {
			synchronized (Validator.class) {
				if (factory == null) {
					factory = new ValidatorFactory();
				}
			}
		}
		
		return factory;
	}
	private ValidatorFactory() {}
	
	public Validator createValidator() {
		return new Validator();
	}
}
