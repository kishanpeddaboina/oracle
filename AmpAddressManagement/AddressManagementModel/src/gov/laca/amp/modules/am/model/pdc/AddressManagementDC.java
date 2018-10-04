package gov.laca.amp.modules.am.model.pdc;

import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.extn.dc.AmpPojoDC;
import gov.laca.amp.modules.am.model.facade.AddressManagementFacade;
import gov.laca.amp.modules.am.model.facade.AinPinValidationFacade;
import gov.laca.amp.modules.am.model.pojo.AddressManagementPojo;

import gov.laca.amp.modules.am.model.pojo.AinPojo;

import gov.laca.amp.proxy.soap.addressmanagement.client.FaultMessage;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import oracle.adf.share.ADFContext;

import oracle.adf.share.security.identitymanagement.UserProfile;

import oracle.jbo.JboException;

public class AddressManagementDC extends AmpPojoDC{
    public AddressManagementDC() {
        super();
    }
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private AddressManagementPojo addressManagementPojo=null;
    AddressManagementFacade addressMngmntFacade = new AddressManagementFacade();
    private Boolean primaryStatus = Boolean.FALSE;
    AinPinValidationFacade ainPinValidationfacade = new AinPinValidationFacade();
    public void setAddressManagementPojo(AddressManagementPojo addressManagementPojo) {
        this.addressManagementPojo = addressManagementPojo;
    }

    public AddressManagementPojo getAddressManagementPojo() {
        return addressManagementPojo;
    }

    public String fetchCurrentOwnerShipInfo(String ain, String aoid, String primaryAin, String pageNavigation) {
             logger.info(AddressManagementDC.class, "fetchCurrentOwnerShipInfo()", "Start fetchCurrentOwnerShipInfo", null);
             String addPrimary =null;
             System.out.println("fetchCurrentOwnerShipInfo==========47======"+primaryAin+"======"+pageNavigation);
             if(ain == null || ain.trim().length() == 0)
                 return "Please enter AIN";
             ain = ain.replaceAll("-", "");
             List<AinPojo> ainsList= addressManagementPojo.getAinsPojo();
             if(ainsList==null){
                 ainsList = new ArrayList<AinPojo>();
             }
             if("PCL".equalsIgnoreCase(pageNavigation) && primaryStatus.equals(Boolean.FALSE)){
                 primaryStatus = Boolean.TRUE;
                 addPrimary ="YES";
             }
             try {
                 ainsList.addAll(addressMngmntFacade.retrieveOwnershipAddress(ain, aoid, primaryAin, addPrimary));
             } catch(Exception e)  {
                 logger.error("Error in fetchCurrentOwnerShipInfo", e);
                 if(e.getMessage() != null && e.getMessage().contains("Unable to find AO_ID from AIN"))
                     return "AIN is invalid. Please enter a valid AIN.";
                 else 
                     return "Error Adding AIN";
                  
             }
             addressManagementPojo.setAinsPojo(ainsList);
             addressManagementPojo.setAin(ain);
             logger.info(AddressManagementDC.class, "fetchCurrentOwnerShipInfo()", "End fetchCurrentOwnerShipInfo", null);
              return null;
         }
    
    public void removeAin(Integer currentRowIndex){
                logger.info(AddressManagementDC.class, "removeAin()", "Start removeAin", null);
                AinPojo ainPojo= addressManagementPojo.getAinsPojo().get(currentRowIndex);
                addressManagementPojo.getAinsPojo().remove(ainPojo);
                logger.info(AddressManagementDC.class, "removeAin()", "End removeAin", null);
            } 
    
    public void resetData(){
        logger.info(AddressManagementDC.class, "resetData()", "Start resetData", null);
         addressManagementPojo = new AddressManagementPojo();
        logger.info(AddressManagementDC.class, "resetData()", "End resetData", null);
    }
    
    public String saveAddressWorkUnit(String newMailingAddr) { 
        logger.info(AddressManagementDC.class, "saveAddressWorkUnit()", "Start saveAddressWorkUnit", null);
        String docSetId  = null;// "16441f03493:-4f98:-3247d8e6:2sbe7m";//"16441f03493:-3961:-3247d8e6:2sbe7m";
        try {
            addressManagementPojo.setNewMailingAddress(newMailingAddr);
            AddressManagementFacade addressMngmntFacade = new AddressManagementFacade();   
            
            docSetId=addressMngmntFacade.saveAddressWorkUnitFacade(this.addressManagementPojo);
            
           
        } catch(Exception e) {
            logger.error("Error in saveAddressWorkUnit", e);
            e.printStackTrace();
        }
        logger.info("saveAddressWorkUnit.. docSetId.. "+docSetId);
        logger.info(AddressManagementDC.class, "saveAddressWorkUnit()", "End saveAddressWorkUnit", null);
        return docSetId;
    }
    public String validateAinInForm(){
        logger.info(AddressManagementDC.class, "validateAinInForm()", "Start validateAinInForm", null);
        String ainsListMsg="success";
        List<AinPojo> ainsList=addressManagementPojo.getAinsPojo();
        if(ainsList.size()==0){
            ainsListMsg="failure"; 
        }
        logger.info(AddressManagementDC.class, "validateAinInForm()", "End validateAinInForm", null); 
        return ainsListMsg;
    }
    
    public void onPageLoad()throws AmpException, gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage{
        logger.info(AddressManagementDC.class, "onPageLoad()", "Start onPageLoad", null);
         addressManagementPojo = new AddressManagementPojo();
         String emailId= addressMngmntFacade.retriveEmailId();
        addressManagementPojo.setEmail(emailId);
        logger.info(AddressManagementDC.class, "onPageLoad()", "End onPageLoad", null);
    }
    
    public String duplicateAinCheck(String ain){
        if(ain == null || ain.trim().length() == 0)
            throw new JboException("Please enter AIN");
        ain = ain.replaceAll("-", "");
        String status ="Success";
        List<AinPojo> ainPojoList = addressManagementPojo.getAinsPojo();
        for (AinPojo r : ainPojoList) {
            if(ain.equals(r.getAin())){
                status="failure";
            }
        }
        return status;
        
    }
    
    public String fetchAccessLink(String docSetId) {
            AddressManagementFacade addressMngmntFacade = new AddressManagementFacade();
            try {
                String accessLink = addressMngmntFacade.fetchAccessLink(docSetId);
                logger.info("fetchAccessLink==accessLink"+accessLink);
                return accessLink;
            } catch(Exception e) {
                logger.error("Error in fetchAccessLink", e);
                e.printStackTrace();
            }
           
            return null;
        }
    
    
    public String validateAinPin(String ain, String pin)throws AmpException, gov.laca.amp.proxy.soap.ainpinvalidationservice.client.FaultMessage{
        logger.info(AddressManagementDC.class, "validateAinPin()", "Start validateAinPin", null);
        String msg = null;
        boolean isValid= ainPinValidationfacade.validateAinPin(ain, pin);
        if(isValid == true){
            msg = "SUCCESS";
        }
        logger.info(AddressManagementDC.class, "validateAinPin()", "End validateAinPin", null);
        return msg;
    }
    
    
}
