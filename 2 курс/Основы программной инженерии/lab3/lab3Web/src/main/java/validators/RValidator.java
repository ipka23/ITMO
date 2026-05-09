package validators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;
import java.util.ResourceBundle;

@FacesValidator("rValidator")
public class RValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal r) throws ValidatorException {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
        String rMustBeInRange = bundle.getString("r_must_be_in_range");
        String rMustBeNumber = bundle.getString("r_must_be_number");
        try {
            if (r.compareTo(BigDecimal.valueOf(1)) >= 0 && r.compareTo(BigDecimal.valueOf(3)) <= 0) {

            } else {
                throw new ValidatorException(new FacesMessage(rMustBeInRange));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(rMustBeNumber));
        }
    }
}
