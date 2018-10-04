package view;

import gov.laca.amp.fwk.util.ADFUtils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class UIMainBean {
    public UIMainBean() {
    }

    public String Submit() {
        // Add event code here...
        System.out.println("HI");
        List<SelectItem> ainList = new ArrayList<SelectItem>();
        SelectItem selectObj = new SelectItem();
        
        selectObj.setValue("2004001009");
        ainList.add(selectObj);
        
        ADFUtils.setPageFlowValue("ainList", ainList);
        ADFUtils.setPageFlowValue("readOnlyMode", false);
        ADFUtils.setPageFlowValue("propsId", "2290659");
        ADFUtils.setPageFlowValue("parentId", "4454");
        ADFUtils.setPageFlowValue("propsId", "4454");
        ADFUtils.setPageFlowValue("category", "Exclusions");
        ADFUtils.setPageFlowValue("subcategory", "Prop58");
        ADFUtils.setPageFlowValue("WorkUnitId", "2711");
        ADFUtils.setPageFlowValue("viewLinks", true);
        ADFUtils.setPageFlowValue("wuId", "2900");
        ADFUtils.setPageFlowValue("maskData", true);
        ADFUtils.setPageFlowValue("showValuation", true);
                                   
                                              
        
        return "go";
    }
}
