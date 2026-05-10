packageAltvalidators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;
import java.util.ResourceBundle;

@FacesValidator("xValidator")
public class XValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal x) throws ValidatorException {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
        String xMustBeInRange = bundle.getString("x_must_be_in_range");
        String xMustBeNumber = bundle.getString("x_must_be_number");

        try {
            if (x.compareTo(BigDecimal.valueOf(-3)) >= 0 && x.compareTo(BigDecimal.valueOf(5)) <= 0) {
            } else {
                throw new ValidatorException(new FacesMessage(xMustBeInRange));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(xMustBeNumber));
        }
    }
}