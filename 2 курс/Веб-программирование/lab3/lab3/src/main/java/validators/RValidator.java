package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;

@FacesValidator("rValidator")
public class RValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal r) throws ValidatorException {
        try {
            if (r.compareTo(BigDecimal.valueOf(1)) >= 0 && r.compareTo(BigDecimal.valueOf(3)) <= 0) {

            } else {
                throw new ValidatorException(new FacesMessage("R должен быть в диапазоне [1, 3]!"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage("R должен быть числом!"));
        }
    }
}
