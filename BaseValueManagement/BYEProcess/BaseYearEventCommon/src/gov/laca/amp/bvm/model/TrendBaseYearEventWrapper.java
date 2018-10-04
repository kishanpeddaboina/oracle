package gov.laca.amp.bvm.model;

import gov.laca.amp.bvm.AmpBaseYearEventException;
import gov.laca.amp.bvm.model.data.BaseYearEventDO;
import gov.laca.amp.bvm.model.data.Event;
import gov.laca.amp.bvm.model.data.Header;
import gov.laca.amp.bvm.model.data.Year;
import gov.laca.amp.bvm.util.EventComparator;
import gov.laca.amp.ent.util.JSONUtils;
import gov.laca.amp.fwk.logger.AmpLogger;

import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventResp;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.BaseYearEventRespList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseValueTotalList;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendBaseYearValueResponse;
import gov.lacounty.assessor.amp.data.bvm.trendbaseyearvalue.v1.TrendObjectList;

import java.math.BigInteger;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TrendBaseYearEventWrapper {
    public static final AmpLogger LOGGER = new AmpLogger(TrendBaseYearEventWrapper.class);

    public TrendBaseYearEventWrapper() {
        super();
    }

    public static void generateTrendBaseValues(long targetYear, String operationType, ArrayList<BaseYearEventDO> originalSelectedEventList,
                                               ArrayList<Header> eventHeaderList, ArrayList<Event> eventBodyList,
                                               ArrayList<Event> footerEventList) throws AmpBaseYearEventException {

        // set the format for the date and decimals
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.###");

        // initialize the variables for TBV
        TrendBaseValueList baseValueList = null;
        BigInteger eventId = null;
        Event event;
        Event footerTrendEvent = new Event();
        ArrayList<Year> yearList;
        ArrayList<Year> footerYearList;
        ArrayList<BaseYearEventDO> tbvSelectedEventsList = new ArrayList<BaseYearEventDO>();

        Year year;
        Year trendYear;
        BaseYearEventRulesWrapper rules = new BaseYearEventRulesWrapper();
        ArrayList<BaseYearEventDO> selectedTrendBaseYearEventsList = new ArrayList<BaseYearEventDO>();
        HashMap<String, Date> BYESequenceNumberMap = new HashMap<String, Date>();
        HashMap<String, ArrayList<String>> BYESequenceNumber = new HashMap<String, ArrayList<String>>();
        footerYearList = new ArrayList<Year>();

        HashSet<String> headerYearSet = new HashSet<String>();

        if (originalSelectedEventList.size() > 0) {
            tbvSelectedEventsList.clear();
            // create the selected event list
            for (BaseYearEventDO originalEventElement : originalSelectedEventList) {
                if (originalEventElement.getSelected().equals(Boolean.TRUE)) {
                    BaseYearEventDO copiedToBYE = new BaseYearEventDO();
                    copiedToBYE.copy(originalEventElement);
                    tbvSelectedEventsList.add(copiedToBYE);
                }
            }

            for (BaseYearEventDO eventElement : tbvSelectedEventsList) {
                ArrayList<String> eventInfo = new ArrayList<String>();
                eventElement.setTransactionType(operationType);
                selectedTrendBaseYearEventsList.add(eventElement);
                BYESequenceNumberMap.put(eventElement.getByeId().toString().trim(), eventElement.getEventDate());

                if (eventElement.getSequenceNumber() != null) {
                    eventInfo.add(eventElement.getSequenceNumber().toString().trim());
                }
                BYESequenceNumber.put(eventElement.getByeId().toString().trim(), eventInfo);
            }

            // execute the TBV based on the OPA rules, and get the lists based on the target year and selected events
            TrendBaseYearValueResponse baseYearValueResponse = rules.trendBaseYearValues(BigInteger.valueOf(targetYear), selectedTrendBaseYearEventsList);
            BaseYearEventRespList baseYearEventRespList = baseYearValueResponse.getBaseYearEventRespList();
            List<BaseYearEventResp> baseYearEventResp = baseYearEventRespList.getBaseYearEvent();
            // get the first item in the list from the response
            TrendObjectList trendObjectList = baseYearEventResp.get(0).getTrendObjectList();
            TrendBaseValueTotalList trendBaseValueTotalList = baseYearEventResp.get(0).getTrendBaseValueTotalList();

            for (TrendBaseValueTotalList.TrendBaseValueTotal tobj : trendBaseValueTotalList.getTrendBaseValueTotal()) {
                String yearValue = tobj.getYear().substring(0, tobj.getYear().indexOf(".")).trim();
                headerYearSet.add(yearValue.trim());
                String trendFactorValue = null;
                String totalLandValue = null;
                String totalFixturesValue = null;
                String totalImprovementsValue = null;
                String totalLandImprovementsValue = null;

                if (tobj.getTrendFactor() != null)
                    trendFactorValue = tobj.getTrendFactor().trim();
                if (tobj.getTotalLandValue().trim().length() > 0)
                    totalLandValue = decimalFormat.format(Double.parseDouble(tobj.getTotalLandValue().trim()));
                if (tobj.getTotalFixturesValue().trim().length() > 0)
                    totalFixturesValue = decimalFormat.format(Double.parseDouble(tobj.getTotalFixturesValue().trim()));
                if (tobj.getTotalImprovementsValue().trim().length() > 0)
                    totalImprovementsValue = decimalFormat.format(Double.parseDouble(tobj.getTotalImprovementsValue().trim()));
                if (tobj.getLandImprovementsTotal().trim().length() > 0)
                    totalLandImprovementsValue = decimalFormat.format(Double.parseDouble(tobj.getLandImprovementsTotal().trim()));

                Header header = new Header(yearValue, trendFactorValue);
                trendYear = new Year(yearValue, totalLandValue, totalImprovementsValue, totalFixturesValue, totalLandImprovementsValue);
                footerYearList.add(trendYear);
                eventHeaderList.add(header);
            }

            footerTrendEvent.setEventName(eventId);
            footerTrendEvent.setEventType("Total");
            Collections.sort(footerYearList);
            Collections.reverse(footerYearList);
            footerTrendEvent.setYearList(footerYearList);
            footerEventList.add(footerTrendEvent);

            for (TrendObjectList.TrendObject tobj : trendObjectList.getTrendObject()) {
                baseValueList = tobj.getTrendBaseValueList();
                event = new Event();
                yearList = new ArrayList<Year>();

                for (TrendBaseValueList.TrendBaseValue tbvListobj : baseValueList.getTrendBaseValue()) {
                    String landValue = null;
                    String impValue = null;
                    String fixValue = null;
                    String totalLandValue = null;
                    eventId = tbvListobj.getByeId();
                    String yearValue = tbvListobj.getYear().substring(0, tbvListobj.getYear().indexOf(".")).trim();
                    if (headerYearSet.contains(yearValue)) {

                        if (tbvListobj.getLandValue().trim().length() > 0)
                            landValue = decimalFormat.format(Double.parseDouble(tbvListobj.getLandValue().trim()));
                        if (tbvListobj.getImprovements().trim().length() > 0)
                            impValue = decimalFormat.format(Double.parseDouble(tbvListobj.getImprovements().trim()));
                        if (tbvListobj.getFixtures().trim().length() > 0)
                            fixValue = decimalFormat.format(Double.parseDouble(tbvListobj.getFixtures().trim()));
                        if (tbvListobj.getLandImprovementsTotal().trim().length() > 0)
                            totalLandValue = decimalFormat.format(Double.parseDouble(tbvListobj.getLandImprovementsTotal().trim()));

                        year = new Year(yearValue, landValue, impValue, fixValue, totalLandValue);
                        yearList.add(year);
                        event.setEventType(tbvListobj.getOperation());
                    }
                }

                Collections.sort(yearList);
                Collections.reverse(yearList);
                ArrayList<String> eventInfo = new ArrayList<String>();
                if (eventId != null) {
                    eventInfo = BYESequenceNumber.get(eventId.toString().trim());
                    event.setEventDate(BYESequenceNumberMap.get(eventId.toString().trim()));
                    if (eventInfo.size() == 1) {
                        event.setEventId(dateFormat.format(BYESequenceNumberMap.get(eventId.toString().trim())) + "-" + eventInfo.get(0));
                        event.setEventSequence(eventInfo.get(0));
                    } else {
                        event.setEventId(dateFormat.format(BYESequenceNumberMap.get(eventId.toString().trim())));
                    }
                }
                event.setYearList(yearList);
                eventBodyList.add(event);

                // This loop checks Trendevent list data with headelist year, if there is no year for that particular header year then we will set an emptydata in that year,
                // loop continues to check for all the header year in order to maintain consistency
                for (String headerYear : headerYearSet) {
                    for (Event eventItem : eventBodyList) {
                        boolean yearExists = Boolean.FALSE;
                        Year newYear = null;
                        for (Year yearItem : eventItem.getYearList()) {
                            if (yearItem.getTyear().equals(headerYear)) {
                                yearExists = Boolean.TRUE;
                                break;
                            }
                        }
                        if (!yearExists) {
                            newYear = new Year(headerYear, null, null, null, null);
                            eventItem.getYearList().add(newYear);
                        }
                        Collections.sort(eventItem.getYearList());
                        Collections.reverse(eventItem.getYearList());
                    }
                }
                Collections.sort(eventHeaderList);
                Collections.reverse(eventHeaderList);
                // sort the body collection by date and sequence descending order. Sequence of null will sort to the top
                Collections.sort(eventBodyList, new EventComparator());
            }
        }
    }

}
