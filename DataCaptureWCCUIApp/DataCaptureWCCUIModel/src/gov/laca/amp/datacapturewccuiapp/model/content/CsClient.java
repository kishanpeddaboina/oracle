package gov.laca.amp.datacapturewccuiapp.model.content;

import gov.laca.amp.datacapturewccuiapp.model.data.Attribute;

import gov.laca.amp.datacapturewccuiapp.model.data.LovOptions;
import gov.laca.amp.datacapturewccuiapp.model.util.AttributeProperty;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.naming.NamingException;


import oracle.adf.model.connection.url.URLConnectionProxy;
import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;

import oracle.stellent.ridc.IdcClientManager;

import oracle.stellent.ridc.IdcContext;

import oracle.stellent.ridc.model.DataBinder;

import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;

import oracle.stellent.ridc.protocol.ServiceResponse;

import oracle.wcc.ridc.adfca.framework.RidcConnectionUtils;

import org.apache.commons.lang.StringEscapeUtils;


public class CsClient {
    

    private String idcServerURL;
    private String username;
    
 
    private IdcClient idcClient;
    private static String wccRenderUrl;
    private static String wccConnName = "dcdocs-idc-conn";
    private static String wccRenderConnName = "dcdocs-wccurl-conn";
    
    private static ADFLogger _logger = ADFLogger.createADFLogger(CsClient.class);
    
    public CsClient() {
        this(wccConnName);
    }
    
    public CsClient(String adfConnectionName)  {
        if(idcServerURL == null) {
            try {
              //  WccConnection w = RidcConnectionUtils.__getWccConnection(adfConnectionName);// WccConnection internal class
                idcServerURL = RidcConnectionUtils.__getWccConnection(adfConnectionName).getPropConnectionUrl(); 
                username = RidcConnectionUtils.__getWccConnection(adfConnectionName).getPropCredentialUsername();
                _logger.finer(CsClient.class.getName(), "CsClient","idcServerURL :"+idcServerURL+"==username:"+username);
            } catch (NamingException e) {
               // e.printStackTrace();
                _logger.severe(e);
            }
        } 
    }
    
    public CsClient(String idcServerURL, String user) {
        if (idcServerURL == null || idcServerURL.length() <= 0) {
            throw new IllegalArgumentException("idServerURL cannot be null or empty string, please provide a valid IDC Server URL.");
        }
        
        this.idcServerURL = idcServerURL;
        username = user;
    }
    
    public IdcClient getIdcClient() throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "getIdcClient");
        if(idcClient == null) {
            IdcClientManager clientManager = new IdcClientManager();
            idcClient = clientManager.createClient(this.idcServerURL);
        }
        _logger.exiting(CsClient.class.getName(), "getIdcClient");
        return idcClient;
    }
    
    public static String getWccRenderUrl(String dDocName) {
        _logger.entering(CsClient.class.getName(), "getWccRenderUrl");
        if(wccRenderUrl == null) {
            try {
                wccRenderUrl = ((URLConnectionProxy)ADFContext.getCurrent().getConnectionsContext().lookup(wccRenderConnName)).getURL().toString();
            } catch (NamingException e) {
                //e.printStackTrace();
                _logger.severe(e);
            }
            
            
        }
        _logger.exiting(CsClient.class.getName(), "getWccRenderUrl");
        return wccRenderUrl+"?dDocName="+dDocName+"&Rendition=web";
    }
    
    public void docMetaInfo() throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "docMetaInfo");
        Map<String, Map<String,Object>> metaDataMap =  new HashMap<String, Map<String, Object>>();
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "GET_DOC_METADATA_INFO");
        binder = client.sendRequest(ctx, binder).getResponseAsBinder();
        for(String r : binder.getResultSetNames()) {
                        dump(r, binder.getResultSet(r));
                    }
        DataResultSet ds = binder.getResultSet("DocMetaDefinition");
        for(DataObject d : ds.getRows()) {
            Map<String, Object> attrPropMap = new HashMap<String, Object>();
            attrPropMap.put(AttributeProperty.ORDER, d.get("dOrder"));
            attrPropMap.put(AttributeProperty.LABEL, d.get("dCaption"));
            attrPropMap.put(AttributeProperty.REQUIRED, d.get("dIsRequired"));
            attrPropMap.put(AttributeProperty.COMPONENT, d.get("dType"));
            metaDataMap.put(d.get("dName"), attrPropMap);
        }
        
        _logger.exiting(CsClient.class.getName(), "docMetaInfo");
        
      
            
    }    
    
    
    public Map<String, Map<String,Object>> docMetaInfo(Map<String, Map<String,Object>> attrRuleMap) throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "docMetaInfo");
        Map<String, Map<String,Object>> metaDataMap =  new HashMap<String, Map<String, Object>>();
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "GET_DOC_METADATA_INFO");
        binder = client.sendRequest(ctx, binder).getResponseAsBinder();
        
        DataResultSet ds = binder.getResultSet("DocMetaDefinition");
        
        for(DataObject d : ds.getRows()) {
            String metaFieldName = d.get("dName");
            if(attrRuleMap.containsKey(metaFieldName)) {
                Map<String, Object> attrPropMap = new HashMap<String, Object>();
                attrPropMap.put(AttributeProperty.ORDER, d.get("dOrder"));
                if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.LABEL)) {
                    attrPropMap.put(AttributeProperty.LABEL, d.get("dCaption"));  //rule value overrides attribute
                }
                
                if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.REQUIRED)) {
                    attrPropMap.put(AttributeProperty.REQUIRED, d.get("dIsRequired"));  //rule value overrides attribute
                }  
                
                if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.TYPE)) {
                    attrPropMap.put(AttributeProperty.TYPE,  d.get("dType"));  //rule value overrides attribute
                } else {
                    attrPropMap.put(AttributeProperty.TYPE,  attrRuleMap.get(metaFieldName).get(AttributeProperty.TYPE));
                }
                
              
                
                String cmpType = compType((String) attrPropMap.get(AttributeProperty.TYPE)) ;
                attrPropMap.put(AttributeProperty.COMPONENT, cmpType);
                if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.MAXLENGTH)) {
                    attrPropMap.put(AttributeProperty.MAXLENGTH, getFieldLength(cmpType,(String) attrPropMap.get(AttributeProperty.TYPE)));  //rule value overrides attribute
                }
                
                if(attrRuleMap.get(metaFieldName) != null) {
                    attrPropMap.putAll(attrRuleMap.get(metaFieldName));
                }
                
                metaDataMap.put(metaFieldName, attrPropMap);
            }
            
        }
        
        _logger.exiting(CsClient.class.getName(), "docMetaInfo");
        return metaDataMap;
        
      
                    
    }   
    
    public Map<String, String> searchDoc(String dDocName) throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "searchDoc");
        Map<String, String> docInfoMap = new HashMap<String,String>(10) ;
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "DOC_INFO_BY_NAME");
        binder.putLocal("dDocName", dDocName);
        binder.putLocal("RevisionSelectionMethod", "Latest");
        binder = client.sendRequest(ctx, binder).getResponseAsBinder();
       
        DataResultSet ds = binder.getResultSet("DOC_INFO");
        for(DataObject d : ds.getRows()) {
            docInfoMap.putAll(d);

        }
        
        _logger.exiting(CsClient.class.getName(), "searchDoc");
        
        return docInfoMap;
       
       
    }
    
    public Map<String, String> fetchDocInfo(String dDocName)  {
        _logger.entering(CsClient.class.getName(), "fetchDocInfo");
        Map<String, String> docInfoMap = new HashMap<String,String>(10) ;
        try {
            IdcClient client= getIdcClient();
            IdcContext ctx = new IdcContext(username);
            DataBinder binder = client.createBinder();
            binder.putLocal("IdcService", "DOC_INFO_BY_NAME");
            binder.putLocal("dDocName", dDocName);
            binder.putLocal("RevisionSelectionMethod", "Latest");
            binder = client.sendRequest(ctx, binder).getResponseAsBinder();
           
            DataResultSet ds = binder.getResultSet("DOC_INFO");
            for(DataObject d : ds.getRows()) {
                docInfoMap.putAll(d);
            }
            
        } catch(IdcClientException e) {
            //e.printStackTrace();
            _logger.severe(e);
            throw new JboException(e.getMessage());
        }
        
        _logger.exiting(CsClient.class.getName(), "fetchDocInfo");
        
       return docInfoMap;
       
       
      
    }
    
    public List<String> getProfileRules(String profileName) throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "getProfileRules");
        _logger.finer("Profle..."+profileName);
        List<String> profileRules =  new ArrayList<String>(5);
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "GET_DOCPROFILE");
        binder.putLocal("dpName", profileName);
        binder = client.sendRequest(ctx, binder).getResponseAsBinder();
        
          
        
        DataResultSet ds = binder.getResultSet("ProfileRules");
        for(DataObject d : ds.getRows()) {
           profileRules.add(d.get("dpRuleName"));
                    
        }
        
        _logger.exiting(CsClient.class.getName(), "getProfileRules");                           
       return profileRules;
    }
    
    public Map<String, Map<String,Object>> getAllRuleAttributes(List<String> profileRules) throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "getAllRuleAttributes");
        Map<String, Map<String,Object>> attrRuleMap =  new HashMap<String, Map<String, Object>>();
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        for(String ruleName : profileRules) {
            _logger.finer(CsClient.class.getName(), "getAllRuleAttributes", "ruleName:"+ruleName);
            DataBinder binder = client.createBinder();
            binder.putLocal("IdcService", "GET_DOCRULE");
            binder.putLocal("dpRuleName", ruleName);
            binder = client.sendRequest(ctx, binder).getResponseAsBinder();
            Map localData = binder.getLocalData();
            
            String dprSideEffects = (String) localData.get("dprSideEffects");
            
            dprSideEffects = StringEscapeUtils.unescapeHtml(dprSideEffects);
            
            Map<String,Integer> attrLenghtMap = parseLengthRule(dprSideEffects);
            Map<String,String> attrLabelMap = parseLabelRule(dprSideEffects);
            Map<String,String> attrTypeMap = parseTypeRule(dprSideEffects);
            Map<String,String> attrDependentMap = parseDependentLovRule(dprSideEffects);
            DataResultSet ds = binder.getResultSet("RuleFields");
            
            for(DataObject d : ds.getRows()) {
                String dpRuleFieldName =  d.get("dpRuleFieldName");           
                String dpRuleFieldType =  d.get("dpRuleFieldType");
                Map<String,Object> attrProp =  new HashMap<String,Object>();
                
                if(AttributeProperty.VISIBLE.equals(dpRuleFieldType)) {
                    attrProp.put(AttributeProperty.VISIBLE, Boolean.TRUE.toString());
                   
                } else if(AttributeProperty.REQUIRED.equals(dpRuleFieldType)) {
                    attrProp.put(AttributeProperty.REQUIRED, Boolean.TRUE.toString());
                    String valMsg = (String) localData.get(dpRuleFieldName + ".dprFieldRequiredMsg");//fieldname.dprFieldRequiredMsg
                    attrProp.put(AttributeProperty.VALIDATION_MESSAGE, valMsg);
                }
                
                if(attrLenghtMap.containsKey(dpRuleFieldName)) {
                    attrProp.put(AttributeProperty.MAXLENGTH, attrLenghtMap.get(dpRuleFieldName));
                }
                
                if(attrLabelMap.containsKey(dpRuleFieldName)) {
                    attrProp.put(AttributeProperty.LABEL, attrLabelMap.get(dpRuleFieldName));
                }
                
                if(attrTypeMap.containsKey(dpRuleFieldName)) {
                    attrProp.put(AttributeProperty.TYPE, attrTypeMap.get(dpRuleFieldName));
                }
                
                if(attrDependentMap.containsKey(dpRuleFieldName)) {
                    attrProp.put(AttributeProperty.DEPENDANT_FIELD, attrDependentMap.get(dpRuleFieldName));
                }
                
                if(attrProp.size() > 0) {
                    if(attrRuleMap.containsKey(dpRuleFieldName)) {
                        attrRuleMap.get(dpRuleFieldName).putAll(attrProp);
                    } else {
                        attrRuleMap.put(dpRuleFieldName, attrProp);
                    }
                }
            }
            
            
                
        }
        
       
        
        _logger.exiting(CsClient.class.getName(), "getAllRuleAttributes");
       return attrRuleMap; 
        
    }
    
//    public void buildUiModel() {
//        List<Attribute> l = new ArrayList<Attribute>(10);
//        for(String s: attrMap.keySet()) {
//            Map<String,Object> attrPropMap = attrMap.get(s);
//            System.out.println("202========="+s+"=="+attrPropMap.entrySet()+"=="+docInfoMap.get(s));
//            Attribute a = new Attribute();
//            a.setId(s);
//            a.setName((String) attrPropMap.get(AttributeProperty.LABEL));
//            a.setComponent((String) attrPropMap.get(AttributeProperty.COMPONENT));
//            a.setOrder(Integer.valueOf((String) attrPropMap.get(AttributeProperty.ORDER)));
//            a.setRequired((Boolean.valueOf((String) attrPropMap.get(AttributeProperty.REQUIRED))));
//            a.setValue(docInfoMap.get(s));
//           // a.setL
//        }
//    }
//    
    public void updateDoc(Map<String, String> docInfoMap) throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "updateDoc");
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "UPDATE_DOCINFO");
        for(String metaFieldNm : docInfoMap.keySet()) {
            binder.putLocal(metaFieldNm, docInfoMap.get(metaFieldNm));
        }
        
       
        ServiceResponse response = client.sendRequest(ctx, binder);
        response.getResponseAsBinder();
       
     
        _logger.exiting(CsClient.class.getName(), "updateDoc");
    }
    
    private void dump(String text, DataResultSet ds) {
        System.out.println("Dumping ds.."+text);
        for(DataObject f : ds.getRows()) {
            System.out.println(text+"="+"row key"+f.keySet()+"=="+f.values());
            for(String k : f.keySet())
               System.out.println(text+"="+k+"="+f.get(k));
        }
        
    }
    
    private static Map<String,Integer> parseLengthRule(String sideRule) {
        Map<String,Integer> attrLenghtMap = new HashMap<String,Integer>(20);
        if(sideRule != null && sideRule.trim().length() > 0) {
            Matcher m = Pattern.compile("<\\$(.*):maxLength=\"(.*)\"\\$>").matcher(sideRule);
            while(m.find()) {
                try {
                    attrLenghtMap.put(m.group(1), Integer.valueOf(m.group(2)));
                } catch(NumberFormatException e) {
                    _logger.severe("Error parsing length:"+m.group(1)+":"+m.group(2)+":"+e.getMessage());
                    _logger.severe(e);
                }
                
            }
        }
        
        return attrLenghtMap;
        
    }
    
    private static Map<String,String> parseLabelRule(String sideRule) {
        Map<String,String> attrLabelMap = new HashMap<String,String>(20);
        if(sideRule != null && sideRule.trim().length() > 0) {
            Matcher m = Pattern.compile("<\\$(.*):fieldCaption=\"(.*)\"\\$>").matcher(sideRule);
            while(m.find()) {
                attrLabelMap.put(m.group(1), m.group(2));               
            }
        }
        
        return attrLabelMap;
    }
    
    private static Map<String,String> parseTypeRule(String sideRule) {
        Map<String,String> attrTypeMap = new HashMap<String,String>(20);
        if(sideRule != null && sideRule.trim().length() > 0) {
            Matcher m = Pattern.compile("<\\$(.*):fieldType=\"(.*)\"\\$>").matcher(sideRule);
            while(m.find()) {
                attrTypeMap.put(m.group(1), m.group(2));               
            }
        }
        
        return attrTypeMap;
    }
    
    private static Map<String,String> parseDependentLovRule(String sideRule) {
        Map<String,String> attrTypeMap = new HashMap<String,String>(20);
        if(sideRule != null && sideRule.trim().length() > 0) {
            Matcher m = Pattern.compile("<\\$(.*):dependentField=\"(.*)\"\\$>").matcher(sideRule);
            while(m.find()) {
                attrTypeMap.put(m.group(1), m.group(2));               
            }
        }
        
        return attrTypeMap;
    }
    
    private static int getFieldLength(String compType, String dType)
    {
      int length;
      switch (compType)
      {
      case AttributeProperty.COMP_STRING: 
        length = "Memo".equals(dType) ? 2000 : "BigText".equals(dType) ? 200 : "Text".equals(dType) ? 30 : 30;
         break;
      case AttributeProperty.COMP_NUMBER: 
        length = 38;
        break;
      default: 
        length = 30;
      }
      return length;
    }
    
    private static String compType(String t) {
        if("Text".equalsIgnoreCase(t) || "BigText".equalsIgnoreCase(t) || "Memo".equalsIgnoreCase(t) || "Long Text".equalsIgnoreCase(t)) {
            return AttributeProperty.COMP_STRING;
        } else if("Int".equalsIgnoreCase(t) || "Decimal".equalsIgnoreCase(t) || "Integer".equalsIgnoreCase(t)) {
            return AttributeProperty.COMP_NUMBER;
        } else if("Date".equalsIgnoreCase(t)) {
            return AttributeProperty.COMP_DATE;
        } else if("Percent".equalsIgnoreCase(t)) {
            return AttributeProperty.COMP_PERCENT;
        }
        
        
        return t;
    }
    
    public List<String> getAllProfiles() throws IdcClientException {
        _logger.entering(CsClient.class.getName(), "getAllProfiles");
        List<String> profileNames =  new ArrayList<String>(5);
        IdcClient client= getIdcClient();
        IdcContext ctx = new IdcContext(username);
        DataBinder binder = client.createBinder();
        binder.putLocal("IdcService", "GET_DOCPROFILES");
        binder = client.sendRequest(ctx, binder).getResponseAsBinder();
 
        DataResultSet ds = binder.getResultSet("DocumentProfiles");
        for(DataObject d : ds.getRows()) {
           profileNames.add(d.get("dpName"));
                    
        }
        
        _logger.exiting(CsClient.class.getName(), "getProfileRules");                           
       return profileNames;
    }
    
 
    public Map<String, Map<String,Object>> docMetaInfo(Map<String, Map<String,Object>> attrRuleMap,Map<String , List<LovOptions>> optionsMap, String category, String subCategory) throws IdcClientException {
            _logger.entering(CsClient.class.getName(), "docMetaInfo");
            Map<String, Map<String,Object>> metaDataMap =  new HashMap<String, Map<String, Object>>();
            IdcClient client= getIdcClient();
            IdcContext ctx = new IdcContext(username);
            DataBinder binder = client.createBinder();
            binder.putLocal("IdcService", "GET_DOC_METADATA_INFO_WITH_VIEWS");
            binder = client.sendRequest(ctx, binder).getResponseAsBinder();
           
            DataResultSet ds = binder.getResultSet("DocMetaDefinition");
            
            for(DataObject d : ds.getRows()) {
                String metaFieldName = d.get("dName");
                if(attrRuleMap.containsKey(metaFieldName)) {
                    Map<String, Object> attrPropMap = new HashMap<String, Object>();
                    boolean isLov = false;
                    attrPropMap.put(AttributeProperty.ORDER, d.get("dOrder"));
                    if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.LABEL)) {
                        attrPropMap.put(AttributeProperty.LABEL, d.get("dCaption"));  //rule value overrides attribute
                    }
                    
                    if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.REQUIRED)) {
                        attrPropMap.put(AttributeProperty.REQUIRED, d.get("dIsRequired"));  //rule value overrides attribute
                    }  
                    
                    if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.TYPE)) {
                        attrPropMap.put(AttributeProperty.TYPE,  d.get("dType"));  //rule value overrides attribute
                    } else {
                        attrPropMap.put(AttributeProperty.TYPE,  attrRuleMap.get(metaFieldName).get(AttributeProperty.TYPE));
                    }
                    
                    String optionKey = d.get("dOptionListKey");
                    String viewName = d.get("dOptionListKey");
                    String dependantField = (String)attrRuleMap.get(metaFieldName).get(AttributeProperty.DEPENDANT_FIELD);
                    if(d.getInteger("dIsOptionList") == 1 && optionKey != null) {
                        isLov = true;
                        if(dependantField != null) {
                            optionKey = optionKey+"_"+metaFieldName;
                        }
                        
                    }
                    
                    if(isLov) {
                        attrPropMap.put(AttributeProperty.COMPONENT, AttributeProperty.COMP_SELECTONECHOICE);
                        attrPropMap.put(AttributeProperty.OPTION_KEY, optionKey);
                            if( !optionsMap.containsKey(optionKey) ) {
                                List<LovOptions> lovOptions = new ArrayList<LovOptions>(5);
                                if(optionKey.startsWith("view://")) {
                                    lovOptions = getOptionsViewData(viewName, binder, (String)attrRuleMap.get(metaFieldName).get(AttributeProperty.DEPENDANT_FIELD), category, subCategory);
                                } else {
                                    String options = d.get("dOptionListValues");
                                    if(options != null) {                                    
                                        for(String keyVal : options.split(",")) {
                                            lovOptions.add(new LovOptions(keyVal));
                                        }
                                        
                                        
                                    }
                                }
                                optionsMap.put(optionKey, lovOptions);
                            }
                        
                    } else {
                        String cmpType = compType((String) attrPropMap.get(AttributeProperty.TYPE)) ;
                        attrPropMap.put(AttributeProperty.COMPONENT, cmpType);
                        if(!attrRuleMap.get(metaFieldName).containsKey(AttributeProperty.MAXLENGTH)) {
                            attrPropMap.put(AttributeProperty.MAXLENGTH, getFieldLength(cmpType,(String) attrPropMap.get(AttributeProperty.TYPE)));  //rule value overrides attribute
                        }
                    }
                    
                  
                    
                    
                    
                    if(attrRuleMap.get(metaFieldName) != null) {
                        attrPropMap.putAll(attrRuleMap.get(metaFieldName));
                    }
                    
                    metaDataMap.put(metaFieldName, attrPropMap);
                }
                
            }
            
            _logger.exiting(CsClient.class.getName(), "docMetaInfo");
            return metaDataMap;
            
          
                        
        }
    
    
    public  List<LovOptions> getOptionsViewData(String viewName) throws IdcClientException {
            viewName = viewName.replaceAll("view://", "");
            List<LovOptions> lovOptions = new ArrayList<LovOptions>(5);
            IdcClient client= getIdcClient();
            IdcContext ctx = new IdcContext(username);
            DataBinder binder = client.createBinder();
            binder.putLocal("IdcService", "GET_SCHEMA_VIEW_FRAGMENT");
            binder.putLocal("schViewName", viewName);
            binder = client.sendRequest(ctx, binder).getResponseAsBinder();
    //        for(String r : binder.getResultSetNames()) {
    //         //   dump(r, binder.getResultSet(r));
    //        }
            DataResultSet ds = binder.getResultSet(viewName.toLowerCase());
            for(DataObject d : ds.getRows()) {
                lovOptions.add(new LovOptions(d.get("Storage.default") == null ? d.get("Display.default") : d.get("Storage.default") , d.get("Display.default")));
            }
            
            return lovOptions;
            
        }
    
    public  List<LovOptions> getOptionsViewData(String viewName, DataBinder binder, String dependentField, String category, String subCategory) throws IdcClientException {
            viewName = viewName.replaceAll("view://", "");
            String dependentValue = null;
            
            if(dependentField != null) {
                if("Category".equalsIgnoreCase(dependentField)) {
                    dependentValue = category;
                } else if("SubCategory".equalsIgnoreCase(dependentField)) {
                    dependentValue = subCategory;
                }
            }
            List<LovOptions> lovOptions = new ArrayList<LovOptions>(5);
            DataResultSet ds = binder.getResultSet(viewName);
            for(DataObject d : ds.getRows()) {
                if(dependentField == null) {
                    lovOptions.add(new LovOptions(d.get("Storage.default") , d.get("Display.default")));
                } else {
                    if(d.get(dependentField) != null && dependentValue.equals(d.get(dependentField))) {
                        lovOptions.add(new LovOptions(d.get("Storage.default") , d.get("Display.default"))); 
                    }
                }
                
            }
            
            return lovOptions;
            
        }
    
   
    
   
}
