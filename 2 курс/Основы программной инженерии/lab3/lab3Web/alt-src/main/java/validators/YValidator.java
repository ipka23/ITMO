packageAltvalidators;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.math.BigDecimal;
import java.util.ResourceBundle;

@FacesValidator("yValidator")
public class YValidator implements Validator<BigDecimal> {
    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal y) throws ValidatorException {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
        String yMustBeInRange = bundle.getString("y_must_be_in_range");
        String yMustBeNumber = bundle.getString("y_must_be_number");

        try {
            if (y.compareTo(BigDecimal.valueOf(-3)) >= 0 && y.compareTo(BigDecimal.valueOf(3)) <= 0) {
            } else {
                throw new ValidatorException(new FacesMessage(yMustBeInRange));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(yMustBeNumber));
        }
    }
}