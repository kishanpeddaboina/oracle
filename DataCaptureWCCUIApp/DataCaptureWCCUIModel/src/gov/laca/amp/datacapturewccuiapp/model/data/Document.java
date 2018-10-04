package gov.laca.amp.datacapturewccuiapp.model.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    public Document() {
        super();
    }
    
    private String docRenderUrl;
    private List<Attribute> attrModelList;
    private  Map<String, String> docInfoMap = new HashMap<String,String>(10) ;
    private Map<String , List<LovOptions>> optionsMap = new HashMap<String , List<LovOptions>>();

    public void setDocRenderUrl(String docRenderUrl) {
        this.docRenderUrl = docRenderUrl;
    }

    public String getDocRenderUrl() {
        return docRenderUrl;
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


    public void setOptionsMap(Map<String, List<LovOptions>> optionsMap) {
        this.optionsMap = optionsMap;
    }

    public Map<String, List<LovOptions>> getOptionsMap() {
        return optionsMap;
    }
}
