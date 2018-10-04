package gov.laca.amp.datacapturewccuiapp.model.dc;

import gov.laca.amp.datacapturewccuiapp.model.content.CsClient;
import gov.laca.amp.datacapturewccuiapp.model.data.Attribute;
import gov.laca.amp.datacapturewccuiapp.model.data.Document;
import gov.laca.amp.datacapturewccuiapp.model.data.LovOptions;
import gov.laca.amp.datacapturewccuiapp.model.facade.DataCaptureWCCUIFacade;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;

public class DataCaptureWCCUIDC implements Serializable {
    
    private transient Map<String, Map<String,Object>> metaDataMap =  new HashMap<String, Map<String, Object>>(10);
    private  Map<String, String> docInfoMap = new HashMap<String,String>(10) ;
    private String docProfileName;
    private List<Attribute> attrModelList;
    private transient Document documentData = new Document();
    private static ADFLogger _logger = ADFLogger.createADFLogger(DataCaptureWCCUIDC.class);
    
    public DataCaptureWCCUIDC() {
        super();
    }


    public void setAttrModelList(List<Attribute> attrModelList) {
        this.attrModelList = attrModelList;
    }

    public List<Attribute> getAttrModelList() {
        return attrModelList;
    }


    public void setDocInfoMap(Map<String, String> docInfoMap) {
        this.docInfoMap = docInfoMap;
    }

    public Map<String, String> getDocInfoMap() {
        return docInfoMap;
    }

    public String populateAttributeModel(String dDocName, String category, String subCategory) {
        _logger.entering(DataCaptureWCCUIDC.class.getName(), "populateAttributeModel");
        String msg = "";
        
        _logger.finer(DataCaptureWCCUIDC.class.getName(), "populateAttributeModel","dDocName:"+dDocName+"==category:"+category+"==subCategory:"+subCategory);
        if(subCategory == null || subCategory.trim().length() == 0) {
            throw new JboException("Unable to determine Document profile, Subcategory is blank");
          
        }
        
       
        
        DataCaptureWCCUIFacade f =  new DataCaptureWCCUIFacade();
       
            documentData = f.fetchWccUIModel(dDocName, category, subCategory);
            setAttrModelList(documentData.getAttrModelList());
            setDocInfoMap(documentData.getDocInfoMap());
            
        _logger.exiting(DataCaptureWCCUIDC.class.getName(), "populateAttributeModel");
        return msg;
        
    }
    
    public void updateWCCDocData() {
        _logger.entering(DataCaptureWCCUIDC.class.getName(), "updateWCCDocData");
        DataCaptureWCCUIFacade f =  new DataCaptureWCCUIFacade();
       
           f.updateWCCDocData(getDocInfoMap(), getAttrModelList());
        
        _logger.exiting(DataCaptureWCCUIDC.class.getName(), "updateWCCDocData");
    }


    public void setDocumentData(Document documentData) {
        this.documentData = documentData;
    }

    public Document getDocumentData() {
        return documentData;
    }

    public static void main(String[] a) {
        DataCaptureWCCUIDC d =  new DataCaptureWCCUIDC();
        d.populateAttributeModel("WCCD_006123", "Exclusions", "Prop 58");
    }
    
    public Map fetchOptions() {
        Map<String, LinkedHashMap<String,String>> optionsMap= new HashMap<String, LinkedHashMap<String,String>>(); 
        
        if(documentData.getOptionsMap() != null) {
            for(String attr : documentData.getOptionsMap().keySet()) {
                LinkedHashMap<String,String> o = new LinkedHashMap<String,String>(5);
                optionsMap.put(attr, o);
                for(LovOptions l : documentData.getOptionsMap().get(attr)) {
                    o.put(l.getValue(), l.getDescription());
                }
            }
        }
        
        return optionsMap;
    }
    
    public Serializable createSnapshot() {
        _logger.info("DataCaptureWCCUIDC.class", "createSnapshot", "creating snapshot");
         return this;
    }
            
    public void restoreSnapshot(Serializable handle) {
        _logger.info("DataCaptureWCCUIDC.class", "restoreSnapshot", "restoring Snapshot");
        try {
            DataCaptureWCCUIDC dc = (DataCaptureWCCUIDC)handle;
            setDocInfoMap(dc.getDocInfoMap());
            setAttrModelList(dc.getAttrModelList()); 
            _logger.info("DataCaptureWCCUIDC.class", "restoreSnapshot", "restored Snapshot");
        } catch (Exception e) {
            _logger.severe(e);
        }
        
       
    }
}
