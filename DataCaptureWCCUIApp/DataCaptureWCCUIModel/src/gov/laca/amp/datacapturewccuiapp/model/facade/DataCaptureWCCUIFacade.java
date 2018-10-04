package gov.laca.amp.datacapturewccuiapp.model.facade;

import gov.laca.amp.datacapturewccuiapp.model.content.CsClient;

import gov.laca.amp.datacapturewccuiapp.model.data.Attribute;
import gov.laca.amp.datacapturewccuiapp.model.data.Document;
import gov.laca.amp.datacapturewccuiapp.model.data.LovOptions;
import gov.laca.amp.datacapturewccuiapp.model.util.AttributeProperty;

import gov.laca.amp.datacapturewccuiapp.model.util.ContentUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;

public class DataCaptureWCCUIFacade {
    
   
    private static Map<String, Map<String,String>> attrCompMap;
    private static ADFLogger _logger = ADFLogger.createADFLogger(DataCaptureWCCUIFacade.class);
    
    static {
        attrCompMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put(AttributeProperty.COMPONENT, AttributeProperty.COMP_CHECKBOX);
        attrCompMap.put("xSignature", hmap);
    }
    
    
    public DataCaptureWCCUIFacade() {
        super();
        
        
    }


  

    public Document fetchWccUIModel(String dDocName, String category, String subCategory)  {
        _logger.entering(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel");
        _logger.finer(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel","category:"+category+"==subCategory:"+subCategory+"==dDocName:"+dDocName);
        CsClient csClient = new CsClient();
       
         Map<String, String> docInfoMap = csClient.fetchDocInfo(dDocName);
        
        String docCategory = docInfoMap.get("xCategory");
        String docSubCategory = docInfoMap.get("xSubCategory");
        _logger.finer(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel","docCategory:"+docCategory+"==docSubCategory:"+docSubCategory+"==dDocName:"+dDocName);
        if(compare(category, docCategory) && compare(subCategory, docSubCategory)) {
            String profileName = null;
            try {
                
                if(docCategory != null) {
                    docCategory = docCategory.replaceAll("\\s", "");
                } else {
                    docCategory = "";  
                }
                    
                
                if(docSubCategory != null) {
                    docSubCategory = docSubCategory.replaceAll("\\s", "");
                } else {
                    docSubCategory = "";
                }
                    
                
                List<String> profileList = csClient.getAllProfiles();
                if(profileList.contains(docCategory+docSubCategory)) {
                    profileName = docCategory+docSubCategory;
                } else if(profileList.contains(docCategory)) {
                    profileName = docCategory;
                } else if(profileList.contains(docSubCategory)) {
                    profileName = docSubCategory;
                } else {
                    _logger.finer(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel","Unable to get WCC profile");
                    _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel");
                    throw new JboException("Unable to retrieve profile information for document category and subcategory");
                }
                
                _logger.finer(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel","profileName:"+profileName);
                List<String> profileRules = csClient.getProfileRules(profileName);
                
                Map<String, Map<String,Object>> attrRuleMap = csClient.getAllRuleAttributes(profileRules);
                
                Map<String , List<LovOptions>> optionsMap = new HashMap<String , List<LovOptions>>();
                attrRuleMap = csClient.docMetaInfo(attrRuleMap,optionsMap, category, subCategory);
                
                
                Document doc =  new Document();
                doc.setDocInfoMap(docInfoMap);
                String documenttumURL = doc.getDocInfoMap().get("xExternalDocURL");
                if(documenttumURL != null && documenttumURL != "") {
                    doc.setDocRenderUrl(documenttumURL);
                } else {
                    doc.setDocRenderUrl(CsClient.getWccRenderUrl(dDocName));
                }
                _logger.finer(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel","docrenderurl:"+doc.getDocRenderUrl());
                
                doc.setAttrModelList(buildUiAttrModel(attrRuleMap,doc.getDocInfoMap()));
                doc.setOptionsMap(optionsMap);
                _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel");
                return doc;
            } catch(Exception e) {
                _logger.severe(e);
                _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel");
                throw new JboException(e.getMessage());
            }
            
        } else {
            _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "fetchWccUIModel");
            throw new JboException("Document Category/SubCategory mismatch.");
        }
        
        
        
    }
    
    public List<Attribute> buildUiAttrModel(Map<String, Map<String,Object>> attrMap, Map<String, String> docInfoMap) {
        _logger.entering(DataCaptureWCCUIFacade.class.getName(), "buildUiAttrModel");
            List<Attribute> attrList = new ArrayList<Attribute>(10);
            for(String metaFieldNm: attrMap.keySet()) {
                _logger.finer(DataCaptureWCCUIFacade.class.getName(), "buildUiAttrModel","attribute:"+metaFieldNm);
                Map<String,Object> attrPropMap = attrMap.get(metaFieldNm);
                if(attrCompMap.containsKey(metaFieldNm))
                    attrPropMap.putAll(attrCompMap.get(metaFieldNm));
               
                Attribute attr = new Attribute();
                attr.setId(metaFieldNm);
                attr.setName((String) attrPropMap.get(AttributeProperty.LABEL));
                attr.setComponent((String) attrPropMap.get(AttributeProperty.COMPONENT));
                attr.setOrder(Integer.valueOf((String) attrPropMap.get(AttributeProperty.ORDER)));
                attr.setRequired((Boolean.valueOf((String) attrPropMap.get(AttributeProperty.REQUIRED))));
                attr.setVisible((Boolean.valueOf((String) attrPropMap.get(AttributeProperty.VISIBLE))));
                attr.setValidationMessage((String) attrPropMap.get(AttributeProperty.VALIDATION_MESSAGE));
                attr.setDataType((String) attrPropMap.get(AttributeProperty.TYPE));
                attr.setMaxLength((Integer) attrPropMap.get(AttributeProperty.MAXLENGTH));
                attr.setOptionKey((String) attrPropMap.get(AttributeProperty.OPTION_KEY));
                
                if("date".equalsIgnoreCase(attr.getDataType())) {
                    attr.setDateValue(ContentUtil.parseRidcString(docInfoMap.get(metaFieldNm), Date.class));
                } else if("Int".equalsIgnoreCase(attr.getDataType())) {
                    attr.setIntValue(ContentUtil.parseRidcString(docInfoMap.get(metaFieldNm), BigInteger.class));
                } else if("Percent".equalsIgnoreCase(attr.getDataType())) {
                    attr.setDecValue(ContentUtil.parseRidcString(docInfoMap.get(metaFieldNm), BigDecimal.class));
                } else {
                    attr.setValue(ContentUtil.parseRidcString(docInfoMap.get(metaFieldNm), String.class));
                }
              
                attrList.add(attr);
            }
            
          sortUiAttrOrder(attrList)  ;    
        _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "buildUiAttrModel");
            return attrList;
    }
    
    public void sortUiAttrOrder (List<Attribute> attrList) {
        _logger.entering(DataCaptureWCCUIFacade.class.getName(), "sortUiAttrOrder");
        Collections.sort(attrList, new Comparator<Attribute>() {
                @Override
                public int compare(Attribute o1, Attribute o2) {
                        return o1.getOrder().compareTo(o2.getOrder());
                }
        });
        _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "sortUiAttrOrder");
       // attrList.sort( (Attribute a, Attribute b) -> a.getOrder()-b.getOrder());
        
    }
    
    
    public void updateWCCDocData(Map<String, String> docDataMap, List<Attribute> attrList) {
        _logger.entering(DataCaptureWCCUIFacade.class.getName(), "updateWCCDocData");
        CsClient csClient = new CsClient();
        Map<String, String> docUpdDataMap = new HashMap<String, String>(10);
        docUpdDataMap.put("dDocName", docDataMap.get("dDocName"));
        docUpdDataMap.put("dID", docDataMap.get("dID"));
        docUpdDataMap.put("dRevLabel", docDataMap.get("dRevLabel"));
        docUpdDataMap.put("dSecurityGroup", docDataMap.get("dSecurityGroup"));
        for(Attribute attr : attrList) {
            String val = null;
            if(attr.getValue() != null || attr.getDateValue() != null || attr.getIntValue() != null || attr.getDecValue() != null) {
                val = ContentUtil.toRidcString(attr.getValue()); 
                if("date".equalsIgnoreCase(attr.getDataType())) {
                    val = ContentUtil.toRidcString(attr.getDateValue());
                } else if("Int".equalsIgnoreCase(attr.getDataType())) {
                    val = ContentUtil.toRidcString(attr.getIntValue());
                }else if("Percent".equalsIgnoreCase(attr.getDataType())) {
                    val = ContentUtil.toRidcString(attr.getDecValue());
                } else {
                    val = ContentUtil.toRidcString(attr.getValue());
                }
                
            }
            docUpdDataMap.put(attr.getId(), val);
        }
        try {
           csClient.updateDoc(docUpdDataMap);
        } catch (Exception e) {
            _logger.severe(e);
           throw new JboException("Error updating document metadata");
        }
        
        _logger.exiting(DataCaptureWCCUIFacade.class.getName(), "updateWCCDocData");
    }
    
    
    private static boolean compare(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }
    
    
    
    
  
    
}
