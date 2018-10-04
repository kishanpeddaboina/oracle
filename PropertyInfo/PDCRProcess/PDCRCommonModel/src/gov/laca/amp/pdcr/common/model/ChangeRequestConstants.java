package gov.laca.amp.pdcr.common.model;

import gov.laca.amp.fwk.util.AmpConstants;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.SourceType;
import gov.laca.amp.proxy.soap.pdcrmgmtservice.client.gen.UserType;


public class ChangeRequestConstants extends AmpConstants {

    // resourse bundle location
    public static final String BUNDLE_VIEW_LOCATION = "gov.laca.amp.pdcr.common.view.PDCRCommonViewBundle";
    public static final String BUNDLE_MODEL_LOCATION = "gov.laca.amp.pdcr.common.model.PDCRCommonModelBundle";

    // Common logger prefix for PDCR
    public static final String LOGGER_PREFIX = "\\tDEBUG >> PDCR Log: ";

    // location of URL for content server, and the image
    public static final String CONTENT_SERVER_ICONS_RELATIVE_PATH = "/content/conn/ucmserver/path/spaces/assessorportal/app-icons/";
    public static final String BPM_ICONS_RELATIVE_PATH = "/images/";
    public static final String SITUS_ADDRESS_IMAGE = "situs-address-icon.png";
    public static final String PRINT_ICON = "print-page.png";

    // Home url part for AMP portal and navigation
    public static final String AMP_PORTAL = "/portal/assessorportal";
    public static final String AMP_PORTAL_NAV_MODEL_PDTL = "#{navigationContext.defaultNavigationModel.node['propertyDetails'].prettyUrl}";
    public static final String AMP_WEBCENTER_PORTAL_APP_URL = "#{WCAppContext.applicationURL}";

    // Security Roles
    public static final String EXTERNAL_GROUP = "ExternalPublic";
    // This is the value used for clerical at this time since there is no role specific to this role
    public static final String CLERICAL_GROUP = "AssessorStaff";

    public static final String PUBLIC_SERVICE_RELATED = "Public Service Request";

    // BPM Rules
    public static final String BPM_ASIIGNBY_APPRAISER = "Appraiser";
    public static final String BPM_ASIIGNBY_WITHDRAW = "Withdraw";

    // Requestor information
    public static final String PREFERED_METHOD_OF_CONTACT_EMAIL = "email";
    public static final String PREFERED_METHOD_OF_CONTACT_PHONE = "phone";

    // Data source
    public static final String SOURCE_SOA = "soa";
    public static final String SOURCE_BPM = "bpm";

    // PDCR operations
    public static final String REALPROPERTY_OPERATION_RETRIEVE = "retrieve";
    public static final String REALPROPERTY_OPERATION_INITIATE = "initiate";

    // Real property type flag
    public static final String REALPROPERTY_TYPE_ORIGINAL = "original";
    public static final String REALPROPERTY_TYPE_USER_ENTERED = "userentered";
    public static final String REALPROPERTY_TYPE_FINAL = "final";

    // Human Task to work on
    public static final String HUMAN_TASK_MANAGE = "manage";
    public static final String HUMAN_TASK_REVIEW = "review";
    public static final String HUMAN_TASK_SUBMIT = "submit";

    // Initiate realpropertyInfo
    public static final String RERALPROPERTYINFO_SOURCE_CREATE = SourceType.CREATE_PDCR.value();
    public static final String RERALPROPERTYINFO_SOURCE_EDIT = SourceType.EDIT_PDCR.value();

    //Process type for check lock and initiate
    public static final String PROCESS_TYPE_CHECKLOCK = "PDCR";
    public static final String PROCESS_TYPE_INITIATE = "PDCR";

    // Requester Information
    public static final String USER_TYPE_INTERNAL = UserType.INTERNAL.value();
    public static final String USER_TYPE_EXTERNAL = UserType.EXTERNAL.value();

    // Default prefix for create transactions
    public static final String CREATE_SUBPART_U_PREFIX = "SP_U_";
    public static final String CREATE_COMPOSITE_U_PREFIX = "C_U_";
    public static final String CREATE_SUBPART_F_PREFIX = "SP_F_";
    public static final String CREATE_COMPOSITE_F_PREFIX = "C_F_";
    public static final String CREATE_LAND_PREFIX = "L_";

    // Action Types
    public static final String ACTION_TYPE_UPDATE = "UPDATE";
    public static final String ACTION_TYPE_UPDATE_MANUAL_PROCESSING = "UPDATE AND MANUAL PROCESSING";
    public static final String ACTION_TYPE_NO_CHANGE = "NO CHANGE";
    public static final String ACTION_TYPE_NO_CHANGE_MANUAL_PROCESSING = "NO CHANGE AND MANUAL PROCESSING";

    // Transaction types for real property
    public static final String REAL_PROPERTY_UPDATE = "U";

    // Transaction types for sub parts
    public static final String SUBPART_CREATE = "C";
    public static final String SUBPART_UPDATE = "U";
    public static final String SUBPART_DELETE = "D";
    // Transaction types for composite
    public static final String COMPOSITE_CREATE = "C";
    public static final String COMPOSITE_UPDATE = "U";
    public static final String COMPOSITE_DELETE = "D";
    // Transaction types for land
    public static final String LAND_CREATE = "C";
    public static final String LAND_UPDATE = "U";

    // Initial SubPart Number for Create
    public static final String SUBPART_NUMBER_SEED = "0101";

    // Phone conversion regex
    public static final String PHONE_REGEX_CONVERSION = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    public static final String PHONE_CONVERSION_PATTERN = "$1-$2-$3"; //($1) $2-$3

    // Service information
    public static final String PDCRMGMT_SERVICE_CONNECTION_NAME = "PDCRMgmtService";
    public static final String PDCRMGMT_QNAME = "http://assessor.lacounty.gov/amp/wsdl/ao/PDCRMgmtService/v1.0";
    public static final String PDCRMGMT_SERVICE_CLASS = "PDCRMgmtService_ep";

    public static final String AINLOCK_SERVICE_CONNECTION_NAME = "AINLockMgmtService";
    public static final String AINLOCK_QNAME = "http://assessor.lacounty.gov/amp/wsdl/ao/AINLockMgmtService/v1.0";
    public static final String AINLOCK_SERVICE_CLASS = "AINLockMgmtService_ep";

    public static final String HEADER_APPLICATION_ID = "PDCR";

    // AIN lock status values
    public static final String AIN_LOCK_STATUS_LOCKED = "Locked";
    public static final String AIN_LOCK_STATUS_UNLOCKED = "UNLOCKED";

    // BPM return status values
    public static final String BPM_STATUS_SUBMITTED = "Submitted";
    public static final String BPM_STATUS_COMPLETED = "COMPLETED";

    // Iterators for PDCR portal pages
    public static final String ORIGINAL_ITERATOR = "originalRPIIterator";
    public static final String REQUESTOR_ITERATOR = "requestorInfoDOIterator";
    public static final String SUB_PART_LIST_ITERATOR = "subPartListDOIterator";
    public static final String TEMP_SUB_PART_ITERATOR = "tempSubPartDOIterator";
    public static final String COMPOSITE_ITERATOR = "compositeDOIterator";
    public static final String TEMP_COMPOSITE_ITERATOR = "tempCompositeDOIterator";
    public static final String LAND_ITERATOR = "landDOIterator";
    public static final String TEMP_LAND_ITERATOR = "tempLandDOIterator";

    // BMP iterators for the different human tasks
    public static final String BPM_ORIGINAL_ITERATOR = "originalRealPropertyInfoIterator";
    public static final String BPM_ORIGINAL_SUBPART_ITERATOR = "subPartIterator";
    public static final String BPM_ORIGINAL_COMPOSITE_ITERATOR = "compositeIterator";
    public static final String BPM_ORIGINAL_LAND_ITERATOR = "landIterator";
    public static final String BPM_USER_ENTERED_ITERATOR = "userEnteredRealPropertyInfoIterator";
    public static final String BPM_USER_ENTERED_SUBPART_ITERATOR = "subPartIterator1";
    public static final String BPM_USER_ENTERED_COMPOSITE_ITERATOR = "compositeIterator1";
    public static final String BPM_USER_ENTERED_LAND_ITERATOR = "landIterator1";
    public static final String BPM_FINAL_ITERATOR = "finalRealPropertyInfoIterator";
    public static final String BPM_FINAL_SUBPART_ITERATOR = "subPartIterator2";
    public static final String BPM_FINAL_COMPOSITE_ITERATOR = "compositeIterator2";
    public static final String BPM_FINAL_LAND_ITERATOR = "landIterator2";

    // The control ID's for the fields to validate in create PDCR
    public static final String[] VALIDATE_CONTROLS = { "itNumUnits", "itSqFt", "itYearBuilt", "itNumBeds", "itNumBaths", "itComments" };

    // ViewCode Types
    //public static final String VIEW_CODE_NONE = "N";
    // The ciew codes for 'None'
    public static final String[] VIEW_CODE_NONE = { "N", "None", "1" };
    public static final String VIEW_CODE_CANYON = "2";
    public static final String VIEW_CODE_CITY = "3";
    public static final String VIEW_CODE_CITY_CANYON = "4";
    public static final String VIEW_CODE_WATER = "5";
    public static final String VIEW_CODE_WATER_CANYON = "6";
    public static final String VIEW_CODE_WATER_CITY = "7";
    public static final String VIEW_CODE_WATER_CITY_CANYON = "8";
    public static final String VIEW_CODE_YES = "Y";

    // ViewCode display lables
    public static final String VIEW_CODE_NONE_DISPLAY = "None";
    public static final String VIEW_CODE_CANYON_DISPLAY = "Canyon";
    public static final String VIEW_CODE_CITY_DISPLAY = "City";
    public static final String VIEW_CODE_CITY_CANYON_DISPLAY = "City/Canyon";
    public static final String VIEW_CODE_WATER_DISPLAY = "Water";
    public static final String VIEW_CODE_WATER_CANYON_DISPLAY = "Water/Canyon";
    public static final String VIEW_CODE_WATER_CITY_DISPLAY = "Water/City";
    public static final String VIEW_CODE_WATER_CITY_CANYON_DISPLAY = "Water/Canyon/City";
    public static final String VIEW_CODE_YES_DISPLAY = "Yes";

    // View Code references
    //TODO: These valuse are different based on ENV, and should not be used in Phase 1
    public static final String VIEW_CODE_NONE_REF = "449";
    public static final String VIEW_CODE_CANYON_REF = "441";
    public static final String VIEW_CODE_CITY_REF = "442";
    public static final String VIEW_CODE_CITY_CANYON_REF = "443";
    public static final String VIEW_CODE_WATER_REF = "444";
    public static final String VIEW_CODE_WATER_CANYON_REF = "445";
    public static final String VIEW_CODE_WATER_CITY_REF = "446";
    public static final String VIEW_CODE_WATER_CITY_CANYON_REF = "447";
    public static final String VIEW_CODE_YES_REF = "448";

    // Vacant land Use Type
    public static final String USE_TYPE_VACANT_LAND = "Vacant Land";

    // Yes/No values
    public static final String YES_VALUE = "Y";
    public static final String NO_VALUE = "N";

    // Transaction Type
    public static final String TRANSACTION_CREATE_SUBPART = "Add SubPart";
    public static final String TRANSACTION_EDIT_SUBPART = "Edit SubPart";
    public static final String TRANSACTION_REVIEW_SUBPART = "Review SubPart";
    public static final String TRANSACTION_DELETE_SUBPART = "Delete SubPart";
    public static final String TRANSACTION_CREATE_COMPOSITE = "Add Composite";
    public static final String TRANSACTION_EDIT_COMPOSITE = "Edit Composite";
    public static final String TRANSACTION_DELETE_COMPOSITE = "Delete Composite";
    public static final String TRANSACTION_CREATE_LAND = "Create Land";
    public static final String TRANSACTION_EDIT_LAND = "Edit Land";

    // Return values for saving and canceling Add, edit, and delete
    public static final String SAVE_SUCESS = "sucess";
    public static final String SAVE_FAILURE = "fail";
    public static final String CANCEL_TRANSACTION = "cancelPDCR";

    // constants used for LOV's
    public static final String LOV_REQUESTER_TYPE = "requester_type";
    public static final String LOV_REASON_FOR_REQUEST = "reason_for_request";
    public static final String LOV_PROPERTY_TYPE = "property_type";
    public static final String LOV_DATA_CHANGE_SOURCE = "data_change_source";
    public static final String LOV_VIEW_CODE = "view_code";
    public static final String LOV_BPM_USER_TYPE = "bpm_user_type";
    
    
    
    

}
