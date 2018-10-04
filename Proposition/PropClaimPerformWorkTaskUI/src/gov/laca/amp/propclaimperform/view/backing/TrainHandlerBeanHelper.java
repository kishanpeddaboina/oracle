package gov.laca.amp.propclaimperform.view.backing;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;

public class TrainHandlerBeanHelper extends AmpManagedBean {
    @SuppressWarnings("compatibility:7141172637459113996")
    private static final long serialVersionUID = 286773763866893063L;
    private String selectedTrainStopOutcome = null;
    
    public TrainHandlerBeanHelper() {
        super();
    }

    public void setSelectedTrainStopOutcome(String selectedTrainStopOutcome) {
        this.selectedTrainStopOutcome = selectedTrainStopOutcome;
    }

    public String getSelectedTrainStopOutcome() {
        return selectedTrainStopOutcome;
    }
}
