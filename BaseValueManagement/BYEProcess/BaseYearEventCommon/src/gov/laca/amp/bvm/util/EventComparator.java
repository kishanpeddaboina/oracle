package gov.laca.amp.bvm.util;

import gov.laca.amp.bvm.model.data.Event;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;


public class EventComparator implements Comparator<Event>{
    
    @Override
    public int compare(Event e1, Event e2) {
        return new CompareToBuilder()
                .append(e2.getEventDate(), e1.getEventDate())
                .append(e2.getEventSequence(), e1.getEventSequence()).toComparison();
    }

}
