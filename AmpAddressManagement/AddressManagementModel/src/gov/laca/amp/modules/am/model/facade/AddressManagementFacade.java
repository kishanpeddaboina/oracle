package gov.laca.amp.modules.am.model.facade;

import gov.laca.amp.ent.exception.AmpException;

import gov.laca.amp.fwk.extn.facade.AmpProxyFacade;
import gov.laca.amp.modules.am.model.pojo.AddressManagementPojo;
import gov.laca.amp.modules.am.model.pojo.AddressPojo;
import gov.laca.amp.modules.am.model.pojo.Ain;
import gov.laca.amp.modules.am.model.pojo.AinObject;
import gov.laca.amp.modules.am.model.pojo.AinPojo;
import gov.laca.amp.modules.am.model.pojo.ChangeMailAddress;
import gov.laca.amp.modules.am.model.pojo.NewMailingAddress;
import gov.laca.amp.modules.am.model.pojo.PropertyAddress;
import gov.laca.amp.proxy.api.wcc.local.WccWrapper;
import gov.laca.amp.proxy.soap.addressmanagement.client.FaultMessage;

import gov.laca.amp.proxy.soap.addressmanagement.client.gen.OwnershipAddrRecordType;
import gov.laca.amp.proxy.soap.addressmanagement.client.gen.OwnershipInfoResponseType;

import gov.laca.amp.proxy.soap.addressmanagement.client.gen.RetrieveCurrentOwnershipInfoRequest;
import gov.laca.amp.proxy.soap.addressmanagement.client.gen.RetrieveCurrentOwnershipInfoResponse;
import gov.laca.amp.proxy.soap.addressmanagement.local.MailingAddrAssocMgmtServiceWrapper;

//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.PropertyIdentificationType;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.AddressType;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.MailingAddrPayload;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.ManageAddrWorkUnitRequest;
//
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.ManageAddrWorkUnitRequest.UserDetails;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.ManageAddrWorkUnitResponse;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.PayloadListType;
//
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.PropertyIdentificationList;
//import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.local.MailingAddrAssocWrapperServiceWrapper;

import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.ManageAddrWorkUnitResponse;
import gov.laca.amp.proxy.soap.addressmgmtwrapperservice.client.gen.PayloadListType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.AddressType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.CreateTransactionStateRequest;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.CreateTransactionStateResponse;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.MailingAddrPayload;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.MemberInfoType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.PropertyIdentificationList;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.PropertyIdentificationType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.SigneeInfoType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.SubmitDocType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.gen.SubmitterInfoType;
import gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.local.AMPTransactionStateMgmtServiceWrapper;
import gov.laca.amp.proxy.soap.bipublisher.client.gen.ReportRequest;

import gov.laca.amp.proxy.soap.bipublisher.client.gen.ReportResponse;

import gov.laca.amp.proxy.soap.bipublisher.local.BipReportServiceWrapper;

import gov.laca.amp.proxy.soap.identitylookupservice.client.gen.UserDetailsType;
import gov.laca.amp.proxy.soap.identitylookupservice.local.IdentityLookupServiceWrapper;

import gov.laca.amp.proxy.soap.mailaddrpdf.client.gen.ChangeMailAddressType;
import gov.laca.amp.proxy.soap.mailaddrpdf.client.gen.GenratePMACPDFRequest;
import gov.laca.amp.proxy.soap.mailaddrpdf.local.PublicMailAddressChangePDFServiceWrapper;
import gov.laca.amp.proxy.soap.repositoryDocumentsMgmtService.client.gen.RepositoryDocumentType;
import gov.laca.amp.proxy.soap.repositoryDocumentsMgmtService.client.gen.UpsertRepositoryDocumentsRequest;
import gov.laca.amp.proxy.soap.repositoryDocumentsMgmtService.local.RepositoryDocumentsMgmtServiceWrapper;
import gov.laca.amp.proxy.soap.repositoryDocumentsMgmtService.client.gen.UpsertRepositoryDocumentsRequest;
import gov.laca.amp.proxy.soap.repositoryDocumentsMgmtService.client.gen.UpsertRepositoryDocumentsResponse;

import gov.laca.amp.proxy.soap.signixdocmgmtwrapperservice.client.gen.CustInfoType;
import gov.laca.amp.proxy.soap.signixdocmgmtwrapperservice.client.gen.GetAccessLinkRequest;
import gov.laca.amp.proxy.soap.signixdocmgmtwrapperservice.client.gen.GetAccessLinkResponse;
import gov.laca.amp.proxy.soap.signixdocmgmtwrapperservice.local.SignixDocMgmtWrapperServiceWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import java.math.BigInteger;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import java.util.StringTokenizer;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;




import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.share.security.identitymanagement.UserProfile;


public class AddressManagementFacade extends AmpProxyFacade {
    public AddressManagementFacade() {
        super();
    }
    private BigInteger encodeSize = null;
    
    public List<AinPojo> retrieveOwnershipAddress(String ain, String aoid, String primaryAin, String PrimaryCheck) throws AmpException, FaultMessage {
        logger.error(AddressManagementFacade.class, "retrieveOwnershipAddress()", "Start retrieveOwnershipAddress", null);
        RetrieveCurrentOwnershipInfoResponse response = null;
        List<AinPojo> ainsList = new ArrayList<AinPojo>();
        MailingAddrAssocMgmtServiceWrapper AddrMgmtWrapper= new MailingAddrAssocMgmtServiceWrapper();
        if("YES".equalsIgnoreCase(PrimaryCheck)){
            response= AddrMgmtWrapper.retrieveOwnerShipDetails(primaryAin);
            if (response != null) {
               List<RetrieveCurrentOwnershipInfoResponse.CurrentOwnershipInfoList.CurrentOwnershipInfo> currentOwnerShipInfo= response.getCurrentOwnershipInfoList().getCurrentOwnershipInfo();
                if (currentOwnerShipInfo != null) {
                    AinPojo ainPojo = null;
                    
                    for (int i = 0; i < currentOwnerShipInfo.size(); i++) {
                        if (currentOwnerShipInfo.get(i) != null) {
                            ainPojo = new AinPojo();
                            ainPojo.setAin(currentOwnerShipInfo.get(i).getAIN());
                            String sain = ainPojo.getAin();
                            if(sain != null && sain.length() == 10) {
                                ainPojo.setAinDisplay(sain.substring(0, 4) + "-" + sain.substring(4, 7) + "-" + sain.substring(7, 10));
                            }
                            ainPojo.getCurrentSitusAddress().setStreetAddress(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getStreet());
                            ainPojo.getCurrentSitusAddress().setCity(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCity());
                            ainPojo.getCurrentSitusAddress().setCountry(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCountry());
                            ainPojo.getCurrentSitusAddress().setState(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getState());
                            ainPojo.getCurrentSitusAddress().setZipCode(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getZip());
                            
                            ainPojo.getCurrentMailingAddress().setStreetAddress(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getStreet());
                            ainPojo.getCurrentMailingAddress().setCity(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getCity());
                            ainPojo.getCurrentMailingAddress().setCountry(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getCountry());
                            ainPojo.getCurrentMailingAddress().setState(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getState());
                            ainPojo.getCurrentMailingAddress().setZipCode(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getZip());
                            ainPojo.setPropertyAddress(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getStreet()+", "+
                                                       currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCity()+", "+
                                                       currentOwnerShipInfo.get(i).getCurrentSitusAddress().getState()+", "+
                                                       currentOwnerShipInfo.get(i).getCurrentSitusAddress().getZip()+", "+
                                                       currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCountry());
                            if (currentOwnerShipInfo.get(i).getRecordingDate() != null) {
                            ainPojo.setRecordingDate(toSQLDateOnly(currentOwnerShipInfo.get(i).getRecordingDate()));
                            }
                            ainPojo.setAoid(currentOwnerShipInfo.get(i).getAOID());
                            ainPojo.setAssesseeName(currentOwnerShipInfo.get(i).getAssesseeName());
                            ainPojo.setCluster(currentOwnerShipInfo.get(i).getCluster());
                            ainPojo.setCommunityName(currentOwnerShipInfo.get(i).getCommunityName());
                            ainPojo.setDistrict(currentOwnerShipInfo.get(i).getDistrict());
                            ainPojo.setDocumentID(currentOwnerShipInfo.get(i).getDocumentID());
                            ainPojo.setDocumentSeq(currentOwnerShipInfo.get(i).getDocumentSeq());
                            ainPojo.setParcelStatus(currentOwnerShipInfo.get(i).getParcelStatus());
                            ainPojo.setRegion(currentOwnerShipInfo.get(i).getRegion());
                            ainPojo.setOhid(currentOwnerShipInfo.get(i).getOHID());
                            ainPojo.setInCareOf(currentOwnerShipInfo.get(i).getCareOfName());
                            ainPojo.setPrimary("Yes");
                            ainsList.add(ainPojo);
                            
                          }
                        
                    }
                }
            }
        }
        response= AddrMgmtWrapper.retrieveOwnerShipDetails(ain);
        if (response != null) {
           List<RetrieveCurrentOwnershipInfoResponse.CurrentOwnershipInfoList.CurrentOwnershipInfo> currentOwnerShipInfo= response.getCurrentOwnershipInfoList().getCurrentOwnershipInfo();
            if (currentOwnerShipInfo != null) {
                AinPojo ainPojo = null;
                
                for (int i = 0; i < currentOwnerShipInfo.size(); i++) {
                    Boolean duplicateCheck= Boolean.FALSE;
                   
                    if (currentOwnerShipInfo.get(i) != null) {
                        if(ainsList.size()>0){
                            for (AinPojo r : ainsList) {
                            if(currentOwnerShipInfo.get(i).getAIN().equals(r.getAin())){
                                duplicateCheck =Boolean.TRUE;
                            }
                           }
                        }
                        if(!duplicateCheck){
                        ainPojo = new AinPojo();
                        ainPojo.setAin(currentOwnerShipInfo.get(i).getAIN());
                        String sain = ainPojo.getAin();
                        if(sain != null && sain.length() == 10) {
                            ainPojo.setAinDisplay(sain.substring(0, 4) + "-" + sain.substring(4, 7) + "-" + sain.substring(7, 10));
                        }
                        ainPojo.getCurrentSitusAddress().setStreetAddress(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getStreet());
                        ainPojo.getCurrentSitusAddress().setCity(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCity());
                        ainPojo.getCurrentSitusAddress().setCountry(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCountry());
                        ainPojo.getCurrentSitusAddress().setState(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getState());
                        ainPojo.getCurrentSitusAddress().setZipCode(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getZip());
                        
                        ainPojo.getCurrentMailingAddress().setStreetAddress(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getStreet());
                        ainPojo.getCurrentMailingAddress().setCity(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getCity());
                        ainPojo.getCurrentMailingAddress().setCountry(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getCountry());
                        ainPojo.getCurrentMailingAddress().setState(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getState());
                        ainPojo.getCurrentMailingAddress().setZipCode(currentOwnerShipInfo.get(i).getCurrentMailingAddress().getZip());
                        ainPojo.setPropertyAddress(currentOwnerShipInfo.get(i).getCurrentSitusAddress().getStreet()+", "+
                                                   currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCity()+", "+
                                                   currentOwnerShipInfo.get(i).getCurrentSitusAddress().getState()+", "+
                                                   currentOwnerShipInfo.get(i).getCurrentSitusAddress().getZip()+", "+
                                                   currentOwnerShipInfo.get(i).getCurrentSitusAddress().getCountry());
                        if (currentOwnerShipInfo.get(i).getRecordingDate() != null) {
                        ainPojo.setRecordingDate(toSQLDateOnly(currentOwnerShipInfo.get(i).getRecordingDate()));
                        }
                        ainPojo.setAoid(currentOwnerShipInfo.get(i).getAOID());
                        ainPojo.setAssesseeName(currentOwnerShipInfo.get(i).getAssesseeName());
                        ainPojo.setCluster(currentOwnerShipInfo.get(i).getCluster());
                        ainPojo.setCommunityName(currentOwnerShipInfo.get(i).getCommunityName());
                        ainPojo.setDistrict(currentOwnerShipInfo.get(i).getDistrict());
                        ainPojo.setDocumentID(currentOwnerShipInfo.get(i).getDocumentID());
                        ainPojo.setDocumentSeq(currentOwnerShipInfo.get(i).getDocumentSeq());
                        ainPojo.setParcelStatus(currentOwnerShipInfo.get(i).getParcelStatus());
                        ainPojo.setRegion(currentOwnerShipInfo.get(i).getRegion());
                        ainPojo.setOhid(currentOwnerShipInfo.get(i).getOHID());
                        ainPojo.setInCareOf(currentOwnerShipInfo.get(i).getCareOfName());
                        ainPojo.setPrimary("No");
                        ainsList.add(ainPojo);  
                        }
                      }
                    
                }
               // addressManagementPojo.setAinsPojo(ainsList);
            }
        }
        logger.error(AddressManagementFacade.class, "retrieveOwnershipAddress()", "End retrieveOwnershipAddress", null);
        return ainsList;
        
    }
    
    public String saveAddressWorkUnitFacade(AddressManagementPojo addressManagementPojo)throws AmpException, gov.laca.amp.proxy.soap.amptransactionstatemgmtservice.client.FaultMessage,JAXBException,
                                                                                                DatatypeConfigurationException {
        logger.error(AddressManagementFacade.class, "saveAddressWorkUnitFacade()", "Start saveAddressWorkUnitFacade", null);
        byte[] unsignedPdf=generateMacPdf(addressManagementPojo);//readBytesFromFile("");//biPublisherRunReport(addressManagementPojo);//readBytesFromFile("");
        CreateTransactionStateRequest request = new CreateTransactionStateRequest();
        CreateTransactionStateRequest.PayloadList payloadList = new CreateTransactionStateRequest.PayloadList();
        CreateTransactionStateRequest.UserDetails userDetails = new CreateTransactionStateRequest.UserDetails();
        SecurityContext secCntx = ADFContext.getCurrent().getSecurityContext();
        String userName = secCntx.getUserName();
        //userDetails.setUserName(userName);
        userDetails.setUserType("Internal");
        userDetails.setUserId(userName);
        //userDetails.setUserLevel("");
        UserProfile upf = ADFContext.getCurrent().getSecurityContext().getUserProfile();
        
       List<CreateTransactionStateRequest.PayloadList.PayloadRecord> payloadRecords = payloadList.getPayloadRecord();
       CreateTransactionStateRequest.PayloadList.PayloadRecord payloadRecord = new CreateTransactionStateRequest.PayloadList.PayloadRecord();
       
        MailingAddrPayload mailingAddresspayload = new MailingAddrPayload();
    //    payloadRecord.setPayloadType("MAILING ADDRESS");
       // mailingAddresspayload.setAgentName(addressManagementPojo.getAgentName());
       // mailingAddresspayload.setIsAuthorizedAgent(addressManagementPojo.getIsAuthorizedAgent());
        mailingAddresspayload.setEmail(addressManagementPojo.getEmail());
        mailingAddresspayload.setPropertyOwnerName(addressManagementPojo.getPropertyOwnerName());
        mailingAddresspayload.setSignature(addressManagementPojo.getDigitalSignature());
        mailingAddresspayload.setPhone(addressManagementPojo.getDaytimePhone());
        mailingAddresspayload.setIsSetAsSitusAddr(Boolean.FALSE);
        AddressType newMailingAddr = addressParsing(addressManagementPojo.getNewMailingAddress());
        mailingAddresspayload.setMailingAddr(newMailingAddr);
        mailingAddresspayload.setDate(asXMLGregorianCalendar(addressManagementPojo.getEnteredDate()));
        MailingAddrPayload.AINList ainList = new MailingAddrPayload.AINList();
        List<MailingAddrPayload.AINList.AINRecord> ainRecords = ainList.getAINRecord();
 
        PropertyIdentificationList propIdentificationlist = new PropertyIdentificationList();
        List<PropertyIdentificationType>  ainListTypeMain = propIdentificationlist.getPropertyIdentification();
        
        
        if(addressManagementPojo.getAinsPojo().size()>0){  
          for (int i = 0; i < addressManagementPojo.getAinsPojo().size(); i++) { 
              MailingAddrPayload.AINList.AINRecord ainRecord= new MailingAddrPayload.AINList.AINRecord();
//              AINListType.AINRecord ainRecordMain= new AINListType.AINRecord();
//              AINListType.AINRecord.AIN ain = new AINListType.AINRecord.AIN();
               PropertyIdentificationType ainRecordMain= new PropertyIdentificationType();
              AddressType currentSitusAddress = new AddressType();
              AddressType currentMailingAddress = new AddressType();
              if (addressManagementPojo.getAinsPojo().get(i) != null) {
                  if(addressManagementPojo.getAinsPojo().get(i).getAin()!=null)
                      ainRecord.setAIN(addressManagementPojo.getAinsPojo().get(i).getAin());
                     ainRecordMain.setAIN(addressManagementPojo.getAinsPojo().get(i).getAin());
                     ainRecordMain.setAOID(addressManagementPojo.getAinsPojo().get(i).getAoid().intValue());
                  ainRecordMain.setPropertyType("RP");// added for soa service failure signix notification
//                      ain.setValue(addressManagementPojo.getAin());
//                      ainRecordMain.setAIN(ain);
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getCity()!=null)
                      currentSitusAddress.setCity(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getCity());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getCountry()!=null)
                      currentSitusAddress.setCountry(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getCountry());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getState()!=null)
                          currentSitusAddress.setState(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getState());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress()!=null)
                      currentSitusAddress.setStreet(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getZipCode()!=null)
                      currentSitusAddress.setZip(addressManagementPojo.getAinsPojo().get(i).getCurrentSitusAddress().getZipCode());
                  ainRecord.setCurrentSitusAddress(currentSitusAddress);
                  
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getCity()!=null)
                      currentMailingAddress.setCity(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getCity());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getCountry()!=null)
                      currentMailingAddress.setCountry(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getCountry());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getState()!=null)
                          currentMailingAddress.setState(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getState());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getStreetAddress()!=null)
                      currentMailingAddress.setStreet(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getStreetAddress());
                  if(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getZipCode()!=null)
                      currentMailingAddress.setZip(addressManagementPojo.getAinsPojo().get(i).getCurrentMailingAddress().getZipCode());
                  ainRecord.setCurrentMailingAddress(currentMailingAddress);
                  ainRecord.setOHID(addressManagementPojo.getAinsPojo().get(i).getOhid());
                  ainRecord.setAOID(addressManagementPojo.getAinsPojo().get(i).getAoid());
                  ainRecord.setAssesseeName(addressManagementPojo.getAinsPojo().get(i).getAssesseeName());
                  ainRecord.setCluster(addressManagementPojo.getAinsPojo().get(i).getCluster());
                  ainRecord.setCommunityName(addressManagementPojo.getAinsPojo().get(i).getCommunityName());
                  ainRecord.setDistrict(addressManagementPojo.getAinsPojo().get(i).getDistrict());
                  ainRecord.setDocumentID(addressManagementPojo.getAinsPojo().get(i).getDocumentID());
                  ainRecord.setDocumentSequence(addressManagementPojo.getAinsPojo().get(i).getDocumentSeq());
                  if(addressManagementPojo.getAinsPojo().get(i).getRecordingDate()!=null){
                  ainRecord.setRecordingDate(asXMLGregorianCalendar(addressManagementPojo.getAinsPojo().get(i).getRecordingDate()));
                  }
                  ainRecord.setRegion(addressManagementPojo.getAinsPojo().get(i).getRegion());
                  ainRecord.setParcelStatus(addressManagementPojo.getAinsPojo().get(i).getParcelStatus());
                  ainRecord.setIsApproveAddrChange(Boolean.FALSE);
                  ainRecord.setIsDenyAddrChange(Boolean.FALSE);
                  ainRecord.setIsMailingAddrInvestigationReq(Boolean.FALSE);
                  ainRecord.setIsSitusAddrInvestigationReq(Boolean.FALSE);
                  ainRecord.setRemarks("");
                  ainRecord.setSitusAddrInvestigationNotes("");
                  ainRecord.setCareOfName(addressManagementPojo.getAinsPojo().get(i).getInCareOf());
              }
              ainRecords.add(ainRecord);
              ainListTypeMain.add(ainRecordMain);
          }
        }
        List<MailingAddrPayload.AINList.AINRecord> ainrecord =ainList.getAINRecord();
        mailingAddresspayload.setAINList(ainList);
        payloadRecord.setMailingAddrPayload(mailingAddresspayload);
        payloadRecords.add(payloadRecord);
        request.setPayloadList(payloadList);
        
        SubmitDocType sdt = new SubmitDocType();
        sdt.setUnsignedPDF(unsignedPdf);
        sdt.setLength(BigInteger.valueOf(unsignedPdf.length));
        
//        SubmitterInfoType submInfo = new SubmitterInfoType();
//        submInfo.setSubmitterName(addressManagementPojo.getPropertyOwnerName());
//        submInfo.setSubmitterEmail(addressManagementPojo.getEmail());
//        submInfo.setContactInfo(addressManagementPojo.getDaytimePhone());
//        sdt.setSubmitterInfo(submInfo);
        
        SigneeInfoType signeeInfo = new SigneeInfoType();
        signeeInfo.getDateSignedFormat().add("MM/dd/yy");


        
        String fn = upf.getFirstName();
        String ln = upf.getLastName();
        String mi = upf.getMiddleName();
        String email = addressManagementPojo.getEmail();
        
     



        signeeInfo.getDateSignField().add("DATE1");
        signeeInfo.getFirstName().add(checkVal(fn));
        signeeInfo.getLastName().add(checkVal(ln));
        signeeInfo.getMiddleInitial().add(mi);
        //signeeInfo.getSigneeId().add(new BigInteger("1"));
        signeeInfo.getSignField().add("SIGNATURE1");
        sdt.setSigneeInfo(signeeInfo);
        
        MemberInfoType memType = new MemberInfoType();
        memType.setFirstName(checkVal(fn));
        memType.setLastName(checkVal(ln));
        memType.setMiddleInitial(mi);
        memType.setEmail(email);
      
      
        sdt.setMemberInfo(memType);
        
        DateFormat df = new SimpleDateFormat("_MMddyyyy_HHmmss");       
        Date today = Calendar.getInstance().getTime();         
        sdt.setFileName(checkVal(upf.getDisplayName())+df.format(today));
        //request.setAINList(ainListTypeMain);
        request.setPropertyIdentificationList(propIdentificationlist);
        request.setCategory("Ownership");
        request.setSubCategory("Mailing Address Change");
        request.setInputSource("Portal");
        request.setEventSource("Internet");
        request.setUIAction("Submit");
        request.setEventDate(asXMLGregorianCalendar(addressManagementPojo.getEnteredDate()));
        request.setType("MAILING ADDRESS");
        request.setUserDetails(userDetails);
        request.setSubmitDoc(sdt);
        AMPTransactionStateMgmtServiceWrapper wrapper= new AMPTransactionStateMgmtServiceWrapper();
        CreateTransactionStateResponse response= wrapper.createTransaction(request);
//        String workUintId =null;
//        if(response!=null){
//            if(response.getWUID()!=null){
//                workUintId=response.getWUID().toString();
//            }
//        }
        //BIPublisher Call
        
  //      String fileprefix =getFileName();
    //    String path=pdfGeneration(docFormat,fileprefix);
//        WccWrapper wccWrapper = new WccWrapper();
//        String docId=wccWrapper.addAddrChangeDocument(fileprefix, path, "Ownership", "Mailing Address Change", "RP", workUintId); // for c6 all AINS are RP
//        RepositoryDocumentsMgmtServiceWrapper reposDocmMgmtSrvcWrapper = new RepositoryDocumentsMgmtServiceWrapper();
//        UpsertRepositoryDocumentsRequest upsertRequest = new UpsertRepositoryDocumentsRequest(); 
//        UpsertRepositoryDocumentsRequest.RepositoryDocumentList repoDocList =
//            new UpsertRepositoryDocumentsRequest.RepositoryDocumentList();
//        List<RepositoryDocumentType> repoDocTypeList = repoDocList.getRepositoryDocument();
//        if (response.getWUID() != null) {
//            upsertRequest.setWUID(response.getWUID());
//        }
//        RepositoryDocumentType repoDoc = new RepositoryDocumentType();
//        repoDoc.setWUID(response.getWUID());
//        repoDoc.setDocName(fileprefix+".pdf");
//        repoDoc.setDocRefID(docId);
//        repoDoc.setDocCategory("Ownership");
//        repoDoc.setDocSubcategory("Mailing Address Change");
//        repoDoc.setDocRepository("WCC");
//        repoDoc.setOperationFlag("C");
//        repoDoc.setDocSource("MAILING ADDRESS CHANGE");
//        repoDoc.setReceivedDate(asXMLGregorianCalendar(new Date()));
//        repoDocTypeList.add(repoDoc);
//        upsertRequest.setRepositoryDocumentList(repoDocList);
//        UpsertRepositoryDocumentsResponse upsertResponse = reposDocmMgmtSrvcWrapper.upsertRepositoryDocuments(upsertRequest);
        logger.error(AddressManagementFacade.class, "saveAddressWorkUnitFacade()", "END saveAddressWorkUnitFacade", null);
        return response.getDocSetId();
 
    } 
    
    public byte[] biPublisherRunReport(AddressManagementPojo addressManagementObj) throws AmpException,JAXBException{
            logger.error(AddressManagementFacade.class, "biPublisherRunReport()", "Start biPublisherRunReport", null);
            ReportRequest request = new ReportRequest();
            ReportResponse response = null;
            String newAddress= addressManagementObj.getNewMailingAddress();
            String[] tokens =newAddress.split(",");
            int tokencount=tokens.length;
            String street = "";
//            for(int j=0; j<tokencount; j++){
//                System.out.println("split Output:"+tokens[j]);
//            }
            NewMailingAddress newMailingAdd= new NewMailingAddress();
            if(tokencount == 5) {
                String[] newMailStreetSplit = new String[5];
                if(tokencount>2){
                newMailingAdd.setZipCode(tokens[tokencount-2]);
                }if(tokencount>3){
                newMailingAdd.setState(tokens[tokencount-3]);
                }if(tokencount>4){
                newMailingAdd.setCity(tokens[tokencount-4]);
                }if(tokencount>4){
                for (int j=0; j<tokencount-4 ;j++){
                street+=tokens[j];
                }
                //            newMailStreetSplit= street.split("\\s",2);
                //            newMailingAdd.setStreetNumber(newMailStreetSplit[0]);
                //            newMailingAdd.setStreet(newMailStreetSplit[1]);
                newMailingAdd.setStreetAddress(street);
                } 
            } else {
                newMailingAdd.setStreetAddress(newAddress);
            }
            ChangeMailAddress ChangeMailAddressObj = new ChangeMailAddress();
            ArrayList<AinObject> ainList = new ArrayList<AinObject>();
            ArrayList<NewMailingAddress> propAddress = new ArrayList<NewMailingAddress>();
            PropertyAddress propAdd = new PropertyAddress();
            Ain ain_g = new Ain();
            
            ChangeMailAddressObj.setPhone(addressManagementObj.getDaytimePhone());
            ChangeMailAddressObj.setEmail(addressManagementObj.getEmail());
            ChangeMailAddressObj.setDate(addressManagementObj.getEnteredDate());
            ChangeMailAddressObj.setName(addressManagementObj.getPropertyOwnerName());
            
            if(addressManagementObj.getAinsPojo().size()>0){  
              for (int i = 0; i < addressManagementObj.getAinsPojo().size(); i++) {
                  AinObject ain = new AinObject();
                  NewMailingAddress oldmailingAdd = new NewMailingAddress();
                  String[] streetSplit = new String[5];
                  if (addressManagementObj.getAinsPojo().get(i) != null) {
                      if(addressManagementObj.getAinsPojo().get(i).getAin()!=null)
                          ain.setAin(addressManagementObj.getAinsPojo().get(i).getAin());
                      ain.setMapbook(addressManagementObj.getAinsPojo().get(i).getAin().substring(0, 4));
                      ain.setPage(addressManagementObj.getAinsPojo().get(i).getAin().substring(4, 7));
                      ain.setParcel(addressManagementObj.getAinsPojo().get(i).getAin().substring(7, 10));
                      if(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getCity()!=null)
                          oldmailingAdd.setCity(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getCity());
                      if(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getState()!=null)
                              oldmailingAdd.setState(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getState());
                      if(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress()!=null)
                         // oldmailingAdd.setStreet(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress());
//                      streetSplit=addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress().split("\\s",2);
//                      System.out.println("streetSplit[0]:"+streetSplit[0]);
//                      System.out.println("streetSplit[1]:"+streetSplit[1]);
//                      oldmailingAdd.setStreetNumber(streetSplit[0]);
//                      oldmailingAdd.setStreet(streetSplit[1]);
                        oldmailingAdd.setStreetAddress(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getStreetAddress());  
                      if(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getZipCode()!=null)
                          oldmailingAdd.setZipCode(addressManagementObj.getAinsPojo().get(i).getCurrentSitusAddress().getZipCode());
                      propAddress.add(oldmailingAdd);
                      ainList.add(ain);
                  }
                  propAdd.setPropertyMailingAddress(propAddress); 
                  ain_g.setAin_g(ainList);
              }
            }
            ChangeMailAddressObj.setPropertyAddressGroup(propAdd);
            ChangeMailAddressObj.setNewAddress(newMailingAdd);
            ChangeMailAddressObj.setAin(ain_g );
            
            String xmlString = null;
                StringWriter acsw = new StringWriter(); 
                JAXBContext jaxbctxt = JAXBContext.newInstance(ChangeMailAddress.class);
                Marshaller jaxbm = jaxbctxt.createMarshaller();
                jaxbm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbm.marshal(ChangeMailAddressObj, acsw);
                xmlString = acsw.toString();
                xmlString = xmlString.substring(xmlString.indexOf('\n')+1);
                //System.out.println("xmlString:"+xmlString);
                byte[] convertEntry=xmlString.getBytes();
                //System.out.println("convertEntry:"+convertEntry);
                request.setReportData(DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(convertEntry)));
               // request.setReportData(convertEntry);
                request.setReportAbsolutePath("/Mailing Address/ChangeMailingAddress.xdo");
                request.setSizeOfDataChunkDownload(-1);
                BipReportServiceWrapper wrapper= new BipReportServiceWrapper();
                response = wrapper.getReport(request);
//                byte[] abc = readBytesFromFile("");

                return response.getReportBytes();
        }
    
    public String pdfGeneration(String ouputBase64Doc, String fileprefix){
        logger.error(AddressManagementFacade.class, "pdfGeneration()", "Start pdfGeneration", null);
        FileOutputStream fop;
        File tempFile=null;
        String path = null;
        try {
            tempFile = File.createTempFile(fileprefix, ".pdf");
            path = tempFile.getAbsolutePath();
            System.out.println("Temp File:"+tempFile.getAbsolutePath());
            fop = new FileOutputStream(tempFile);
            fop.write(DatatypeConverter.parseBase64Binary(ouputBase64Doc));
            fop.flush();
            fop.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.error(AddressManagementFacade.class, "pdfGeneration()", "End pdfGeneration", null);
        return path;
    }
    private static String getFileName() {
        logger.error(AddressManagementFacade.class, "getFileName()", "Start getFileName", null);
        String fileName = "";
                String name = "MailingAddressChange";
                fileName = name.replaceFirst("[.][^.]+$", "");
        logger.error(AddressManagementFacade.class, "getFileName()", "End getFileName", null);
        return fileName;    
    }
   
    public  XMLGregorianCalendar asXMLGregorianCalendar(java.sql.Date date) {
            try{
                 DatatypeFactory df = DatatypeFactory.newInstance();
                if (date == null) {
                    return null;
                } else {
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTimeInMillis(date.getTime());
                    return df.newXMLGregorianCalendar(gc);
                }
            }catch(DatatypeConfigurationException e) {
                    throw new IllegalStateException(
                    "Error while trying to obtain a new instance of DatatypeFactory", e);
                    
                }

            }
    
    public String retriveEmailId()throws AmpException, gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage{
        logger.error(AddressManagementFacade.class, "retriveEmailId()", "End retriveEmailId", null); 
        IdentityLookupServiceWrapper identityWrapper = new IdentityLookupServiceWrapper();
        SecurityContext secCntx = ADFContext.getCurrent().getSecurityContext();
        String userName = secCntx.getUserName();
        UserDetailsType userDetails= identityWrapper.retrieveUserDetails(userName);
        String email=userDetails.getEmail();
        logger.error(AddressManagementFacade.class, "retriveEmailId()", "End retriveEmailId", null);
        return email;
    }
    
    public AddressType addressParsing(String newAddress){
        AddressType newMailingAdd= new AddressType();
        String[] tokens =newAddress.split(",");
        int tokencount=tokens.length;
        String street = "";
//        for(int j=0; j<tokencount; j++){
//            System.out.println("split Output:"+tokens[j]);
//        }
        if(tokencount>1){
        newMailingAdd.setCountry(tokens[tokencount-1]);
        }if(tokencount>2){
        newMailingAdd.setZip(tokens[tokencount-2]);
        }if(tokencount>3){
        newMailingAdd.setState(tokens[tokencount-3]);
        }if(tokencount>4){
        newMailingAdd.setCity(tokens[tokencount-4]);
        }if(tokencount>4){
                for (int j=0; j<tokencount-4 ;j++){
                street+=tokens[j];
            } 
            newMailingAdd.setStreet(street);
        }
        
        if(newMailingAdd.getCountry() != null)
            newMailingAdd.setCountry(newMailingAdd.getCountry().trim());        
        
        if(newMailingAdd.getZip() != null)
            newMailingAdd.setZip(newMailingAdd.getZip().trim());
        
        if(newMailingAdd.getState() != null)
            newMailingAdd.setState(newMailingAdd.getState().trim());        
        
        if(newMailingAdd.getCity() != null)
            newMailingAdd.setCity(newMailingAdd.getCity().trim());
        
        if(newMailingAdd.getStreet() != null)
            newMailingAdd.setStreet(newMailingAdd.getStreet().trim());
        
        
        return newMailingAdd;
    }
    
    public XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
            if (date == null) {
                return null;
            } else {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(date.getTime());
                return df.newXMLGregorianCalendar(gc);
            }
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Error while trying to obtain a new instance of DatatypeFactory", e);

        }

    }
    
    public String fetchAccessLink(String docSetId) throws AmpException, gov.laca.amp.proxy.soap.signixdocmgmtwrapperservice.client.FaultMessage {
        SignixDocMgmtWrapperServiceWrapper s =  new SignixDocMgmtWrapperServiceWrapper();
        GetAccessLinkRequest r =  new GetAccessLinkRequest();
        r.setDocSetId(docSetId);
      //  r.setCustInfo(new CustInfoType());
        GetAccessLinkResponse rs = s.getAccessLink(r);
        
        return rs.getAccessLink();
    }
    
    private static String checkVal(String val) {
//        if(val == null)
//            return "test";
        
        return val;
    }
    
    private static byte[] readBytesFromFile(String filedata) {


                byte[] bytesArray = null;

                String s = "JVBERi0xLjQKJeLjz9MKMSAwIG9iago8PAovQXV0aG9yIChzaWduaXgpCi9DcmVhdGlvbkRhdGUgKEQ6MjAwODAzMTAxMzU5MjMtMDUnMDAnKQovQ3JlYXRvciAoUFNjcmlwdDUuZGxsIFZlcnNpb24gNS4yKQovTW9kRGF0ZSAoRDoyMDE4MDcwNjE4MTAzOCswNSczMCcpCi9Qcm9kdWNlciAoQWNyb2JhdCBEaXN0aWxsZXIgNy4wLjUgXChXaW5kb3dzXCkpCi9UaXRsZSAoTWljcm9zb2Z0IFdvcmQgLSB0aHJlZVNpZ25lcldpdGhTaWduaW5nRGF0ZUFuZFRleHRGaWVsZHMuZG9jKQo+PgplbmRvYmoKMiAwIG9iago8PAovQWNyb0Zvcm0gMyAwIFIKL01ldGFkYXRhIDQgMCBSCi9QYWdlTGFiZWxzIDUgMCBSCi9QYWdlcyA2IDAgUgovVHlwZSAvQ2F0YWxvZwo+PgplbmRvYmoKMyAwIG9iago8PAovREEgKC9IZWx2IDAgVGYgMCBnICkKL0RSIDw8Ci9FbmNvZGluZyA8PAovUERGRG9jRW5jb2RpbmcgNyAwIFIKPj4KL0ZvbnQgPDwKL0hlbHYgOCAwIFIKL1phRGIgOSAwIFIKPj4KPj4KL0ZpZWxkcyBbMTAgMCBSIDExIDAgUiAxMiAwIFIgMTMgMCBSIDE0IDAgUiAxNSAwIFIgMTYgMCBSIDE3IDAgUiAxOCAwIFJdCj4+CmVuZG9iago0IDAgb2JqCjw8Ci9MZW5ndGggMzI5NQovU3VidHlwZSAvWE1MCi9UeXBlIC9NZXRhZGF0YQo+PgpzdHJlYW0KPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iWE1QIENvcmUgNS41LjAiPgoJPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KCQk8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIgoJCQkJeG1sbnM6cGRmPSJodHRwOi8vbnMuYWRvYmUuY29tL3BkZi8xLjMvIgoJCQkJeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIgoJCQkJeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIgoJCQkJeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iPgoJCQk8cGRmOlByb2R1Y2VyPkFjcm9iYXQgRGlzdGlsbGVyIDcuMC41IChXaW5kb3dzKTwvcGRmOlByb2R1Y2VyPgoJCQk8eG1wOkNyZWF0b3JUb29sPlBTY3JpcHQ1LmRsbCBWZXJzaW9uIDUuMjwveG1wOkNyZWF0b3JUb29sPgoJCQk8eG1wOkNyZWF0ZURhdGU+MjAwOC0wMy0xMFQxMzo1OToyMy0wNTowMDwveG1wOkNyZWF0ZURhdGU+CgkJCTx4bXA6TWV0YWRhdGFEYXRlPjIwMDgtMDMtMTBUMTQ6MDc6MDgtMDU6MDA8L3htcDpNZXRhZGF0YURhdGU+CgkJCTx4bXA6TW9kaWZ5RGF0ZT4yMDE4LTA3LTA2VDE4OjEwOjM4KzA1OjMwPC94bXA6TW9kaWZ5RGF0ZT4KCQkJPGRjOmZvcm1hdD5hcHBsaWNhdGlvbi9wZGY8L2RjOmZvcm1hdD4KCQkJPGRjOnRpdGxlPgoJCQkJPHJkZjpBbHQ+CgkJCQkJPHJkZjpsaSB4bWw6bGFuZz0ieC1kZWZhdWx0Ij5NaWNyb3NvZnQgV29yZCAtIHRocmVlU2lnbmVyV2l0aFNpZ25pbmdEYXRlQW5kVGV4dEZpZWxkcy5kb2M8L3JkZjpsaT4KCQkJCTwvcmRmOkFsdD4KCQkJPC9kYzp0aXRsZT4KCQkJPGRjOmNyZWF0b3I+CgkJCQk8cmRmOlNlcT4KCQkJCQk8cmRmOmxpPnNpZ25peDwvcmRmOmxpPgoJCQkJPC9yZGY6U2VxPgoJCQk8L2RjOmNyZWF0b3I+CgkJCTx4bXBNTTpEb2N1bWVudElEPnV1aWQ6M2MzNGE3YzUtNTRjYy00OGIxLTg5OGItMTFmZTdhY2Q2MGUwPC94bXBNTTpEb2N1bWVudElEPgoJCQk8eG1wTU06SW5zdGFuY2VJRD51dWlkOjIyOWY2ZDcwLTZiMzAtNGQxNi1hYTY5LWEyODgzNzhlZTZkYzwveG1wTU06SW5zdGFuY2VJRD4KCQk8L3JkZjpEZXNjcmlwdGlvbj4KCTwvcmRmOlJERj4KPC94OnhtcG1ldGE+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAKPD94cGFja2V0IGVuZD0idyI/PgplbmRzdHJlYW0KZW5kb2JqCjUgMCBvYmoKPDwKL051bXMgWzAgMTkgMCBSXQo+PgplbmRvYmoKNiAwIG9iago8PAovQ291bnQgMQovS2lkcyBbMjAgMCBSXQovVHlwZSAvUGFnZXMKPj4KZW5kb2JqCjcgMCBvYmoKPDwKL0RpZmZlcmVuY2VzIFsyNCAvYnJldmUgL2Nhcm9uIC9jaXJjdW1mbGV4IC9kb3RhY2NlbnQgL2h1bmdhcnVtbGF1dCAvb2dvbmVrIC9yaW5nIC90aWxkZSAzOSAvcXVvdGVzaW5nbGUgOTYgL2dyYXZlIDEyOCAvYnVsbGV0IC9kYWdnZXIKL2RhZ2dlcmRibCAvZWxsaXBzaXMgL2VtZGFzaCAvZW5kYXNoIC9mbG9yaW4gL2ZyYWN0aW9uIC9ndWlsc2luZ2xsZWZ0IC9ndWlsc2luZ2xyaWdodCAvbWludXMgL3BlcnRob3VzYW5kIC9xdW90ZWRibGJhc2UgL3F1b3RlZGJsbGVmdCAvcXVvdGVkYmxyaWdodCAvcXVvdGVsZWZ0IC9xdW90ZXJpZ2h0IC9xdW90ZXNpbmdsYmFzZQovdHJhZGVtYXJrIC9maSAvZmwgL0xzbGFzaCAvT0UgL1NjYXJvbiAvWWRpZXJlc2lzIC9aY2Fyb24gL2RvdGxlc3NpIC9sc2xhc2ggL29lIC9zY2Fyb24gL3pjYXJvbiAxNjAgL0V1cm8gMTY0Ci9jdXJyZW5jeSAxNjYgL2Jyb2tlbmJhciAxNjggL2RpZXJlc2lzIC9jb3B5cmlnaHQgL29yZGZlbWluaW5lIDE3MiAvbG9naWNhbG5vdCAvLm5vdGRlZiAvcmVnaXN0ZXJlZCAvbWFjcm9uIC9kZWdyZWUgL3BsdXNtaW51cyAvdHdvc3VwZXJpb3IgL3RocmVlc3VwZXJpb3IKL2FjdXRlIC9tdSAxODMgL3BlcmlvZGNlbnRlcmVkIC9jZWRpbGxhIC9vbmVzdXBlcmlvciAvb3JkbWFzY3VsaW5lIDE4OCAvb25lcXVhcnRlciAvb25laGFsZiAvdGhyZWVxdWFydGVycyAxOTIgL0FncmF2ZSAvQWFjdXRlIC9BY2lyY3VtZmxleCAvQXRpbGRlCi9BZGllcmVzaXMgL0FyaW5nIC9BRSAvQ2NlZGlsbGEgL0VncmF2ZSAvRWFjdXRlIC9FY2lyY3VtZmxleCAvRWRpZXJlc2lzIC9JZ3JhdmUgL0lhY3V0ZSAvSWNpcmN1bWZsZXggL0lkaWVyZXNpcyAvRXRoIC9OdGlsZGUgL09ncmF2ZSAvT2FjdXRlCi9PY2lyY3VtZmxleCAvT3RpbGRlIC9PZGllcmVzaXMgL211bHRpcGx5IC9Pc2xhc2ggL1VncmF2ZSAvVWFjdXRlIC9VY2lyY3VtZmxleCAvVWRpZXJlc2lzIC9ZYWN1dGUgL1Rob3JuIC9nZXJtYW5kYmxzIC9hZ3JhdmUgL2FhY3V0ZSAvYWNpcmN1bWZsZXggL2F0aWxkZQovYWRpZXJlc2lzIC9hcmluZyAvYWUgL2NjZWRpbGxhIC9lZ3JhdmUgL2VhY3V0ZSAvZWNpcmN1bWZsZXggL2VkaWVyZXNpcyAvaWdyYXZlIC9pYWN1dGUgL2ljaXJjdW1mbGV4IC9pZGllcmVzaXMgL2V0aCAvbnRpbGRlIC9vZ3JhdmUgL29hY3V0ZQovb2NpcmN1bWZsZXggL290aWxkZSAvb2RpZXJlc2lzIC9kaXZpZGUgL29zbGFzaCAvdWdyYXZlIC91YWN1dGUgL3VjaXJjdW1mbGV4IC91ZGllcmVzaXMgL3lhY3V0ZSAvdGhvcm4gL3lkaWVyZXNpc10KL1R5cGUgL0VuY29kaW5nCj4+CmVuZG9iago4IDAgb2JqCjw8Ci9CYXNlRm9udCAvSGVsdmV0aWNhCi9FbmNvZGluZyA3IDAgUgovTmFtZSAvSGVsdgovU3VidHlwZSAvVHlwZTEKL1R5cGUgL0ZvbnQKPj4KZW5kb2JqCjkgMCBvYmoKPDwKL0Jhc2VGb250IC9aYXBmRGluZ2JhdHMKL05hbWUgL1phRGIKL1N1YnR5cGUgL1R5cGUxCi9UeXBlIC9Gb250Cj4+CmVuZG9iagoxMCAwIG9iago8PAovQVAgPDwKL04gMjEgMCBSCj4+Ci9GIDQKL0ZUIC9UeAovTUsgPDwKPj4KL1AgMjAgMCBSCi9SZWN0IFsxNjkuMzYgNTA4LjM0NSAzNzAuNDUzIDUzMC44NDVdCi9TdWJ0eXBlIC9XaWRnZXQKL1QgKFNpZ25hdHVyZTFEYXRlKQovVHlwZSAvQW5ub3QKPj4KZW5kb2JqCjExIDAgb2JqCjw8Ci9BUCA8PAovTiAyMiAwIFIKPj4KL0YgNAovRlQgL1R4Ci9NSyA8PAo+PgovUCAyMCAwIFIKL1JlY3QgWzE2OS4zNiAzOTcuODEyIDM3MC40NTMgNDIwLjMxMl0KL1N1YnR5cGUgL1dpZGdldAovVCAoU2lnbmF0dXJlMkRhdGUpCi9UeXBlIC9Bbm5vdAo+PgplbmRvYmoKMTIgMCBvYmoKPDwKL0FQIDw8Ci9OIDIzIDAgUgo+PgovRiA0Ci9GVCAvVHgKL01LIDw8Cj4+Ci9QIDIwIDAgUgovUmVjdCBbMTY5LjM2IDI4Ny42NTcgMzcwLjQ1MyAzMTAuMTU3XQovU3VidHlwZSAvV2lkZ2V0Ci9UIChTaWduYXR1cmUzRGF0ZSkKL1R5cGUgL0Fubm90Cj4+CmVuZG9iagoxMyAwIG9iago8PAovQVAgPDwKL04gMjQgMCBSCj4+Ci9GIDQKL0ZUIC9TaWcKL01LIDw8Cj4+Ci9QIDIwIDAgUgovUmVjdCBbMTYxLjcxOCA1NDAuMjIgNDM0LjA2IDU4OC4wMzNdCi9TdWJ0eXBlIC9XaWRnZXQKL1QgKFNpZ25hdHVyZTEpCi9UeXBlIC9Bbm5vdAo+PgplbmRvYmoKMTQgMCBvYmoKPDwKL0FQIDw8Ci9OIDI1IDAgUgo+PgovRiA0Ci9GVCAvU2lnCi9NSyA8PAo+PgovUCAyMCAwIFIKL1JlY3QgWzE2MS43MTggNDI5Ljg3NyA0MzQuMDYgNDc3LjY4OV0KL1N1YnR5cGUgL1dpZGdldAovVCAoU2lnbmF0dXJlMikKL1R5cGUgL0Fubm90Cj4+CmVuZG9iagoxNSAwIG9iago8PAovQVAgPDwKL04gMjYgMCBSCj4+Ci9GIDQKL0ZUIC9TaWcKL01LIDw8Cj4+Ci9QIDIwIDAgUgovUmVjdCBbMTYxLjcxOCAzMTkuODE1IDQzNC4wNiAzNjcuNjI4XQovU3VidHlwZSAvV2lkZ2V0Ci9UIChTaWduYXR1cmUzKQovVHlwZSAvQW5ub3QKPj4KZW5kb2JqCjE2IDAgb2JqCjw8Ci9BUCA8PAovTiAyNyAwIFIKPj4KL0YgNAovRlQgL1R4Ci9NSyA8PAo+PgovUCAyMCAwIFIKL1JlY3QgWzE1My4yOCA2NjAuMzQ1IDQ1OC40MzUgNjgxLjkwN10KL1N1YnR5cGUgL1dpZGdldAovVCAoVGV4dEZpZWxkMSkKL1R5cGUgL0Fubm90Cj4+CmVuZG9iagoxNyAwIG9iago8PAovQVAgPDwKL04gMjggMCBSCj4+Ci9GIDQKL0ZUIC9UeAovTUsgPDwKPj4KL1AgMjAgMCBSCi9SZWN0IFsxNTMuMjggNjMyLjMxMSA0NTguNDM1IDY1My44NzNdCi9TdWJ0eXBlIC9XaWRnZXQKL1QgKFRleHRGaWVsZDIpCi9UeXBlIC9Bbm5vdAo+PgplbmRvYmoKMTggMCBvYmoKPDwKL0FQIDw8Ci9OIDI5IDAgUgo+PgovRiA0Ci9GVCAvVHgKL01LIDw8Cj4+Ci9QIDIwIDAgUgovUmVjdCBbMTUzLjI4IDYwNC4wNjEgNDU4LjQzNSA2MjUuNjIzXQovU3VidHlwZSAvV2lkZ2V0Ci9UIChUZXh0RmllbGQzKQovVHlwZSAvQW5ub3QKPj4KZW5kb2JqCjE5IDAgb2JqCjw8Ci9TIC9ECj4+CmVuZG9iagoyMCAwIG9iago8PAovQW5ub3RzIDMwIDAgUgovQ29udGVudHMgMzEgMCBSCi9Dcm9wQm94IFswIDAgNjEyIDc5Ml0KL01lZGlhQm94IFswIDAgNjEyIDc5Ml0KL1BhcmVudCA2IDAgUgovUmVzb3VyY2VzIDMyIDAgUgovUm90YXRlIDAKL1R5cGUgL1BhZ2UKPj4KZW5kb2JqCjIxIDAgb2JqCjw8Ci9CQm94IFswIDAgMjAxLjA5MzAwMiAyMi40OTk5NjldCi9GaWx0ZXIgWy9GbGF0ZURlY29kZV0KL0xlbmd0aCAxOAovUmVzb3VyY2VzIDw8Cj4+Ci9TdWJ0eXBlIC9Gb3JtCi9UeXBlIC9YT2JqZWN0Cj4+CnN0cmVhbQp42tMPqVBw8nXmcgViABQmAtcKZW5kc3RyZWFtCmVuZG9iagoyMiAwIG9iago8PAovQkJveCBbMCAwIDIwMS4wOTMwMDIgMjIuNV0KL0ZpbHRlciBbL0ZsYXRlRGVjb2RlXQovTGVuZ3RoIDE4Ci9SZXNvdXJjZXMgPDwKPj4KL1N1YnR5cGUgL0Zvcm0KL1R5cGUgL1hPYmplY3QKPj4Kc3RyZWFtCnja0w+pUHDydeZyBWIAFCYC1wplbmRzdHJlYW0KZW5kb2JqCjIzIDAgb2JqCjw8Ci9CQm94IFswIDAgMjAxLjA5MzAwMiAyMi41XQovRmlsdGVyIFsvRmxhdGVEZWNvZGVdCi9MZW5ndGggMTgKL1Jlc291cmNlcyA8PAo+PgovU3VidHlwZSAvRm9ybQovVHlwZSAvWE9iamVjdAo+PgpzdHJlYW0KeNrTD6lQcPJ15nIFYgAUJgLXCmVuZHN0cmVhbQplbmRvYmoKMjQgMCBvYmoKPDwKL0JCb3ggWzAgMCAyNzIuMzQxOTggNDcuODEzMDQ5XQovTGVuZ3RoIDAKL1Jlc291cmNlcyA8PAo+PgovU3VidHlwZSAvRm9ybQovVHlwZSAvWE9iamVjdAo+PgpzdHJlYW0KCmVuZHN0cmVhbQplbmRvYmoKMjUgMCBvYmoKPDwKL0JCb3ggWzAgMCAyNzIuMzQxOTggNDcuODExOTgxXQovTGVuZ3RoIDAKL1Jlc291cmNlcyA8PAo+PgovU3VidHlwZSAvRm9ybQovVHlwZSAvWE9iamVjdAo+PgpzdHJlYW0KCmVuZHN0cmVhbQplbmRvYmoKMjYgMCBvYmoKPDwKL0JCb3ggWzAgMCAyNzIuMzQxOTggNDcuODEyOTg4XQovTGVuZ3RoIDAKL1Jlc291cmNlcyA8PAo+PgovU3VidHlwZSAvRm9ybQovVHlwZSAvWE9iamVjdAo+PgpzdHJlYW0KCmVuZHN0cmVhbQplbmRvYmoKMjcgMCBvYmoKPDwKL0JCb3ggWzAgMCAzMDUuMTU0OTk5IDIxLjU2MjAxMl0KL0ZpbHRlciBbL0ZsYXRlRGVjb2RlXQovTGVuZ3RoIDE4Ci9SZXNvdXJjZXMgPDwKPj4KL1N1YnR5cGUgL0Zvcm0KL1R5cGUgL1hPYmplY3QKPj4Kc3RyZWFtCnja0w+pUHDydeZyBWIAFCYC1wplbmRzdHJlYW0KZW5kb2JqCjI4IDAgb2JqCjw8Ci9CQm94IFswIDAgMzA1LjE1NDk5OSAyMS41NjIwMTJdCi9GaWx0ZXIgWy9GbGF0ZURlY29kZV0KL0xlbmd0aCAxOAovUmVzb3VyY2VzIDw8Cj4+Ci9TdWJ0eXBlIC9Gb3JtCi9UeXBlIC9YT2JqZWN0Cj4+CnN0cmVhbQp42tMPqVBw8nXmcgViABQmAtcKZW5kc3RyZWFtCmVuZG9iagoyOSAwIG9iago8PAovQkJveCBbMCAwIDMwNS4xNTQ5OTkgMjEuNTYyMDEyXQovRmlsdGVyIFsvRmxhdGVEZWNvZGVdCi9MZW5ndGggMTgKL1Jlc291cmNlcyA8PAo+PgovU3VidHlwZSAvRm9ybQovVHlwZSAvWE9iamVjdAo+PgpzdHJlYW0KeNrTD6lQcPJ15nIFYgAUJgLXCmVuZHN0cmVhbQplbmRvYmoKMzAgMCBvYmoKWzE2IDAgUiAxNyAwIFIgMTggMCBSIDEwIDAgUiAxMSAwIFIgMTIgMCBSIDEzIDAgUiAxNCAwIFIgMTUgMCBSXQplbmRvYmoKMzEgMCBvYmoKPDwKL0ZpbHRlciBbL0ZsYXRlRGVjb2RlXQovTGVuZ3RoIDI5NAo+PgpzdHJlYW0KSIm8kk9rwkAQxe/7Kea4KWTd3fwxehTTlh7rHApFSjRr3KIpmBX78TublmiLhNqCSSBDmPm99zYzuJspqBo2QTZA1KAAV0xpkHTTayRhKDMxSgG3TELFpJBSxoBLFvqSWvDAnjmubQP0FID3wVCk/DHPw8ZWtdmBM42D8m253waZ0NwEYSIyXjs4BHN8YCoVcUxqOCUB4kpP5Natwc/buoKyIASY0rpisTHEe3ewsmZTNgICfGXhJyJUQiUew9uveHMsWtdR59qXpIEedOtBoMbQ9R5N/Hpcj/8zHfVN9xQtTidt+4zOqnD7nfFJnl4uuk7Uf9pWne1EfdeZ0k/pP7U/R9BXjKAviSDbHYtPlkye2bqzmaIrZoq+MuXIPgQYAFjl/VkKCmVuZHN0cmVhbQplbmRvYmoKMzIgMCBvYmoKPDwKL0V4dEdTdGF0ZSA8PAovR1MxIDMzIDAgUgo+PgovRm9udCA8PAovVFQyIDM0IDAgUgo+PgovUHJvY1NldCBbL1BERiAvVGV4dF0KPj4KZW5kb2JqCjMzIDAgb2JqCjw8Ci9PUCBmYWxzZQovb3AgZmFsc2UKL09QTSAxCi9TQSBmYWxzZQovU00gMC4wMgovVHlwZSAvRXh0R1N0YXRlCj4+CmVuZG9iagozNCAwIG9iago8PAovQmFzZUZvbnQgL1RpbWVzTmV3Um9tYW5QU01UCi9FbmNvZGluZyAvV2luQW5zaUVuY29kaW5nCi9GaXJzdENoYXIgMzIKL0ZvbnREZXNjcmlwdG9yIDM1IDAgUgovTGFzdENoYXIgMTIwCi9TdWJ0eXBlIC9UcnVlVHlwZQovVHlwZSAvRm9udAovV2lkdGhzIFsyNTAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMzMzIDI1MCAwCjAgNTAwIDUwMCA1MDAgMCAwIDAgMCAwIDAgMjc4IDAgMCAwIDAgMAowIDAgMCAwIDcyMiA2MTEgNTU2IDAgNzIyIDAgMCAwIDAgMCAwIDAKMCAwIDY2NyA1NTYgNjExIDAgMCAwIDcyMiAwIDAgMCAwIDAgMCA1MDAKMCA0NDQgNTAwIDQ0NCA1MDAgNDQ0IDMzMyA1MDAgNTAwIDI3OCAwIDAgMjc4IDc3OCA1MDAgNTAwCjAgMCAzMzMgMzg5IDI3OCA1MDAgMCA3MjIgNTAwXQo+PgplbmRvYmoKMzUgMCBvYmoKPDwKL0FzY2VudCA4OTEKL0NhcEhlaWdodCA2NTYKL0Rlc2NlbnQgLTIxNgovRmxhZ3MgMzQKL0ZvbnRCQm94IFstNTY4IC0zMDcgMjAwMCAxMDA3XQovRm9udEZhbWlseSAoVGltZXMgTmV3IFJvbWFuKQovRm9udE5hbWUgL1RpbWVzTmV3Um9tYW5QU01UCi9Gb250U3RyZXRjaCAvTm9ybWFsCi9Gb250V2VpZ2h0IDQwMAovSXRhbGljQW5nbGUgMAovU3RlbVYgODIKL1R5cGUgL0ZvbnREZXNjcmlwdG9yCi9YSGVpZ2h0IC01NDYKPj4KZW5kb2JqCnhyZWYKMCAzNgowMDAwMDAwMDAwIDY1NTM1IGYNCjAwMDAwMDAwMTUgMDAwMDAgbg0KMDAwMDAwMDI4MiAwMDAwMCBuDQowMDAwMDAwMzgxIDAwMDAwIG4NCjAwMDAwMDA1ODEgMDAwMDAgbg0KMDAwMDAwMzk1OCAwMDAwMCBuDQowMDAwMDAzOTk2IDAwMDAwIG4NCjAwMDAwMDQwNTQgMDAwMDAgbg0KMDAwMDAwNTM3OCAwMDAwMCBuDQowMDAwMDA1NDc2IDAwMDAwIG4NCjAwMDAwMDU1NjEgMDAwMDAgbg0KMDAwMDAwNTcyNSAwMDAwMCBuDQowMDAwMDA1ODg5IDAwMDAwIG4NCjAwMDAwMDYwNTMgMDAwMDAgbg0KMDAwMDAwNjIxMyAwMDAwMCBuDQowMDAwMDA2Mzc0IDAwMDAwIG4NCjAwMDAwMDY1MzUgMDAwMDAgbg0KMDAwMDAwNjY5NSAwMDAwMCBuDQowMDAwMDA2ODU1IDAwMDAwIG4NCjAwMDAwMDcwMTUgMDAwMDAgbg0KMDAwMDAwNzA0MyAwMDAwMCBuDQowMDAwMDA3MTk4IDAwMDAwIG4NCjAwMDAwMDczNzAgMDAwMDAgbg0KMDAwMDAwNzUzNyAwMDAwMCBuDQowMDAwMDA3NzA0IDAwMDAwIG4NCjAwMDAwMDc4MzMgMDAwMDAgbg0KMDAwMDAwNzk2MiAwMDAwMCBuDQowMDAwMDA4MDkxIDAwMDAwIG4NCjAwMDAwMDgyNjMgMDAwMDAgbg0KMDAwMDAwODQzNSAwMDAwMCBuDQowMDAwMDA4NjA3IDAwMDAwIG4NCjAwMDAwMDg2ODggMDAwMDAgbg0KMDAwMDAwOTA1NyAwMDAwMCBuDQowMDAwMDA5MTU0IDAwMDAwIG4NCjAwMDAwMDkyMzkgMDAwMDAgbg0KMDAwMDAwOTY1NyAwMDAwMCBuDQp0cmFpbGVyCjw8Ci9JRCBbPDQ3QTVDMzFDRjIxQTQ3QjlCM0Q2MjFFOEVCRjIyMzBFPiA8MEIxMjc0N0ZDMTk5M0U0RTg2RjdBRjAyMTAxQUU0MDk+XQovSW5mbyAxIDAgUgovUm9vdCAyIDAgUgovU2l6ZSAzNgo+PgpzdGFydHhyZWYKOTkxOQolJUVPRgo=";
                if(filedata != null) {
                    s = filedata;
                }
                bytesArray = Base64.getDecoder().decode(s);
                return bytesArray;

            }
        
        
        private static void writeBytesToFile(byte[] bFile, String fileDest) {

                try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
                    fileOuputStream.write(bFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
        }
        
        
        public byte[] generateMacPdf(AddressManagementPojo addressManagementObj) throws AmpException {
                logger.error(AddressManagementFacade.class, "generateMacPdf()", "Start generateMacPdf", null);
             
                ChangeMailAddressType chgMailAddrType = new ChangeMailAddressType();
             
                AddressType newMailingAdd = addressParsing(addressManagementObj.getNewMailingAddress());
    //            String[] tokens =newAddress.split(",");
    //            int tokencount=tokens.length;
    //            String street = "";
    //    //            for(int j=0; j<tokencount; j++){
    //    //                System.out.println("split Output:"+tokens[j]);
    //    //            }
    //            NewMailingAddress newMailingAdd= new NewMailingAddress();
    //            if(tokencount == 5) {
    //                String[] newMailStreetSplit = new String[5];
    //                if(tokencount>2){
    //                newMailingAdd.setZipCode(tokens[tokencount-2]);
    //                }if(tokencount>3){
    //                newMailingAdd.setState(tokens[tokencount-3]);
    //                }if(tokencount>4){
    //                newMailingAdd.setCity(tokens[tokencount-4]);
    //                }if(tokencount>4){
    //                for (int j=0; j<tokencount-4 ;j++){
    //                street+=tokens[j];
    //                }
    //                //            newMailStreetSplit= street.split("\\s",2);
    //                //            newMailingAdd.setStreetNumber(newMailStreetSplit[0]);
    //                //            newMailingAdd.setStreet(newMailStreetSplit[1]);
    //                newMailingAdd.setStreetAddress(street);
    //                } 
    //            } else {
    //                newMailingAdd.setStreetAddress(newAddress);
    //            }
            
             //   ArrayList<AinObject> ainList = new ArrayList<AinObject>();
                ArrayList<NewMailingAddress> propAddress = new ArrayList<NewMailingAddress>();

    //            ChangeMailAddressObj.setPhone(addressManagementObj.getDaytimePhone());
    //            ChangeMailAddressObj.setEmail(addressManagementObj.getEmail());
    //            ChangeMailAddressObj.setDate(addressManagementObj.getEnteredDate());
    //            ChangeMailAddressObj.setName(addressManagementObj.getPropertyOwnerName());
                
                chgMailAddrType.setPHONE(addressManagementObj.getDaytimePhone());
                chgMailAddrType.setEMAIL(addressManagementObj.getEmail());
                chgMailAddrType.setNAME(addressManagementObj.getPropertyOwnerName());
             //   c.setDATE1(addressManagementObj.getEnteredDate());
            //    c.setSIGNATURE1("test");
                chgMailAddrType.setNEWMAILINGADDRESSCITY(newMailingAdd.getCity());
                chgMailAddrType.setNEWMAILINGADDRESSCOUNTRY(newMailingAdd.getCountry());
                chgMailAddrType.setNEWMAILINGADDRESSSTATE(newMailingAdd.getState());
                chgMailAddrType.setNEWMAILINGADDRESSSTREETADDRESS(newMailingAdd.getStreet());
                chgMailAddrType.setNEWMAILINGADDRESSZIP(newMailingAdd.getZip());
                
                if(addressManagementObj.getAinsPojo().size()>0){  
                    ChangeMailAddressType.ADDITIONALPROPERTYADDRESSG addPropAddr = null;
                  for (int i = 0; i < addressManagementObj.getAinsPojo().size(); i++) {
                      AinPojo ainPojo = addressManagementObj.getAinsPojo().get(i);
                    //  NewMailingAddress oldmailingAdd = new NewMailingAddress();
                      if (ainPojo != null) {
                          
                          if( i == 1) {
                              addPropAddr = new ChangeMailAddressType.ADDITIONALPROPERTYADDRESSG();
                              chgMailAddrType.setADDITIONALPROPERTYADDRESSG(addPropAddr);
                          }
                          
              
                          if(i ==0) {
                              chgMailAddrType.setPROPERTYADDRESSCITY(ainPojo.getCurrentSitusAddress().getCity());
                              chgMailAddrType.setPROPERTYADDRESSSTATE(ainPojo.getCurrentSitusAddress().getState());
                              chgMailAddrType.setPROPERTYADDRESSSTREETADDRESS(ainPojo.getCurrentSitusAddress().getStreetAddress());
                              chgMailAddrType.setPROPERTYADDRESSZIP(ainPojo.getCurrentSitusAddress().getZipCode());
                              chgMailAddrType.setPROPERTYADDRESSMAPBOOK(ainPojo.getAin().substring(0, 4));
                              chgMailAddrType.setPROPERTYADDRESSPAGE(ainPojo.getAin().substring(4, 7));
                              chgMailAddrType.setPROPERTYADDRESSPARCEL(ainPojo.getAin().substring(7, 10));   
                          } else {
                              ChangeMailAddressType.ADDITIONALPROPERTYADDRESSG.ADDITIONALPROPERTYADDRESS propAddr =  new ChangeMailAddressType.ADDITIONALPROPERTYADDRESSG.ADDITIONALPROPERTYADDRESS();
                              propAddr.setCITY(ainPojo.getCurrentSitusAddress().getCity());
                              propAddr.setSTATE(ainPojo.getCurrentSitusAddress().getState());
                              propAddr.setSTREETADDRESS(ainPojo.getCurrentSitusAddress().getStreetAddress());
                              propAddr.setZIP(ainPojo.getCurrentSitusAddress().getZipCode());
                              propAddr.setMAPBOOK(ainPojo.getAin().substring(0, 4));
                              propAddr.setPAGE(ainPojo.getAin().substring(4, 7));
                              propAddr.setPARCEL(ainPojo.getAin().substring(7, 10));  
                              addPropAddr.getADDITIONALPROPERTYADDRESS().add(propAddr);
                          }
                          
                         
                          
                      }
                   
                  }
                }
             
                PublicMailAddressChangePDFServiceWrapper pdfSvcWrapper = new PublicMailAddressChangePDFServiceWrapper();
                GenratePMACPDFRequest req = new GenratePMACPDFRequest();
                req.setCHANGEMAILADDRESS(chgMailAddrType);
                String pdfEncoded= pdfSvcWrapper.generateMacPdf(req);
    //            byte[] decodedImg = Base64.getDecoder().decode(pdfEncoded.getBytes(StandardCharsets.UTF_8));
    //            Path destinationFile = Paths.get("d:/laca", "sample.pdf");
    //        try {
    //            Files.write(destinationFile, decodedImg);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
            return   readBytesFromFile(pdfEncoded);
             

                 //   return response.getReportBytes();
            }

    }
