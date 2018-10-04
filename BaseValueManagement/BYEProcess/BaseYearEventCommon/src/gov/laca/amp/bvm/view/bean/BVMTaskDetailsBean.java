package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.bvm.util.BaseYearEventUtils;
import gov.laca.amp.fwk.extn.view.AmpManagedBean;
import gov.laca.amp.fwk.logger.AmpLogger;
import gov.laca.amp.fwk.util.ADFUtils;

import gov.laca.amp.fwk.util.JSFUtils;

import java.math.BigInteger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.component.rich.RichPanelWindow;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.util.ComponentReference;


/**
 * @author Vijay Redla
 * @version 2.0
 */
public class BVMTaskDetailsBean extends AmpManagedBean {
    @SuppressWarnings("compatibility:2435894330127160905")
    private static final long serialVersionUID = -8891141720069985902L;
    public static final AmpLogger LOGGER =
        new AmpLogger(BVMTaskDetailsBean.class);

    private ComponentReference reviewEventsTable;
    public BigInteger l_byeId = null;

    public BVMTaskDetailsBean() {
        super();
    }

    private static void ShowPopup(String popupId) {
        // get the UIComponent of the popup to show
        RichPopup popup = popup = (RichPopup)JSFUtils.findComponentInRoot(popupId);

        // Show the popup
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        ph.add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID, popup);
        popup.show(ph);
    }

    public void onCurrentEventRowSelection(SelectionEvent selectionEvent) {
        /* START PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        String expression =
            "#{bindings.filteredCurrentEventsList.collectionModel.makeCurrent}";
        makeCurrent(selectionEvent, expression);
        /* END PRESERVER DEFAULT ADF SELECT BEHAVIOR */

        RichTable eventsTbl = (RichTable)selectionEvent.getSource();
        BaseYearEventUtils.setSelectedRow(eventsTbl,
                                          "setSelectedCurrentEvent");
    }


    public void onHistoricalEventRowSelection(SelectionEvent selectionEvent) {
        /* START PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        String expression =
            "#{bindings.filteredHistoricalEventsList.collectionModel.makeCurrent}";
        makeCurrent(selectionEvent, expression);
        /* END PRESERVER DEFAULT ADF SELECT BEHAVIOR */

        //Custom Code to work with selection.
        RichTable eventsTbl = (RichTable)selectionEvent.getSource();
        BaseYearEventUtils.setSelectedRow(eventsTbl,
                                          "setSelectedHistoricalEvent");
    }

    private void makeCurrent(SelectionEvent selectionEvent,
                             String expression) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();
        MethodExpression mExpression =
            exprFactory.createMethodExpression(elCtx, expression, Object.class,
                                               new Class[] { SelectionEvent.class });
        mExpression.invoke(elCtx, new Object[] { selectionEvent });
    }

    public void reviewChangesHandler(ActionEvent actionEvent) {
        OperationBinding reviewAllChanges =
            ADFUtils.findOperation("reviewAllChanges");
        reviewAllChanges.execute();
        DCIteratorBinding reviewEventsListIterator =
            ADFUtils.findIterator("reviewEventsListIterator");
        reviewEventsListIterator.executeQuery();

        // Calculate the number of rows, and alter the height of the popup
        int firsRow = 0;
       // int contentWidth = 950;
        int contentWidth = 1400 ;  // Defect fix 3196
        int contentHeight = 170;
        for (int i = 0; i < reviewEventsListIterator.getViewObject().getEstimatedRowCount(); i++) {
            if (firsRow > 0) {
                contentHeight = contentHeight + 25;
    }
            firsRow++;
        }
        // Set the panel window dimensions for the height of the popup
        RichPanelWindow pw = (RichPanelWindow)JSFUtils.findComponentInRoot("reviewAllpw");
        pw.setContentHeight(contentHeight);
        pw.setContentWidth(contentWidth);
        ShowPopup("reviewAllChanges");

        AdfFacesContext.getCurrentInstance().addPartialTarget(getReviewEventsTable());
    }

    public void setReviewEventsTable(RichTable reviewEventsTable) {
        this.reviewEventsTable = ComponentReference.newUIComponentReference(reviewEventsTable);
    }

    public RichTable getReviewEventsTable() {
        if (reviewEventsTable != null) {
            return (RichTable)reviewEventsTable.getComponent();
        }
        return null;
    }

     public void setL_byeId(BigInteger l_byeId) {
        this.l_byeId = l_byeId;
    }

    public BigInteger getL_byeId() {
        l_byeId =(BigInteger)JSFUtils.getFromSession("byeId");
        return l_byeId;
    }
} 

