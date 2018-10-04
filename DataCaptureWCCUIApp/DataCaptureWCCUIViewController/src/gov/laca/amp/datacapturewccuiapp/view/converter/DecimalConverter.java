package gov.laca.amp.datacapturewccuiapp.view.converter;

import java.math.BigDecimal;

import java.math.RoundingMode;

import java.text.DecimalFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import javax.faces.convert.ConverterException;

import javax.faces.convert.FacesConverter;

import oracle.adf.view.rich.context.AdfFacesContext;

@FacesConverter("DecimalConverter")
public class DecimalConverter implements Converter {
    public DecimalConverter() {
        super();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if(string != null && string.trim().length() > 0) {
            try {
               
                AdfFacesContext.getCurrentInstance().addPartialTarget(uIComponent);
                
                String val = (new BigDecimal(string)).setScale(4, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
                return new BigDecimal(val);
            } catch(Exception e) {
                e.printStackTrace();
                FacesMessage msg =  new FacesMessage("The value is not a number.", 
                                                                "Enter valid value.");
                                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        throw new ConverterException(msg);
            }
            
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {

        DecimalFormat df = new DecimalFormat("###.####");
        return object != null ? df.format(object) : null;
    }
}
