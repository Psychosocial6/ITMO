package utils;

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
    public void validate(FacesContext facesContext, UIComponent uiComponent, BigDecimal value) throws ValidatorException {
        if (value.compareTo(BigDecimal.valueOf(3)) > 0 || value.compareTo(BigDecimal.valueOf(-3)) < 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Значение Y должно быть от -3 до 3"));
        }
    }
}
