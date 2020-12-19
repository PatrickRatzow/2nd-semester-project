package model.requirements;

import model.Requirement;
import util.Converter;
import util.validation.Validator;
import util.validation.rules.IntegerRangeValidationRule;
import util.validation.rules.StringNotEmptyValidationRule;

public class RequirementWidth extends Requirement<Integer> {
    @Override
    public String getName() {
        return "Bredde";
    }

    @Override
    public String getId() {
        return "width";
    }

    @Override
    public String getSQLKey() {
        return getId();
    }

    @Override
    public String getSQLValue() {
        return String.valueOf(getValue());
    }

    @Override
    public void setValueFromSQLValue(String sql) {
        setValue(Converter.tryParse(sql));
    }
    
    @Override
    public void validate() throws Exception {
        Validator validator = new Validator();
        validator.addRule(new StringNotEmptyValidationRule(getSQLValue(), getName() + " må ikke være tom!"));
        validator.addRule(new IntegerRangeValidationRule(getValue(), getName() + " skal være over 1!", 1, 2_000_000));
        
        if (validator.hasErrors()) {
            throw validator.getCompositeException();
        }
    }
}
