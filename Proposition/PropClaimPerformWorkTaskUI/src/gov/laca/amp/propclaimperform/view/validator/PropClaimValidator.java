package gov.laca.amp.propclaimperform.view.validator;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PropClaimValidator implements Serializable {
    @SuppressWarnings("compatibility:3607512742168399357")
    private static final long serialVersionUID = 1L;
    public PropClaimValidator() {
        super();
    }

    public void percentageTransferValidate(FacesContext facesContext, UIComponent uIComponent, Object object) throws ValidatorException {
        // TODO Implement this method

        try {
           
              BigDecimal input=  new BigDecimal(object.toString());
            System.out.println("original value =="+input);
              BigDecimal standardMax = BigDecimal.valueOf(100.0000);
              BigDecimal standardMin = BigDecimal.valueOf(0);
//              input =input.setScale(4, BigDecimal.ROUND_UP);
//               System.out.println("input---"+input);
              if (input.compareTo(standardMax)>0 ) {
                  FacesMessage msg = new FacesMessage("Validation failed.", 
                          "Percentage Transfer must be less that 100");
                  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msg); 
              } 
            System.out.println("input.compareTo(standardMin)---"+input.compareTo(standardMin));
              if (input.compareTo(standardMin) < 0 ) {
                  FacesMessage msg = new FacesMessage("Validation failed.", 
                          "Percentage Transfer must be greater that 0");
                  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msg); 
              } 
            
          } catch (NumberFormatException ex) {
              FacesMessage msg = new FacesMessage("Validation failed.", "Not a valid Percentage Transfer.");
              msg.setSeverity(FacesMessage.SEVERITY_ERROR);
              throw new ValidatorException(msg); 
          }
        }
}
