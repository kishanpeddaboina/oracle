package gov.laca.amp.datacapturewccuiapp.view.beans;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.binding.BindingContainer;

public class AttrDataBean {
    public AttrDataBean() {
    }

    public String afterPopupulate() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        bindingContainer.findIteratorBinding("attrModelListIterator").executeQuery();
        return null;
    }
}
