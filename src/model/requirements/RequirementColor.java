package model.requirements;

import model.Requirement;
import util.validation.Validator;
import util.validation.rules.StringNotEmptyValidationRule;

public class RequirementColor extends Requirement<String> {
    @Override
    public String getName() {
        return "Farve";
    }

    @Override
    public String getId() {
        return "color";
    }

    @Override
    public String getSQLKey() {
        return getId();
    }

    @Override
    public String getSQLValue() {
        return getValue();
    };

    @Override
    public void setValueFromSQLValue(String sql) {
        setValue(sql);
    }
    
    @Override
    public void validate() throws Exception {
        Validator validator = new Validator();
        validator.addRule(new StringNotEmptyValidationRule(getSQLValue(), getName() + " må ikke være tom!"));
        
        if (validator.hasErrors()) {
            throw validator.getCompositeException();
        }
    }
}
