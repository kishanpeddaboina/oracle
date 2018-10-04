package gov.laca.amp.bvm.model.data;

import java.io.Serializable;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;

import utils.system;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigInteger eventName;
    private String eventType;
    private String eventSequence;
    private String eventId;
    private Date eventDate;

    private ArrayList<Year> yearList = new ArrayList<Year>();

    public Event() {
        super();
    }

    public void setEventName(BigInteger eventName) {
        this.eventName = eventName;
    }

    public BigInteger getEventName() {
        return eventName;
    }

    public void setYearList(ArrayList<Year> yearList) {
        this.yearList = yearList;
    }

    public ArrayList<Year> getYearList() {
        return yearList;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventSequence(String eventSequence) {
        this.eventSequence = eventSequence;
    }

    public String getEventSequence() {
        return eventSequence;
    }
    
}

