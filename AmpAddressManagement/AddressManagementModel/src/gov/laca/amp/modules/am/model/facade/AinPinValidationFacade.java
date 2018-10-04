package gov.laca.amp.modules.am.model.facade;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.extn.facade.AmpProxyFacade;
import gov.laca.amp.proxy.soap.ainpinvalidationservice.local.AINPINValidationServiceWrapper;
import gov.laca.amp.proxy.soap.ainpinvalidationservice.client.FaultMessage;

public class AinPinValidationFacade extends AmpProxyFacade{
    public AinPinValidationFacade() {
        super();
    }
    
    public boolean validateAinPin(String ain, String pin) throws AmpException, FaultMessage {
        logger.error(AinPinValidationFacade.class, "validateAinPin()", "Start validateAinPin",
                     null);
        AINPINValidationServiceWrapper wrapper = new AINPINValidationServiceWrapper();
        boolean isValid = wrapper.ainPinValidation(ain, pin);
        logger.error(AinPinValidationFacade.class, "validateAinPin()", "End validateAinPin",
                     null);
        return isValid;
    }
}
