package gov.laca.amp.bvm.view.bean;

import gov.laca.amp.fwk.extn.view.AmpManagedBean;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.model.SelectItem;


public class LienRollYearOptions extends AmpManagedBean{

    @SuppressWarnings("compatibility:1696644828423462616")
    private static final long serialVersionUID = 1L;
    private List lienRollYears;
        
        public LienRollYearOptions() {
            lienRollYears = new CopyOnWriteArrayList();            
            for (int i=Calendar.getInstance().get(Calendar.YEAR) + 1; i >=1975 ; i--) {
                SelectItem rollYear = new  SelectItem(Integer.toString(i), Integer.toString(i));
                lienRollYears.add(rollYear);
            }           
        }


    public void setLienRollYears(List lienRollYears) {
        this.lienRollYears = lienRollYears;
    }

    public List getLienRollYears() {
        return lienRollYears;
    }
}
