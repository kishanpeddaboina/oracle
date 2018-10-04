package gov.laca.amp.datacapturewccuiapp.view.validator;

import java.math.BigDecimal;

import java.math.BigInteger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PercentValidator")
public class PercentValidator implements Validator {
    
    private static final double minval = 0;
    private static final double maxval = 999.9999;
    private static final String errMsg = "Please enter valid percentage value.";
    public PercentValidator() {
        super();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) throws ValidatorException {
        // TODO Implement this method
        
        if(object != null) {
            double val;
            if(object instanceof  BigDecimal) {
                val = ((BigDecimal)object).doubleValue();
            } else if(object instanceof  BigInteger) {
                val = ((BigInteger)object).doubleValue();
            } else if(object instanceof  Integer) {
                val = ((Integer)object).doubleValue();
            } else if(object instanceof  Double) {
                val = ((Double)object).doubleValue();
            } else if(object instanceof  Float) {
                val = ((Float)object).doubleValue();
            } else  {
                 return;
            } 
            
            
            
           
           
            if(val < minval || val > maxval) {
                FacesMessage msg = new FacesMessage("Invalid Percentage Value", errMsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    }
}
