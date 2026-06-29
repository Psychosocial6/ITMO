package utils;

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
    public void validate(FacesContext facesContext, UIComponent uiComponent, BigDecimal value) throws ValidatorException {
        if (value.compareTo(BigDecimal.valueOf(5)) > 0 || value.compareTo(BigDecimal.valueOf(-5)) < 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Значение X должно быть от -5 до 5"));
        }
    }
}
