package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;

@FacesValidator("xValidator")
public class XValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal x) throws ValidatorException {
        try {
            if (x.compareTo(BigDecimal.valueOf(-3)) >= 0 && x.compareTo(BigDecimal.valueOf(5)) <= 0) {

            } else {
                throw new ValidatorException(new FacesMessage("X должен быть в диапазоне [-3, 5]!"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage("X должен быть числом!"));
        }
    }
}
