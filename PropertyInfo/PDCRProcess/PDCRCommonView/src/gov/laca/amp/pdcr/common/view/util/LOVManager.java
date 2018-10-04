package gov.laca.amp.pdcr.common.view.util;

import gov.laca.amp.pdcr.common.model.ChangeRequestConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


public class LOVManager {
    
    // the lov map for the application, and currently used for PDCR
    private static Map<String, List<SelectItem>> lovMap;

    private static final Map<String, String> REQUESTER_TYPE_MAP;
    static {
        REQUESTER_TYPE_MAP = new HashMap<String, String>();
        REQUESTER_TYPE_MAP.put("Owner", "Owner");
        REQUESTER_TYPE_MAP.put("Authorized Agent", "Authorized Agent");
        REQUESTER_TYPE_MAP.put("Other", "Other");
    }

    public static final Map<String, String> REASON_FOR_REQUEST_MAP;
    static {
        REASON_FOR_REQUEST_MAP = new HashMap<String, String>();
        REASON_FOR_REQUEST_MAP.put("Pending Sale / Refinance", "Pending Sale / Refinance");
        REASON_FOR_REQUEST_MAP.put("Applying For Building Permit", "Applying For Building Permit");
        REASON_FOR_REQUEST_MAP.put("Updating Records", "Updating Records");
        REASON_FOR_REQUEST_MAP.put("Other", "Other");
    }

    public static final Map<String, String> PROPERTY_TYPE_MAP;
    static {
        PROPERTY_TYPE_MAP = new HashMap<String, String>();
        PROPERTY_TYPE_MAP.put("Single Family Residence", "Single Family Residence");
        PROPERTY_TYPE_MAP.put("Multiple Family Residence", "Multiple Family Residence");
        PROPERTY_TYPE_MAP.put("Condominium", "Condominium");
        PROPERTY_TYPE_MAP.put("Commercial / Industrial", "Commercial / Industrial");
        PROPERTY_TYPE_MAP.put("Vacant Land", "Vacant Land");
    }

    public static final Map<String, String> DATA_CHANGE_SOURCE_MAP;
    static {
        DATA_CHANGE_SOURCE_MAP = new HashMap<String, String>();
        DATA_CHANGE_SOURCE_MAP.put("Public Service Request", "Public Service Request");
        DATA_CHANGE_SOURCE_MAP.put("Real Estate Resource", "Real Estate Resource");
        DATA_CHANGE_SOURCE_MAP.put("Aerial View", "Aerial View");
        DATA_CHANGE_SOURCE_MAP.put("Field Pick Up", "Field Pick Up");
    }

    public static final Map<String, String> VIEW_CODE_MAP;
    static {
        VIEW_CODE_MAP = new HashMap<String, String>();
        VIEW_CODE_MAP.put("Canyon", "2");
        VIEW_CODE_MAP.put("City", "3");
        VIEW_CODE_MAP.put("City/Canyon", "4");
        VIEW_CODE_MAP.put("Water", "5");
        VIEW_CODE_MAP.put("Water/Canyon", "6");
        VIEW_CODE_MAP.put("Water/City", "7");
        VIEW_CODE_MAP.put("Water/City/Canyon", "8");
        VIEW_CODE_MAP.put("None", "N");
    }

    public static final Map<String, String> BPM_USER_TYPE_MAP;
    static {
        BPM_USER_TYPE_MAP = new HashMap<String, String>();
        BPM_USER_TYPE_MAP.put("Appraiser", "Appraiser");
    }
    
    public static List getLov(String lovName) {
        List<SelectItem> lovList = new ArrayList<SelectItem>();
        lovMap = getLovMap();
        if (null == lovMap) {
            lovMap = new HashMap<String, List<SelectItem>>();
        } else {
            lovList = lovMap.get(lovName);
            if (null != lovList)
                return lovList;
        }

        Map<String, String> lovItemsMap = getLovItemsMap(lovName);
        
        Map<String, String> treeMap = new TreeMap<String, String>(lovItemsMap);
        lovList = new ArrayList<SelectItem>();
        if (null != treeMap) {
            for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                SelectItem item = new SelectItem();
                item.setLabel(entry.getKey());
                item.setValue(entry.getValue());
                lovList.add(item);
            }
            lovMap.put(lovName, lovList);
            getApplicationMap().put("lovMap", lovMap);
        }
        return lovList;
    }
    
    public static Map<String, String> getLovItemsMap (String lovName){
        Map<String, String> lovItemsMap = null;
        if (lovName.equals(ChangeRequestConstants.LOV_REQUESTER_TYPE)) {
            lovItemsMap = REQUESTER_TYPE_MAP;
        } else if (lovName.equals(ChangeRequestConstants.LOV_REASON_FOR_REQUEST)) {
            lovItemsMap = REASON_FOR_REQUEST_MAP;
        } else if (lovName.equals(ChangeRequestConstants.LOV_PROPERTY_TYPE)) {
            lovItemsMap = PROPERTY_TYPE_MAP;
        } else if (lovName.equals(ChangeRequestConstants.LOV_DATA_CHANGE_SOURCE)) {
            lovItemsMap = DATA_CHANGE_SOURCE_MAP;
        } else if (lovName.equals(ChangeRequestConstants.LOV_VIEW_CODE)) {
            lovItemsMap = VIEW_CODE_MAP;
        }else if (lovName.equals(ChangeRequestConstants.LOV_BPM_USER_TYPE)) {
            lovItemsMap = BPM_USER_TYPE_MAP;
        }
        return lovItemsMap;
    }

    private static Map getApplicationMap() {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        return facesCtx.getExternalContext().getApplicationMap();
    }

    private static Map<String, List<SelectItem>> getLovMap() {
        return (Map<String, List<SelectItem>>)getApplicationMap().get("lovMap");
    }
}
