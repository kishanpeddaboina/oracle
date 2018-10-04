package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.data.AinDO;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.model.data.Event;
import gov.laca.amp.bvm.model.data.Header;
import gov.laca.amp.bvm.model.data.Year;
import gov.laca.amp.bvm.util.BaseYearEventConstants;
import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.bvm.util.Roles;
import gov.laca.amp.ent.exception.AmpException;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;
import gov.laca.amp.fwk.util.JSFUtils;
import gov.laca.amp.portal.extension.AmpBusinessUserProfile;
import gov.laca.amp.proxy.soap.identitylookupservice.client.FaultMessage;
import gov.laca.amp.proxy.soap.rppropertydetails.client.gen.PropNotf;  // OATS Issues# 14580, 19576 and 23976
import gov.laca.amp.proxy.soap.rppropertydetails.client.gen.PropNotfList;  // OATS Issues# 14580, 19576 and 23976
import gov.laca.amp.proxy.soap.rppropertydetails.client.gen.RetrievePropNotfResponse;  // OATS Issues# 14580, 19576 and 23976
import gov.laca.amp.proxy.soap.rppropertydetails.local.PropertyDetailsWrapper;  // OATS Issues# 14580, 19576 and 23976

import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventResp;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventRespList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueTotalList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseYearValueResponse;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendObjectList;
import gov.lacounty.assessor.amp.data.bvm.validatebaseyearevent.v1.BaseYearEventErr;

import java.math.BigInteger;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;


/**
 * @author Vijay Redla
 * @version 2.2
 * 
 * Modified by: Mark Piller
 * OATS Issues# 14580, 19576 and 23976 (version 2.1)
 * OATS Issue# 26296 - Only show assigned to if event type = BVM (version 2.2)
 * 
 * 
 */
public class InitiateBaseYearEvent {
    public static final AmpLogger LOGGER =
        new AmpLogger(InitiateBaseYearEvent.class);
    private static final String FILTERED_CURRENT_EVENTS = "CURRENT";
    private static final String FILTERED_HISTORICAL_EVENTS = "HOSTORICAL";
    private static final String BPM_ORIGINAL_ITERATOR_NAME =
        "baseYearEventIterator";
    private static final String BPM_MODIFIED_ITERATOR_NAME =
        "baseYearEventIterator1";
    private static final String SELCTED_EVENTS_KEY = "selectedEvents";
    private static final String CURRENT_SELECTED_EVENT_KEY =
        "currentSelectedEvent";
    private static final String HISTORICAL_SELECTED_EVENT_KEY =
        "selectedHistoricalEvent";
    private ArrayList<BaseYearEventDO> originalEventsList =
        new ArrayList<BaseYearEventDO>();
    private ArrayList<BaseYearEventDO> currentEventsList = null;
    private ArrayList<BaseYearEventDO> filteredCurrentEventsList =
        new ArrayList<BaseYearEventDO>();
    private ArrayList<BaseYearEventDO> filteredHistoricalEventsList =
        new ArrayList<BaseYearEventDO>();
    private final ArrayList<BaseYearEventDO> reviewEventsList =
        new ArrayList<BaseYearEventDO>();
    private final ArrayList<BaseYearEventDO> selectedEventsList =
        new ArrayList<BaseYearEventDO>();
    private final ArrayList<BaseYearEventDO> tbvSelectedEventsList = new ArrayList<BaseYearEventDO>();

    private final ArrayList<BigInteger> deletedEventsList =
        new ArrayList<BigInteger>();
    private final ArrayList<BigInteger> newEventsList =
        new ArrayList<BigInteger>();
    Boolean isBVNInitiated = false;
    String initiatedBy;
    Boolean filtered = false;
    private AinDO ainDO = new AinDO();

    private final BaseYearEventDO originialCurrentEvent =
        new BaseYearEventDO();
    private final BaseYearEventDO originialHistoricalEvent =
        new BaseYearEventDO();
    private final BaseYearEventDO selectedCurrentEvent = new BaseYearEventDO();
    private final BaseYearEventDO selectedHistoricalEvent =
        new BaseYearEventDO();
    private final BaseYearEventDO copyOfEvent = new BaseYearEventDO();

    private String lockedMessage = null; // OATS Issues# 14580, 19576 and 23976
    private String ain = null;
    private String userId = null;
    private BigInteger aoid = null;
    private BaseYearEventDO newEvent = null;
    private BigInteger newEventID = BigInteger.valueOf(0);
    private Boolean isBVMRecord = false; // OATS issue 26296
    private Boolean isDirty = false;
    private Boolean isRealProperty = true;
    private Boolean isFixture = true;
    private Boolean isPersonalProperty = false;
    private Boolean checkDistrict = false;
    private Boolean checkManageBVM = false;
    private Boolean isVisibleNotificationMessage = false;

    // Trend base value objects
    private ArrayList<Event> trendEventList = new ArrayList<Event>();
    private ArrayList<Event> footerTrendEventList  = new ArrayList<Event>();
 //   private ArrayList<Event> eventTrendTotalList  = new ArrayList<Event>();
    private ArrayList<Header> trendHeaderList  = new ArrayList<Header>();
    private String targerYear = null;
    // Trend base value objects
    private ArrayList<Header> detrendHeaderList = new ArrayList<Header>();
    private ArrayList<Event> deTrendEventList = new ArrayList<Event>();
    private ArrayList<Event> footerDeTrendEventList = new ArrayList<Event>();
    
    private String outputText = null;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");
    private List<LookupItem> eventNameLov;
    private List<LookupItem> eventDescriptionLov;
    private List<LookupItem> valueReasonCodeLov;

    private void checkPropertyType() {
        String ainType = getAinDO().getAinType();
        if (ainType != null) {
            if (ainType.equals(personalProperties.Company) ||
                ainType.equals(personalProperties.Marine) ||
                ainType.equals(personalProperties.Aircraft)) {
                setIsPersonalProperty(true);
            }
        }
    }

    /**
     * Check if current user belogns to the same district as
     * AIN. If user do not belong to AIN district then user cannot
     * initiate BVM
     */
    private void checkForAINDistrict() {
        String ainDsitrictName = ainDO.getDistrictName();
        LOGGER.warn(this, "checkForAINDistrict",
                    BaseYearEventUtils.LOGGER_PREFIX + "checkForAINDistrict() - AIN District Name1: " +
                    ainDsitrictName, null);
        if (ainDsitrictName != null && !"".equals(ainDsitrictName)) {
            AmpBusinessUserProfile ampBusinessUserProfile =
                (AmpBusinessUserProfile)JSFUtils.getManagedBeanValue(BaseYearEventConstants.AMP_PROFILEBEAN_NAME);
            if (ampBusinessUserProfile != null) {
                try {
                    if (ampBusinessUserProfile.getUserId() == null) {
                        ampBusinessUserProfile.setUserId(getUserId());
                        ampBusinessUserProfile.retrieveUserDetails();
                    }
                    List<String> districtList =
                        ampBusinessUserProfile.getUserDistrictList();
                    for (String district : districtList) {
                        if (ainDsitrictName.contains(district)) {
                            checkDistrict = true;
                        }
                        LOGGER.warn(this, "checkForAINDistrict",
                                    BaseYearEventUtils.LOGGER_PREFIX +
                                    "User District Name: " + district, null);
                    }
                    if (checkDistrict) {
                        checkForMangedBVM(ampBusinessUserProfile.getUserRoleList());
                    }
                } catch (FaultMessage e) {
                    LOGGER.warn(this, "checkForAINDistrict",
                                BaseYearEventUtils.LOGGER_PREFIX +
                                "Current User Lookup Failed", null);
                }
            } else {
                LOGGER.error(this, "checkForAINDistrict",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "User Profile Infromatiion Missing", null);
                ADFUtils.addFacesErrorMessage("User Profile is missing");
            }
        } else {
            LOGGER.error(this, "checkForAINDistrict",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "No District for This AIN", null);
            ADFUtils.addFacesErrorMessage("No District for This AIN");
        }
    }

    public Boolean getCheckDistrict() {
        return checkDistrict;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setTargerYear(String targerYear) {
        this.targerYear = targerYear;
    }

    public String getTargerYear() {
        return targerYear;
    }

    /**
     * Retrieve Event Name Drop down List from LOV Service Call.
     * @return
     */
    public List<LookupItem> getEventNameLov() {
        if (eventNameLov == null) {
            eventNameLov = new ArrayList<LookupItem>();
            LOVManagementServiceWrapper lovService =
                new LOVManagementServiceWrapper();
            try {
                lovService.retrieveLovListFor("EVENT NAME", eventNameLov);
            } catch (AmpBaseYearEventException e) {
                LOGGER.error(this, "getEventNameLov",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "Error Look up Event Name Codes", null);
            }
        }
        return eventNameLov;
    }

    /**
     * Retrieve Event Description Drop down List from LOV Service Call.
     * @return
     */
    public List<LookupItem> getEventDescriptionLov() {
        if (eventDescriptionLov == null) {
            eventDescriptionLov = new ArrayList<LookupItem>();
            LOVManagementServiceWrapper lovService =
                new LOVManagementServiceWrapper();
            try {
                lovService.retrieveLovListFor("EVENT DESCRIPTION",
                                              eventDescriptionLov);
            } catch (AmpBaseYearEventException e) {
                LOGGER.error(this, "getEventDescriptionLov",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "Error Look up Event Description Codes", null);
            }
        }
        return eventDescriptionLov;
    }

    /**
     * Retrieve Value Reason Code Drop down List from LOV Service Call.
     * @return
     */
    public List<LookupItem> getValueReasonCodeLov() {
        if (valueReasonCodeLov == null) {
            valueReasonCodeLov = new ArrayList<LookupItem>();
            LOVManagementServiceWrapper lovService =
                new LOVManagementServiceWrapper();
            try {
                lovService.retrieveLovListFor("ValueReasonCode",
                                              valueReasonCodeLov);
            } catch (AmpBaseYearEventException e) {
                LOGGER.error(this, "getValueReasCodeLov",
                             BaseYearEventUtils.LOGGER_PREFIX +
                             "Error Look up Value Reason Codes", null);
            }
        }
        return valueReasonCodeLov;
    }

    public Boolean getCheckManageBVM() {
        return checkManageBVM;
    }

    /**
     *This method checks who has can initiatate a BVM process.
     * 1. Check for existing lock
     * 2. Chief Appraiser and Director cannot initiate an BVM
     * @param roles
     */
    private void checkForMangedBVM(List<String> roles) {
        boolean checkRole = false;
        for (String role : roles) {
            if (Roles.ASSISTANT_ASSESSOR.equalsIgnoreCase(role) ||
                Roles.CHIEF_APPRAISER.equalsIgnoreCase(role) ||
                Roles.DIRECTOR.equalsIgnoreCase(role)) {
                checkRole = true;
            }
            LOGGER.warn(this, "checkForMangedBVM",
                        BaseYearEventUtils.LOGGER_PREFIX + "User Role: " +
                        role, null);
        }
        LOGGER.warn(this, "checkForMangedBVM",
                    BaseYearEventUtils.LOGGER_PREFIX + "checkRole " +
                    checkRole, null);
        
        //if (getInitiatedBy() == null && !checkRole) {
        // OATS Issues#: 14580, 19576 and 23976
        // When no message is on display then the button is visible
        // If any message (locked or assigned to) is on display then button is invisible
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "checkForMangedBVM() - getLockedMessage() = '" + getLockedMessage() + "'");
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "checkForMangedBVM() - getInitiatedBy() = '" + getInitiatedBy() + "'");
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "checkForMangedBVM() - checkRole = " + checkRole + " note: if checkRole is true then button is NOT visible");
        if (getLockedMessage() == null && getInitiatedBy() == null && !checkRole && !isBVMRecord) {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "checkForMangedBVM() - getLockedMessage() is null and getInitiatedBy() is null and checkRole = true therefore make button visible");
            checkManageBVM = true;
        } else {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "checkForMangedBVM() - make button visible");
            checkManageBVM = false;
        }
        LOGGER.warn(this, "checkForMangedBVM",
                    BaseYearEventUtils.LOGGER_PREFIX + "checkManageBVM " +
                    checkManageBVM, null);
    }

    public void setIsFixture(Boolean isFixture) {
        this.isFixture = isFixture;
    }

    public Boolean getIsFixture() {
        return isFixture;
    }

    /**
     * setLockedMessage()
     * This will create a message to display to the user of 
     * the status of the record.
     * 
     * Author: Mark Piller
     * OATS Issues#: 14580, 19576 and 23976
     * 
     */
    public void setLockedMessage(String message) {
        String _lockedMessage = null;

        if(message == null ) {
            String dbLockStatus = ainDO.getDbLockStatus();
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setLockedMessage() Locked Status = " + dbLockStatus);
            if("LOCKED".equals(dbLockStatus) ) {
                // get initiated by which equal assigned to
                // if there is no value the locked statement does not need a comma in the beginning
                // if there is an assigned to then have a comma for the locked statment
                String _initiatedBy = getInitiatedBy();
                if(_initiatedBy == null ||  "".equals(_initiatedBy)) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setLockedMessage() set lockedMessage to 'Manage Base Year Events is in progress'");
                    _lockedMessage = " Manage Base Year Events is in progress";
                } else {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setLockedMessage() set lockedMessage to ', Manage Base Year Events is in progress'");
                    _lockedMessage = ", Manage Base Year Events is in progress";
                }
            } else {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setLockedMessage() set lockedMessage = null");
                _lockedMessage = null;
            }
        } else {
            _lockedMessage = message;
        }
        
        this.lockedMessage = _lockedMessage;
    }

    public String getLockedMessage() {
        return lockedMessage;
    }

    public void setIsVisibleNotificationMessage(Boolean isVisibleNotificationMessage) {
        this.isVisibleNotificationMessage = isVisibleNotificationMessage;
    }

    /**
     *
     * @return
     * 
     * Author: Mark Piller
     * OATS Issues#: 14580, 19576 and 23976
     * 
     * Easiest way to determiine if the message line is to be dieplayed.
     * 
     */
    public Boolean getIsVisibleNotificationMessage() {
        String lockedMessage = getLockedMessage();
        String initiatedBy = getInitiatedBy();
        
        // default
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getIsVisibleNotificationMessage() default... isVisibleNotificationMessage = false");
        this.isVisibleNotificationMessage = false;
        
        // if there is a locked message then make the notification visible to the user
        if( (lockedMessage != null) && (!"".equals(lockedMessage)) ) {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getIsVisibleNotificationMessage() default... lockedMessage = '" + lockedMessage + "' make it visible to user");
            this.isVisibleNotificationMessage = true;
        }

        // if there is an initiated by then make the notification visible to the user        
        if( (initiatedBy != null) && (!"".equals(initiatedBy)) ) {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getIsVisibleNotificationMessage() default... initiatedBy = '" + initiatedBy + "' make it visible to user");
            this.isVisibleNotificationMessage = true;
        }

        return isVisibleNotificationMessage;
    }

    public void setIsBVMRecord(Boolean isBVMRecord) {
        this.isBVMRecord = isBVMRecord;
    }

    public Boolean getIsBVMRecord() {
        return isBVMRecord;
    }

    private enum AINTypes {
        Company,
        Aircraft,
        Marine;
        @SuppressWarnings("compatibility:7413865038253710519")
        private static final long serialVersionUID = 1L;
    }
    private AINTypes personalProperties;

    public InitiateBaseYearEvent() {
        super();
    }

    /**
     *Search for an event in a list based on ByeID
     * @param searchList
     * @param byeId
     * @return
     */
    private BaseYearEventDO findBYE(ArrayList<BaseYearEventDO> searchList,
                                    BigInteger byeId) {
        for (BaseYearEventDO baseYearEvent : searchList) {
            if (baseYearEvent.getByeId().equals(byeId)) {
                return baseYearEvent;
            }
        }
        return null;
    }

    /**
     * Check all the filter criteria and find events that match. All criteria are
     * required and filters Current and Historical event Lists
     * @param eventDateBegin
     * @param eventDateEnd
     * @param effectiveDateBegin
     * @param effectiveDateEnd
     * @param effectiveEndDateBegin
     * @param effectiveEndDateEnd
     * @param rollYear
     * @param verified
     * @param unverified
     * @param active
     * @param inactive
     * @return
     */
    public Boolean filterBaseYearEvents(Date eventDateBegin, Date eventDateEnd,
                                        Date effectiveDateBegin,
                                        Date effectiveDateEnd,
                                        Date effectiveEndDateBegin,
                                        Date effectiveEndDateEnd,
                                        String rollYear, Boolean verified,
                                        Boolean unverified, Boolean active,
                                        Boolean inactive) {
        if (eventDateBegin == null && eventDateEnd == null &&
            effectiveDateBegin == null && effectiveDateEnd == null &&
            effectiveEndDateBegin == null && effectiveEndDateEnd == null &&
            rollYear == null && (verified == null || !verified) &&
            (unverified == null || !unverified) &&
            (active == null || !active) && (inactive == null || !inactive)) {
            return false;
        }
        
        //12c change, if null make Boolean false to fix null pointer
                
                if(verified == null)
                    verified = Boolean.FALSE;
                if(unverified == null)
                    unverified = Boolean.FALSE;
                if(active == null)
                    active = Boolean.FALSE;
                if(inactive == null)
                    inactive = Boolean.FALSE;
        
        //Clear the existing lists
        getFilteredCurrentEventsList().clear();
        getFilteredHistoricalEventsList().clear();

        if (currentEventsList != null) {
            for (BaseYearEventDO baseYearEvent : currentEventsList) {
                //Check for EventDateBegin
                boolean checkEventDateBegin = true;
                final Date eventDate = baseYearEvent.getEventDate();
                if (eventDateBegin != null) {
                    if (eventDate != null &&
                        eventDateBegin.compareTo(eventDate) <= 0) {
                        checkEventDateBegin = true;
                    } else {
                        checkEventDateBegin = false;
                    }
                }

                //Check for EventDateEnd
                boolean checkEventDateEnd = true;
                if (eventDateEnd != null) {
                    if (eventDate != null &&
                        eventDateEnd.compareTo(eventDate) >= 0) {
                        checkEventDateEnd = true;
                    } else {
                        checkEventDateEnd = false;
                    }
                }

                //Check for EffectivetBeginDateBegin
                boolean checkEffectiveBeginDateBegin = true;


                if (effectiveDateBegin != null) {
                    final Date effectiveBeginDate =
                        baseYearEvent.getEffectiveBeginDate();
                    if (effectiveBeginDate != null &&
                        effectiveDateBegin.compareTo(effectiveBeginDate) <=
                        0) {
                        checkEffectiveBeginDateBegin = true;
                    } else {
                        checkEffectiveBeginDateBegin = false;
                    }
                }

                //Check for EffectivetBeginDateEnd
                boolean checkEffectiveBeginDateEnd = true;
                if (effectiveDateEnd != null) {
                    final Date effectiveBeginDate =
                        baseYearEvent.getEffectiveBeginDate();
                    if (effectiveBeginDate != null &&
                        effectiveDateEnd.compareTo(effectiveBeginDate) >= 0) {
                        checkEffectiveBeginDateEnd = true;


                    } else {
                        checkEffectiveBeginDateEnd = false;

                    }
                }

                //Check for EffectivetEndDateBegin
                boolean checkEffectiveEndDateBegin = true;
                if (effectiveEndDateBegin != null) {


                    final Date effectiveEndDate =
                        baseYearEvent.getEffectiveEndDate();
                    if (effectiveEndDate != null &&
                        effectiveEndDateBegin.compareTo(effectiveEndDate) <=
                        0) {
                        checkEffectiveEndDateBegin = true;
                    } else {
                        checkEffectiveEndDateBegin = false;
                    }
                }

                //Check for EffectivetEndDateEnd
                boolean checkEffectiveEndDateEnd = true;
                if (effectiveEndDateEnd != null) {
                    final Date effectiveEndDate =
                        baseYearEvent.getEffectiveEndDate();

                    if (effectiveEndDate != null &&
                        effectiveEndDateEnd.compareTo(effectiveEndDate) >= 0) {
                        checkEffectiveEndDateEnd = true;

                    } else {
                        checkEffectiveEndDateEnd = false;

                    }
                }


                //Check for LienDate for RollYear
                //Defect 1316 include effectiveBeginDate into the logic --Vijay.
                boolean checkLienDate = true;
                if (rollYear != null) {
                    final Date effectiveBeginDate =
                        baseYearEvent.getEffectiveBeginDate();
                    final Date effectiveEndDate =
                        baseYearEvent.getEffectiveEndDate();
                    final Date lienDate = findLienDate(Integer.parseInt(rollYear));
                    if (eventDate != null &&
                        eventDate.compareTo(lienDate) <= 0) {
                        if ((effectiveBeginDate != null &&
                            effectiveBeginDate.compareTo(lienDate) <=0) &&
                            (effectiveEndDate == null || 
                             effectiveEndDate.compareTo(lienDate) >= 0)) {
                            checkLienDate = true;
                        } else {
                            checkLienDate = false;
                        }
                    } else {
                        checkLienDate = false;
                    }
                }

                //Check for verified/unverified
                final Date verificationDate =
                    baseYearEvent.getVerificationDate();
                boolean checkVerified = true;
                boolean checkUnverified = true;
                //Kallesh
                System.out.println("\n**InitiatebaseYearEvnt Verfied Value**"+verified+"\n**Unverified Flag Value"+unverified);
                //Kallesh Null Check For Active/Inactive Flag
                if (verified != null || verified!=null)
                {
                if (verified || unverified) {
                    if (verified) {
                        if (verificationDate != null) {
                            checkVerified = true;
                        } else {
                            if (!unverified) {
                                checkVerified = false;
                            }
                        }
                    }
                    if (unverified) {
                        if (verificationDate == null) {
                            checkUnverified = true;
                        } else {
                            if (!verified) {
                                checkUnverified = false;
                            }
                        }
                    }
                }
                }
                //Check for active/inactive

                final Date inactiveDate = baseYearEvent.getInactiveDate();
                boolean checkActive = true;
                boolean checkInactive = true;
                //Kallesh Null Check For Active/Inactive Flag
                if (active != null || inactive!=null)
                {
                if (active || inactive) {
                    if (active) {
                        if (inactiveDate == null) {
                            checkActive = true;
                        } else {
                            if (!inactive) {
                                checkActive = false;
                            }
                        }
                    }
                    if (inactive) {
                        if (inactive != null){
                        if (inactiveDate != null) {
                            checkInactive = true;
                        } else {
                            if (!active) {
                                checkInactive = false;
                            }
                        }
                    }
                    }
                }
            }
                if (checkEventDateBegin && checkEventDateEnd &&
                    checkEffectiveBeginDateBegin &&
                    checkEffectiveBeginDateEnd && checkEffectiveEndDateBegin &&
                    checkEffectiveEndDateEnd && checkLienDate &&
                    checkVerified && checkUnverified && checkActive &&
                    checkInactive) {
                    //Found the Matching record
                    if (isCurrentEvent(baseYearEvent)) {
                        getFilteredCurrentEventsList().add(baseYearEvent);
                    } //else if (isHistoricalEvent(baseYearEvent)) {
                    else {
                        getFilteredHistoricalEventsList().add(baseYearEvent);
                    }
                }
            }
        } else {
            LOGGER.error(this, "filterBaseYearEvents",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "Current Event List is NULL", null);
        }
        return Boolean.TRUE;
    }


    /**
     * If the Base Year Event is on or before December 31, 1996, then the Lien Date is March 1st.
     * If the Base Year Event is after December 31, 1996, then the Lien Date is January 1st.
     * @param rollYear as Date
     * @return Lien Date
     */
    public Date findLienDate(int rollYear) {
        //Issue 1316 forced to rewrite the logic -Vijay 10/06/2016
        final Calendar cutOffDate = Calendar.getInstance();
        cutOffDate.set(1996, 11, 31);
        final Calendar eventYear = Calendar.getInstance();
        eventYear.set(rollYear, 1, 1);
        Calendar lienDate = Calendar.getInstance();
        if (eventYear.compareTo(cutOffDate) <= 0) {
            lienDate.set(rollYear, 3, 1);
            return lienDate.getTime();
        } else if (eventYear.compareTo(cutOffDate) > 0) {
            lienDate.set(rollYear, 0, 1);
            return lienDate.getTime();
        }
        return null;
    }

    /**
     * Removes current filters and shows all events.
     * @return
     */
    public Boolean allBaseYearEvents() {
        getFilteredCurrentEventsList().clear();
        getFilteredHistoricalEventsList().clear();
        Collections.sort(currentEventsList);
        if (currentEventsList != null) {
            for (BaseYearEventDO baseYearEvent : currentEventsList) {
                //Split Events as Current and Historical
                BaseYearEventDO event = new BaseYearEventDO();
                event.copy(baseYearEvent);
                if (isCurrentEvent(event)) {
                    getFilteredCurrentEventsList().add(event);
                } else {
                    getFilteredHistoricalEventsList().add(event);
                }
            }
        } else {
            LOGGER.error(this, "allBaseYearEvents",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "Current Event List is NULL", null);
        }
        return Boolean.TRUE;
    }

    /**
     * @param filteredBYEList
     */
    public void setFilteredCurrentEventsList(ArrayList<BaseYearEventDO> filteredBYEList) {
        this.filteredCurrentEventsList = filteredBYEList;
    }

    /**
     * @return
     */
    public ArrayList<BaseYearEventDO> getFilteredCurrentEventsList() {
        return filteredCurrentEventsList;
    }

    /**
     * @return
     */
    public ArrayList<BaseYearEventDO> getSelectedEventsList() {
        if (selectedEventsList != null) {
            selectedEventsList.clear();
        }
        //        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Current Events Size: " +
        //                    currentEventsList.size());
        for (BaseYearEventDO baseYearEvent : filteredHistoricalEventsList) {
            if (baseYearEvent.getSelected()) {
                selectedEventsList.add(baseYearEvent);
                //                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                //                            "Event Selected : " + baseYearEvent.getByeId());
            }
        }
        for (BaseYearEventDO baseYearEvent : filteredCurrentEventsList) {
            if (baseYearEvent.getSelected()) {
                selectedEventsList.add(baseYearEvent);
                //                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                //                            "Event Selected : " + baseYearEvent.getByeId());
            }
        }
        //        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Selected Events Size: " +
        //                    selectedEventsList.size());
        return selectedEventsList;
    }

    /**
     * @return
     */
    public ArrayList<BaseYearEventDO> getFilteredHistoricalEventsList() {
        return filteredHistoricalEventsList;
    }

    /**
     * @param source
     * @param ain
     * @param userId
     * @param aoid
     */
    public void loadData(String source, String ain, String userId,
                         String aoid) {
        if (ain == null) {
            LOGGER.error(this, "loadData",
                         BaseYearEventUtils.LOGGER_PREFIX + " FATAL Error in Input Paramters",
                         null);
            LOGGER.error(this, "loadData",
                         BaseYearEventUtils.LOGGER_PREFIX + "AIN Value: " +
                         ain, null);
            LOGGER.error(this, "loadData",
                         BaseYearEventUtils.LOGGER_PREFIX + "UserId Value: " +
                         userId, null);
            LOGGER.error(this, "loadData",
                         BaseYearEventUtils.LOGGER_PREFIX + "ID Value: " +
                         aoid, null);
        } else {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "loadData() source Value: " +
                        source);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "loadData() AIN Value: " +
                        ain);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "loadData() UserId Value: " +
                        userId);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "loadData() ID Value: " +
                        aoid);
        }
        
        if (ain != null) {
            setAin(ain);
            setUserId(userId);
            setAoid(aoid);
            if (getCurrentEventsList() != null) {
                getCurrentEventsList().clear();
            } else {
                setCurrentEventsList(new ArrayList<BaseYearEventDO>());
            }
            getFilteredCurrentEventsList().clear();
            getFilteredHistoricalEventsList().clear();
            setIsBVNInitiated(false);
            setInitiatedBy(null);
            setLockedMessage(null);
            if (source.equalsIgnoreCase("SOA")) {
                try {
                    BaseYearEventsMgmtWrapper baseYearEventsMgmtWrapper =
                        new BaseYearEventsMgmtWrapper();
                    Boolean result =
                        baseYearEventsMgmtWrapper.retrieveEventsFromService(currentEventsList,
                                                                            ainDO,
                                                                            getAin(),
                                                                            getUserId(),
                                                                            getAoid());
                    if (result) {
                        if (aoid == null && ainDO.getAoid() != null) {
                            setAoid(ainDO.getAoid().toString());
                        }
                        // OATS Issues#: 14580, 19576 and 23976
                        // no longer use lock management service...
                        //final AINLockMgmtServiceWrapper aINLockMgmtService =
                        //    new AINLockMgmtServiceWrapper();
                        //setInitiatedBy(aINLockMgmtService.checkLock(getAin(),
                        //                                            getAoid()));
                        setInitiatedBy(""); // as long as not setting to null - will call web service to set correctly
                        setLockedMessage(null); // OATS Issues#: 14580, 19576 and 23976
                        checkForAINDistrict();
                        //Copy to original list
                        for (BaseYearEventDO baseYearEventDO :
                             currentEventsList) {
                            final BaseYearEventDO event = new BaseYearEventDO();
                            event.copy(baseYearEventDO);
                            originalEventsList.add(event);
                        }
                        checkPropertyType();
                    }
                } catch (AmpBaseYearEventException e) {
                    ADFUtils.addFacesErrorMessage(e.getMessage());
                }
            }
            if (!getCurrentEventsList().isEmpty()) {
                calculateTotals(currentEventsList);
                allBaseYearEvents();
            }
        }
    }
    
    public String checkForChangedEvents(String ain, BigInteger aoid){
        BaseYearEventsMgmtWrapper baseYearEventsMgmtWrapper =
            new BaseYearEventsMgmtWrapper();
        try {
            BigInteger changeCount =
                baseYearEventsMgmtWrapper.checkForBYEChanges(ain, aoid);
            if(changeCount != null && changeCount.intValue() > 0){
                //Events changed in the database.
                return "MODIFIED";
            }else{
                return Boolean.FALSE.toString();
            }
        } catch (AmpBaseYearEventException e) {
            ADFUtils.addFacesErrorMessage(e.getMessage());
        }
        return Boolean.FALSE.toString();
    }

    public void loadDataForTBV() {
        try {
            if (getCurrentEventsList() == null) {
                setCurrentEventsList(new ArrayList<BaseYearEventDO>());
                ADFUtils.setPageFlowValue("pageAction",null);
            } else {
                // 01-feb-2017 NK added for defect 29157
                
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Inside loadDataForTBV ELSE " );
                String pageActionStr= ADFUtils.getPageFlowStringValue("pageAction");
                System.out.println("str--"+pageActionStr);
                
                if(pageActionStr == null){
                        getFilteredHistoricalEventsList().clear(); 
                        clearTBVCalutatorData();
                        DCIteratorBinding targetYearItr =(DCIteratorBinding) ADFUtils.findIterator("InitiateBaseYearEventIterator");
                        targetYearItr.executeQuery();
                        setTargerYear(null);
                }
//                else {
//                    ADFUtils.setPageFlowValue("pageAction",null);
//                }
                
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }

    private void refreshFilteredEvents() {
        Collections.sort(currentEventsList);

        for (BaseYearEventDO event : filteredCurrentEventsList) {
            BigInteger id = event.getByeId();
            event.copy(findBYE(currentEventsList, id));
        }

        for (BaseYearEventDO event : filteredHistoricalEventsList) {
            BigInteger id = event.getByeId();
            event.copy(findBYE(currentEventsList, id));
        }
    }

    private void reFilterAllEvents() {
        getFilteredCurrentEventsList().clear();
        getFilteredHistoricalEventsList().clear();
        calculateTotals(getCurrentEventsList());
        for (BaseYearEventDO baseYearEvent : getCurrentEventsList()) {
            BaseYearEventDO event = new BaseYearEventDO();
            event.copy(baseYearEvent);
            if (this.isCurrentEvent(event)) {
                getFilteredCurrentEventsList().add(event);
            } else {
                getFilteredHistoricalEventsList().add(event);
            }
        }
    }

    public void loadFromBPM() {
        if (getCurrentEventsList() == null) {
            currentEventsList = new ArrayList<BaseYearEventDO>();
            BaseYearEventsMgmtBPMWrapper wrapper =
                new BaseYearEventsMgmtBPMWrapper();
            wrapper.loadFromBPM(ainDO, originalEventsList,
                                BPM_ORIGINAL_ITERATOR_NAME);
            int lastNewEventId =
                wrapper.loadFromBPM(null, getCurrentEventsList(),
                                    BPM_MODIFIED_ITERATOR_NAME);
            newEventID = BigInteger.valueOf(lastNewEventId);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "loadFromBPM() New Event ID: " +
                        lastNewEventId);
            checkPropertyType();
            calculateTotals(originalEventsList);
            if (!getCurrentEventsList().isEmpty()) {
                calculateTotals(currentEventsList);
                allBaseYearEvents();
            } else {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                            "loadFromBPM()  No BYE Data read from Task");
            }
            reapplySelection();
        }
        Collections.sort(this.getCurrentEventsList());
        
    }

    public void reapplySelection() {
        //reapply selection
        AmpBusinessUserProfile ampBusinessUserProfile =
            (AmpBusinessUserProfile)JSFUtils.getManagedBeanValue(BaseYearEventConstants.AMP_PROFILEBEAN_NAME);
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                    "loadFromBPM()  ampBusinessUserProfile  : " + ampBusinessUserProfile);
        if (ampBusinessUserProfile != null) {
            HashMap hashMap = ampBusinessUserProfile.getSaveState();
            ArrayList<BaseYearEventDO> selectedEvents =
                (ArrayList<BaseYearEventDO>)hashMap.get(SELCTED_EVENTS_KEY);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "loadFromBPM()  Historical Events Size: " +
                        getFilteredHistoricalEventsList().size());
            if (selectedEvents != null) {
                for (BaseYearEventDO event : selectedEvents) {
                    BaseYearEventDO foundEvent = null;
                    foundEvent =
                            findBYE(getFilteredCurrentEventsList(), event.getByeId());
                    if (foundEvent == null) {
                        foundEvent =
                                findBYE(getFilteredHistoricalEventsList(),
                                        event.getByeId());
                    }
                    if (foundEvent != null) {
                        foundEvent.setSelected(event.getSelected());
                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                                    "loadFromBPM() Set Selected: " +
                                    foundEvent.getSelected());
                    }
                }
            }
            hashMap.remove(SELCTED_EVENTS_KEY);

            //Set Slected Events
            BigInteger byeId =
                (BigInteger)hashMap.get(CURRENT_SELECTED_EVENT_KEY);
            if (byeId != null) {
                setSelectedCurrentEvent(byeId);
            }
            byeId = (BigInteger)hashMap.get(HISTORICAL_SELECTED_EVENT_KEY);
            if (byeId != null) {
                setSelectedHistoricalEvent(byeId);
            }
            hashMap.remove(CURRENT_SELECTED_EVENT_KEY);
            hashMap.remove(HISTORICAL_SELECTED_EVENT_KEY);
        }
    }

    public void saveToBPMPayload() {
        //preserve current selection
        AmpBusinessUserProfile ampBusinessUserProfile =
            (AmpBusinessUserProfile)JSFUtils.getManagedBeanValue(BaseYearEventConstants.AMP_PROFILEBEAN_NAME);
        if (ampBusinessUserProfile != null) {
            HashMap hashMap = ampBusinessUserProfile.getSaveState();
            hashMap.put(SELCTED_EVENTS_KEY, getSelectedEventsList());
            hashMap.put(CURRENT_SELECTED_EVENT_KEY,
                        this.getSelectedCurrentEvent().getByeId());
            hashMap.put(HISTORICAL_SELECTED_EVENT_KEY,
                        getSelectedHistoricalEvent().getByeId());
        }
        updateBPMPayload();
    }

    private void updateBPMPayload() {
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "updateBPMPayload()  Updating BPM Payload");
        ADFUtils.setBoundAttributeValue(BaseYearEventConstants.ISDIRTY,
                                        getIsDirty());
        BaseYearEventsMgmtBPMWrapper wrapper =
            new BaseYearEventsMgmtBPMWrapper();
        wrapper.loadToBPM(getCurrentEventsList(), newEventsList,
                          deletedEventsList);
        newEventsList.clear();
        deletedEventsList.clear();
    }

    private boolean isCurrentEvent(BaseYearEventDO baseYearEvent) {
        if (baseYearEvent.getEffectiveBeginDate() != null &&
            baseYearEvent.getEffectiveEndDate() == null &&
            baseYearEvent.getVerificationDate() != null &&
            baseYearEvent.getInactiveDate() == null) {
            return true;
        }
        return false;
    }

    public void setCurrentEventsList(ArrayList<BaseYearEventDO> currentEventsList) {
        this.currentEventsList = currentEventsList;
    }

    public ArrayList<BaseYearEventDO> getCurrentEventsList() {
        return currentEventsList;
    }


    /**
     * This is called by InitiateBVMBean class, method manageBVMLstnr()
     *
     *
     * @return
     * @throws AmpBaseYearEventException
     * 
     * 
     */
    public String initiateBaseYearEvent() throws AmpBaseYearEventException {
        String result = null;
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "initiateBaseYearEvent()");
        if (getAin() != null && getUserId() != null && getAoid() != null) {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "initiateBaseYearEvent() AIN: " + ain);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "initiateBaseYearEvent() User ID: " + userId );
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "initiateBaseYearEvent() AOID: " + aoid);
            BaseYearEventsMgmtWrapper baseYearEventsMgmtWrapper =
                new BaseYearEventsMgmtWrapper();
            result =
                    baseYearEventsMgmtWrapper.initiateBaseYearEvent(getAin(), getUserId(),
                                                                    getAoid(),
                                                                    "BYE");
        } else {
            LOGGER.error(this, "initiateBaseYearEvent",
                         BaseYearEventUtils.LOGGER_PREFIX + " FATAL Error in Input Paramters",
                         null);
            LOGGER.error(this, "initiateBaseYearEvent",
                         BaseYearEventUtils.LOGGER_PREFIX + "AIN Value: " +
                         ain, null);
            LOGGER.error(this, "initiateBaseYearEvent",
                         BaseYearEventUtils.LOGGER_PREFIX + "UserId Value: " +
                         userId, null);
            LOGGER.error(this, "initiateBaseYearEvent",
                         BaseYearEventUtils.LOGGER_PREFIX + "ID Value: " +
                         aoid, null);
        }
        if (result != null) {
            final AINLockMgmtServiceWrapper aINLockMgmtService =
                new AINLockMgmtServiceWrapper();
            setInitiatedBy(aINLockMgmtService.lookupDisplayName(userId));
        }
        return result;
    }

    public void setIsBVNInitiated(Boolean isBVNInitiated) {
        this.isBVNInitiated = isBVNInitiated;
    }

    public Boolean getIsBVNInitiated() {
        return isBVNInitiated;
    }

    /**
     *
     * This method uses the PropertyDetailsService
     * to get the locked status and assigned to values
     *
     * Author: Mark Piller
     * OATS Issues#: 14580, 19576 and 23976
     *
     * @return
     * 
     * 
     */
    private String getInitiatedByFromPropertyDetails() {
        String initiatedBy = null;
        
        // OATS Issues#: 14580, 19576 and 23976
        try {
            String _assignedTo = null;

            final PropertyDetailsWrapper propertyDetailsWrapper =
                new PropertyDetailsWrapper();

            RetrievePropNotfResponse propertyNotificationResponse =
                propertyDetailsWrapper.retrievePropNotification(getAin(),
                                                                getAoid().toString());
            PropNotfList propNotfList = propertyNotificationResponse.getPropNotfList();
            List<PropNotf> propertyNotificationList = propNotfList.getPropNotf();

            int iListSize = propertyNotificationList.size();
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() Test the record count returned by the SOA PropertyDetails - count is: " + iListSize);
            if (iListSize == 0) {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() AIN Value: " + ain + " - RetrievePropNotification returned 0 records - Setting assignedTo = null which is Not Assigned");
                initiatedBy = null;
            } else if (iListSize == 1) {
                PropNotf propertyNotification = propertyNotificationList.get(0);

                // OATS Issue 26296 Create a initiatedBy if 1) there is an assigned to and 2) eventType = BVM
                String _eventType = propertyNotification.getEventType();
                _assignedTo = propertyNotification.getAssignedTo();
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() AIN Value: " + ain + " - RetrievePropNotification returned 1 record EventType = '" + _eventType + "' AssignedTo = '" + _assignedTo + "'");
                
                // OATS Issue 26296
                if("".equals(_assignedTo) || _assignedTo == null) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() AIN Value: " + ain + " - RetrievePropNotification returned 1 record but NO Assigned To - Setting assignedTo = null");
                    initiatedBy = null;
                } else { // there is an assigned to value
                    // Event Type must be BVM to create an Assigned By display
                    if( (_eventType != null) && ("BVM".equals(_eventType))) {
                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() AIN Value: " + ain + " - RetrievePropNotification returned 1 record - Setting assignedTo = '" + propertyNotification.getAssignedTo() + "'");
                        initiatedBy = "Assigned To " + _assignedTo;
                    } // if( (_eventType != null) || ("BVM".equals(_eventType)))
                } // if("".equals(_assignedTo) || _assignedTo == null)
            } else {
                // Filter on eventType = BVM and eventName <> TAM
                initiatedBy = null; // default
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() AIN Value: " + ain + " - RetrievePropNotification returned " + iListSize + " records");
                Iterator<PropNotf> propertyNotfIterator = propertyNotificationList.iterator();
                while (propertyNotfIterator.hasNext()) {
                    PropNotf propNotf = propertyNotfIterator.next();
                    String _eventType = propNotf.getEventType();
                    String _eventName = propNotf.getEventName();
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails()  in iterator -EVALUATING THIS... Event Type: '" + _eventType + "' Event Name: '" + _eventName + "'");
                    if("BVM".equals(_eventType) && (_assignedTo == null)) { // default assigned to for first record of event type BPM
                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails()  in iterator Event Type = '" + _eventType + "' Do not display button - get assigned to");
                        setIsBVMRecord(true);
                        _assignedTo = propNotf.getAssignedTo();
                    }
                    // if there is more than 1 BVM record then make the assigned value = to the record where eventName <> TAM
                    if ("BVM".equals(_eventType) && !"TAM".equals(_eventName)) {
                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails()  in iterator -GOOD Event Type and Event Name... get Assigned To... Event Type: " + propNotf.getEventType() + " Event Name: " + propNotf.getEventName());
                        _assignedTo = propNotf.getAssignedTo();
                    }
                } // while (propertyNotfIterator.hasNext())

                // teest the assigned to - set initiatedBy
                if("".equals(_assignedTo) || _assignedTo == null) {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() - RetrievePropNotification returned >1 records AND assignedTo is empty - setting to NULL");
                    initiatedBy = null;
                } else {
                    LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "getInitiatedByFromPropertyDetails() - RetrievePropNotification returned >1 records - Setting assignedTo = '" + _assignedTo + "'");
                    initiatedBy = "Assigned To " + _assignedTo;
                }

            } // if (iListSize == 0)

        } catch (AmpException ae) {
            LOGGER.error(this, "getInitiatedByFromPropertyDetails",
                         BaseYearEventUtils.LOGGER_PREFIX +
                         "Error calling web service " + ae.getErrCode() + "\n" + ae.getMessage(), null);
            ADFUtils.addFacesErrorMessage(ae.getMessage());
        } // end try/catch
        
        return initiatedBy;
    } // getInitiatedByFromPropertyDetails()


    /**
     *
     * @param initiatedBy
     * 
     * Revised by: Mark Piller
     * Revised on: 10-19-2016
     * 
     * OATS Issues#: 14580, 19576 and 23976
     * Use Web Service: PropertyDetailService
     *       Operation: RetrievePropNotf
     *     to get the Assigned To
     *     
     */
    public void setInitiatedBy(String initiatedBy) {
        
        if(initiatedBy == null) {
            // do nothing
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - value of initiatedBy = null ");
        } else if ("".equals(initiatedBy)) {
            // OATS Issues#: 14580, 19576 and 23976
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - value of initiatedBy parameter = '' - call getInitiatedByFromPropertyDetails()");
            initiatedBy = getInitiatedByFromPropertyDetails();
        } else {
            // OATS Issues#: 14580, 19576 and 23976
            // this should only be the user name coming from
            // setInitiatedBy(aINLockMgmtService.lookupDisplayName(userId));
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - value of initiatedBy parameter = '" + initiatedBy + "'");
            // , Manage Base Year Events is in progress
            initiatedBy = "Assigned To " + initiatedBy + " (" + getUserId() + ")";
            setLockedMessage(", Manage Base Year Events is in progress");

            // Original logic - called when user clicks on Manage BVM button
            /* 
            String processStatus = getAinDO().getProcessStatus();
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - processStatus = '" + processStatus + "'");
            if ("Submitted".equals(processStatus)) {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - processStatus = '" + processStatus + "'  - setOutputText");
                setOutputText("Manage Base Year Events Process is Submitted and initiated by " +
                              initiatedBy);
            } else if ("Cancelled".equals(processStatus)) {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - processStatus = '" + processStatus + "'  - setOutputText");
                setOutputText("Manage Base Year Events Process is Cancelled due to NoDataFound and initiated by " +
                              initiatedBy);
            } else if ("InvalidRole".equals(processStatus)) {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "setInitiatedBy() - processStatus = '" + processStatus + "'  - setOutputText");
                setOutputText("Manage Base Year Events Process is Cancelled due to InvalidRole and initiated by " +
                              initiatedBy);
            }
            */

        }

        this.initiatedBy = initiatedBy;
    } // setInitiatedBy(String initiatedBy)
    
    

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public Boolean getFiltered() {
        return filtered;
    }

    private void calculateTotals(ArrayList<BaseYearEventDO> eventsList) {
        //Calculate totals for the following attributes.
        //Total Value = Land Value + Improvement Value
        //Retained Total Value = Retained Land Value + Retained Imp Value
        for (BaseYearEventDO baseYearEventDO : eventsList) {
            calculateTotalValue(baseYearEventDO);
            calculateRetainedTotalValue(baseYearEventDO);
        }
    }

    private void calculateRetainedTotalValue(BaseYearEventDO baseYearEventDO) {
        Double retainedLandVal = baseYearEventDO.getRetainedLandValue();
        Double retainedImpVal = baseYearEventDO.getRetainedImprovementValue();
        if (retainedLandVal == null) {
            retainedLandVal = 0.0;
        }
        if (retainedImpVal == null) {
            retainedImpVal = 0.0;
        }
        final Double total = retainedLandVal + retainedImpVal;
        if(total == 0){
            baseYearEventDO.setRetainedTotalValue(null);
        }else{
            baseYearEventDO.setRetainedTotalValue(total);
        }
    }

    private void calculateTotalValue(BaseYearEventDO baseYearEventDO) {
        Double landVal = baseYearEventDO.getLandValue();
        Double improvVal = baseYearEventDO.getImprovementValue();
        if (landVal == null) {
            landVal = 0.0;
        }
        if (improvVal == null) {
            improvVal = 0.0;
        }
        final Double total = landVal + improvVal;
        if(total == 0){
            baseYearEventDO.setTotalValue(null);
        }else{
            baseYearEventDO.setTotalValue(total);
        }
    }

    /**
     * @param byeId
     * @param listType
     * @return
     */
    public Boolean editEvent(BigInteger byeId, String listType) {
        BaseYearEventDO editEvent = null;
        if (FILTERED_CURRENT_EVENTS.equals(listType)) {
            editEvent = this.findBYE(getFilteredCurrentEventsList(), byeId);
        }
        if (FILTERED_HISTORICAL_EVENTS.equals(listType)) {
            editEvent = this.findBYE(getFilteredHistoricalEventsList(), byeId);
        }
        BaseYearEventDO currentEvent =
            findBYE(getFilteredCurrentEventsList(), byeId);
        if (editEvent != null) {
            currentEvent = editEvent;
        }
        return Boolean.FALSE;
    }

    public ArrayList<BaseYearEventDO> getReviewEventsList() {
        return reviewEventsList;
    }

    public void reviewAllChanges() {
        ArrayList<BaseYearEventDO> reviewEvents = getReviewEventsList();
        //        LOGGER.info(this, "reviewAllChanges",
        //                    BaseYearEventUtils.LOGGER_PREFIX + "reviewEvents count: " +
        //                    reviewEvents.size(), null);
        //Clear all reviewEvents
        reviewEvents.clear();
        //        LOGGER.info(this, "reviewAllChanges",
        //                    BaseYearEventUtils.LOGGER_PREFIX + "reviewEvents count: " +
        //                    reviewEvents.size(), null);
        //        LOGGER.info(this, "reviewAllChanges",
        //                    BaseYearEventUtils.LOGGER_PREFIX +
        //                    "CurrentEventsList count: " +
        //                    getCurrentEventsList().size(), null);

        //Loop through all events for modified list
        for (BaseYearEventDO event : getCurrentEventsList()) {
            if (event.getTransactionType() != null) {
                if (event.getTransactionType().equalsIgnoreCase("U")) {
                    //Update review event. Need to create a diffevent that shows ONLY changes between update review event and original event.
                    //                LOGGER.info(this, "reviewAllChanges",
                    //                            BaseYearEventUtils.LOGGER_PREFIX +
                    //                            "This is an update event ", null);
                    BaseYearEventDO originalEvent =
                        findBYE(originalEventsList, event.getByeId());
                    BaseYearEventDO diffEvent =
                        BaseYearEventUtils.getDiffReviewChangeEvent(originalEvent,
                                                                    event);
                    if (diffEvent != null) {
                        //                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        //                                    "Adding Diff event ");
                        reviewEvents.add(diffEvent);
                    }
                    //Add original event
                    if (originalEvent != null) {
                        calculateTotalValue(originalEvent);
                        calculateRetainedTotalValue(originalEvent);
                        //                        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        //                                    "Adding original event ");
                        reviewEvents.add(originalEvent);
                    }
                }
                if ("C".equalsIgnoreCase(event.getTransactionType())) {
                    //Create review event. Add as is.
                    //                LOGGER.info(this, "reviewAllChanges",
                    //                            BaseYearEventUtils.LOGGER_PREFIX +
                    //                            "Adding Create event ", null);
                    reviewEvents.add(event);
                }
            } else {
                reviewEvents.add(event);
            }
        }
    }

    /**
     * OnSubmit OPA validation call to verify all events and display messages
     * @return
     */
    public String onSubmit() throws AmpBaseYearEventException {
        ArrayList<BaseYearEventDO> opaInput = new ArrayList<BaseYearEventDO>();
        for (BaseYearEventDO event : getCurrentEventsList()) {
            opaInput.add(event);
        }
        BaseYearEventRulesWrapper rulesService =
            new BaseYearEventRulesWrapper();
        Boolean result =
            rulesService.validate(opaInput, BaseYearEventConstants.OPA_CALL_ONSUBMIT);
        if (!result) {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "onSubmit()  BYE Events Validations have error messages");
            return generateOPAErrorMessage(rulesService);
        } else {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "onSubmit()  BYE Events On Submit Validations passed");
            if (getIsDirty()) {
                updateBPMPayload();
            }
            String checkResult = checkForChangedEvents(getAinDO().getAin(), getAinDO().getAoid());
            if("FALSE".equalsIgnoreCase(checkResult)){
                return Boolean.TRUE.toString();
            }
            return checkResult;
        }
    }

    private String generateOPAErrorMessage(BaseYearEventRulesWrapper rulesService) {
        //We have Error Messages
        List<BaseYearEventErr> errorEvents = rulesService.getErrorEvents();
        if (errorEvents != null) {
            StringBuilder message = new StringBuilder("<html><body>");
            String id = null;
            for (BaseYearEventErr baseYearEventErr : errorEvents) {
                String byeId = baseYearEventErr.getErrorBYEId();
                if (id == null || !id.equals(byeId)) {
                    if (id != null && !id.equals(byeId)) {
                        message.append("</ul>");
                    }
                    id = byeId;
                    message.append("<p><b>" + "Base Year Event ID: " + id +
                                   "</b></p>");
                    message.append("<ul style=\"list-style-type:disc\">");
                }
                message.append("<li>" + baseYearEventErr.getErrorMessage() +
                               "</li>");
            }
            message.append("</body></html>");
            return message.toString();
        }
        return null;
    }

    /**
     * OnClose is called anytime any event is Modified/Created. OPA Validations for
     * OnClose is called to validate current event. On success the event is updated
     * and displayed.
     * @param eventType
     * @param isNew
     * @return
     * @throws AmpBaseYearEventException
     */
    public String onClose(String eventType,
                          Boolean isNew) throws AmpBaseYearEventException {
        System.out.println("in side Onclose Method"+eventType);
        
        BaseYearEventDO selectedEvent = null;
        String beforeOPATransactionType = null;
        if (BaseYearEventConstants.CURRENT_EVENT_TYPE.equals(eventType)) {
            selectedEvent = getSelectedCurrentEvent();
            beforeOPATransactionType = selectedEvent.getTransactionType();
        }
        if (BaseYearEventConstants.HISTORICAL_EVENT_TYPE.equals(eventType)) {
            selectedEvent = getSelectedHistoricalEvent();
            beforeOPATransactionType = selectedEvent.getTransactionType();
        }
        if (selectedEvent != null) {
          System.out.println("1485----"+selectedEvent.getAin()+"--"+selectedEvent.getEventDescriptionKey()+" ----"+selectedEvent.getEventNameKey());
            BaseYearEventDO currentEvent = null;
            if (isNew && selectedEvent.getTransactionType().equals("C")) {
                //Create Mode
            System.out.println("1491---");
                newEvent.copy(selectedEvent);
                BaseYearEventDO alreadyAdded =
                    findBYE(getCurrentEventsList(), newEvent.getByeId());
                if (alreadyAdded != null) {
                System.out.println("1496----");
                    //Already added event just update
                    alreadyAdded.copy(selectedEvent);
                } else {
                    System.out.println("1500----");
                    //Its a brand new event add to lists
                    getCurrentEventsList().add(newEvent);
                    newEvent.setCreateBy(userId);
                    newEvent.setCreatedData(Calendar.getInstance().getTime());
                    newEventsList.add(newEvent.getByeId());
                    
                    //also add to filtered events.
                    if (isCurrentEvent(newEvent)) {
                    System.out.println("1509-----");
                        getFilteredCurrentEventsList().add(newEvent);
                    } else {
                        System.out.println("1512-----");
                        getFilteredHistoricalEventsList().add(newEvent);
                    }
                }
                currentEvent = newEvent;
            } else {
                System.out.println("1518------");
                //Edit mode
                currentEvent =
                        findBYE(getCurrentEventsList(), selectedEvent.getByeId());
                currentEvent.copy(selectedEvent);
            }
            currentEvent.setTransactionType("O");

            final BaseYearEventRulesWrapper rulesService =
                new BaseYearEventRulesWrapper();
            Boolean result =
                rulesService.validate(getCurrentEventsList(), BaseYearEventConstants.OPA_CALL_ONCLOSE);
            //            Boolean result = true;
            if (!result) {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                            "onClose() BYE Events Validations have error messages");
                currentEvent.setTransactionType(beforeOPATransactionType);
                return generateOPAErrorMessage(rulesService);
            } else {
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                            "onClose() BYE Events Validations passed");
                //Events list is updated by OPA refresh current list
                if (beforeOPATransactionType != null &&
                    beforeOPATransactionType.equals("C")) {
                    currentEvent.setTransactionType("C");
                    String eventNameKey = currentEvent.getEventNameKey();
                    if (eventNameKey != null && eventNameKey.equalsIgnoreCase("New Construction")) {
                        currentEvent.setOwnershipCode(null);
                        currentEvent.setDocumentNumber(null);
                        currentEvent.setRecordingDate(null);
                        currentEvent.setSequenceNumber(null);
                    }
                } else {
                    currentEvent.setTransactionType("U");
                }
                //Set Modified attributes
                currentEvent.setModifiedBy(userId);
                currentEvent.setModifiedDate(Calendar.getInstance().getTime());

                //Save UI State
                setIsDirty(true);
                calculateRetainedTotalValue(currentEvent);
                calculateTotalValue(currentEvent);
                moveEventsAcrossLists(currentEvent);
                copyOfEvent.copy(currentEvent);
                return Boolean.TRUE.toString();
            }
        } else {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "onClose() Currennt Selected Event is null");
        }
        return Boolean.FALSE.toString();
    }

    /**
     * Check if the event passed is Current or Historical event and move
     * the event to the right table list.
     * @param currentEvent
     */
    private void moveEventsAcrossLists(BaseYearEventDO currentEvent) {
        final boolean newEventIsCurrent = isCurrentEvent(currentEvent);
        if (newEventIsCurrent) {
            BaseYearEventDO event =
                findBYE(getFilteredHistoricalEventsList(), currentEvent.getByeId());
            if (event != null) {
                getFilteredHistoricalEventsList().remove(event);
            }
            event =
                    findBYE(getFilteredCurrentEventsList(), currentEvent.getByeId());
            if (event == null) {
                getFilteredCurrentEventsList().add(currentEvent);
            }else{
                event.copy(currentEvent);
            }     
            selectedHistoricalEvent.copy(new BaseYearEventDO());
            selectedCurrentEvent.copy(currentEvent);
        } else {
            BaseYearEventDO event =
                findBYE(getFilteredCurrentEventsList(), currentEvent.getByeId());
            if (event != null) {
                getFilteredCurrentEventsList().remove(event);
            }
            event =
                    findBYE(getFilteredHistoricalEventsList(), currentEvent.getByeId());
            if (event == null) {
                getFilteredHistoricalEventsList().add(currentEvent);
            }else{
                event.copy(currentEvent);
            }
            selectedCurrentEvent.copy(new BaseYearEventDO());
            selectedHistoricalEvent.copy(currentEvent);
        }
        copyToCurrentEventList(currentEvent);
    }

    private void copyToCurrentEventList(BaseYearEventDO copyFrom) {
        BaseYearEventDO event =
            findBYE(getCurrentEventsList(), copyFrom.getByeId());
        event.copy(copyFrom);
    }

    /**
     * @return
     */
    public BaseYearEventDO getOriginialCurrentEvent() {
        return originialCurrentEvent;
    }

    /**
     * @return
     */
    public BaseYearEventDO getSelectedHistoricalEvent() {
        return selectedHistoricalEvent;
    }

    /**
     * @return
     */
    public BaseYearEventDO getOriginialHistoricalEvent() {
        return originialHistoricalEvent;
    }

    /**
     * @param byeId
     */
    public void setSelectedCurrentEvent(BigInteger byeId) {
        BaseYearEventDO event = findBYE(getFilteredCurrentEventsList(), byeId);
        selectedCurrentEvent.copy(event);
        copyOfEvent.copy(event);
        BaseYearEventDO originalBye = findBYE(originalEventsList, byeId);
        if(originalBye != null){
            originialCurrentEvent.copy(originalBye);
        }else{
            originialCurrentEvent.copy(new BaseYearEventDO());
        }
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                    "setSelectedCurrentEvent() Selection Current Event Set");
    }

    /**
     * @param byeId
     */
    public void setSelectedHistoricalEvent(BigInteger byeId) {
        BaseYearEventDO event =
            findBYE(getFilteredHistoricalEventsList(), byeId);
        selectedHistoricalEvent.copy(event);
        copyOfEvent.copy(event);
        BaseYearEventDO originalBye = findBYE(originalEventsList, byeId);
        if(originalBye != null){
            originialHistoricalEvent.copy(originalBye);
        }else{
            originialHistoricalEvent.copy(new BaseYearEventDO());
        }
        LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                    "setSelectedHistoricalEvent() Selection Historical Event Set");
    }

    /**
     * @return
     */
    public BaseYearEventDO getSelectedCurrentEvent() {
        return selectedCurrentEvent;
    }

    /**
     * @return
     */
    public BigInteger getAoid() {
        return aoid;
    }

    /**
     * @param aoid
     */
    public void setAoid(String aoid) {
        if (aoid != null && !"".equalsIgnoreCase(aoid)) {
            //Convert the string to BigInteger the type defined in SOA process
            this.aoid = BigInteger.valueOf((Long.parseLong(aoid)));
        } else {
            this.aoid = null;
        }
    }

    /**
     * @return
     */
    public String getAin() {
        return ain;
    }

    /**
     * @return
     */
    public String getUserId() {
        if (userId == null) {
            //Try to get it from Identity Service
            ADFContext adfCtx = ADFContext.getCurrent();
            SecurityContext secCntx = adfCtx.getSecurityContext();
            userId = secCntx.getUserName();
        }
        return userId;
    }

    /**
     * @param ain
     */
    public void setAin(String ain) {
        this.ain = ain;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @param isNew
     * @param eventType
     */
    public void cancel(Boolean isNew, String eventType) {
        BaseYearEventDO selectedEvent = getSelectedEvent(eventType);
        if (!isNew) {
            selectedEvent.setTransactionType(null);
            selectedEvent.copy(copyOfEvent);
//            copyToCurrentEventList(selectedEvent);
        }
        if (isNew) {
            BigInteger id = new BigInteger(String.valueOf(newEventID.intValue()));
            getCurrentEventsList().remove(newEvent);
            newEvent = null;

            int val = newEventID.intValue();
            val++;
            newEventID = BigInteger.valueOf(val);

            BaseYearEventDO event =
                findBYE(getCurrentEventsList(), id);
            selectedEvent.copy(event);
        }
    }

    private BaseYearEventDO getSelectedEvent(String eventType) {
        BaseYearEventDO selectedEvent = null;
        if (BaseYearEventConstants.CURRENT_EVENT_TYPE.equalsIgnoreCase(eventType)) {
            return selectedEvent = getSelectedCurrentEvent();
        }
        if (BaseYearEventConstants.HISTORICAL_EVENT_TYPE.equalsIgnoreCase(eventType)) {
            return selectedEvent = getSelectedHistoricalEvent();
        }
        return selectedEvent;
    }

    /**
     * @param ainDO
     */
    public void setAinDO(AinDO ainDO) {
        this.ainDO = ainDO;
    }

    /**
     * @return
     */
    public AinDO getAinDO() {
        return ainDO;
    }

    /**
     * @param eventType
     */
    public void addEvent(String eventType) {
        newEvent = new BaseYearEventDO();
        int val = newEventID.intValue();
        val--;
        newEventID = BigInteger.valueOf(val);
        newEvent.setByeId(newEventID);
        String tg= ADFUtils.getPageFlowStringValue("sPageAction");
        AinDO ainDO = getAinDO();
        //Added Condition for Defect# 29636
        if("page".equals(tg)){
            ainDO=null;
        }
        if (ainDO != null) {
            newEvent.setAin(ainDO.getAin());
            newEvent.setAoid(getAinDO().getAoid());
        }
        newEvent.setTransactionType("C");
        
        if (isCurrentEvent(newEvent)) {
            System.out.println("\n***Inside InitiateBaseYearEvet.addEvent() Method***");
            getSelectedCurrentEvent().copy(newEvent);
        } else {
            System.out.println("\n***Inside InitiateBaseYearEvet.addEvent() Method Before Copy*** ");
            getSelectedHistoricalEvent().copy(newEvent);
            System.out.println("\n***Inside InitiateBaseYearEvet.addEvent() Method After Copy***");
        }
    }

    /**
     * @param eventType
     */
    public void deleteEvent(String eventType) {
        BaseYearEventDO selectedEvent = null;
        if (BaseYearEventConstants.CURRENT_EVENT_TYPE.equals(eventType)) {
            selectedEvent = getSelectedCurrentEvent();
            BaseYearEventDO eventToRemove =
                findBYE(getFilteredCurrentEventsList(),
                        selectedEvent.getByeId());
            getFilteredCurrentEventsList().remove(eventToRemove);
        }
        if (BaseYearEventConstants.HISTORICAL_EVENT_TYPE.equals(eventType)) {
            selectedEvent = getSelectedHistoricalEvent();
            BaseYearEventDO eventToRemove =
                findBYE(this.getFilteredHistoricalEventsList(),
                        selectedEvent.getByeId());
            getFilteredHistoricalEventsList().remove(eventToRemove);
        }
        BaseYearEventDO eventToRemove =
            findBYE(this.getCurrentEventsList(), selectedEvent.getByeId());
        getCurrentEventsList().remove(eventToRemove);
        {
            BaseYearEventDO deletedEvent = new BaseYearEventDO();
            deletedEvent.copy(selectedEvent);
            deletedEventsList.add(deletedEvent.getByeId());
            setIsDirty(true);
        }
        selectedEvent.clear();
    }

    public void setIsDirty(Boolean isDirty) {
        //Save the state to session bean to workaround the refresh page after save.
        this.isDirty = isDirty;
    }

    public Boolean getIsDirty() {
        //Read fromthe session bean.
        return isDirty;
    }

    public void setIsRealProperty(Boolean isRealProperty) {
        this.isRealProperty = isRealProperty;
    }

    public Boolean getIsRealProperty() {
        return isRealProperty;
    }

    public void setIsPersonalProperty(Boolean isPersonalProperty) {
        this.isPersonalProperty = isPersonalProperty;
    }

    public Boolean getIsPersonalProperty() {
        return isPersonalProperty;
    }

    /**
     * @param targetYear
     * @param oprerationType
     * @param
     * @param
     */
     public String generateBaseYearEventData(long targetYear,
                                             String oprerationType) throws AmpBaseYearEventException {


         LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                     "generateBaseYearEventData Start");
         tbvSelectedEventsList.clear();
         
         TrendBaseValueList baseValueList = null;
         BigInteger eventId = null;
         Event event;
         Event footerTrendrEvent = new Event();
         ArrayList<Year> yearList;
         ArrayList<Year> footerYearList;
         Year year;
         Year trendYear;
         BaseYearEventRulesWrapper rules = new BaseYearEventRulesWrapper();
         ArrayList<BaseYearEventDO> selectedTrendBaseYearEventsList =
             new ArrayList<BaseYearEventDO>();
         HashMap<String, Date> BYESequenceNumberMap =
             new HashMap<String, Date>();
         HashMap<String, ArrayList<String>> BYESequenceNumber =
             new HashMap<String, ArrayList<String>>();
         DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

         LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                     "Selected envents for trend: " +
                     getSelectedEventsList().size());

         if (getSelectedEventsList().size() > 0) {

             ArrayList<BaseYearEventDO> selectedEventList =
                 getSelectedEventsList();
             tbvSelectedEventsList.clear();

             for (BaseYearEventDO originalEventElement : selectedEventList) {
                 if (originalEventElement.getSelected().equals(true)) {
                     BaseYearEventDO copiedToBYE = new BaseYearEventDO();
                     copiedToBYE.copy(originalEventElement);
                     tbvSelectedEventsList.add(copiedToBYE);
                 }
             }

             for (BaseYearEventDO eventElement : tbvSelectedEventsList) {
                 eventElement.setTransactionType(oprerationType);
                 ArrayList<String> eventInfo = new ArrayList<String>();

                 selectedTrendBaseYearEventsList.add(eventElement);
                 BYESequenceNumberMap.put(eventElement.getByeId().toString().trim(),
                                          eventElement.getEventDate());

                 eventInfo.add(dateFormat.format(eventElement.getEventDate()));

                 if (eventElement.getSequenceNumber() != null) {
                     eventInfo.add(eventElement.getSequenceNumber().toString().trim());
                 }

                 BYESequenceNumber.put(eventElement.getByeId().toString().trim(),
                                       eventInfo);
                 //}
             }

             LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                         "Selected envents before SOA service call: " +
                         selectedTrendBaseYearEventsList.size());

             TrendBaseYearValueResponse baseYearValueResponse =
                 rules.trendBaseYearValues(BigInteger.valueOf(targetYear),
                                          selectedTrendBaseYearEventsList);

             BaseYearEventRespList baseYearEventRespList =
                 baseYearValueResponse.getBaseYearEventRespList();
             List<BaseYearEventResp> baseYearEventResp =
                 baseYearEventRespList.getBaseYearEvent();
             TrendObjectList trendObjectList = null;
             TrendBaseValueTotalList trendBaseValueTotalList = null;

             if (baseYearEventResp.get(0).getTrendObjectList() != null)
                 trendObjectList =
                         baseYearEventResp.get(0).getTrendObjectList();

             if (baseYearEventResp.get(0).getTrendBaseValueTotalList() != null)
                 trendBaseValueTotalList =
                         baseYearEventResp.get(0).getTrendBaseValueTotalList();

             trendHeaderList =
                     new ArrayList<Header>(); //check for trend and detrend
             trendEventList =
                     new ArrayList<Event>(); //check for trend and detrend
             footerTrendEventList = new ArrayList<Event>();

             footerYearList = new ArrayList<Year>();
             HashSet<String> headerYearSet = new HashSet<String>();

             for (TrendBaseValueTotalList.TrendBaseValueTotal tobj :
                  trendBaseValueTotalList.getTrendBaseValueTotal()) {
                 String yearValue =
                     tobj.getYear().substring(0, tobj.getYear().indexOf(".")).trim();

                 headerYearSet.add(yearValue.trim());

                 String trendFactorValue = null;
                 String totalLandValue = null;
                 String totalFixturesValue = null;
                 String totalImprovementsValue = null;
                 String totalLandImprovementsValue = null;

                 if (tobj.getTrendFactor() != null)
                     trendFactorValue = tobj.getTrendFactor().trim();

                 if (tobj.getTotalLandValue().trim().length() > 0)
                     totalLandValue =
                             decimalFormat.format(Double.parseDouble(tobj.getTotalLandValue().trim()));

                 if (tobj.getTotalFixturesValue().trim().length() > 0)
                     totalFixturesValue =
                             decimalFormat.format(Double.parseDouble(tobj.getTotalFixturesValue().trim()));

                 if (tobj.getTotalImprovementsValue().trim().length() > 0)
                     totalImprovementsValue =
                             decimalFormat.format(Double.parseDouble(tobj.getTotalImprovementsValue().trim()));

                 if (tobj.getLandImprovementsTotal().trim().length() > 0)
                     totalLandImprovementsValue =
                             decimalFormat.format(Double.parseDouble(tobj.getLandImprovementsTotal().trim()));

                 Header header = new Header(yearValue, trendFactorValue);
                 trendYear =
                         new Year(yearValue, totalLandValue, totalImprovementsValue,
                                  totalFixturesValue,
                                  totalLandImprovementsValue);

                 footerYearList.add(trendYear);
                 trendHeaderList.add(header);
             }

             footerTrendrEvent.setEventName(eventId); //change eventId
             footerTrendrEvent.setEventType("Total");
             Collections.sort(footerYearList);
             Collections.reverse(footerYearList);
             footerTrendrEvent.setYearList(footerYearList);
             footerTrendEventList.add(footerTrendrEvent);

             for (TrendObjectList.TrendObject tobj :
                  trendObjectList.getTrendObject()) {

                 baseValueList = tobj.getTrendBaseValueList();
                 event = new Event();
                 yearList = new ArrayList<Year>();

                 for (TrendBaseValueList.TrendBaseValue tbvListobj :
                      baseValueList.getTrendBaseValue()) {
                     String lanValue = null;
                     String impValue = null;
                     String fixValue = null;
                     String totalLandValue = null;

                     eventId = tbvListobj.getByeId();
                     String yearValue =
                         tbvListobj.getYear().substring(0, tbvListobj.getYear().indexOf(".")).trim();
                     if (headerYearSet.contains(yearValue)) {
                         if (tbvListobj.getLandValue().trim().length() > 0)
                             lanValue =
                                     decimalFormat.format(Double.parseDouble(tbvListobj.getLandValue().trim()));

                         if (tbvListobj.getImprovements().trim().length() > 0) {
                             impValue =
                                     decimalFormat.format(Double.parseDouble(tbvListobj.getImprovements().trim()));
                         }

                         if (tbvListobj.getFixtures().trim().length() > 0)
                             fixValue =
                                     decimalFormat.format(Double.parseDouble(tbvListobj.getFixtures().trim()));

                         if (tbvListobj.getLandImprovementsTotal().trim().length() >
                             0)
                             totalLandValue =
                                     decimalFormat.format(Double.parseDouble(tbvListobj.getLandImprovementsTotal().trim()));

                         year =
     new Year(yearValue, lanValue, impValue, fixValue, totalLandValue);

                         yearList.add(year);
                         event.setEventType(tbvListobj.getOperation()); //replace byeid with operation
                         ArrayList<String> eventInfo = new ArrayList<String>();
                         eventInfo =
                                 BYESequenceNumber.get(eventId.toString().trim());

                         if (eventInfo.size() > 1)
                             event.setEventId(eventInfo.get(0) + "-" +
                                              eventInfo.get(1));
                         else
                             event.setEventId(eventInfo.get(0));

                     }
                 }

                 Collections.sort(yearList);
                 Collections.reverse(yearList);
                 if (eventId != null) {
                     event.setEventName(eventId);
                     event.setEventDate(BYESequenceNumberMap.get(eventId.toString().trim()));
                 }
                 event.setYearList(yearList);
                 trendEventList.add(event);

             }
             //}

             // This loop checks Trendevent list data with headelist year, if there is no year for that particular header year then we will set an emptydata in that year,
             // loop continues to check for all the header year in order to maintain consistency
             for (String headerYear : headerYearSet) {
                 for (Event eventItem : trendEventList) {
                     boolean yearExists = false;
                     Year newYear = null;
                     for (Year yearItem : eventItem.getYearList()) {
                         if (yearItem.getTyear().equals(headerYear)) {
                             yearExists = true;
                             break;
                         }
                     }

                     if (!yearExists) {
                         newYear = new Year(headerYear, null, null, null, null);
                         eventItem.getYearList().add(newYear);
                     }

                     Collections.sort(eventItem.getYearList());
                     Collections.reverse(eventItem.getYearList());

                 }
             }

             Collections.sort(trendHeaderList);
             Collections.reverse(trendHeaderList);
             //Collections.sort(trendEventList);
             //Collections.reverse(trendEventList);
         }
         LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                     "generateBaseYearEventData End");

         return "success";
     }

    public Integer selectedEventListSize() {
        return getSelectedEventsList().size();
    }

    public void clearTBVCalutatorData() throws Exception {
        if (trendHeaderList != null && trendEventList != null &&
            footerTrendEventList != null) {
            trendHeaderList.clear();
            trendEventList.clear();
            footerTrendEventList.clear();
        }
        if (deTrendEventList != null && detrendHeaderList != null &&
            footerDeTrendEventList != null) {
            detrendHeaderList.clear();
            deTrendEventList.clear();
            footerDeTrendEventList.clear();
        }

    }

    public void calculateTrendBaseValues(long targetYear) throws AmpBaseYearEventException {
        
        if (trendHeaderList != null && trendEventList != null && footerTrendEventList != null) {
            trendHeaderList.clear();
            trendEventList.clear();
            footerTrendEventList.clear();
        }
        
        TrendBaseYearEventWrapper.generateTrendBaseValues(
                                  targetYear,
                                  "Trend",
                                  getSelectedEventsList(),
                                  trendHeaderList,
                                  trendEventList,
                                  footerTrendEventList);       
    }

    public void calculateDeTrendBaseValues(long targetYear) throws AmpBaseYearEventException {
        if (deTrendEventList != null && detrendHeaderList != null && footerDeTrendEventList != null) {
            detrendHeaderList.clear();
            deTrendEventList.clear();
            footerDeTrendEventList.clear();
        }
        
        TrendBaseYearEventWrapper.generateTrendBaseValues(
                                  targetYear,
                                  "Detrend",
                                  getSelectedEventsList(),
                                  detrendHeaderList,
                                  deTrendEventList,
                                  footerDeTrendEventList);       
        
    }

    public void resetEvent(String eventType) {
        BaseYearEventDO selectedEvent = getSelectedEvent(eventType);
        if ("U".equalsIgnoreCase(selectedEvent.getTransactionType())) {
            BaseYearEventDO eventToReset =
                findBYE(getCurrentEventsList(), selectedEvent.getByeId());
            BaseYearEventDO originalEvent =
                findBYE(originalEventsList, selectedEvent.getByeId());
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Reset Called " +
                        eventToReset.getByeId());
                if (originalEvent != null) {
                eventToReset.copy(originalEvent);
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX + "Reset Done " +
                            eventToReset.getByeId());
                moveEventsAcrossLists(eventToReset);
                setIsDirty(true);
            }
        }
    }

    public void updateSelection(BigInteger byeId, Boolean newValue) {
        BaseYearEventDO selectedEvent =
            findBYE(this.getCurrentEventsList(), byeId);
        if (selectedEvent != null) {
            selectedEvent.setSelected(newValue);
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "Update Selection Change: " +
                        selectedEvent.getSelected());
        } else {
            LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                        "Selected Event Null");
        }
    }

    public void updateReviewRequired(Boolean flag) {
        if (flag) {
            final BaseYearEventsMgmtWrapper baseYearEventsMgmtWrapper =
                new BaseYearEventsMgmtWrapper();
            try {
                String required =
                    baseYearEventsMgmtWrapper.updateReviewRequired(getCurrentEventsList(),
                                                                   ain,
                                                                   userId);
                LOGGER.fine(BaseYearEventUtils.LOGGER_PREFIX +
                            "updateReviewRequired: " + required);
            } catch (AmpBaseYearEventException msg) {
                ADFUtils.addFacesErrorMessage(msg.getMessage());
            }
        }
    }

    public void setTrendEventList(ArrayList<Event> trendEventList) {
        this.trendEventList = trendEventList;
    }

    public ArrayList<Event> getTrendEventList() {
        return trendEventList;
    }

    public void setTrendHeaderList(ArrayList<Header> trendHeaderList) {
        this.trendHeaderList = trendHeaderList;
    }

    public ArrayList<Header> getTrendHeaderList() {
        return trendHeaderList;
    }

    public void setFooterTrendEventList(ArrayList<Event> footerTrendEventList) {
        this.footerTrendEventList = footerTrendEventList;
    }

    public ArrayList<Event> getFooterTrendEventList() {
        return footerTrendEventList;
    }

    public void setDetrendHeaderList(ArrayList<Header> detrendHeaderList) {
        this.detrendHeaderList = detrendHeaderList;
    }

    public ArrayList<Header> getDetrendHeaderList() {
        return detrendHeaderList;
    }

    public void setDeTrendEventList(ArrayList<Event> deTrendEventList) {
        this.deTrendEventList = deTrendEventList;
    }

    public ArrayList<Event> getDeTrendEventList() {
        return deTrendEventList;
    }

    public void setFooterDeTrendEventList(ArrayList<Event> footerDeTrendEventList) {
        this.footerDeTrendEventList = footerDeTrendEventList;
    }

    public ArrayList<Event> getFooterDeTrendEventList() {
        return footerDeTrendEventList;
    }

    public static Boolean IsNullOrEmpty(String value) {
        return (value == null || (value != null && value.isEmpty()));
    }

}
