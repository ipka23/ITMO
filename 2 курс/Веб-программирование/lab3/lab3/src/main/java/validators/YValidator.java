package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;

@FacesValidator("yValidator")
public class YValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal y) throws ValidatorException {
        try {
            if (y.compareTo(BigDecimal.valueOf(-3)) >= 0 && y.compareTo(BigDecimal.valueOf(3)) <= 0) {

            } else {
                throw new ValidatorException(new FacesMessage("Y должен быть в диапазоне [-3, 3]!"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage("Y должен быть числом!"));
        }
    }
}
